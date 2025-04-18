package com.palo.trophyparser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.java.Log;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import javax.json.JsonWriter;

import org.apache.commons.codec.binary.StringUtils;

@Log
public class ExophaseGameParser {

  private static Map<String, String> JSOUP_HEADERS = Map.of("User-Agent", "PostmanRuntime/7.41.2");

  private File gameDir = null;
  private String logPrefix = "";
  private boolean alreadyParsed = false;
  private boolean doPolish = true;
  private HashMap<String, TrophyCounter> trophyCounts = new HashMap<String, TrophyCounter>();
  private HashMap<String, List<Trophy>> trophies = new HashMap<String, List<Trophy>>();
  private HashMap<String, Trophy> indexedTrophies = new HashMap<String, Trophy>();
  private Game theGame;

  public ExophaseGameParser() {
  }

  public void parseGame(Game game, int system) throws IOException {
    String url = null;
    String urlPl = null;
    if (game.isLocal()) {
      File gameDir = new File(game.getUrl());

      // Get all files in the game directory
      File[] files = gameDir.listFiles(File::isFile);
      if (files == null || files.length == 0) {
        System.out.println("No files found in: " + gameDir);
        return;
      }
      for (File file : files) {
        if (file.getName().contains(system == App.SYSTEM_PS_FLAG ? "Trophies - PS" : "- Xbox")) {
          url = file.getAbsolutePath();
        } else if (file.getName().contains("Trofea - PS")) {
          urlPl = file.getAbsolutePath();
        }
      }
    } else {
      url = game.getUrl();
    }
    if (null == url) {
      return;
    }
    Document d = game.isLocal() ? Jsoup.parse(new File(url), "UTF-8")
        : Jsoup.connect(url).headers(JSOUP_HEADERS).get();

    // Get game name from header
    String gameName = game.getName();// d.select("h3").first().text().split("â€º")[1];
    String shortGameName = game.getShortName();

    theGame = game;

    if (null == gameDir) {
      System.out.println(gameName + "(" + shortGameName + ")");
      gameDir = new File(shortGameName);
      if (Files.exists(gameDir.toPath())) {
        FileUtils.forceDelete(gameDir);
      }
      FileUtils.forceMkdir(gameDir);
    }

    // Figure out if Polish is available
    boolean hasPolish = false || (game.isLocal() && urlPl != null);
    System.out.println(hasPolish + " | " + urlPl);
    if (!game.isLocal()) {
      Elements languageElement = d.select(".languages");
      if (null != languageElement && !languageElement.isEmpty()) {
        List<Element> languages = languageElement.first().nextElementSibling().select("a");
        if (languages.size() > 0) {
          for (Element language : languages) {
            if (language.select("a").first().text().contains("Polish")) {
              hasPolish = true;
              break;
            }
          }
        }
      }
    }

    parseTrophies(d, shortGameName, system, false);

    if (hasPolish && doPolish) {
      d = game.isLocal() ? Jsoup.parse(new File(urlPl), "UTF-8")
          : Jsoup.connect(url + "pl").headers(JSOUP_HEADERS).get();
      parseTrophies(d, shortGameName, system, true);
      doPolish = false;
    }
    alreadyParsed = true;
  }

  private void parseTrophies(Document d, String shortGameName,
      int system, boolean checkPolish) throws MalformedURLException, IOException {
    if (checkPolish) {
      System.out.println(String.format(">> %s Sprawdzam polską listę", logPrefix));
    } else {
      System.out.println(String.format(">> %s Sprawdzam angielską listę", logPrefix));
    }

    List<Trophy> localTrophies = new LinkedList<Trophy>();
    Element awardsGlobal = d.select("div#awards").first();
    Elements dlcListings = awardsGlobal.select("h3.listing");
    Elements awardSections = awardsGlobal.select("ul.list-unordered-base");

    int order = 1;
    int dlcIndex = -1;
    String key = "base";
    TrophyCounter currentTrophyCounter = new TrophyCounter();
    for (Element awardSection : awardSections) {
      if (dlcIndex >= 0) {
        key = "dlc" + dlcIndex;
      }
      Elements awards = awardSection.select("li");

      System.out.println("Wykrywam <" + key + ">");

      for (Element award : awards) {
        // Get image
        String imageUrl = award.select("img.trophy-image").first().attr("src");
        String extension = imageUrl.substring(imageUrl.lastIndexOf("."));
        if (extension.contains("?")) {
          extension = extension.substring(0, extension.indexOf('?'));
        }
        String imageFileName
            =
            shortGameName + "_" + key + (App.SYSTEM_PS_FLAG == system ? "_trophy" : "_achievement")
                + String.format("%02d", order) + extension;
        File targetFile = new File(shortGameName + File.separator + imageFileName);
        try {
          FileUtils.copyURLToFile(new URL(imageUrl), targetFile);
        } catch (FileNotFoundException e) {
          System.out.println(String.format("!! Brak obrazka na serwerze : " + imageUrl));
        }

        // Get title
        String title = award.select("div.award-title > a").first().text();
        String description = award.select("div.award-description").first().text();
        boolean addTrophy = !alreadyParsed;
        if (!checkPolish) {
          Trophy t = null;
          if (App.SYSTEM_XBOX_FLAG == system) {
            String gamerscore = award.select("div.award-points > span").first().text();
            if (alreadyParsed) {
              t = indexedTrophies.get(title.trim().toLowerCase());
              if (null != t) {
                t.setGamerscore(gamerscore);
              } else {
                addTrophy = true;
              }
            }
            if (addTrophy) {
              t = new Trophy(order, title, description, imageFileName, gamerscore.trim());
              indexedTrophies.put(title.trim().toLowerCase(), t);
            }
          } else if (App.SYSTEM_PS_FLAG == system) {
            final Set<String> trophyColors = award.select("div.award-points").first().child(0)
                .classNames();
            final String[] trophyColorArray = trophyColors.stream()
                .filter(x -> x.startsWith("exo-icon-trophy-")).findFirst().get().split("-");
            final String trophyColor = trophyColorArray[trophyColorArray.length - 1];
            final TrophyColor color = TrophyColor.valueOf(trophyColor.toUpperCase());
            if (alreadyParsed) {
              t = indexedTrophies.get(title.trim().toLowerCase());
              if (null != t) {
                t.setColor(color);
              } else {
                addTrophy = true;
              }
            }
            if (addTrophy) {
              t = new Trophy(order, title, description, imageFileName, color);
              currentTrophyCounter.addCount(color);
              indexedTrophies.put(title.trim().toLowerCase(), t);
            }
          }
          if (addTrophy) {
            localTrophies.add(t);
            System.out.println(t.toString());
          }
        } else {
          Trophy t = trophies.get(key).get(order - 1);
          t.setPolishDescription(description);
          t.setPolishTitle(title);
        }
        order++;
      }
      order = 1;
      if (!checkPolish) {
        if (!alreadyParsed) {
          System.out
              .println(String.format("Tworzę %d trofeów dla <%s>", localTrophies.size(), key));
          trophies.put(key, localTrophies);
          trophyCounts.put(key, currentTrophyCounter);
        } else {
          System.out.println(String.format("Dodaję %d trofeów do <%s>", localTrophies.size(), key));
          if (localTrophies.size() > 0) {
            doPolish = true;
          }
          trophies.get(key).addAll(localTrophies);
        }
      }
      localTrophies = new LinkedList<Trophy>();

      if (dlcListings.size() == dlcIndex) {
        break;
      }
      key = "dlc" + dlcIndex++;
      currentTrophyCounter = new TrophyCounter();
    }
    if (!checkPolish) {
      if (trophies.size() == 0 && !alreadyParsed) {
        System.out.println(String.format("%s Nie znaleziono :(", logPrefix));
        System.exit(-1);
      }
    }
  }

  public void printTrophies(boolean providedByPublisher)
      throws IOException {
    for (String s : trophies.keySet()) {

      PrintWriter writer = new PrintWriter(gameDir.getPath() + File.separator + s + "_html.txt",
          "UTF-8");
      PrintWriter writerGoogle = new PrintWriter(
          gameDir.getPath() + File.separator + s + "_gdocs.txt", "UTF-8");

      FileWriter fileWriter = new FileWriter(gameDir.getPath() + File.separator + s + "_json.txt");

      writer.write(Header.getHtml(trophyCounts.get(s)));

      JsonObject jsonObject = Header.getJson(trophyCounts.get(s), theGame, trophies.get(s));

      Map<String, Object> properties = new HashMap<>(1);
      properties.put(JsonGenerator.PRETTY_PRINTING, true);

      JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
      JsonWriter jsonWriter = writerFactory.createWriter(fileWriter);
      jsonWriter.writeObject(jsonObject);

      fileWriter.close();
      jsonWriter.close();

      for (Trophy t : trophies.get(s)) {
        writer.write(t.printHtml());
        writer.write(System.lineSeparator());
        writer.write(System.lineSeparator());
        writerGoogle.write(t.printText());
        writerGoogle.write(System.lineSeparator());
        writerGoogle.write(System.lineSeparator());
      }
      writer.write(Footer.getHtml(providedByPublisher));
      writer.close();
      writerGoogle.close();
    }
  }

  public boolean isAlreadyParsed() {
    return alreadyParsed;
  }

  public String getGameDirString() {
    if (null != gameDir) {
      return gameDir.getName();
    } else {
      return "";
    }
  }

  public void applyEmptyGamerscore() {
    for (String s : trophies.keySet()) {
      for (Trophy t : trophies.get(s)) {
        t.setGamerscore("");
      }
    }
  }

}

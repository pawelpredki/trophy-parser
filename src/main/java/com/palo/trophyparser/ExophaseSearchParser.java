package com.palo.trophyparser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExophaseSearchParser {

  private String searchTerm;
  private int system;

  public ExophaseSearchParser(String searchTerm, int system) throws UnsupportedEncodingException {
    this.searchTerm = URLEncoder.encode(searchTerm.toLowerCase(), "UTF-8");
    this.system = system;
  }

  public List<Game> search() throws IOException {
    List<Game> games = new LinkedList<Game>();
    int index = 1;

    Document d = Jsoup.connect(App.EXOPHASE_DOMAIN + "/search?s=" + this.searchTerm).get();
    Element main = d.select("ul.list-unordered-base").first();
    if (null == main) {
      System.out.println("MAIN NOT FOUND");
      return games;
    }
    String urlSuffix = "";
    Set<String> platformFilter = new HashSet<>();
    switch (this.system) {
      case App.SYSTEM_XBOX_FLAG:
        urlSuffix = "achievements";
        platformFilter.add("xbox");
        break;
      case App.SYSTEM_PS_FLAG:
        urlSuffix = "trophies";
        platformFilter.add("ps");
        platformFilter.add("vita");
        break;
      default:
        return games;
    }
    Elements gameRows = main.select("li");
    for (Element row : gameRows) {
      Element urlCell = row.select("a").first();
      String gameUrl = urlCell.attr("href");
      Element titleCell = row.select("div").first().select("h3").first().select("a").first();
      String gameTitle = titleCell.text();
      Elements platformCells = row.select("div.inline-pf");
      String gamePlatform = "";
      for (Element platformCell : platformCells) {
        if (!gamePlatform.isEmpty()) {
          gamePlatform += ", ";
        }
        gamePlatform += platformCell.text();
      }
      final String lowerGamePlatform = gamePlatform.toLowerCase();
      boolean found = platformFilter.stream().anyMatch(x -> lowerGamePlatform.contains(x));
      if (found) {
        System.out.println(index++ + " : " + gameTitle + " | " + gamePlatform);
        Game g = new Game(gameTitle, gamePlatform, gameUrl);
        games.add(g);
      }
    }
    return games;
  }

}

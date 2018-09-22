package com.palo.trophyparser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ExophaseGameParser {

	private Game game;
	private int system;
	private String logPrefix = "";
	private HashMap<String, TrophyCounter> trophyCounts = new HashMap<String, TrophyCounter>();

	public ExophaseGameParser(Game game, int system) {
		this.game = game;
		this.system = system;
		switch (system) {
		case App.SYSTEM_PS_FLAG:
			logPrefix = "[PlayStation]";
			break;
		case App.SYSTEM_XBOX_FLAG:
			logPrefix = "[Xbox";
			break;
		default:
			break;
		}
	}

	public void parseGame() throws IOException {
		String url = game.getUrl();// + "?lang=pl";
		Document d = Jsoup.connect(url).get();

		// Get game name from header
		String gameName = game.getName();// d.select("h3").first().text().split("›")[1];
		String shortGameName = game.getShortName();

		System.out.println(gameName + "(" + shortGameName + ")");
		File gameDir = new File(shortGameName);
		if (Files.exists(gameDir.toPath())) {
			FileUtils.forceDelete(gameDir);
		}
		FileUtils.forceMkdir(gameDir);

		// Figure out if Polish is available
		boolean hasPolish = false;
		List<Element> languages = d.select("div.col.btn-group.p-0").first().select("ul").first().select("li");
		if (languages.size() > 0) {
			for (Element language : languages) {
				if (language.select("a").first().text().contains("Polish")) {
					hasPolish = true;
					break;
				}
			}
		}

		HashMap<String, List<Trophy>> trophies = parseTrophies(d, shortGameName, null);

		if (hasPolish) {
			d = Jsoup.connect(url + "/pl").get();
			parseTrophies(d, shortGameName, trophies);

		}
		for (String s : trophies.keySet()) {

			PrintWriter writer = new PrintWriter(gameDir.getPath() + File.separator + s + "_html.txt", "UTF-8");
			PrintWriter writerGoogle = new PrintWriter(gameDir.getPath() + File.separator + s + "_gdocs.txt", "UTF-8");

			writer.write(Header.getHtml(trophyCounts.get(s)));

			for (Trophy t : trophies.get(s)) {
				writer.write(t.printHtml());
				writer.write(System.lineSeparator());
				writer.write(System.lineSeparator());
				writerGoogle.write(t.printText());
				writerGoogle.write(System.lineSeparator());
				writerGoogle.write(System.lineSeparator());
			}
			writer.close();
			writerGoogle.close();
		}
	}

	private HashMap<String, List<Trophy>> parseTrophies(Document d, String shortGameName,
			HashMap<String, List<Trophy>> englishTrophies) throws MalformedURLException, IOException {
		if (null != englishTrophies) {
			System.out.println(String.format(">> %s Sprawdzam polską listę", logPrefix));
		} else {
			System.out.println(String.format(">> %s Sprawdzam angielską listę", logPrefix));
		}

		HashMap<String, List<Trophy>> allTrophies = new HashMap<String, List<Trophy>>();
		List<Trophy> trophies = new LinkedList<Trophy>();
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
				String imageFileName = shortGameName + "_" + key + "_trophy" + String.format("%02d", order) + extension;
				File targetFile = new File(shortGameName + File.separator + imageFileName);
				FileUtils.copyURLToFile(new URL(imageUrl), targetFile);

				// Get title
				String title = award.select("div.trophy-title > a").first().text();
				String description = award.select("div.trophy-desc").first().text();
				if (null == englishTrophies) {
					Trophy t = null;
					if (App.SYSTEM_XBOX_FLAG == system) {
						String gamerscore = award.select("div.gamerscore").first().text();
						t = new Trophy(order, title, description, imageFileName,
								gamerscore.substring(gamerscore.indexOf(" ")).trim());
					} else if (App.SYSTEM_PS_FLAG == system) {
						String trophyColor = award.select("span.trophy").first().attr("title");
						TrophyColor color = TrophyColor.valueOf(trophyColor.toUpperCase());
						t = new Trophy(order, title, description, imageFileName, color);
						currentTrophyCounter.addCount(color);
					}
					trophies.add(t);
					System.out.println(t.toString());
				} else {
					Trophy t = englishTrophies.get(key).get(order - 1);
					t.setPolishDescription(description);
					t.setPolishTitle(title);
				}
				order++;
			}
			order = 1;
			allTrophies.put(key, trophies);
			trophies = new LinkedList<Trophy>();
			if (null == englishTrophies) {
				trophyCounts.put(key, currentTrophyCounter);
			}
			if (dlcListings.size() == dlcIndex) {
				break;
			}
			key = "dlc" + dlcIndex++;
			currentTrophyCounter = new TrophyCounter();
		}
		if (null == englishTrophies) {
			if (allTrophies.size() == 0) {
				System.out.println(String.format("%s Nie znaleziono :(", logPrefix));
				System.exit(-1);
			}
			return allTrophies;
		} else {
			return englishTrophies;
		}
	}
}

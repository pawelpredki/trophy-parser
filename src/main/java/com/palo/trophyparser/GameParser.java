package com.palo.trophyparser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GameParser {

	private Game game;
	private int platCount = 0, goldCount = 0, silverCount = 0, bronzeCount = 0;

	public GameParser(Game game) {
		this.game = game;
	}

	public void parseGame() throws IOException {
		String url = game.getUrl();// + "?lang=pl";
		Document d = Jsoup.connect(url).get();

		// Get game name from header
		String gameName = d.select("h3").first().text().split("›")[1];
		String shortGameName = game.getShortName();

		System.out.println(gameName + "(" + shortGameName + ")");
		File gameDir = new File(shortGameName);
		if (Files.exists(gameDir.toPath())) {
			FileUtils.forceDelete(gameDir);
		}
		FileUtils.forceMkdir(gameDir);

		// Figure out if Polish is available
		boolean hasPolish = false;
		List<Element> languages = d.select("a.language");
		if (languages.size() > 0) {
			List<Element> languagesMenu = languages.get(0).nextElementSibling().select("a[href]");
			for (Element language : languagesMenu) {
				if (language.text().equalsIgnoreCase("Polish")) {
					hasPolish = true;
					break;
				}
			}
		}

		HashMap<String, List<Trophy>> trophies = parseTrophies(d, shortGameName, null);

		if (hasPolish) {
			d = Jsoup.connect(url + "?lang=pl").get();
			parseTrophies(d, shortGameName, trophies);

		}
		for (String s : trophies.keySet()) {

			PrintWriter writer = new PrintWriter(gameDir.getPath() + File.separator + s + "_html.txt", "UTF-8");
			PrintWriter writerGoogle = new PrintWriter(gameDir.getPath() + File.separator + s + "_gdocs.txt", "UTF-8");

			writer.write(Header.getHtml(platCount, goldCount, silverCount, bronzeCount));

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
			System.out.println(">> Polskie Trofea");
		} else {
			System.out.println(">> Angielskie Trofea");
		}

		HashMap<String, List<Trophy>> allTrophies = new HashMap<String, List<Trophy>>();
		List<Trophy> trophies = new LinkedList<Trophy>();
		Elements noTopBorderDivs = d.select("div.no-top-border");
		int gameWithDlcCount = 0;
		int gameWithDlcTotal = noTopBorderDivs.size() - 1;
		System.out.println("Gra + DLC: " + gameWithDlcTotal);
		Elements zebraTables = d.select("table.zebra");
		Elements trophyRows = null;
		Iterator<Element> zebraIterator = zebraTables.iterator();

		int order = 1;
		String key = "base";
		while (zebraIterator.hasNext()) {
			Element zebra = zebraIterator.next();
			if (zebra.hasAttr("style")) {
				continue;
			}
			Elements tbodies = zebra.select("tbody");
			if (tbodies.size() > 0) {
				Element tbody = tbodies.first();
				trophyRows = tbody.select("tr[class]");
				System.out.println("Wykrywam trofea <" + key + ">");
			} else {
				continue;
			}

			Iterator<Element> rowsIterator = trophyRows.iterator();
			while (rowsIterator.hasNext()) {
				Element row = rowsIterator.next();
				if (row.childNodeSize() == 0) {
					continue;
				}
				Element titleHref = row.select("a.title").first();
				if (null == titleHref) {
					break;
				}
				Element imageElement = row.select("img[src]").first();
				String imageUrl = imageElement.attr("src");
				String extension = imageUrl.substring(imageUrl.lastIndexOf("."));
				String imageFileName = shortGameName + "_" + key + "_trophy" + String.format("%02d", order) + extension;
				File targetFile = new File(shortGameName + File.separator + imageFileName);
				FileUtils.copyURLToFile(new URL(imageUrl), targetFile);
				Element titleAndDescriptionElement = row.select("td[style]").first();
				String title = titleAndDescriptionElement.select("a[href]").text();
				String description = titleAndDescriptionElement.ownText();
				if (null == englishTrophies) {
					TrophyColor color = TrophyColor.valueOf(
							row.select("td[style]").last().select("img[title]").first().attr("title").toUpperCase());
					Trophy t = new Trophy(order, title, description, imageFileName, color);
					switch (color) {
					case BRONZE:
						bronzeCount++;
						break;
					case SILVER:
						silverCount++;
						break;
					case GOLD:
						goldCount++;
						break;
					case PLATINUM:
						platCount++;
						break;
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
			if (++gameWithDlcCount == gameWithDlcTotal) {
				break;
			}
			key = "dlc" + gameWithDlcCount;

		}
		// }
		if (null == englishTrophies) {
			if (allTrophies.size() == 0) {
				System.out.println("Nie znaleziono trofeów :(");
				System.exit(-1);
			}
			return allTrophies;
		} else {
			return englishTrophies;
		}
	}

}

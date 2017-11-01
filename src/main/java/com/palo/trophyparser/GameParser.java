package com.palo.trophyparser;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
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
		List<Element> languagesMenu = d.select("a.language").first().nextElementSibling().select("a[href]");
		for (Element language : languagesMenu) {
			if (language.text().equalsIgnoreCase("Polish")) {
				hasPolish = true;
				break;
			}
		}

		List<Trophy> trophies = parseTrophies(d, shortGameName, null);

		if (hasPolish) {
			d = Jsoup.connect(url + "?lang=pl").get();
			parseTrophies(d, shortGameName, trophies);
		}

		PrintWriter writer = new PrintWriter(gameDir.getPath() + File.separator + "html.txt", "UTF-8");

		writer.write(Header.getHtml(platCount, goldCount, silverCount, bronzeCount));

		for (Trophy t : trophies) {
			writer.write(t.printHtml());
			writer.write(System.lineSeparator());
			writer.write(System.lineSeparator());
		}
		writer.close();
	}

	private List<Trophy> parseTrophies(Document d, String shortGameName, List<Trophy> englishTrophies)
			throws MalformedURLException, IOException {
		List<Trophy> trophies = new LinkedList<Trophy>();
		int order = 1;
		Elements trophyRows = d.select("table.zebra").first().select("tbody").first().select("tr");
		Iterator<Element> rowsIterator = trophyRows.iterator();
		while (rowsIterator.hasNext()) {
			Element row = rowsIterator.next();
			String imageUrl = row.select("img[src]").first().attr("src");
			String extension = imageUrl.substring(imageUrl.lastIndexOf("."));
			String imageFileName = shortGameName + "_trophy" + String.format("%02d", order) + extension;
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
			} else {
				Trophy t = englishTrophies.get(order - 1);
				t.setPolishDescription(description);
				t.setPolishTitle(title);

			}
			order++;
		}
		if (null == englishTrophies) {
			return trophies;
		} else {
			return englishTrophies;
		}
	}

}

package com.palo.trophyparser;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SearchParser {

	private String searchTerm;

	public SearchParser(String searchTerm) throws UnsupportedEncodingException {
		this.searchTerm = URLEncoder.encode(searchTerm.toLowerCase(), "UTF-8");
	}

	public List<Game> search() throws IOException {
		String searchUrl = App.PSN_PROFILES_DOMAIN + "/search/games?q=" + searchTerm;
		List<Game> games = new LinkedList<Game>();
		int index = 1;
		Document d = Jsoup.connect(searchUrl).get();
		Element zebra = d.select("table.zebra").first();
		if (null == zebra) {
			return games;
		}
		Element tbody = zebra.select("tbody").first();
		Elements gameRows = tbody.select("tr");
		for (Element row : gameRows) {
			Element infoCell = row.select("td").get(1);
			Element a = infoCell.select("a").first();
			String gameUrl = App.PSN_PROFILES_DOMAIN + a.attr("href");
			String gameTitle = a.text();
			String gamePlatform = infoCell.select("span.platform").first().text();
			System.out.println(index++ + " : " + gameTitle + " | " + gamePlatform);
			Game g = new Game(gameTitle, gamePlatform, gameUrl);
			games.add(g);
		}
		return games;
	}

}

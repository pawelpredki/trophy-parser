package com.palo.trophyparser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App {

	public static final String PSN_PROFILES_DOMAIN = "https://psnprofiles.com";

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("Wpisz nazwę gry do wyszukania:");
		System.out.print("> ");
		String input = br.readLine();
		System.out.println();
		SearchParser sp = new SearchParser(input);
		List<Game> games = sp.search();

		if (games.size() == 0) {
			System.out.println("Brak gier o podanym tytule :(");
			System.exit(0);
		}

		System.out.println();
		System.out.println("Wybierz numer gry: (1 - " + games.size() + ")");
		System.out.print("> ");
		input = br.readLine();
		int index = Integer.parseInt(input) - 1;

		Game g = games.get(index);

		GameParser parser = new GameParser(g);
		parser.parseGame();

		System.out.println();
		System.out.println("Obrazk i HTML do wklejenia w WP znajdziesz w folderze /" + g.getShortName());

	}
}

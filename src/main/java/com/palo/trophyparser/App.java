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

	public static final String EXOPHASE_DOMAIN = "https://www.exophase.com";
  public static final String EXOPHASE_API_ROOT = "https://api.exophase.com/public";

	public static final int SYSTEM_XBOX_FLAG = (1 << 0);
	public static final int SYSTEM_PS_FLAG = (1 << 1);

	public static void main(String[] args) throws IOException {
		boolean theEnd = false;
		boolean providedByPublisher = false;
		while (!theEnd) {

			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Wpisz nazwę gry do wyszukania:");
			System.out.print("> ");
			String searchInput = br.readLine();
			String input = null;
			System.out.println();

			ExophaseGameParser parser = new ExophaseGameParser();

			System.out.println("--------- [PlayStation] ---------");
			ExophaseSearchParser sp = new ExophaseSearchParser(searchInput, SYSTEM_PS_FLAG);
			List<Game> games = sp.search();

			if (games.size() == 0) {
				System.out.println("Brak gier o podanym tytule :(");
			} else {
				System.out.println("0 : Pomiń platformę");
				System.out.println();
				System.out.println("[PlayStation] Wybierz numer gry: (0 - " + games.size() + ")");
				System.out.print("> ");
				input = br.readLine();
				int index = Integer.parseInt(input) - 1;

				if (index >= 0) {
					Game playstationGame = games.get(index);
					parser.parseGame(playstationGame, SYSTEM_PS_FLAG);
				}

			}

			System.out.println("--------- [XBox] ---------");

			sp = new ExophaseSearchParser(searchInput, SYSTEM_XBOX_FLAG);
			games = sp.search();

			if (games.size() == 0) {
				System.out.println("[Xbox] Brak gier o podanym tytule :(");
			} else {

				System.out.println("0 : Pomiń platformę");
				System.out.println();
				System.out.println("[Xbox] Wybierz numer gry: (0 - " + games.size() + ")");
				System.out.print("> ");
				input = br.readLine();
				int index = Integer.parseInt(input) - 1;

				if (index >= 0) {
					Game xboxGame = games.get(index);
					parser.parseGame(xboxGame, SYSTEM_XBOX_FLAG);
				}
				if (-1 == index) {
					input = "";
					while (!(input.equalsIgnoreCase("t") || input.equalsIgnoreCase("n"))) {
						System.out.println("Dodać ikonki gamerscore do nazw trofeów [T/N] ?");
						System.out.print("> ");
						input = br.readLine();
					}
					if (input.equalsIgnoreCase("t")) {
						parser.applyEmptyGamerscore();
					}
				}
			}

			// Ask if game was provided by publisher
      while (!(input.equalsIgnoreCase("t") || input.equalsIgnoreCase("n"))) {
        System.out.println();
        System.out.println("Czy grę udostępnił wydawca [T/N] ?");
        System.out.print("> ");
        input = br.readLine();
      }
      if (input.equalsIgnoreCase("t")) {
        providedByPublisher = true;
      } else {
        providedByPublisher = false;
      }

			if (parser.isAlreadyParsed()) {
				parser.printTrophies(providedByPublisher);
				System.out.println();
				System.out.println(
						"Obrazki i HTML do wklejenia w WP znajdziesz w folderze /" + parser.getGameDirString());
			} else {
				System.out.println();
				System.out.println("Nic nie zrobiłem :) Spróbuj ponownie.");
			}
			
			System.out.println();
			while (true) {
				System.out.println("Jedziemy dalej [T/N] ?");
				System.out.print("> ");
				input = br.readLine();
				if (input.equalsIgnoreCase("n")) {
					theEnd = true;
					break;
				} else if (input.equalsIgnoreCase("t")) {
					System.out.println();
					break;
				}
			}
		}
		System.out.println("------------------------");
		System.out.println("---- Do zobaczenia! ----");
		System.out.println("------------------------");
	}
}

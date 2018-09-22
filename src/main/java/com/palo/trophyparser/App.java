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
	public static final String EXOPHASE_DOMAIN = "https://www.exophase.com";

	public static final int SYSTEM_XBOX_FLAG = (1 << 0);
	public static final int SYSTEM_PS_FLAG = (1 << 1);

	public static void main(String[] args) throws IOException {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		final String allowedSystems[] = { "Xbox", "PlayStation" };
		boolean systemOk = false;
		int systemChoice = 0;
		while (!systemOk) {
			System.out.println("Wybierz system:");
			int systemIndex = 1;
			for (String s : allowedSystems) {
				System.out.println(String.format("%d. %s", systemIndex++, s));
			}
			System.out.print("> ");
			String input = br.readLine();
			System.out.println();
			try {
				Integer i = Integer.parseInt(input);
				if (!(i < 1 || i > allowedSystems.length)) {
					switch (i) {
					case 1:
						systemChoice = SYSTEM_XBOX_FLAG;
						break;
					case 2:
						systemChoice = SYSTEM_PS_FLAG;
						break;
					default:
						systemChoice = SYSTEM_XBOX_FLAG | SYSTEM_PS_FLAG;
						break;
					}
					systemOk = true;
				}
			} catch (NumberFormatException e) {

			}
		}

		System.out.println("Wpisz nazwę gry do wyszukania:");
		System.out.print("> ");
		String input = br.readLine();
		System.out.println();

		if ((systemChoice & SYSTEM_PS_FLAG) > 0) {
			ExophaseSearchParser sp = new ExophaseSearchParser(input, SYSTEM_PS_FLAG);
			List<Game> games = sp.search();

			if (games.size() == 0) {
				System.out.println("[PlayStation] Brak gier o podanym tytule :(");
				System.exit(0);
			}

			System.out.println();
			System.out.println("[PlayStation] Wybierz numer gry: (1 - " + games.size() + ")");
			System.out.print("> ");
			input = br.readLine();
			int index = Integer.parseInt(input) - 1;

			Game g = games.get(index);

			ExophaseGameParser parser = new ExophaseGameParser(g, SYSTEM_PS_FLAG);
			parser.parseGame();

			System.out.println();
			System.out.println("Obrazki i HTML do wklejenia w WP znajdziesz w folderze /" + g.getShortName());
		}
		if ((systemChoice & SYSTEM_XBOX_FLAG) > 0) {
			ExophaseSearchParser sp = new ExophaseSearchParser(input, SYSTEM_XBOX_FLAG);
			List<Game> games = sp.search();

			if (games.size() == 0) {
				System.out.println("[Xbox] Brak gier o podanym tytule :(");
				System.exit(0);
			}

			System.out.println();
			System.out.println("[Xbox] Wybierz numer gry: (1 - " + games.size() + ")");
			System.out.print("> ");
			input = br.readLine();
			int index = Integer.parseInt(input) - 1;

			Game g = games.get(index);

			ExophaseGameParser parser = new ExophaseGameParser(g, SYSTEM_XBOX_FLAG);
			parser.parseGame();

			System.out.println();
			System.out.println("Obrazki i HTML do wklejenia w WP znajdziesz w folderze /" + g.getShortName());
		}

	}
}

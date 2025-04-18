package com.palo.trophyparser;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Game {
	private String name;
	private String console;
	private String url;
	private boolean local;

	public Game(String name, String console, String url) {
		super();
		this.name = name;
		this.console = console;
		this.url = url;
	}

	public String getShortName() {
		return name.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
	}

}

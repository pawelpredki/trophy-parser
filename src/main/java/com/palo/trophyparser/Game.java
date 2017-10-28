package com.palo.trophyparser;

public class Game {
	private String name;
	private String console;
	private String url;

	public Game(String name, String console, String url) {
		super();
		this.name = name;
		this.console = console;
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConsole() {
		return console;
	}

	public void setConsole(String console) {
		this.console = console;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getShortName() {
		return name.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
	}

}

package com.palo.trophyparser;

public enum TrophyColor {
	BRONZE("#ff6600"), SILVER("#808080"), GOLD("#f0c311"), PLATINUM("#00ccff");
	
	private String colorString;

	private TrophyColor(String colorString) {
		this.colorString = colorString;
	}

	public String getColorString() {
		return colorString;
	}

	public void setColorString(String colorString) {
		this.colorString = colorString;
	}
	
	
}

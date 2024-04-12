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

	public String getColorTranslation(String str){
		if(str.equals("#ff6600")){
			return "Brąz";
		}else if(str.equals("#808080")){
			return "Srebro";
		}else if(str.equals("#f0c311")){
			return "Złoty";
		}else if(str.equals("#00ccff")){
			return "Platyna";
		}else{
			return str;
		}
	}

	public void setColorString(String colorString) {
		this.colorString = colorString;
	}

	public String getColorImageHtml() {
		StringBuilder html = new StringBuilder();
		switch (this) {
		case BRONZE:
			html.append(
					"<img class=\"inlineimg size-full wp-image-345\" src=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/braz.png\" alt=\"braz\" width=\"16\" height=\"16\" />");
			break;
		case GOLD:
			html.append(
					"<img class=\"inlineimg size-full wp-image-345\" src=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/zloto.png\" alt=\"zloto\" width=\"16\" height=\"16\" />");
			break;
		case PLATINUM:
			html.append(
					"<img class=\"inlineimg size-full wp-image-345\" src=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/platyna.png\" alt=\"platyna\" width=\"16\" height=\"16\" />");
			break;
		case SILVER:
			html.append(
					"<img class=\"inlineimg size-full wp-image-345\" src=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/srebro.png\" alt=\"srebro\" width=\"16\" height=\"16\" />");
			break;
		default:
			break;

		}
		return html.toString();
	}

}

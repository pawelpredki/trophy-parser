package com.palo.trophyparser;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trophy {

	private static String WP_CONTENT_UPLOAD_URL = "https://www.lowcytrofeow.pl/wp-content/uploads/";
	private static DateFormat WP_CONTENT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/");

	private int order;
	private String title;
	private String polishTitle = null;
	private String description;
	private String polishDescription = null;
	private String imageFileName;
	private TrophyColor color = null;
	private String gamerscore = null;

	public Trophy(int order, String title, String description, String imageFileName, TrophyColor color) {
		super();
		this.order = order;
		this.title = title;
		this.description = description;
		this.imageFileName = imageFileName;
		this.color = color;
	}

	public Trophy(int order, String title, String description, String imageFileName, String gamerscore) {
		super();
		this.order = order;
		this.title = title;
		this.description = description;
		this.imageFileName = imageFileName;
		this.gamerscore = gamerscore;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPolishTitle() {
		return polishTitle;
	}

	public void setPolishTitle(String polishTitle) {
		this.polishTitle = polishTitle;
	}

	public String getPolishDescription() {
		return polishDescription;
	}

	public void setPolishDescription(String polishDescription) {
		this.polishDescription = polishDescription;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public TrophyColor getColor() {
		return color;
	}

	public void setColor(TrophyColor color) {
		this.color = color;
	}

	public String getGamerscore() {
		return gamerscore;
	}

	public void setGamerscore(String gamerscore) {
		this.gamerscore = gamerscore;
	}

	public String printHtml() {
		StringBuilder htmlBuilder = new StringBuilder();

		htmlBuilder.append("<strong><a href=\"");
		htmlBuilder.append(WP_CONTENT_UPLOAD_URL);
		htmlBuilder.append(WP_CONTENT_DATE_FORMAT.format(new Date()));
		htmlBuilder.append(getImageFileName());
		htmlBuilder.append("\"><img class=\"inlineimg size-full wp-image-11317\" src=\"");
		htmlBuilder.append(WP_CONTENT_UPLOAD_URL);
		htmlBuilder.append(WP_CONTENT_DATE_FORMAT.format(new Date()));
		htmlBuilder.append(getImageFileName());
		htmlBuilder.append("\" alt=\"\" width=\"56\" height=\"56\" /></a> ");
		if (null != this.getColor()) {
			htmlBuilder.append(getColor().getColorImageHtml());
		}
		htmlBuilder.append(" ");
		if (null != polishTitle) {
			htmlBuilder.append(getPolishTitle());
			htmlBuilder.append(" / ");
		}
		htmlBuilder.append(getTitle());
		if (null != this.gamerscore) {
			htmlBuilder.append(String.format(
					" <span style=\"color: #339966;\">%s <a href=\"https://lowcytrofeow.pl/wp-content/uploads/2018/09/Xbox_gamerscore-lowcy.png\"><img class=\"inlineimg wp-image-9297\" src=\"https://lowcytrofeow.pl/wp-content/uploads/2018/09/Xbox_gamerscore-lowcy.png\" alt=\"\" width=\"15\" height=\"15\" /></a></span>",
					this.gamerscore));
		}
		htmlBuilder.append("</strong>");
		htmlBuilder.append(System.lineSeparator());
		htmlBuilder.append("<span style=\"text-decoration: underline;\">");
		if (null != polishDescription) {
			htmlBuilder.append(getPolishDescription());
			htmlBuilder.append(" / ");
		}
		htmlBuilder.append(getDescription());
		htmlBuilder.append("</span>");
		htmlBuilder.append(System.lineSeparator());
		htmlBuilder.append("<br><br>");
		htmlBuilder.append(System.lineSeparator());
		return htmlBuilder.toString();
	}

	public String printText() {
		StringBuilder textBuilder = new StringBuilder();

		if (null != polishTitle) {
			textBuilder.append(getPolishTitle());
			textBuilder.append(" / ");
		}
		textBuilder.append(getTitle());
		textBuilder.append(System.lineSeparator());
		if (null != polishDescription) {
			textBuilder.append(getPolishDescription());
			textBuilder.append(" / ");
		}
		textBuilder.append(getDescription());
		textBuilder.append(System.lineSeparator());
		return textBuilder.toString();
	}

	public JsonObject printJson(){
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();

		if(polishTitle != null){
			jsonBuilder.add("polska_nazwa_osiagniecia", polishTitle);
		}
		jsonBuilder.add("angielska_nazwa_osiagniecia", title);
		if(polishDescription != null){
			jsonBuilder.add("polski_podpis_osiagniecia", polishDescription);
		}
		jsonBuilder.add("angielski_podpis_osiagniecia", description);
		jsonBuilder.add("ikona_osiagniecia", getImageFileName());
		if(color != null){
			jsonBuilder.add(color.name().toLowerCase(), color.getColorTranslation(color.getColorString()));
		}

		return jsonBuilder.build();
	}

	@Override
	public String toString() {
		return "Trophy [order=" + order + ", " + (title != null ? "title=" + title : "") + "]";
	}

}

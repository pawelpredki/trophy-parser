package com.palo.trophyparser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Trophy {

	private static String WP_CONTENT_UPLOAD_URL = "https://www.lowczynitrofeow.pl/wp-content/uploads/";
	private static DateFormat WP_CONTENT_DATE_FORMAT = new SimpleDateFormat("yyyy/MM/");

	private int order;
	private String title;
	private String description;
	private String imageFileName;
	private TrophyColor color;

	public Trophy(int order, String title, String description, String imageFileName, TrophyColor color) {
		super();
		this.order = order;
		this.title = title;
		this.description = description;
		this.imageFileName = imageFileName;
		this.color = color;
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

	public String printHtml() {
		StringBuilder htmlBuilder = new StringBuilder();

		htmlBuilder.append("<span style=\"color: ");
		htmlBuilder.append(getColor().getColorString());
		htmlBuilder.append(";\"><strong><a href=\"");
		htmlBuilder.append(WP_CONTENT_UPLOAD_URL);
		htmlBuilder.append(WP_CONTENT_DATE_FORMAT.format(new Date()));
		htmlBuilder.append(getImageFileName());
		htmlBuilder.append("\"><img class=\"alignnone size-full wp-image-11317\" src=\"");
		htmlBuilder.append(WP_CONTENT_UPLOAD_URL);
		htmlBuilder.append(WP_CONTENT_DATE_FORMAT.format(new Date()));
		htmlBuilder.append(getImageFileName());
		htmlBuilder.append("\" alt=\"\" width=\"56\" height=\"56\" /></a> ");
		htmlBuilder.append(getTitle());
		htmlBuilder.append("</strong></span>");
		htmlBuilder.append(System.lineSeparator());
		htmlBuilder.append("<span style=\"text-decoration: underline;\">");
		htmlBuilder.append(getDescription());
		htmlBuilder.append("</span>");
		return htmlBuilder.toString();
	}

}

package com.palo.trophyparser;

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

	public String printHtml() {
		StringBuilder htmlBuilder = new StringBuilder();

		htmlBuilder.append("<strong><a href=\"");
		htmlBuilder.append(WP_CONTENT_UPLOAD_URL);
		htmlBuilder.append(WP_CONTENT_DATE_FORMAT.format(new Date()));
		htmlBuilder.append(getImageFileName());
		htmlBuilder.append("\"><img class=\"alignnone size-full wp-image-11317\" src=\"");
		htmlBuilder.append(WP_CONTENT_UPLOAD_URL);
		htmlBuilder.append(WP_CONTENT_DATE_FORMAT.format(new Date()));
		htmlBuilder.append(getImageFileName());
		htmlBuilder.append("\" alt=\"\" width=\"56\" height=\"56\" /></a> ");
		htmlBuilder.append(getColor().getColorImageHtml());
		htmlBuilder.append(" ");
		if (null != polishTitle) {
			htmlBuilder.append(getPolishTitle());
			htmlBuilder.append(" / ");
		}
		htmlBuilder.append(getTitle());
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

	@Override
	public String toString() {
		return "Trophy [order=" + order + ", " + (title != null ? "title=" + title : "") + "]";
	}

}

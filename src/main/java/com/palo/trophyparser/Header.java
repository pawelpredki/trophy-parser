package com.palo.trophyparser;

public class Header {

	public static String getHtml(int platinum, int gold, int silver, int bronze) {
		StringBuilder bld = new StringBuilder();

		bld.append(
				"<span style=\"color: #ffcc99;\"><strong><a class=\"fbx-instance fbx-link\" href=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/trofea.png\" rel=\"attachment wp-att-348\" data-slb-active=\"1\" data-slb-asset=\"344457185\" data-slb-internal=\"0\" data-slb-group=\"11026\"><img class=\"alignnone size-full wp-image-348\" src=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/trofea.png\" alt=\"trofea\" width=\"34\" height=\"34\" data-attachment-id=\"348\" data-permalink=\"http://www.lowczynitrofeow.pl/the-order-1886/trofea/#main\" data-orig-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/trofea.png\" data-image-title=\"trofea\" data-image-description=\"\" data-medium-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/trofea.png\" data-large-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/trofea.png\" /></a><span style=\"color: #db9a58;\">Informacje ogólne<span style=\"color: #333333;\">:</span><a class=\"fbx-link\" href=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/trofea.png\" rel=\"attachment wp-att-348\" data-slb-active=\"1\" data-slb-asset=\"344457185\" data-slb-group=\"11026\"><img class=\"alignnone size-full wp-image-348\" src=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/trofea.png\" alt=\"trofea\" width=\"34\" height=\"34\" data-attachment-id=\"348\" data-permalink=\"http://www.lowczynitrofeow.pl/the-order-1886/trofea/#main\" data-orig-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/trofea.png\" data-orig-size=\"34,34\" data-comments-opened=\"1\" data-image-title=\"trofea\" data-image-description=\"\" data-medium-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/trofea.png\" data-large-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/trofea.png\" /></a></span></strong></span>");
		bld.append(System.lineSeparator());
		bld.append("<strong>Stopień trudności wbicia platyny</strong>: /10");
		bld.append(System.lineSeparator());
		bld.append(
				"<strong>Liczba trofeów</strong>:<strong> <img class=\"alignnone size-full wp-image-345\" src=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/platyna.png\" alt=\"platyna\" width=\"16\" height=\"16\" data-attachment-id=\"345\" data-permalink=\"http://www.lowczynitrofeow.pl/the-order-1886/platyna/\" data-orig-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/platyna.png\" data-orig-size=\"16,16\" data-comments-opened=\"1\" data-image-title=\"platyna\" data-image-description=\"\" data-medium-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/platyna.png\" data-large-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/platyna.png\" /><span style=\"color: #00ccff;\">x"
						+ platinum
						+ "</span>/ <a class=\"fbx-instance fbx-link\" href=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/zloto.png\" data-slb-active=\"1\" data-slb-asset=\"1996600008\" data-slb-internal=\"0\" data-slb-group=\"11026\"><img class=\"alignnone size-full wp-image-359\" src=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/zloto.png\" alt=\"zloto\" width=\"16\" height=\"16\" data-attachment-id=\"359\" data-permalink=\"http://www.lowczynitrofeow.pl/infamous-first-light/zloto/#main\" data-orig-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/zloto.png\" data-orig-size=\"16,16\" data-comments-opened=\"1\" data-image-title=\"zloto\" data-image-description=\"\" data-medium-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/zloto.png\" data-large-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/zloto.png\" /></a><span style=\"color: #f0c311;\">x"
						+ gold
						+ "</span> </strong><strong>/ <img class=\"alignnone size-full wp-image-347\" src=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/srebro.png\" alt=\"\" width=\"16\" height=\"16\" data-attachment-id=\"347\" data-permalink=\"http://www.lowczynitrofeow.pl/the-order-1886/srebro/\" data-orig-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/srebro.png\" data-orig-size=\"16,16\" data-comments-opened=\"1\" data-image-title=\"srebro\" data-image-description=\"\" data-medium-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/srebro.png\" data-large-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/srebro.png\" /><span style=\"color: #808080;\">x"
						+ silver
						+ " </span>/ <a class=\"fbx-instance fbx-link\" href=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/braz.png\" data-slb-active=\"1\" data-slb-asset=\"1520434919\" data-slb-internal=\"0\" data-slb-group=\"11026\"><img class=\"alignnone size-full wp-image-346\" src=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/braz.png\" alt=\"\" width=\"16\" height=\"16\" data-attachment-id=\"346\" data-permalink=\"http://www.lowczynitrofeow.pl/the-order-1886/braz/#main\" data-orig-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/braz.png\" data-orig-size=\"16,16\" data-comments-opened=\"1\" data-image-title=\"braz\" data-image-description=\"\" data-medium-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/braz.png\" data-large-file=\"http://www.lowczynitrofeow.pl/wp-content/uploads/2015/12/braz.png\" /></a><span style=\"color: #ff6600;\">x"
						+ bronze + "</span></strong>");
		bld.append(System.lineSeparator());
		bld.append("<strong>Liczba trofeów offline</strong>:");
		bld.append(System.lineSeparator());
		bld.append("<strong>Liczba trofeów online</strong>:");
		bld.append(System.lineSeparator());
		bld.append("<strong>Minimalna liczba przejść</strong>:");
		bld.append(System.lineSeparator());
		bld.append("<strong>Trofea możliwe do przeoczenia</strong>: ");
		bld.append(System.lineSeparator());
		bld.append("<strong>Zglitchowane trofea: </strong>");
		bld.append(System.lineSeparator());
		bld.append("<strong>Czas do zdobycia platyny</strong>:");
		bld.append(System.lineSeparator());
		bld.append(
				"<p style=\"text-align: center;\"><span style=\"color: #db9a58;\"><strong>Krótko i na temat</strong><span style=\"color: #333333;\">:</span></span></p>");
		bld.append(System.lineSeparator());
		bld.append("<h4 style=\"text-align: center;\"><strong>Opinia znajduje się TUTAJ!</strong></h4>");
		bld.append(System.lineSeparator());
		bld.append("<strong><span style=\"color: #db9a58;\">Krok po kroku</span></strong>:");
		bld.append(System.lineSeparator());
		bld.append("<span style=\"color: #db9a58;\"><strong>Poradnik</strong></span>:");
		bld.append(System.lineSeparator());
		return bld.toString();

	}

}

package com.palo.trophyparser;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonArrayBuilder;
import java.util.List;

public class Header {

    public static String getHtml(TrophyCounter trophyCounter) {
        int platinum = trophyCounter.getCount(TrophyColor.PLATINUM);
        int gold = trophyCounter.getCount(TrophyColor.GOLD);
        int silver = trophyCounter.getCount(TrophyColor.SILVER);
        int bronze = trophyCounter.getCount(TrophyColor.BRONZE);

        StringBuilder bld = new StringBuilder();
        //bld.append("<p style=\"text-align: center;\">PORADNIK</p>");
        //bld.append(System.lineSeparator());
        //bld.append("<!--more-->");
        //bld.append(System.lineSeparator());
        //bld.append("<h3><strong>Informacje ogólne:</strong></h3>");
        bld.append(System.lineSeparator());
        bld.append("<ul>");
        bld.append(System.lineSeparator());
        bld.append("<li><strong>Stopień trudności wbicia platyny</strong>: /10</li>");
        bld.append(System.lineSeparator());
        bld.append("<li><strong>Czas do zdobycia platyny</strong>:</li>");
        bld.append(System.lineSeparator());
        bld.append(
                "<li><strong>Liczba trofeów</strong>:<strong> <img class=\"inlineimg size-full wp-image-345\" src=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/platyna.png\" alt=\"platyna\" width=\"16\" height=\"16\"  data-orig-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/platyna.png\" data-orig-size=\"16,16\" data-comments-opened=\"1\" data-image-title=\"platyna\" data-image-description=\"\" data-medium-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/platyna.png\" data-large-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/platyna.png\" /><span style=\"color: #00ccff;\">x"
                        + platinum
                        + "</span>/ <a class=\"fbx-instance fbx-link\" href=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/zloto.png\" data-slb-active=\"1\" data-slb-asset=\"1996600008\" data-slb-internal=\"0\" data-slb-group=\"11026\"><img class=\"inlineimg size-full wp-image-359\" src=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/zloto.png\" alt=\"zloto\" width=\"16\" height=\"16\"  data-orig-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/zloto.png\" data-orig-size=\"16,16\" data-comments-opened=\"1\" data-image-title=\"zloto\" data-image-description=\"\" data-medium-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/zloto.png\" data-large-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/zloto.png\" /></a><span style=\"color: #f0c311;\">x"
                        + gold
                        + "</span> </strong><strong>/ <img class=\"inlineimg size-full wp-image-347\" src=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/srebro.png\" alt=\"\" width=\"16\" height=\"16\"  data-orig-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/srebro.png\" data-orig-size=\"16,16\" data-comments-opened=\"1\" data-image-title=\"srebro\" data-image-description=\"\" data-medium-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/srebro.png\" data-large-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/srebro.png\" /><span style=\"color: #808080;\">x"
                        + silver
                        + " </span>/ <a class=\"fbx-instance fbx-link\" href=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/braz.png\" data-slb-active=\"1\" data-slb-asset=\"1520434919\" data-slb-internal=\"0\" data-slb-group=\"11026\"><img class=\"inlineimg size-full wp-image-346\" src=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/braz.png\" alt=\"\" width=\"16\" height=\"16\"  data-orig-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/braz.png\" data-orig-size=\"16,16\" data-comments-opened=\"1\" data-image-title=\"braz\" data-image-description=\"\" data-medium-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/braz.png\" data-large-file=\"https://lowcytrofeow.pl/wp-content/uploads/2018/06/braz.png\" /></a><span style=\"color: #ff6600;\">x"
                        + bronze + "</span></strong></li>");
        bld.append(System.lineSeparator());
        bld.append("<li><strong>Liczba trofeów offline</strong>:</li>");
        bld.append(System.lineSeparator());
        bld.append("<li><strong>Liczba trofeów online</strong>:</li>");
        bld.append(System.lineSeparator());
        bld.append("<li><strong>Minimalna liczba przejść</strong>:</li>");
        bld.append(System.lineSeparator());
        bld.append("<li><strong>Trofea możliwe do przeoczenia</strong>:</li>");
        bld.append(System.lineSeparator());
        bld.append("<li><strong>Zglitchowane trofea</strong>:</li>");
        bld.append(System.lineSeparator());
        bld.append("</ul>");
        bld.append(System.lineSeparator());

        bld.append("<h4 style=\"text-align: center;\"><strong>Opinia znajduje się TUTAJ!</strong></h4>");
        bld.append(System.lineSeparator());
        bld.append("<h3><strong>Krok po kroku</strong>:</h3>");
        bld.append(System.lineSeparator());
        bld.append("<h2><strong>Poradnik</strong>:</h2>");
        bld.append(System.lineSeparator());
        return bld.toString();

    }

    public static JsonObject getJson(TrophyCounter trophyCounter, Game game, List<Trophy> trophyList) {
        int platinum = trophyCounter.getCount(TrophyColor.PLATINUM);
        int gold = trophyCounter.getCount(TrophyColor.GOLD);
        int silver = trophyCounter.getCount(TrophyColor.SILVER);
        int bronze = trophyCounter.getCount(TrophyColor.BRONZE);

        JsonObjectBuilder jsonObject = Json.createObjectBuilder()
                .add("game_title", game.getName())
                .add("console", game.getConsole())
                .add("game_url", game.getUrl())
                .add("stopien_trudnosci_wbicia_platyny", "")
                .add("czas_do_zdobycia_platyny", "")
                .add("liczba_trofeow", Json.createObjectBuilder()
                        .add("platyna", platinum)
                        .add("zloto", gold)
                        .add("srebro", silver)
                        .add("braz", bronze))
                .add("liczb_trofeow_offline", "")
                .add("liczba_trofeow_online", "")
                .add("minimalna_liczba_przejsc", "")
                .add("trofea_mozliwe_do_przeoczenia", "")
                .add("zglitchowane_trofea", "");

        JsonArray trophiesArray = trophyList.stream()
                .map(Trophy::printJson)
                .collect(Json::createArrayBuilder, JsonArrayBuilder::add, JsonArrayBuilder::add)
                .build();

        jsonObject.add("trofea", trophiesArray);

        return jsonObject.build();
    }
}

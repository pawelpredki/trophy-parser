package com.palo.trophyparser;

public class Footer {

  public static String getHtml(boolean providedByPublisher) {
    StringBuilder bld = new StringBuilder();

    bld.append(
        "<span style=\"color: #ff0000;\"><strong>Poradnik napisany w całości na bazie własnych doświadczeń z grą.");
    bld.append(System.lineSeparator());
    bld.append(
        "</strong></span><span style=\"color: #ff0000;\"><strong>Zabrania się kopiowania bez zgody autora!</strong></span>");
    bld.append(System.lineSeparator());
    if (providedByPublisher) {
      bld.append(System.lineSeparator());
      bld.append(System.lineSeparator());
      bld.append("<pre style=\"text-align: center;\">Grę udostępnił wydawca.</pre>");
    }
    return bld.toString();
  }
}


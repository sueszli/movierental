package at.ac.tuwien.qs.movierental.service.validation;


import at.ac.tuwien.qs.movierental.entity.Movie;

import java.time.Year;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class MovieValidation {

    public static String getValidationErrors(Movie m) {
        String errorMessage = "";

        if (m.getTitle().length() < 3 || m.getTitle().length() > 250) {
            errorMessage += "Der Filmtitel darf nicht mit einem Leerzeichen beginnen oder enden.\n";
        }
        if (m.getTitle().length() < 3 || m.getTitle().length() > 250) {
            errorMessage += "Der Filmtitel muss zwischen 3 und 250 Zeichen lang sein.\n";
        }
        if (m.getSubtitle() != null && !m.getSubtitle().isEmpty() && (m.getSubtitle().length() < 3 || m.getSubtitle().length() > 250)) {
            errorMessage += "Der Subtitel darf nicht mit einem Leerzeichen beginnen oder enden.\n";
        }
        if (m.getSubtitle() != null && !m.getSubtitle().isEmpty() && m.getSubtitle().length() < 3 || Objects.requireNonNull(m.getSubtitle()).length() > 250) {
            errorMessage += "Der Subtitel muss zwischen 3 und 250 Zeichen lang sein.\n";
        }
        if (m.getRating() < 0 || m.getRating() > 5) {
            errorMessage += "Das Rating muss zwischen 0 und 5 liegen.\n";
        }
        if (m.getYearPublished().isBefore(Year.parse("1800")) || m.getYearPublished().isAfter(Year.now())) {
            errorMessage += "Das Erscheinungsjahr muss nach 1800 und nicht in der Zukunft liegen.\n";
        }
        if (m.getDirector().startsWith(" ") || m.getDirector().endsWith(" ")) {
            errorMessage += "Der Name des Regisseur darf nicht mit einem Leerzeichen beginnen oder enden.\n";
        }
        if (m.getDirector() != null && !m.getDirector().isEmpty() && m.getDirector().length() < 3 || m.getDirector().length() > 250) {
            errorMessage += "Der Name des Regisseur muss zwischen 3 und 250 Zeichen lang sein.\n";
        }
        if (m.getStock() < 0 || m.getStock() > 50) {
            errorMessage += "Die Anzahl der verfügbaren Filme muss zwischen 0 und 50 liegen.\n";
        }

        return errorMessage;
    }

    public static String getRatingValidationError(String unparsedRating) {
        try {
            Float.parseFloat(unparsedRating);
            return "";
        } catch (DateTimeParseException e) {
            return "Es muss ein gültiges Rating eingegeben werden.\n";
        }
    }

    public static String getYearPublishedValidationError(String unparsedYearPublished) {
        try {
            Year.parse(unparsedYearPublished);
            return "";
        } catch (DateTimeParseException e) {
            return "Das eingegebene Erscheinungsjahr ist ungültig.\n";
        }
    }

    public static String getStockValidationError(String unparsedStock) {
        try {
            Integer.parseInt(unparsedStock);
            return "";
        } catch (DateTimeParseException e) {
            return "Die Anzahl der verfügbaren Filme muss zwischen 0 und 50 liegen.\n";
        }
    }
}


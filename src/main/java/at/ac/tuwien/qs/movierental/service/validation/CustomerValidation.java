package at.ac.tuwien.qs.movierental.service.validation;

import at.ac.tuwien.qs.movierental.entity.Customer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomerValidation {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd. MM. yyyy");

    public static String getValidationErrors(Customer c) {
        String errorMessage = "";

        if (c.getFirstName().startsWith(" ") || c.getFirstName().endsWith(" ")) {
            errorMessage += "Der Vorname darf nicht mit einem Leerzeichen beginnen oder enden.\n";
        }
        if (c.getFirstName().length() < 3 || c.getFirstName().length() > 250) {
            errorMessage += "Der Vorname muss zwischen 3 und 250 Zeichen lang sein.\n";
        }
        if (c.getLastName().startsWith(" ") || c.getLastName().endsWith(" ")) {
            errorMessage += "Der Nachname darf nicht mit einem Leerzeichen beginnen oder enden.\n";
        }
        if (c.getLastName().length() < 3 || c.getLastName().length() > 250) {
            errorMessage += "Der Nachname muss zwischen 3 und 250 Zeichen lang sein.\n";
        }
        if (!c.getEmail().isEmpty() && !c.getEmail().matches("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {
            errorMessage += "Die eingegebene Email Adresse ist ungültig.\n";
        }
        if (!c.getPhone().isEmpty() && c.getPhone().length() < 3 || c.getPhone().length() > 250) {
            errorMessage += "Der Telefonnummer muss zwischen 3 und 250 Zeichen lang sein.\n";
        }
        if (c.getAddress().startsWith(" ") || c.getAddress().endsWith(" ")) {
            errorMessage += "Die Adresse darf nicht mit einem Leerzeichen beginnen oder enden.\n";
        }
        if (c.getAddress().length() < 3 || c.getAddress().length() > 1000) {
            errorMessage += "Die Adresse muss zwischen 3 und 1000 Zeichen lang sein.\n";
        }
        if (c.getCity().startsWith(" ") || c.getCity().endsWith(" ")) {
            errorMessage += "Die Stadt darf nicht mit einem Leerzeichen beginnen oder enden.\n";
        }
        if (c.getCity().length() < 3 || c.getCity().length() > 250) {
            errorMessage += "Die Stadt muss zwischen 3 und 250 Zeichen lang sein.\n";
        }
        if (c.getZipCode().startsWith(" ") || c.getZipCode().endsWith(" ")) {
            errorMessage += "Die Postleitzahl darf nicht mit einem Leerzeichen beginnen oder enden.\n";
        }
        if (c.getZipCode().length() < 2 || c.getZipCode().length() > 250) {
            errorMessage += "Die Postleitzahl muss zwischen 2 und 250 Zeichen lang sein.\n";
        }
        if (c.getBirthday().isBefore(LocalDate.now().minusYears(120L)) || c.getBirthday().isAfter(LocalDate.now())) {
            errorMessage += "Das eingegebene Geburtsdatum darf maximal 120 Jahre in der Vergangenheit liegen.\n";
        }
        return errorMessage;
    }

    public static String getBirthdateValidationError(String unparsedBirthdate) {
        try {
            LocalDate.parse(unparsedBirthdate
                .replace("\\s", "")
                .replace("(^\\d\\.)", "0$1")
                .replace("(\\.)(\\d\\.)", ".0$2")
                .replace("\\.", ". "), DATE_TIME_FORMATTER);
            return "";
        } catch (DateTimeParseException e) {
            return "Das eingegebene Geburtsdatum ist ungültig.\n";
        }
    }
}

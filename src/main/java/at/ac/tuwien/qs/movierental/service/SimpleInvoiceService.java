package at.ac.tuwien.qs.movierental.service;

import at.ac.tuwien.qs.movierental.config.AgeRating;
import at.ac.tuwien.qs.movierental.config.Genre;
import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.entity.Movie;
import at.ac.tuwien.qs.movierental.entity.Rental;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class SimpleInvoiceService implements InvoiceService {

    private static final DecimalFormat EURO_FORMATTER = new DecimalFormat("'€ '0.00");
    private static final DecimalFormat PERCENT_FORMATTER = new DecimalFormat("0.0'%'");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd. LLL yyyy / HH:mm");

    public void payInvoice(Customer customer, List<Rental> returnedList, LocalDate returnDate) {
        long priceInCent = getTotalPriceInCents(customer, returnedList, returnDate);

        // update videopoints of customer based on price
        if (!returnedList.isEmpty()) {
            if (customer.getVideopoints() >= 10) {
                customer.setVideopoints(customer.getVideopoints() - 10);
            }
            if (customer.getVideopoints() >= 20) {
                customer.setVideopoints(customer.getVideopoints() - 20);
            }
        }
        int newVideopoints = customer.getVideopoints() + calculateVideopointsGain(priceInCent, customer.getPatron());
        customer.setVideopoints(newVideopoints);
    }

    public String generateInvoicePreview(Customer customer, List<Rental> returnedList, List<Rental> lentList, LocalDateTime returnDate) {
        StringBuilder out = new StringBuilder();
        out.append("Kundennummer:").append(String.format("%37s", customer.getId())).append("\n");
        out.append("Kunde:").append(String.format("%44s", customer.getLastName().toUpperCase() + ", " + customer.getFirstName())).append("\n");
        out.append("Datum/Zeit:").append(String.format("%39s", DATE_TIME_FORMATTER.format(returnDate))).append("\n");

        if (!returnedList.isEmpty()) {
            out.append("\n").append("----------------- Zurückgebracht -----------------").append("\n\n");
            for (Rental rental : returnedList) {
                String price = EURO_FORMATTER.format(calculatePriceForRental(rental, returnDate.toLocalDate()) / 100.0);
                out.append(String.format("%-33s %16s", get30charName(rental), price)).append("\n");
            }
        }

        if (!lentList.isEmpty()) {
            out.append("\n").append("------------------- Ausgeborgt -------------------").append("\n\n");
            for (Rental rental : lentList) {
                String price = EURO_FORMATTER.format(calculatePriceForRental(rental, returnDate.toLocalDate()) / 100.0) + "/tag";
                out.append(String.format("%-33s %16s", get30charName(rental), price)).append("\n");
            }
        }

        long totalPriceInCents = this.getTotalPriceInCents(customer, returnedList, returnDate.toLocalDate());

        int oldVideopoints = customer.getVideopoints();
        int videopointsUsed = 0;
        int videopointsGained = 0;
        if (!returnedList.isEmpty()) {
            if (oldVideopoints > 20) {
                videopointsUsed = 20;
            } else if (oldVideopoints > 10) {
                videopointsUsed = 10;
            }
            videopointsGained = calculateVideopointsGain(totalPriceInCents, customer.getPatron());
        }
        int newVideopoints = oldVideopoints + videopointsGained - videopointsUsed;

        out.append("\n").append("------------------- Videopoints ------------------").append("\n\n");
        out.append("Bisherige Videopoints:").append(String.format("%28s", oldVideopoints)).append("\n");
        out.append("Verbrauch Videopoints:").append(String.format("%28s", videopointsUsed)).append("\n");
        out.append("Gutschrift Videopoints:").append(String.format("%27s", videopointsGained)).append("\n");
        out.append("Neue Videopoints:").append(String.format("%33s", newVideopoints)).append("\n\n");

        out.append("--------------------- Rabatte --------------------").append("\n\n");
        out.append("Stammkunde:").append(String.format("%39s", (customer.getPatron()) ? "Ja" : "Nein")).append("\n");
        out.append("Rabatt:").append(String.format("%43s", PERCENT_FORMATTER.format(calculateDiscount(customer)))).append("\n\n");

        out.append("------------------- Abrechnung -------------------").append("\n\n");
        out.append("Zu zahlender Betrag:").append(String.format("%30s", EURO_FORMATTER.format(totalPriceInCents / 100.0))).append("\n");

        return out.toString();
    }

    // utility::
    private long getTotalPriceInCents(Customer customer, List<Rental> returnedList, LocalDate returnDate) {
        long priceInCent = returnedList.stream().unordered()
            .mapToLong(r -> calculatePriceForRental(r, returnDate))
            .sum();
        priceInCent = (long) (priceInCent - (priceInCent / 100.00 * calculateDiscount(customer)));
        return priceInCent;
    }

    private long calculatePriceForRental(Rental rental, LocalDate returnDate) {
        float factor = Genre.getGenreFactor(rental.getMovie().getGenre());

        long daysRented = ChronoUnit.DAYS.between(rental.getDateLent(), returnDate);
        long priceInCents = rental.getMovie().getPriceInCents();

        return (long) (priceInCents * daysRented * factor);
    }

    private int calculateVideopointsGain(long priceInCent, boolean patron) {
        int videopoints = patron ? 1 : 0; // additional point if patron

        long euro = priceInCent / 100;
        videopoints += euro / 2;
        return videopoints;
    }

    private String get30charName(Rental rental) {
        // get name
        Movie movie = rental.getMovie();
        String movieShortname = movie.getId() + "; " + movie.getTitle()
            + ((movie.getSubtitle() != null && !movie.getSubtitle().isEmpty()) ? " - " + movie.getSubtitle() : "");

        // limit to 30 chars
        if (movieShortname.length() > 33) {
            movieShortname = movieShortname.substring(0, 30) + "...";
        }
        return movieShortname;
    }

    private float calculateDiscount(Customer customer) {
        float discount = 0;
        if (customer.getPatron()) {
            discount += 1.5;
        }
        if (customer.getVideopoints() >= 10) {
            discount += 2;
        }
        if (customer.getVideopoints() >= 20) {
            discount += 4;
        }
        return discount;
    }
    // :: utility
}

package at.ac.tuwien.qs.movierental.service.validation;

import at.ac.tuwien.qs.movierental.config.AgeRating;
import at.ac.tuwien.qs.movierental.entity.Rental;
import at.ac.tuwien.qs.movierental.exceptions.ValidationException;

public class RentalValidation {

    public static void validateRental(Rental rental) throws ValidationException {
        if (rental == null) {
            throw new IllegalArgumentException("Rental has to be not null");
        }
        if (rental.getCustomer() == null) {
            throw new ValidationException("Kunde muss gesetzt sein.");
        }
        if (rental.getMovie() == null) {
            throw new ValidationException("Film muss gesetzt sein.");
        }
        if (rental.getDateLent() == null) {
            throw new ValidationException("Verleihtag muss gesetzt sein.");
        }

        int minAge = AgeRating.getAgeRating(rental.getMovie().getAgeRating());
        if (rental.getDateLent().minusYears(minAge).isBefore(rental.getCustomer().getBirthday())) {
            throw new ValidationException("Der Kunde erfüllt die Altersfreigabe nicht! (" + rental.getMovie().getAgeRating() + ")");
        }
    }
}

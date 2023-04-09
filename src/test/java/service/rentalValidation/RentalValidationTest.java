package service.rentalValidation;

import at.ac.tuwien.qs.movierental.entity.Rental;
import at.ac.tuwien.qs.movierental.exceptions.ValidationException;
import at.ac.tuwien.qs.movierental.service.validation.RentalValidation;
import base.TestData;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class RentalValidationTest {

    @Test
    void testValidateRental_noCustomerInRental_shouldThrowValidationException() {
        // customer not set
        Rental rental = new Rental();
        rental.setCustomer(null);
        rental.setMovie(TestData.getChildrensMovie());
        rental.setDateLent(null);
        assertThrows(ValidationException.class, () -> RentalValidation.validateRental(rental));
    }

    @Test
    void testValidateRental_noMovieInRental_shouldThrowValidationException() {
        // movie not set
        Rental rental = new Rental();
        rental.setCustomer(TestData.getMinorCustomer());
        rental.setMovie(null);
        rental.setDateLent(null);
        assertThrows(ValidationException.class, () -> RentalValidation.validateRental(rental));
    }

    @Test
    void testValidateRental_noDateOfRental_shouldThrowValidationException() {
        // date of rental not set
        Rental rental = new Rental();
        rental.setCustomer(TestData.getMinorCustomer());
        rental.setMovie(TestData.getChildrensMovie());
        rental.setDateLent(null);
        assertThrows(ValidationException.class, () -> RentalValidation.validateRental(rental));
    }

    @Test
    void testValidateRental_childBorrowingAdultMovie_shouldThrowValidationException() {
        // invalid movie, because customer is too young (<18 years old)
        Rental rental = new Rental();
        rental.setCustomer(TestData.getMinorCustomer());
        rental.setMovie(TestData.getAdultMovie());
        rental.setDateLent(TestData.FIXED_DATE_NOW.minusDays(13).toLocalDate());
        assertThrows(ValidationException.class, () -> RentalValidation.validateRental(rental));
    }

    @Test
    void testValidateRental_validEntry_shouldNotThrowAnyExceptions() throws ValidationException {
        // valid entry
        Rental rental = new Rental();
        rental.setCustomer(TestData.getMinorCustomer());
        rental.setMovie(TestData.getChildrensMovie());
        rental.setDateLent(TestData.FIXED_DATE_NOW.minusDays(13).toLocalDate());
        RentalValidation.validateRental(rental);
    }
}

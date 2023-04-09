package service.rentalValidation;

import at.ac.tuwien.qs.movierental.config.AgeRating;
import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.entity.Rental;
import at.ac.tuwien.qs.movierental.exceptions.ValidationException;
import at.ac.tuwien.qs.movierental.service.validation.RentalValidation;
import base.TestData;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Equivalence partitioning age-rated movies and testing validateRental()
 */
class TestAgeRating {

    @Test
    void testValidateRental_negativeAge() throws ValidationException {
        testWithArg(-1);
    }

    @Test
    void testValidateRental_age0() throws ValidationException {
        testWithArg(0);
    }

    @Test
    void testValidateRental_age1() throws ValidationException {
        testWithArg(1);
    }

    @Test
    void testValidateRental_age3() throws ValidationException {
        testWithArg(3);
    }

    @Test
    void testValidateRental_age5() throws ValidationException {
        testWithArg(5);
    }

    @Test
    void testValidateRental_age6() throws ValidationException {
        testWithArg(6);
    }

    @Test
    void testValidateRental_age9() throws ValidationException {
        testWithArg(9);
    }

    @Test
    void testValidateRental_age11() throws ValidationException {
        testWithArg(11);
    }

    @Test
    void testValidateRental_age12() throws ValidationException {
        testWithArg(12);
    }

    @Test
    void testValidateRental_age13() throws ValidationException {
        testWithArg(13);
    }

    @Test
    void testValidateRental_age15() throws ValidationException {
        testWithArg(15);
    }

    @Test
    void testValidateRental_age16() throws ValidationException {
        testWithArg(16);
    }

    @Test
    void testValidateRental_age17() throws ValidationException {
        testWithArg(17);
    }

    @Test
    void testValidateRental_age18() throws ValidationException {
        testWithArg(18);
    }

    @Test
    void testValidateRental_age20() throws ValidationException {
        testWithArg(20);
    }

    @Test
    void testValidateRental_age21() throws ValidationException {
        testWithArg(21);
    }

    @Test
    void testValidateRental_age22() throws ValidationException {
        testWithArg(22);
    }

    @Test
    void testValidateRental_age30() throws ValidationException {
        testWithArg(30);
    }

    // util ::
    private void testWithArg(int age) throws ValidationException {
        // customer
        Customer c = TestData.getCustomerByAge(age);
        LocalDate now = TestData.FIXED_DATE_NOW.toLocalDate();
        Rental fsk0 = TestData.getRental(TestData.getFSK0movie(), c, now);
        Rental fsk6 = TestData.getRental(TestData.getFSK6movie(), c, now);
        Rental fsk12 = TestData.getRental(TestData.getFSK12movie(), c, now);
        Rental fsk16 = TestData.getRental(TestData.getFSK16movie(), c, now);
        Rental fsk18 = TestData.getRental(TestData.getFSK18movie(), c, now);
        Rental rated21 = TestData.getRental(TestData.getRATED21movie(), c, now);

        // expectation based on specification
        if (age < 0) {
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk0));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk6));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk12));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk16));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk18));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(rated21));
        } else if (age < 6) {
            RentalValidation.validateRental(fsk0);
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk6));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk12));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk16));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk18));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(rated21));
        } else if (age < 12) {
            RentalValidation.validateRental(fsk0);
            RentalValidation.validateRental(fsk6);
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk12));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk16));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk18));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(rated21));
        } else if (age < 16) {
            RentalValidation.validateRental(fsk0);
            RentalValidation.validateRental(fsk6);
            RentalValidation.validateRental(fsk12);
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk16));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk18));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(rated21));
        } else if (age < 18) {
            RentalValidation.validateRental(fsk0);
            RentalValidation.validateRental(fsk6);
            RentalValidation.validateRental(fsk12);
            RentalValidation.validateRental(fsk16);
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(fsk18));
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(rated21));
        } else if (age < 21) {
            RentalValidation.validateRental(fsk0);
            RentalValidation.validateRental(fsk6);
            RentalValidation.validateRental(fsk12);
            RentalValidation.validateRental(fsk16);
            RentalValidation.validateRental(fsk18);
            assertThrows(ValidationException.class, () -> RentalValidation.validateRental(rated21));
        } else {
            RentalValidation.validateRental(fsk0);
            RentalValidation.validateRental(fsk6);
            RentalValidation.validateRental(fsk12);
            RentalValidation.validateRental(fsk16);
            RentalValidation.validateRental(fsk18);
            RentalValidation.validateRental(rated21);
        }
    }
    // :: util
}

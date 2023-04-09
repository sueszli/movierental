package service.simpleInvoiceService;

import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.entity.Rental;
import at.ac.tuwien.qs.movierental.service.SimpleInvoiceService;
import base.TestData;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Equivalence partitioning videopoints and testing calculateVideopointsGain()
 */
class TestMoviePoints {

    private static final SimpleInvoiceService SIMPLE_INVOICE_SERVICE = new SimpleInvoiceService();

    // patron customers ::
    @Test
    void testCalculateVideopointsGain_patronCustomer_0euros() {
        testWithArgs(0, true);
    }

    @Test
    void testCalculateVideopointsGain_patronCustomer_1euros() {
        testWithArgs(1, true);
    }

    @Test
    void testCalculateVideopointsGain_patronCustomer_3euros() {
        testWithArgs(3, true);
    }

    @Test
    void testCalculateVideopointsGain_patronCustomer_5euros() {
        testWithArgs(5, true);
    }
    // :: patron customers

    // non-patron customers ::
    @Test
    void testCalculateVideopointsGain_NonPatronCustomer_0euros() {
        testWithArgs(0, true);
    }

    @Test
    void testCalculateVideopointsGain_NonPatronCustomer_1euros() {
        testWithArgs(1, true);
    }

    @Test
    void testCalculateVideopointsGain_NonPatronCustomer_3euros() {
        testWithArgs(3, true);
    }

    @Test
    void testCalculateVideopointsGain_NonPatronCustomer_5euros() {
        testWithArgs(5, true);
    }
    // :: non-patron customers

    // util ::
    private void testWithArgs(int totalPriceInEuro, boolean patron) {
        LocalDateTime lentDate = TestData.FIXED_DATE_NOW.minusDays(totalPriceInEuro); // 1€ per day lent
        LocalDateTime returnDate = TestData.FIXED_DATE_NOW;

        // customer
        Customer c;
        if (patron) {
            c = TestData.getPatronCustomer();
        } else {
            c = TestData.getNonPatronCustomer();
        }
        int initialVP = 9; // must be <10 so there are no discounts
        c.setVideopoints(initialVP);

        // returned a single movie
        Rental r = TestData.getRental(TestData.get1euroMovie(), c, lentDate.toLocalDate());
        ArrayList<Rental> returned = new ArrayList<>();
        returned.add(r);

        String invoice = SIMPLE_INVOICE_SERVICE.generateInvoicePreview(c, returned, new ArrayList<>(), returnDate);
        assertTrue(invoice.contains("€ " + totalPriceInEuro + ",00"));

        // check VP gain (based on specification)
        int gain = 0;
        if (totalPriceInEuro < 2 && patron) {
            gain = 1;
        } else if (patron) {
            gain = (totalPriceInEuro / 2) + 1;
        } else {
            gain = totalPriceInEuro / 2;
        }
        int expectedVP = initialVP + gain;

        SIMPLE_INVOICE_SERVICE.payInvoice(c, returned, returnDate.toLocalDate());
        assertEquals(expectedVP, c.getVideopoints());
    }
    // :: util
}

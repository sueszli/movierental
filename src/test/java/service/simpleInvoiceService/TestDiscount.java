package service.simpleInvoiceService;

import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.entity.Rental;
import at.ac.tuwien.qs.movierental.service.SimpleInvoiceService;
import base.TestData;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Equivalence partitioning discounts and testing payInvoice()
 */
class TestDiscount {

    private static final SimpleInvoiceService SIMPLE_INVOICE_SERVICE = new SimpleInvoiceService();

    // patron customers ::
    @Test
    void testPayInvoice_patronCustomer_negative() {
        testWithArgs(-100, "1,5%", true);
    }

    @Test
    void testPayInvoice_patronCustomer_0VP() {
        testWithArgs(0, "1,5%", true);
    }

    @Test
    void testPayInvoice_patronCustomer_1VP() {
        testWithArgs(1, "1,5%", true);
    }

    @Test
    void testPayInvoice_patronCustomer_5VP() {
        testWithArgs(5, "1,5%", true);
    }

    @Test
    void testPayInvoice_patronCustomer_9VP() {
        testWithArgs(9, "1,5%", true);
    }

    @Test
    void testPayInvoice_patronCustomer_10VP() {
        testWithArgs(10, "3,5%", true);
    }

    @Test
    void testPayInvoice_patronCustomer_11VP() {
        testWithArgs(11, "3,5%", true);
    }

    @Test
    void testPayInvoice_patronCustomer_15VP() {
        testWithArgs(15, "3,5%", true);
    }

    @Test
    void testPayInvoice_patronCustomer_19VP() {
        testWithArgs(19, "3,5%", true);
    }

    @Test
    void testPayInvoice_patronCustomer_20VP() {
        testWithArgs(20, "7,5%", true);
    }

    @Test
    void testPayInvoice_patronCustomer_21VP() {
        testWithArgs(21, "7,5%", true);
    }

    @Test
    void testPayInvoice_patronCustomer_100P() {
        testWithArgs(100, "7,5%", true);
    }
    // :: patron customers


    // non-patron customers ::
    @Test
    void testPayInvoice_nonPatronCustomer_negative() {
        testWithArgs(100, "0%", false);
    }

    @Test
    void testPayInvoice_nonPatronCustomer_0VP() {
        testWithArgs(0, "0%", false);
    }

    @Test
    void testPayInvoice_nonPatronCustomer_1VP() {
        testWithArgs(1, "0%", false);
    }

    @Test
    void testPayInvoice_nonPatronCustomer_5VP() {
        testWithArgs(5, "0%", false);
    }

    @Test
    void testPayInvoice_nonPatronCustomer_9VP() {
        testWithArgs(9, "0%", false);
    }

    @Test
    void testPayInvoice_nonPatronCustomer_10VP() {
        testWithArgs(10, "2,0%", false);
    }

    @Test
    void testPayInvoice_nonPatronCustomer_11VP() {
        testWithArgs(11, "2,0%", false);
    }

    @Test
    void testPayInvoice_nonPatronCustomer_15VP() {
        testWithArgs(15, "2,0%", false);
    }

    @Test
    void testPayInvoice_nonPatronCustomer_19VP() {
        testWithArgs(19, "2,0%", false);
    }

    @Test
    void testPayInvoice_nonPatronCustomer_20VP() {
        testWithArgs(20, "6,0%", false);
    }

    @Test
    void testPayInvoice_nonPatronCustomer_21VP() {
        testWithArgs(21, "6,0%", false);
    }

    @Test
    void testPayInvoice_nonPatronCustomer_100P() {
        testWithArgs(100, "6,0%", false);
    }
    // :: non-patron customers


    // util ::
    private void testWithArgs(int initialVP, String discount, boolean patron) {
        // customer with initialVP
        Customer c;
        if (patron) {
            c = TestData.getPatronCustomer();
        } else {
            c = TestData.getNonPatronCustomer();
        }
        c.setVideopoints(initialVP);

        // returned one movie
        List<Rental> returned = TestData.getRentalList(
            TestData.getRental(TestData.getChildrensMovie(), c, TestData.FIXED_DATE_NOW.toLocalDate())
        );

        String invoice = SIMPLE_INVOICE_SERVICE.generateInvoicePreview(c, returned, new ArrayList<>(), TestData.FIXED_DATE_NOW);
        assertTrue(invoice.contains(discount));
    }
    // :: util
}

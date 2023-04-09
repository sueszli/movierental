package service.simpleInvoiceService;

import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.entity.Movie;
import at.ac.tuwien.qs.movierental.entity.Rental;
import at.ac.tuwien.qs.movierental.service.SimpleInvoiceService;
import base.TestData;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * generateInvoicePreview() tests
 */
class InvoiceTextTest {

    private static final SimpleInvoiceService SIMPLE_INVOICE_SERVICE = new SimpleInvoiceService();

    @Test
    void testGenerateInvoicePreview_twoItems() {
        Customer c = TestData.getTestCustomer();
        c.setId(1L);

        Movie m1 = TestData.getLOTR1();
        m1.setId(1L);

        Movie m2 = TestData.getLOTR2();
        m2.setId(2L);

        Rental r1 = TestData.getRental(m1, c, TestData.FIXED_DATE_NOW.toLocalDate().minusDays(1));
        Rental r2 = TestData.getRental(m2, c, TestData.FIXED_DATE_NOW.toLocalDate());

        List<Rental> returned = TestData.getRentalList(r1);
        List<Rental> lent = TestData.getRentalList(r2);

        String invoice = SIMPLE_INVOICE_SERVICE.generateInvoicePreview(c, returned, lent, TestData.FIXED_DATE_NOW);
        assertTrue(invoice.contains("3,5%"));
        assertThat(invoice).isEqualTo(
                "Kundennummer:                                    1\n"
            +   "Kunde:                             FISCHER, Johann\n"
            +   "Datum/Zeit:                   12. Mär 2022 / 10:15\n"
            +   "\n"
            +    "----------------- Zurückgebracht -----------------\n"
            +   "\n"
            +   "1; Der Herr der Ringe - Die Ge...           € 3,75\n"
            +   "\n"
            +   "------------------- Ausgeborgt -------------------\n"
            +   "\n"
            +   "2; Der Herr der Ringe - Die zw...       € 0,00/tag\n"
            +   "\n"
            +   "------------------- Videopoints ------------------\n"
            +   "\n"
            +   "Bisherige Videopoints:                          12\n"
            +   "Verbrauch Videopoints:                          10\n"
            +   "Gutschrift Videopoints:                          2\n"
            +   "Neue Videopoints:                                4\n"
            +   "\n"
            +   "--------------------- Rabatte --------------------\n"
            +   "\n"
            +   "Stammkunde:                                     Ja\n"
            +   "Rabatt:                                       3,5%\n"
            +   "\n"
            +   "------------------- Abrechnung -------------------\n"
            +   "\n"
            +   "Zu zahlender Betrag:                        € 3,61\n"
        );
    }
}

package at.ac.tuwien.qs.movierental.service;

import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.entity.Rental;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface InvoiceService {

    /**
     * Updates the videopoints of a Customer based on the list of returned items.
     * Subtracts 10 or 20 videopoints if existent for a discount when calculating the total price.
     *
     * @param customer returning a list of movies
     * @param returnedList of movies by customer
     * @param returnDate at which the items from the list are returned
     */
    void payInvoice(Customer customer, List<Rental> returnedList, LocalDate returnDate);

    /**
     * Generates and returns an invoice / bill.
     * Subtracts 10 or 20 videopoints if existent for a discount when calculating the total price.
     *
     * @param customer returning a list of movies
     * @param returnedList of movies by customer (must be a subgroup of lentList)
     * @param lentList of movies by customer
     * @param returnDate at which the items from the list are returned
     * @return the invoice / bill as a String.
     */
    String generateInvoicePreview(Customer customer, List<Rental> returnedList, List<Rental> lentList, LocalDateTime returnDate);
}

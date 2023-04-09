package at.ac.tuwien.qs.movierental.repository;

import at.ac.tuwien.qs.movierental.entity.Rental;
import at.ac.tuwien.qs.movierental.entity.Customer;

import java.util.List;

/**
 * An pure extension of the generic DAO.class interface.
 * Implements CRUD methods for Rental.
 */
public interface RentalDAO extends DAO<Rental> {

    List<Rental> readByCustomer(Customer customer);
}

package at.ac.tuwien.qs.movierental.ui.controller;

import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.entity.Movie;

import java.util.function.Predicate;

public class Filter {

    public static Predicate<Customer> getCustomerPredicate(String filter) {
        return customer -> {
            if (filter == null || filter.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = filter.toLowerCase();
            return (customer.getId() != null && customer.getId().toString().toLowerCase().contains(lowerCaseFilter)) ||
                (customer.getFirstName() != null && customer.getFirstName().toLowerCase().contains(lowerCaseFilter)) ||
                (customer.getLastName() != null && customer.getLastName().toLowerCase().contains(lowerCaseFilter)) ||
                (customer.getEmail() != null && customer.getEmail().toLowerCase().contains(lowerCaseFilter));
        };
    }

    public static Predicate<Movie> getMoviePredicate(String filter) {
        return movie -> {
            if (filter == null || filter.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = filter.toLowerCase();
            return (movie.getId() != null && movie.getId().toString().toLowerCase().contains(lowerCaseFilter)) ||
                (movie.getTitle() != null && movie.getTitle().toLowerCase().contains(lowerCaseFilter)) ||
                (movie.getSubtitle() != null && movie.getSubtitle().toLowerCase().contains(lowerCaseFilter)) ||
                (movie.getGenre() != null && movie.getGenre().toLowerCase().contains(lowerCaseFilter)) ||
                (movie.getDirector() != null && movie.getDirector().toLowerCase().contains(lowerCaseFilter) ||
                    (movie.getYearPublished() != null && movie.getYearPublished().toString().toLowerCase().contains(lowerCaseFilter)));
        };
    }
}

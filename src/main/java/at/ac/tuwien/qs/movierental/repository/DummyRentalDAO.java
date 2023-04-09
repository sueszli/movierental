package at.ac.tuwien.qs.movierental.repository;

import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.entity.Rental;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DummyRentalDAO implements RentalDAO {

    private final AtomicLong ATOMIC_LONG = new AtomicLong(0);
    private final HashSet<Rental> RENTALS = new HashSet<>();

    public DummyRentalDAO(MovieDAO movieDAO, CustomerDAO customerDAO) {
        dataInit(movieDAO, customerDAO);
    }

    @Override
    public Rental create(Rental rental) {
        rental.setId(ATOMIC_LONG.addAndGet(1));
        RENTALS.add(rental);
        return rental;
    }

    @Override
    public Rental read(Long id) {
        return RENTALS.stream().unordered()
            .filter(r -> r.getId().equals(id))
            .findFirst().orElse(null);
    }

    @Override
    public List<Rental> read() {
        ArrayList<Rental> r = new ArrayList<>(this.RENTALS);
        r.sort(Comparator.comparing(Rental::getId));
        return r;
    }

    @Override
    public Rental update(Rental rental) {
        this.RENTALS.add(rental);
        return rental;
    }

    @Override
    public void delete(Rental rental) {
        this.RENTALS.remove(rental);
    }

    @Override
    public List<Rental> readByCustomer(Customer customer) {
        ArrayList<Rental> rs = new ArrayList<>();
        RENTALS.stream().unordered()
            .filter(r -> r.getCustomer().equals(customer))
            .forEach(rs::add);
        rs.sort(Comparator.comparing(Rental::getId));
        return rs;
    }

    // data generation ::
    private void dataInit(MovieDAO movieDAO, CustomerDAO customerDAO) {
        Rental rental;

        rental = new Rental();
        rental.setId(ATOMIC_LONG.addAndGet(1));
        rental.setDateLent(LocalDate.now().minusDays(1));
        rental.setCustomer(customerDAO.read(1L));
        rental.setMovie(movieDAO.read(4L));
        RENTALS.add(rental);

        rental = new Rental();
        rental.setId(ATOMIC_LONG.addAndGet(1));
        rental.setDateLent(LocalDate.now().minusDays(5));
        rental.setCustomer(customerDAO.read(4L));
        rental.setMovie(movieDAO.read(1L));
        RENTALS.add(rental);

        rental = new Rental();
        rental.setId(ATOMIC_LONG.addAndGet(1));
        rental.setDateLent(LocalDate.now().minusDays(3));
        rental.setCustomer(customerDAO.read(1L));
        rental.setMovie(movieDAO.read(2L));
        RENTALS.add(rental);

        rental = new Rental();
        rental.setId(ATOMIC_LONG.addAndGet(1));
        rental.setDateLent(LocalDate.now().minusDays(14));
        rental.setCustomer(customerDAO.read(2L));
        rental.setMovie(movieDAO.read(5L));
        RENTALS.add(rental);
    }
    // :: data generation
}

package base;

import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.entity.Movie;
import at.ac.tuwien.qs.movierental.entity.Rental;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * This interface returns Objects to provide unified access for testing, aswell as a fixed date to make tests time-oblivious.
 * With each call, a new clone with the same data, but a different reference is created through the given methods.
 */
public interface TestData {

    AtomicLong ID_GEN = new AtomicLong();
    DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd. MM. yyyy");
    LocalDateTime FIXED_DATE_NOW = LocalDateTime.parse("2022-03-12T10:15:30"); // Tests must be independent of current time

    // customers ::
    static Customer getTestCustomer() {
        Customer out = new Customer();
        out.setId(ID_GEN.incrementAndGet());
        out.setFirstName("Johann");
        out.setLastName("Fischer");
        out.setEmail("angler@gmail.com");
        out.setPhone("+43 674 156 45 78");
        out.setBirthday(LocalDate.parse("23. 05. 1960", DATE_TIME_FORMATTER));
        out.setAddress("Neustiftgasse 31");
        out.setZipCode("1070");
        out.setCity("Wien");
        out.setPatron(true);
        out.setPhoto(null);
        out.setVideopoints(12);
        return out;
    }

    static Customer getMinorCustomer() {
        Customer out = new Customer();
        out.setId(ID_GEN.incrementAndGet());
        out.setFirstName("Linda");
        out.setLastName("Mayer");
        out.setEmail("Lucy2001@hotmail.com");
        out.setPhone("+43 555 61 61");
        out.setBirthday(LocalDate.parse("01. 04. 2010", DATE_TIME_FORMATTER));
        out.setAddress("Berggasse 11");
        out.setZipCode("3002");
        out.setCity("Purkersdorf");
        out.setPatron(true);
        out.setPhoto(null);
        out.setVideopoints(2);
        return out;
    }

    static Customer getCustomerByAge(int age) {
        Customer c = TestData.getTestCustomer();
        c.setBirthday(TestData.FIXED_DATE_NOW.toLocalDate().minusYears(age));
        return c;
    }

    static Customer getPatronCustomer() {
        return getTestCustomer();
    }

    static Customer getNonPatronCustomer() {
        Customer out = getTestCustomer();
        out.setPatron(false);
        return out;
    }
    // :: customers

    // movies ::
    static Movie getAdultMovie() {
        Movie adultMovie = new Movie();
        adultMovie.setId(ID_GEN.incrementAndGet());
        adultMovie.setTitle("SAW III");
        adultMovie.setSubtitle("");
        adultMovie.setGenre("Horror");
        adultMovie.setAgeRating("FSK 18");
        adultMovie.setLanguage("English");
        adultMovie.setPriceInCents(200);
        adultMovie.setDirector("");
        adultMovie.setRating(3.2F);
        adultMovie.setYearPublished(Year.of(2002));
        adultMovie.setSeries(false);
        adultMovie.setStock(3);
        adultMovie.setCover(null);
        return adultMovie;
    }

    static Movie getChildrensMovie() {
        Movie childrensMovie = new Movie();
        childrensMovie.setId(ID_GEN.incrementAndGet());
        childrensMovie.setTitle("Der Lorax");
        childrensMovie.setSubtitle("");
        childrensMovie.setGenre("Kinder");
        childrensMovie.setAgeRating("FSK 0");
        childrensMovie.setLanguage("German");
        childrensMovie.setPriceInCents(120);
        childrensMovie.setDirector("Chris Renaud");
        childrensMovie.setRating(3.0F);
        childrensMovie.setYearPublished(Year.of(2012));
        childrensMovie.setSeries(false);
        childrensMovie.setStock(8);
        childrensMovie.setCover(null);
        return childrensMovie;
    }

    static Movie getLOTR1() {
        Movie movie1 = new Movie();
        movie1.setId(ID_GEN.incrementAndGet());
        movie1.setTitle("Der Herr der Ringe");
        movie1.setSubtitle("Die Gefährten");
        movie1.setGenre("Fantasy");
        movie1.setAgeRating("FSK 12");
        movie1.setLanguage("German");
        movie1.setPriceInCents(300);
        movie1.setDirector("Peter Jackson");
        movie1.setRating(4.9F);
        movie1.setYearPublished(Year.of(2001));
        movie1.setSeries(true);
        movie1.setStock(10);
        movie1.setCover(null);
        return movie1;
    }

    static Movie getLOTR2() {
        Movie movie2 = new Movie();
        movie2.setId(ID_GEN.incrementAndGet());
        movie2.setTitle("Der Herr der Ringe");
        movie2.setSubtitle("Die zwei Türme");
        movie2.setGenre("Fantasy");
        movie2.setAgeRating("FSK 12");
        movie2.setLanguage("German");
        movie2.setPriceInCents(400);
        movie2.setDirector("Peter Jackson");
        movie2.setRating(4.6F);
        movie2.setYearPublished(Year.of(2002));
        movie2.setSeries(true);
        movie2.setStock(8);
        movie2.setCover(null);
        return movie2;
    }

    static Movie getTestMovie() {
        Movie m = new Movie();
        m.setId(ID_GEN.incrementAndGet());
        m.setTitle("TestMovie");
        m.setSubtitle("");
        m.setGenre("Test Genre"); // factor set to 1
        m.setAgeRating("FSK 0");
        m.setLanguage("German");
        m.setPriceInCents(100); // 1 euro
        m.setDirector("Test Director");
        m.setRating(5F);
        m.setYearPublished(Year.of(2002));
        m.setSeries(true);
        m.setStock(42);
        m.setCover(null);
        return m;
    }

    static Movie get1euroMovie() {
        return getTestMovie();
    }

    static Movie getFSK0movie() {
        Movie m = getTestMovie();
        m.setAgeRating("FSK 0");
        return m;
    }

    static Movie getFSK6movie() {
        Movie m = getTestMovie();
        m.setAgeRating("FSK 6");
        return m;
    }

    static Movie getFSK12movie() {
        Movie m = getTestMovie();
        m.setAgeRating("FSK 12");
        return m;
    }

    static Movie getFSK16movie() {
        Movie m = getTestMovie();
        m.setAgeRating("FSK 16");
        return m;
    }

    static Movie getFSK18movie() {
        Movie m = getTestMovie();
        m.setAgeRating("FSK 18");
        return m;
    }

    static Movie getRATED21movie() {
        Movie m = getTestMovie();
        m.setAgeRating("RATED 21");
        return m;
    }
    // :: movies

    // rentals ::
    static Rental getRental(Movie m, Customer c, LocalDate dateLent) {
        Rental r = new Rental();
        r.setId(ID_GEN.incrementAndGet());
        r.setMovie(m);
        r.setCustomer(c);
        r.setDateLent(dateLent);
        return r;
    }
    // :: rentals

    // rental-list ::
    static List<Rental> getRentalList(Rental r) {
        // only contains a single item
        List<Rental> rentals = new ArrayList<>();
        rentals.add(r);
        return rentals;
    }
    // :: rental-list
}

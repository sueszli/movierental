package at.ac.tuwien.qs.movierental.repository;

import at.ac.tuwien.qs.movierental.entity.Movie;

import java.time.Year;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class DummyMovieDAO implements MovieDAO {

    private final AtomicLong ATOMIC_LONG = new AtomicLong(0);
    private final HashSet<Movie> MOVIES = new HashSet<>();

    public DummyMovieDAO() {
        dataInit();
    }

    @Override
    public Movie create(Movie movie) {
        movie.setId(ATOMIC_LONG.addAndGet(1));
        MOVIES.add(movie);
        return movie;
    }

    @Override
    public Movie read(Long id) {
        return MOVIES.stream().unordered()
            .filter(m -> m.getId().equals(id))
            .findFirst().orElse(null);
    }

    @Override
    public List<Movie> read() {
        ArrayList<Movie> m = new ArrayList<>(this.MOVIES);
        m.sort(Comparator.comparing(Movie::getId));
        return m;
    }

    @Override
    public Movie update(Movie movie) {
        this.MOVIES.add(movie);
        return movie;
    }

    @Override
    public void delete(Movie movie) {
        this.MOVIES.remove(movie);
    }

    // data generation ::
    private void dataInit() {
        Movie movie;

        movie = new Movie();
        movie.setId(ATOMIC_LONG.addAndGet(1));
        movie.setTitle("Der Herr der Ringe");
        movie.setSubtitle("Die Gefährten");
        movie.setGenre("Fantasy");
        movie.setAgeRating("FSK 12");
        movie.setLanguage("German");
        movie.setPriceInCents(300);
        movie.setDirector("Peter Jackson");
        movie.setRating(4.9F);
        movie.setYearPublished(Year.of(2001));
        movie.setSeries(true);
        movie.setStock(10);
        movie.setCover(null);
        MOVIES.add(movie);

        movie = new Movie();
        movie.setId(ATOMIC_LONG.addAndGet(1));
        movie.setTitle("Der Herr der Ringe");
        movie.setSubtitle("Die zwei Türme");
        movie.setGenre("Fantasy");
        movie.setAgeRating("FSK 12");
        movie.setLanguage("German");
        movie.setPriceInCents(400);
        movie.setDirector("Peter Jackson");
        movie.setRating(4.6F);
        movie.setYearPublished(Year.of(2002));
        movie.setSeries(true);
        movie.setStock(8);
        movie.setCover(null);
        MOVIES.add(movie);

        movie = new Movie();
        movie.setId(ATOMIC_LONG.addAndGet(1));
        movie.setTitle("Der Herr der Ringe");
        movie.setSubtitle("Die Rückkehr des Königs");
        movie.setGenre("Fantasy");
        movie.setAgeRating("FSK 12");
        movie.setLanguage("German");
        movie.setPriceInCents(500);
        movie.setDirector("Peter Jackson");
        movie.setRating(4.0F);
        movie.setYearPublished(Year.of(2003));
        movie.setSeries(true);
        movie.setStock(5);
        movie.setCover(null);
        MOVIES.add(movie);

        movie = new Movie();
        movie.setId(ATOMIC_LONG.addAndGet(1));
        movie.setTitle("The Ring");
        movie.setSubtitle("");
        movie.setGenre("Horror");
        movie.setAgeRating("FSK 16");
        movie.setLanguage("English");
        movie.setPriceInCents(200);
        movie.setDirector("Gore Verbinski");
        movie.setRating(3.2F);
        movie.setYearPublished(Year.of(2002));
        movie.setSeries(false);
        movie.setStock(3);
        movie.setCover(null);
        MOVIES.add(movie);

        movie = new Movie();
        movie.setId(ATOMIC_LONG.addAndGet(1));
        movie.setTitle("Der Lorax");
        movie.setSubtitle("");
        movie.setGenre("Kinder");
        movie.setAgeRating("FSK 0");
        movie.setLanguage("German");
        movie.setPriceInCents(120);
        movie.setDirector("Chris Renaud");
        movie.setRating(3.0F);
        movie.setYearPublished(Year.of(2012));
        movie.setSeries(false);
        movie.setStock(8);
        movie.setCover(null);
        MOVIES.add(movie);
    }
    // :: data generation
}

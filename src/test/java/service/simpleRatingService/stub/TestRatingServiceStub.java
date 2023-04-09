package service.simpleRatingService.stub;

import at.ac.tuwien.qs.movierental.entity.Movie;
import at.ac.tuwien.qs.movierental.exceptions.NoMovieFoundException;
import at.ac.tuwien.qs.movierental.exceptions.ServiceNotAvailableException;
import at.ac.tuwien.qs.movierental.exceptions.TooManyMoviesFound;
import at.ac.tuwien.qs.movierental.service.MovieDataService;
import at.ac.tuwien.qs.movierental.service.RatingService;
import at.ac.tuwien.qs.movierental.service.SimpleRatingService;
import base.TestData;
import org.junit.jupiter.api.Test;

import java.time.Year;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TestRatingServiceStub {

    private static RatingService ratingService;

    @Test
    void testTooManyMoviesFound() {
        // configure stub
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(TestData.getLOTR1());
        movies.add(TestData.getLOTR2());
        MovieDataService stubService = new MovieDataServiceStub(movies);
        ratingService = new SimpleRatingService(stubService);

        // test
        Movie movie = new Movie();
        movie.setTitle("Bambi");
        assertThrows(TooManyMoviesFound.class, () -> ratingService.loadRatingForMovie(movie));
    }

    @Test
    void testNoMovieFoundException() {
        // configure stub
        MovieDataService stubService = new MovieDataServiceStub(new ArrayList<>());
        ratingService = new SimpleRatingService(stubService);

        // test
        Movie movie = new Movie();
        movie.setTitle("ABCDEFGHIJKLM");
        assertThrows(NoMovieFoundException.class, () -> ratingService.loadRatingForMovie(movie));
    }

    @Test
    void testServiceNotAvailableException() {
        // configure stub
        ArrayList<Movie> movies = new ArrayList<>();
        Movie m = TestData.getTestMovie();
        m.setRating(null);
        movies.add(m);
        MovieDataService stubService = new MovieDataServiceStub(movies);
        ratingService = new SimpleRatingService(stubService);

        // test
        Movie movie = new Movie();
        movie.setTitle("Bambi");
        assertThrows(ServiceNotAvailableException.class, () -> ratingService.loadRatingForMovie(movie));
    }

    @Test
    void testRatingValueIsCorrect() throws NoMovieFoundException, TooManyMoviesFound, ServiceNotAvailableException {
        // configure stub
        ArrayList<Movie> movies = new ArrayList<>();
        Movie m = TestData.getTestMovie();
        m.setTitle("Bambi");
        m.setRating(3.2f);
        m.setYearPublished(Year.of(1942));
        movies.add(m);
        MovieDataService stubService = new MovieDataServiceStub(movies);
        ratingService = new SimpleRatingService(stubService);

        // test
        Movie movie = new Movie();
        movie.setTitle("Bambi");
        movie.setYearPublished(Year.of(1942));
        assertThat(ratingService.loadRatingForMovie(movie)).isEqualTo(3.2f);
    }
}

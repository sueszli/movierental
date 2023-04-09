package service.simpleRatingService;

import at.ac.tuwien.qs.movierental.entity.Movie;
import at.ac.tuwien.qs.movierental.exceptions.NoMovieFoundException;
import at.ac.tuwien.qs.movierental.exceptions.TooManyMoviesFound;
import at.ac.tuwien.qs.movierental.service.RatingService;
import at.ac.tuwien.qs.movierental.service.SimpleRatingService;
import at.ac.tuwien.qs.movierental.service.TheMovieDbMovieDataService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestRatingService {

    private static RatingService ratingService;

    @BeforeAll
    public static void setUp() {
        ratingService = new SimpleRatingService(new TheMovieDbMovieDataService());
    }

    @Test
    void testTooManyMoviesFound() {
        Movie movie = new Movie();
        movie.setTitle("Bambi");
        assertThrows(TooManyMoviesFound.class, () -> ratingService.loadRatingForMovie(movie));
    }

    @Test
    void testNoMovieFoundException() {
        Movie movie = new Movie();
        movie.setTitle("ABCDEFGHIJKLM");
        assertThrows(NoMovieFoundException.class, () -> ratingService.loadRatingForMovie(movie));
    }
}

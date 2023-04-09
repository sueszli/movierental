package service.simpleRatingService.mock;

import at.ac.tuwien.qs.movierental.entity.Movie;
import at.ac.tuwien.qs.movierental.exceptions.NoMovieFoundException;
import at.ac.tuwien.qs.movierental.exceptions.ServiceNotAvailableException;
import at.ac.tuwien.qs.movierental.exceptions.TooManyMoviesFound;
import at.ac.tuwien.qs.movierental.service.MovieDataService;
import at.ac.tuwien.qs.movierental.service.RatingService;
import at.ac.tuwien.qs.movierental.service.SimpleRatingService;
import at.ac.tuwien.qs.movierental.service.TheMovieDbMovieDataService;
import base.TestData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Year;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestRatingServiceMock {

    private static final MovieDataService MOVIE_DATA_SERVICE_MOCK = Mockito.mock(TheMovieDbMovieDataService.class);
    private static RatingService ratingService;

    @BeforeAll
    public static void setUp() {
        ratingService = new SimpleRatingService(MOVIE_DATA_SERVICE_MOCK);
    }

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTooManyMoviesFound() throws ServiceNotAvailableException {
        // configure mock
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(TestData.getLOTR1());
        movies.add(TestData.getLOTR2());
        Mockito.when(MOVIE_DATA_SERVICE_MOCK.searchMovies(Mockito.any(Movie.class))).thenReturn(movies);

        // test
        Movie movie = new Movie();
        movie.setTitle("Bambi");
        assertThrows(TooManyMoviesFound.class, () -> ratingService.loadRatingForMovie(movie));
    }

    @Test
    void testNoMovieFoundException() throws ServiceNotAvailableException {
        // configure mock
        Mockito.when(MOVIE_DATA_SERVICE_MOCK.searchMovies(Mockito.any(Movie.class))).thenReturn(new ArrayList<>());

        // test
        Movie movie = new Movie();
        movie.setTitle("ABCDEFGHIJKLM");
        assertThrows(NoMovieFoundException.class, () -> ratingService.loadRatingForMovie(movie));
        Mockito.verify(MOVIE_DATA_SERVICE_MOCK, Mockito.times(1)).searchMovies(movie);
    }

    @Test
    void testServiceNotAvailableException() throws ServiceNotAvailableException {
        // configure mock
        ArrayList<Movie> movies = new ArrayList<>();
        Movie m = TestData.getTestMovie();
        m.setRating(null);
        movies.add(m);
        Mockito.when(MOVIE_DATA_SERVICE_MOCK.searchMovies(Mockito.any(Movie.class))).thenReturn(movies);

        // test
        Movie movie = new Movie();
        movie.setTitle("Bambi");
        assertThrows(ServiceNotAvailableException.class, () -> ratingService.loadRatingForMovie(movie));
        Mockito.verify(MOVIE_DATA_SERVICE_MOCK, Mockito.times(1)).searchMovies(movie);
    }

    @Test
    void testRatingValueIsCorrect() throws NoMovieFoundException, TooManyMoviesFound, ServiceNotAvailableException {
        // configure mock
        ArrayList<Movie> movies = new ArrayList<>();
        Movie m = TestData.getTestMovie();
        m.setTitle("Bambi");
        m.setRating(3.2f);
        m.setYearPublished(Year.of(1942));
        movies.add(m);
        Mockito.when(MOVIE_DATA_SERVICE_MOCK.searchMovies(Mockito.any(Movie.class))).thenReturn(movies);

        // test
        Movie movie = new Movie();
        movie.setTitle("Bambi");
        movie.setYearPublished(Year.of(1942));
        assertThat(ratingService.loadRatingForMovie(movie)).isEqualTo(3.2f);
        Mockito.verify(MOVIE_DATA_SERVICE_MOCK, Mockito.times(1)).searchMovies(movie);
    }
}

package at.ac.tuwien.qs.movierental.service;

import at.ac.tuwien.qs.movierental.entity.Movie;
import at.ac.tuwien.qs.movierental.exceptions.NoMovieFoundException;
import at.ac.tuwien.qs.movierental.exceptions.ServiceNotAvailableException;
import at.ac.tuwien.qs.movierental.exceptions.TooManyMoviesFound;

import java.util.List;

public class SimpleRatingService implements RatingService {

    private final MovieDataService MOVIE_DATA_SERVICE;

    public SimpleRatingService(MovieDataService movieDataService) {
        this.MOVIE_DATA_SERVICE = movieDataService;
    }

    @Override
    public Float loadRatingForMovie(Movie movie) throws TooManyMoviesFound, NoMovieFoundException, ServiceNotAvailableException {
        List<Movie> movieList = MOVIE_DATA_SERVICE.searchMovies(movie);

        if (movieList.isEmpty()) {
            throw new NoMovieFoundException();
        } else if (movieList.size() > 1) {
            throw new TooManyMoviesFound();
        } else {
            Movie ratedMovie = movieList.get(0);
            if (ratedMovie.getRating() == null || ratedMovie.getRating() < 0 || ratedMovie.getRating() > 5) {
                throw new ServiceNotAvailableException("The service returned an unexpected Value.");
            }
            return ratedMovie.getRating();
        }
    }
}

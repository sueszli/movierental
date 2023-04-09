package service.simpleRatingService.stub;

import at.ac.tuwien.qs.movierental.entity.Movie;
import at.ac.tuwien.qs.movierental.exceptions.ServiceNotAvailableException;
import at.ac.tuwien.qs.movierental.service.MovieDataService;

import java.util.List;

public class MovieDataServiceStub implements MovieDataService {

    private final List<Movie> MOVIES;

    public MovieDataServiceStub(List<Movie> movies) {
        this.MOVIES = movies;
    }

    @Override
    public List<Movie> searchMovies(Movie movie) throws ServiceNotAvailableException {
        return MOVIES;
    }
}

package at.ac.tuwien.qs.movierental.exceptions;

public class TooManyMoviesFound extends Exception {
    public TooManyMoviesFound(String message) {
        super(message);
    }

    public TooManyMoviesFound() {
        super();
    }

    public TooManyMoviesFound(Throwable t) {
        super(t);
    }
}
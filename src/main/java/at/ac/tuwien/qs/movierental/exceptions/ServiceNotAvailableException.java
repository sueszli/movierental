package at.ac.tuwien.qs.movierental.exceptions;

public class ServiceNotAvailableException extends Exception {
    public ServiceNotAvailableException(String message) {
        super(message);
    }

    public ServiceNotAvailableException() {
        super();
    }

    public ServiceNotAvailableException(Throwable t) {
        super(t);
    }
}

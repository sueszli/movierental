package at.ac.tuwien.qs.movierental.app;

import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.entity.Movie;
import at.ac.tuwien.qs.movierental.repository.DummyCustomerDAO;
import at.ac.tuwien.qs.movierental.repository.DummyMovieDAO;
import at.ac.tuwien.qs.movierental.repository.DummyRentalDAO;
import at.ac.tuwien.qs.movierental.service.*;
import at.ac.tuwien.qs.movierental.ui.controller.CustomerManagementController;
import at.ac.tuwien.qs.movierental.ui.controller.MovieManagementController;
import at.ac.tuwien.qs.movierental.ui.controller.MovieRentalController;
import at.ac.tuwien.qs.movierental.ui.controller.RentalController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

public class App extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private MovieRentalController movieRentalController; // root controller
    private ObservableList<Movie> movies;
    private ObservableList<Customer> customers;
    private DummyMovieDAO movieDAO;
    private DummyCustomerDAO customerDAO;
    private DummyRentalDAO rentalDAO;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // set window title and icon
        LOG.info("Videorental application started");
        primaryStage.setTitle("Filmverleih");
        primaryStage.getIcons().add(
            new Image(
                Objects.requireNonNull(this.getClass().getResourceAsStream("/images/rent.png"))
            )
        );

        // initialize dummy objects to generate data (via their constructor)
        movieDAO = new DummyMovieDAO();
        movies = FXCollections.observableArrayList(movieDAO.read());
        customerDAO = new DummyCustomerDAO();
        customers = FXCollections.observableArrayList(customerDAO.read());
        rentalDAO = new DummyRentalDAO(movieDAO, customerDAO);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("/fxml/movieRental.fxml"));
        Parent root = fxmlLoader.load();

        this.movieRentalController = fxmlLoader.getController();
        this.movieRentalController.setPrimaryStage(primaryStage);

        // initialize the 3 tabs
        setupMovieManagement();
        setupCustomerManagement();
        setupRental();

        primaryStage.setScene(new Scene(root, 1024, 768));
        primaryStage.show();
    }

    // setup methods ::
    private void setupMovieManagement() throws java.io.IOException {
        LOG.info("setup MovieManagement");
        MovieDataService movieDataService = new TheMovieDbMovieDataService();
        RatingService ratingService = new SimpleRatingService(movieDataService);

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/movieManagement.fxml"));
        this.movieRentalController.setMoviesTabContent(fxmlLoader.load());
        MovieManagementController movieManagementController = fxmlLoader.getController();
        movieManagementController.setMovies(this.movies);
        movieManagementController.setMovieDAO(this.movieDAO);
        movieManagementController.setRatingService(ratingService);
    }

    private void setupCustomerManagement() throws java.io.IOException {
        LOG.info("setup CustomerManagement");

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/customerManagement.fxml"));
        this.movieRentalController.setCustomersTabContent(fxmlLoader.load());
        CustomerManagementController customerManagementController = fxmlLoader.getController();
        customerManagementController.setCustomers(this.customers);
        customerManagementController.setCustomerDAO(this.customerDAO);
    }

    private void setupRental() throws java.io.IOException {
        LOG.info("setup Rentals");
        InvoiceService invoiceService = new SimpleInvoiceService();

        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/rental.fxml"));
        this.movieRentalController.setRentalTabContent(fxmlLoader.load());
        RentalController rentalController = fxmlLoader.getController();
        rentalController.setMovies(this.movies);
        rentalController.setCustomers(this.customers);
        rentalController.setRentalDAO(this.rentalDAO);
        rentalController.setInvoiceService(invoiceService);
    }
    // :: setup methods
}

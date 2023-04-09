package at.ac.tuwien.qs.movierental.ui.controller;

import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.entity.Movie;
import at.ac.tuwien.qs.movierental.entity.Rental;
import at.ac.tuwien.qs.movierental.exceptions.ValidationException;
import at.ac.tuwien.qs.movierental.repository.RentalDAO;
import at.ac.tuwien.qs.movierental.service.InvoiceService;
import at.ac.tuwien.qs.movierental.service.validation.RentalValidation;
import at.ac.tuwien.qs.movierental.ui.controls.RentalActionButton;
import at.ac.tuwien.qs.movierental.ui.controls.TemporalAccessorCell;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RentalController {

    private static final Image CUSTOMER_SELECTED = new Image(RentalController.class.getResourceAsStream("/images/deselectUser.png"));
    private static final Image NO_CUSTOMER_SELECTED = new Image(RentalController.class.getResourceAsStream("/images/selectUser.png"));
    private final ObservableList<Rental> RENTAL_OBSERVABLE_LIST = FXCollections.observableArrayList();
    private final ObservableList<Rental> RENTALS_TO_PAY = FXCollections.observableArrayList();
    private final ObservableList<Rental> NEW_RENTALS = FXCollections.observableArrayList();

    @FXML
    private TextField txtSearchCustomer;
    @FXML
    private ListView<Customer> lstCustomers;
    @FXML
    private ImageView imgSelectCustomer;
    @FXML
    private ToggleButton tglSelectCustomer;
    @FXML
    private TextField txtSearchMovie;
    @FXML
    private ListView<Movie> lstMovies;
    @FXML
    private Button btnAddMovie;
    @FXML
    private TableView<Rental> tblRentalOverview;
    @FXML
    private TableColumn<Rental, Long> tcMovieID;
    @FXML
    private TableColumn<Rental, String> tcTitle;
    @FXML
    private TableColumn<Rental, LocalDate> tcLentDate;
    @FXML
    private TableColumn<Rental, Rental> tcAction;
    @FXML
    private TextArea txtInvoice;
    @FXML
    private Button btnCompleteRental;
    private FilteredList<Movie> filteredMovies;
    private FilteredList<Customer> filteredCustomers;
    private Customer currentCustomer;
    private RentalDAO rentalDAO;
    private InvoiceService invoiceService;

    @FXML
    private void initialize() {
        // init table columns
        this.tcMovieID.setCellValueFactory(cellData -> cellData.getValue().getMovie().idProperty());
        this.tcTitle.setCellValueFactory(cellData -> cellData.getValue().getMovie().titleProperty());
        this.tcLentDate.setCellValueFactory(cellData -> cellData.getValue().dateLentProperty());
        this.tcLentDate.setCellFactory(param -> new TemporalAccessorCell(DateTimeFormatter.ofPattern("dd. LLL yyyy")));
        this.tcAction.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue()));
        this.tcAction.setCellFactory(param -> new RentalActionButton(rental -> this.removeRentalButtonPressed((Rental) rental), rental -> this.payRentalButtonPressed((Rental) rental)));
        this.txtSearchMovie.textProperty().addListener((observable, oldValue, newValue) -> filterMovieList(newValue));
        this.txtSearchCustomer.textProperty().addListener((observable, oldValue, newValue) -> filterCustomerList(newValue));
        this.tblRentalOverview.setItems(this.RENTAL_OBSERVABLE_LIST);
        // init customer and movie list and add button behaviour
        this.lstMovies.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.btnAddMovie.setDisable(newValue == null);
        });
        this.lstCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.tglSelectCustomer.setDisable(newValue == null);
        });
        // init customer list rendering
        this.lstCustomers.setCellFactory(param -> new ListCell<Customer>() {
            @Override
            protected void updateItem(Customer customer, boolean empty) {
            super.updateItem(customer, empty);
            if (customer == null || empty) {
                setText(null);
            } else {
                setText(customer.getId() + "; " + customer.getLastName().toUpperCase() + ",  " + customer.getFirstName());
            }
            }
        });
        // init movie list rendering
        this.lstMovies.setCellFactory(param -> new ListCell<Movie>() {
            @Override
            protected void updateItem(Movie movie, boolean empty) {
            super.updateItem(movie, empty);
            if (movie == null || empty) {
                setText(null);
            } else {
                setText(movie.getId() + "; " + movie.getTitle() + ((movie.getSubtitle() != null && !movie.getSubtitle().isEmpty()) ? " - " + movie.getSubtitle() : ""));
            }
            }
        });
        // add change listeners to pay/new- Rental lists to recalulcate the invoice
        this.RENTALS_TO_PAY.addListener((ListChangeListener<Rental>) c -> this.previewInvoice());
        this.NEW_RENTALS.addListener((ListChangeListener<Rental>) c -> this.previewInvoice());
    }

    // setter ::
    public void setRentalDAO(RentalDAO rentalDAO) {
        this.rentalDAO = rentalDAO;
    }

    public void setInvoiceService(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    public void setMovies(ObservableList<Movie> movieObservableList) {
        this.filteredMovies = new FilteredList<>(movieObservableList, p -> true);
        SortedList<Movie> sortedData = new SortedList<>(this.filteredMovies);
        this.lstMovies.setItems(sortedData);
    }

    public void setCustomers(ObservableList<Customer> customerObservableList) {
        this.filteredCustomers = new FilteredList<>(customerObservableList, p -> true);
        SortedList<Customer> sortedData = new SortedList<>(this.filteredCustomers);
        this.lstCustomers.setItems(sortedData);
    }
    // :: setter

    private void removeRentalButtonPressed(Rental rental) {
        this.RENTAL_OBSERVABLE_LIST.remove(rental);
        this.NEW_RENTALS.remove(rental);
    }

    private void payRentalButtonPressed(Rental rental) {
        if (RENTALS_TO_PAY.contains(rental)) {
            RENTALS_TO_PAY.remove(rental);
        } else {
            RENTALS_TO_PAY.add(rental);
        }
    }

    private void filterCustomerList(String filter) {
        this.filteredCustomers.setPredicate(Filter.getCustomerPredicate(filter));
    }

    private void filterMovieList(String filter) {
        this.filteredMovies.setPredicate(Filter.getMoviePredicate(filter));
    }

    public void selectCustomer() {
        if (tglSelectCustomer.isSelected()) {
            this.currentCustomer = this.lstCustomers.getSelectionModel().getSelectedItem();
            this.RENTAL_OBSERVABLE_LIST.setAll(this.rentalDAO.readByCustomer(this.currentCustomer));
            imgSelectCustomer.setImage(CUSTOMER_SELECTED);
        } else {
            this.currentCustomer = null;
            this.RENTAL_OBSERVABLE_LIST.clear();
            this.RENTALS_TO_PAY.clear();
            this.NEW_RENTALS.clear();
            imgSelectCustomer.setImage(NO_CUSTOMER_SELECTED);
        }
        this.btnAddMovie.setDisable(true);
        this.txtSearchCustomer.setDisable(!this.txtSearchCustomer.isDisabled());
        this.txtSearchMovie.setDisable(!this.txtSearchMovie.isDisabled());
        this.lstCustomers.setDisable(!this.lstCustomers.isDisabled());
        this.lstMovies.setDisable(!this.lstMovies.isDisabled());
        this.tblRentalOverview.setDisable(!this.tblRentalOverview.isDisabled());
        this.txtInvoice.setDisable(!this.txtInvoice.isDisabled());
        this.btnCompleteRental.setDisable(!this.btnCompleteRental.isDisabled());
        this.previewInvoice();
    }

    public void addMovie() {
        Rental rental = new Rental();
        rental.setCustomer(this.currentCustomer);
        rental.setMovie(this.lstMovies.getSelectionModel().getSelectedItem());
        rental.setDateLent(LocalDate.now());
        try {
            RentalValidation.validateRental(rental);
            this.RENTAL_OBSERVABLE_LIST.add(rental);
            this.NEW_RENTALS.add(rental);

        } catch (ValidationException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Filmverleih | Entleihfehler");
            alert.setHeaderText("Der Film kann nicht entliehen werden.");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    public void completeRental() {
        this.invoiceService.payInvoice(currentCustomer, this.RENTALS_TO_PAY, LocalDate.now()); // update customers videopoints
        for (Rental rental : this.NEW_RENTALS) {
            this.rentalDAO.create(rental);
        }
        for (Rental rental : this.RENTALS_TO_PAY) {
            this.rentalDAO.delete(rental);
            this.RENTAL_OBSERVABLE_LIST.remove(rental);
        }
        this.RENTAL_OBSERVABLE_LIST.clear();
        this.NEW_RENTALS.clear();
        this.RENTALS_TO_PAY.clear();
        this.RENTAL_OBSERVABLE_LIST.setAll(this.rentalDAO.readByCustomer(this.currentCustomer));
    }

    private void previewInvoice() {
        if (this.currentCustomer == null) {
            this.txtInvoice.setText("");
        } else {
            // get invoice txt from InvoiceService
            this.txtInvoice.setText(this.invoiceService.generateInvoicePreview(this.currentCustomer, RENTALS_TO_PAY, NEW_RENTALS, LocalDateTime.now()));
        }
    }
}

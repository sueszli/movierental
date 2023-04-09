package at.ac.tuwien.qs.movierental.ui.controller;

import at.ac.tuwien.qs.movierental.entity.Customer;
import at.ac.tuwien.qs.movierental.repository.CustomerDAO;
import at.ac.tuwien.qs.movierental.service.validation.CustomerValidation;
import at.ac.tuwien.qs.movierental.ui.controls.BooleanCell;
import at.ac.tuwien.qs.movierental.ui.controls.TemporalAccessorCell;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class CustomerManagementController {

    private static final Image NO_PHOTO = new Image(Objects.requireNonNull(RentalController.class.getResourceAsStream("/images/user.png")));
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd. MM. yyyy");

    @FXML
    private TableView<Customer> tblCustomers;
    @FXML
    private TableColumn<Customer, Long> tcCustomerId;
    @FXML
    private TableColumn<Customer, String> tcFirstName;
    @FXML
    private TableColumn<Customer, String> tcLastName;
    @FXML
    private TableColumn<Customer, String> tcEmail;
    @FXML
    private TableColumn<Customer, LocalDate> tcBirthday;
    @FXML
    private TableColumn<Customer, String> tcPhone;
    @FXML
    private TableColumn<Customer, String> tcZipCode;
    @FXML
    private TableColumn<Customer, Boolean> tcPatron;
    @FXML
    private TableColumn<Customer, Integer> tcVideopoints;
    @FXML
    private TableColumn<Customer, Integer> tcRent;
    @FXML
    private TableColumn<Customer, Integer> tcOverdue;
    @FXML
    private Label lblCustomerID;
    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtBirthday;
    @FXML
    private CheckBox chkPatron;
    @FXML
    private TextArea txtAddress;
    @FXML
    private TextField txtZipCode;
    @FXML
    private TextField txtCity;
    @FXML
    private Label lblVideopoints;
    @FXML
    private ImageView imgPhoto;
    @FXML
    private TextField txtFilter;
    @FXML
    private ToggleButton tglFilter;
    private File filePhoto;
    private Customer currentCustomer;
    private ObservableList<Customer> customerObservableList;
    private FilteredList<Customer> filteredCustomers;

    private CustomerDAO customerDAO;

    @FXML
    private void initialize() {
        this.showCustomerDetails(null);
        this.tcCustomerId.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        this.tcFirstName.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        this.tcLastName.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        this.tcEmail.setCellValueFactory(cellData -> cellData.getValue().emailProperty());
        this.tcBirthday.setCellValueFactory(cellData -> cellData.getValue().birthdayProperty());
        this.tcBirthday.setCellFactory(param -> new TemporalAccessorCell(DateTimeFormatter.ofPattern("dd. LLL yyyy")));
        this.tcPhone.setCellValueFactory(cellData -> cellData.getValue().phoneProperty());
        this.tcZipCode.setCellValueFactory(cellData -> cellData.getValue().zipCodeProperty());
        this.tcPatron.setCellValueFactory(cellData -> cellData.getValue().patronProperty());
        this.tcPatron.setCellFactory(param -> new BooleanCell());
        this.tcVideopoints.setCellValueFactory(cellData -> cellData.getValue().videopointsProperty());
        this.txtFilter.textProperty().addListener((observable, oldValue, newValue) -> {
            this.tglFilter.setSelected(newValue != null && !newValue.isEmpty());
            filterTable(newValue);
        });
        this.tblCustomers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showCustomerDetails(newValue));
    }

    // setter ::
    public void setCustomers(ObservableList<Customer> customerObservableList) {
        this.customerObservableList = customerObservableList;
        this.tblCustomers.setItems(this.customerObservableList);
        this.filteredCustomers = new FilteredList<>(this.customerObservableList, p -> true);
        SortedList<Customer> sortedData = new SortedList<>(this.filteredCustomers);
        sortedData.comparatorProperty().bind(tblCustomers.comparatorProperty());
        tblCustomers.setItems(sortedData);
    }

    public void setCustomerDAO(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }
    // :: setter

    private void filterTable(String filter) {
        this.filteredCustomers.setPredicate(Filter.getCustomerPredicate(filter));
    }

    @FXML
    private void filter() {
        if (this.tglFilter.isSelected()) {
            this.filterTable(this.txtFilter.getText());
        } else {
            this.filterTable(null);
        }
    }

    @FXML
    private void showCustomerDetails(Customer customer) {
        this.currentCustomer = customer;
        if (customer != null) {
            this.lblCustomerID.setText((customer.getId() != null) ? customer.getId().toString() : "");
            this.txtFirstName.setText((customer.getFirstName() != null) ? customer.getFirstName() : "");
            this.txtLastName.setText((customer.getLastName() != null) ? customer.getLastName() : "");
            this.txtEmail.setText((customer.getEmail() != null) ? customer.getEmail() : "");
            this.txtPhone.setText((customer.getPhone() != null) ? customer.getPhone() : "");
            this.txtBirthday.setText((customer.getBirthday() != null) ? DATE_TIME_FORMATTER.format(customer.getBirthday()) : "");
            this.chkPatron.setSelected((customer.getPatron() != null) && customer.getPatron());
            this.txtAddress.setText((customer.getAddress() != null) ? customer.getAddress() : "");
            this.txtZipCode.setText((customer.getZipCode() != null) ? customer.getZipCode() : "");
            this.txtCity.setText((customer.getCity() != null) ? customer.getCity() : "");
            this.lblVideopoints.setText((customer.getVideopoints() != null) ? customer.getVideopoints().toString() : "0");
            try {
                this.filePhoto = customer.getPhoto();
                this.imgPhoto.setImage((this.filePhoto != null) ? new Image(new FileInputStream(this.filePhoto)) : NO_PHOTO);
            } catch (FileNotFoundException e) {
                this.imgPhoto.setImage(NO_PHOTO);
                this.filePhoto = null;
            }
        } else {
            this.lblCustomerID.setText("");
            this.txtFirstName.setText("");
            this.txtLastName.setText("");
            this.txtEmail.setText("");
            this.txtPhone.setText("");
            this.txtBirthday.setText("");
            this.chkPatron.setSelected(false);
            this.txtAddress.setText("");
            this.txtZipCode.setText("");
            this.txtCity.setText("");
            this.lblVideopoints.setText("0");
            this.imgPhoto.setImage(NO_PHOTO);
            this.filePhoto = null;
        }
    }

    @FXML
    private void selectPhoto(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Filmverleih | Lade Foto");
        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Bilddaten", "*.png", "*.jpg", "*.jepg"));
        File file = fileChooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        if (file != null && file.exists()) {
            try {
                this.imgPhoto.setImage(new Image(new FileInputStream(file)));
                this.filePhoto = file;
            } catch (FileNotFoundException e) {
                this.imgPhoto.setImage(NO_PHOTO);
            }
        }
    }

    @FXML
    private void delete() {
        Customer selectedCustomer = this.tblCustomers.getSelectionModel().getSelectedItem();
        this.customerObservableList.remove(selectedCustomer);
        this.tblCustomers.getSelectionModel().clearSelection();
    }

    @FXML
    private void persist() {
        String errorMessage = "";
        // validate birthdate before parsing text fields (checks for thrown exceptions while parsing)
        errorMessage += CustomerValidation.getBirthdateValidationError(this.txtBirthday.getText());

        Customer customer = readFieldData();

        errorMessage += CustomerValidation.getValidationErrors(customer);

        if (errorMessage.isEmpty()) {
            if (this.currentCustomer == null) {
                this.currentCustomer = this.customerDAO.create(customer);
                this.customerObservableList.add(currentCustomer);
            } else {
                this.customerDAO.update(customer);
            }
            this.tblCustomers.getSelectionModel().select(currentCustomer);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Filmverleih | Eingabefehler");
            alert.setHeaderText("Folgende Daten der Eingabe sind Fehlerhaft");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
    }

    @FXML
    private void reset() {
        this.tblCustomers.getSelectionModel().clearSelection();
        this.showCustomerDetails(null);
    }

    private Customer readFieldData() {
        Customer customer = currentCustomer;
        if (currentCustomer == null) {
            customer = new Customer();
        }
        customer.setFirstName(this.txtFirstName.getText());
        customer.setLastName(this.txtLastName.getText());
        customer.setEmail(this.txtEmail.getText());
        customer.setPhone(this.txtPhone.getText());
        customer.setBirthday((this.txtBirthday.getText() != null && !this.txtBirthday.getText().isEmpty())
            ? LocalDate.parse(this.txtBirthday.getText()
            .replace("\\s", "")
            .replace("(^\\d\\.)", "0$1")
            .replace("(\\.)(\\d\\.)", ".0$2")
            .replace("\\.", ". "), DATE_TIME_FORMATTER) : null);
        customer.setAddress(this.txtAddress.getText());
        customer.setZipCode(this.txtZipCode.getText());
        customer.setCity(this.txtCity.getText());
        customer.setPatron(this.chkPatron.isSelected());
        customer.setPhoto(this.filePhoto);
        return customer;
    }
}

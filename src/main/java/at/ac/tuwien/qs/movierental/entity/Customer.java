package at.ac.tuwien.qs.movierental.entity;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.File;
import java.time.LocalDate;

@ToString
@EqualsAndHashCode
public class Customer {

    private final ObjectProperty<Long> id = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> firstName = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> lastName = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> email = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> phone = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LocalDate> birthday = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> address = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> zipCode = new SimpleObjectProperty<>(null);
    private final ObjectProperty<String> city = new SimpleObjectProperty<>(null);
    private final ObjectProperty<Boolean> patron = new SimpleObjectProperty<>(false);
    private final ObjectProperty<Integer> videopoints = new SimpleObjectProperty<>(0);
    private final ObjectProperty<File> photo = new SimpleObjectProperty<>(null);

    public Long getId() {
        return id.get();
    }
    public void setId(Long id) {
        this.id.set(id);
    }
    public ObjectProperty<Long> idProperty() {
        return id;
    }

    public String getFirstName() {
        return firstName.get();
    }
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }
    public ObjectProperty<String> firstNameProperty() {
        return firstName;
    }

    public String getLastName() {
        return lastName.get();
    }
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }
    public ObjectProperty<String> lastNameProperty() {
        return lastName;
    }

    public String getEmail() {
        return email.get();
    }
    public void setEmail(String email) {
        this.email.set(email);
    }
    public ObjectProperty<String> emailProperty() {
        return email;
    }

    public String getPhone() {
        return phone.get();
    }
    public void setPhone(String phone) {
        this.phone.set(phone);
    }
    public ObjectProperty<String> phoneProperty() {
        return phone;
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }
    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }
    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public String getAddress() {
        return address.get();
    }
    public void setAddress(String address) {
        this.address.set(address);
    }
    public ObjectProperty<String> addressProperty() {
        return address;
    }

    public String getZipCode() {
        return zipCode.get();
    }
    public void setZipCode(String zipCode) {
        this.zipCode.set(zipCode);
    }
    public ObjectProperty<String> zipCodeProperty() {
        return zipCode;
    }

    public String getCity() {
        return city.get();
    }
    public void setCity(String city) {
        this.city.set(city);
    }
    public ObjectProperty<String> cityProperty() {
        return city;
    }

    public Boolean getPatron() {
        return patron.get();
    }
    public void setPatron(Boolean patron) {
        this.patron.set(patron);
    }
    public ObjectProperty<Boolean> patronProperty() {
        return patron;
    }

    public Integer getVideopoints() {
        return videopoints.get();
    }
    public void setVideopoints(Integer videopoints) {
        this.videopoints.set(videopoints);
    }
    public ObjectProperty<Integer> videopointsProperty() {
        return videopoints;
    }

    public File getPhoto() {
        return photo.get();
    }
    public void setPhoto(File photo) {
        this.photo.set(photo);
    }
    public ObjectProperty<File> photoProperty() {
        return photo;
    }
}

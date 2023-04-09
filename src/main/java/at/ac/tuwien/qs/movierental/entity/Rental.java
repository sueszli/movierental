package at.ac.tuwien.qs.movierental.entity;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@EqualsAndHashCode
public class Rental {

    private final ObjectProperty<Long> id = new SimpleObjectProperty<>(null);
    private final ObjectProperty<LocalDate> dateLent = new SimpleObjectProperty<>(null);
    @Getter @Setter private Movie movie;
    @Getter @Setter private Customer customer;

    public Long getId() {
        return id.get();
    }
    public void setId(Long id) {
        this.id.set(id);
    }
    public ObjectProperty<Long> idProperty() {
        return id;
    }

    public LocalDate getDateLent() {
        return dateLent.get();
    }
    public void setDateLent(LocalDate dateLent) {
        this.dateLent.set(dateLent);
    }
    public ObjectProperty<LocalDate> dateLentProperty() {
        return dateLent;
    }
}

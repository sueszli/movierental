package at.ac.tuwien.qs.movierental.ui.controls;

import at.ac.tuwien.qs.movierental.entity.Rental;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;

import java.util.function.Consumer;

public class RentalActionButton<T> extends TableCell<T, Rental> {

    private final Button PAY_RENTAL_BUTTON = new Button("Rückgabe JA/NEIN");
    private final Button REMOVE_BUTTON = new Button("Entfernen");

    public RentalActionButton(Consumer<Rental> removeRentalButtonPressed, Consumer<Rental> payRentalButtonPressed) {
        // Consumers are similar to lambdas.
        // If button gets pressed, call the consumer function with ".accept(Rental r)"
        PAY_RENTAL_BUTTON.setOnAction(event ->
            payRentalButtonPressed.accept(
                (Rental) RentalActionButton.this.getTableView().getItems().get(RentalActionButton.this.getIndex())
            )
        );
        REMOVE_BUTTON.setOnAction(event ->
            removeRentalButtonPressed.accept(
                (Rental) RentalActionButton.this.getTableView().getItems().get(RentalActionButton.this.getIndex())
            )
        );
    }

    @Override
    protected void updateItem(Rental item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setGraphic(null);
        } else {
            if (item.getId() != null) {
                setGraphic(PAY_RENTAL_BUTTON);
            } else {
                setGraphic(REMOVE_BUTTON);
            }
        }
    }
}

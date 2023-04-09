package at.ac.tuwien.qs.movierental.ui.controls;

import javafx.scene.control.TableCell;

public class BooleanCell<T> extends TableCell<T, Boolean> {

    @Override
    protected void updateItem(Boolean item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
        } else {
            if (item) {
                setText("\u2713"); // ✓
            } else {
                setText("\u274C"); // ❌
            }
        }
    }
}

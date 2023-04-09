package at.ac.tuwien.qs.movierental.ui.controls;

import javafx.scene.control.TableCell;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class TemporalAccessorCell<T> extends TableCell<T, TemporalAccessor> {

    private final DateTimeFormatter DATE_TIME_FORMATTER;

    public TemporalAccessorCell(DateTimeFormatter dateTimeFormatter) {
        this.DATE_TIME_FORMATTER = dateTimeFormatter;
    }

    @Override
    protected void updateItem(TemporalAccessor item, boolean empty) {
        super.updateItem(item, empty);
        if (item == null || empty) {
            setText(null);
        } else {
            if (DATE_TIME_FORMATTER == null) {
                setText(item.toString());
            } else {
                setText(DATE_TIME_FORMATTER.format(item));
            }
        }
    }
}

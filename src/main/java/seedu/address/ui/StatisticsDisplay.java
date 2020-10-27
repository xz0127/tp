package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
import seedu.address.model.AppointmentStatistics;

/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class StatisticsDisplay extends UiPart<Region> {

    private static final String FXML = "StatisticsDisplay.fxml";

    @FXML
    private TextArea statistics;

    /**
     * Creates a {@code StatisticsDisplay} with the given {@code AppointmentStatistics} to display.
     */
    public StatisticsDisplay(AppointmentStatistics stats) {
        super(FXML);
        statistics.setText(stats.toString());
    }

    public void setStatistics(String stats) {
        requireNonNull(stats);
        statistics.setText(stats);
    }

}

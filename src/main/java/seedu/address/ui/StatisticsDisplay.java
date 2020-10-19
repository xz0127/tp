package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Region;
/**
 * A ui for the status bar that is displayed at the header of the application.
 */
public class StatisticsDisplay extends UiPart<Region> {

    private static final String FXML = "StatisticsDisplay.fxml";

    @FXML
    private TextArea stats;

    public StatisticsDisplay() {
        super(FXML);
    }

}

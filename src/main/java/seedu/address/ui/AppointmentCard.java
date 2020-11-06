package seedu.address.ui;

import java.time.LocalDate;
import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import seedu.address.model.appointment.Appointment;

/**
 * An UI component that displays information of an {@code Appointment}.
 */
public class AppointmentCard extends UiPart<Region> {

    private static final String FXML = "AppointmentListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Appointment appointment;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label status;
    @FXML
    private Label name;
    @FXML
    private Label contactNumber;
    @FXML
    private Label id;

    /**
     * Creates a {@code AppointmentCard} with the given {@code Patient} to display.
     */
    public AppointmentCard(Appointment appointment, int displayedIndex) {
        super(FXML);
        this.appointment = appointment;
        id.setText(displayedIndex + ". ");
        date.setText(appointment.getDate().toString());
        time.setText(appointment.getStartTime() + " - " + appointment.getEndTime());
        name.setText("Name: " + appointment.getPatient().getName());
        contactNumber.setText("Contact: " + appointment.getPatient().getPhone());
        updateStatus();
    }

    private void setStatus(String text, Color bgColor) {
        status.setText("Status: " + text);
        status.setBackground(new Background(new BackgroundFill(bgColor, new CornerRadii(5), null)));

    }

    private void updateStatus() {
        if (appointment.getIsDoneStatus()) {
            setStatus("Done", Color.DARKSLATEBLUE);
            ;
            return;
        }

        LocalTime currTime = LocalTime.now();
        LocalDate currDate = LocalDate.now();

        LocalTime startTime = appointment.getStartTime().getTime();
        LocalTime endTime = appointment.getEndTime().getTime();
        LocalDate date = appointment.getDate().getDate();

        if (date.isBefore(currDate)) {
            setStatus("Expired", Color.FIREBRICK);
        } else if (date.isAfter(currDate)) {
            setStatus("Upcoming", Color.GREEN);
        } else if (startTime.isAfter(currTime)) {
            setStatus("Upcoming", Color.GREEN);
        } else if (endTime.isBefore(currTime)) {
            setStatus("Expired", Color.FIREBRICK);
        } else {
            setStatus("Ongoing", Color.DARKORANGE);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AppointmentCard)) {
            return false;
        }

        // state check
        AppointmentCard card = (AppointmentCard) other;
        return appointment.equals(card.appointment);
    }
}

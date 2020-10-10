package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
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
    private Label name;
    @FXML
    private Label contactNumber;

    /**
     * Creates a {@code AppointmentCard} with the given {@code Patient} to display.
     */
    public AppointmentCard(Appointment appointment) {
        super(FXML);
        this.appointment = appointment;
        date.setText("Date: " + appointment.getDate());
        time.setText("Time: " + appointment.getStartTime() + " - " + appointment.getEndTime());
        name.setText("Name: " + appointment.getPatientId());
        // todo: get name for appointment
        contactNumber.setText("Contact: " + appointment.getPatientId());
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
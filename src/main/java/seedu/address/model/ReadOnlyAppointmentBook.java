package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;

/**
 * Unmodifiable view of an appointment book
 */
public interface ReadOnlyAppointmentBook {

    /**
     * Returns an unmodifiable view of the appointments list.
     * This list will not contain any overlapping appointments.
     */
    ObservableList<Appointment> getAppointmentList();

    /**
     * Returns the statistics of the appointment book.
     */
    AppointmentStatistics getAppointmentBookStatistics();
}

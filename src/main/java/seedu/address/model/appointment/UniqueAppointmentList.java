package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;


/**
 * A list of appointments that enforces uniqueness between its elements and does not allow nulls.
 * An appointment is considered unique by comparing using {@code Appointment#isOverlapping(Appointment)}.
 * As such, adding and updating of appointment uses Appointment#isOverlapping(Appointment) for equality so as
 * to ensure that the appointment being added or updated is unique in terms of identity in the UniqueAppointmentList.
 * However, the removal of a person uses Appointment#equals(Object) so as to ensure that the person with
 * exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Appointment#isOverlapping(Appointment)
 */
public class UniqueAppointmentList {

    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList.sorted(new AppointmentComparator()));

    /**
     * Returns true if the list has an overlapping appointment
     */
    public boolean overlaps(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlapping);
    }

    /**
     * Adds an appointment to the list.
     * The appointment must not overlap with existing appointments in the list.
     */
    public void add(Appointment toAdd) {
        requireNonNull(toAdd);
        if (overlaps(toAdd)) {
            throw new OverlappingAppointmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Replaces the contents of this list with {@code appointments}.
     * {@code appointments} must not contain overlapping appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        requireAllNonNull(appointments);
        if (!appointmentsAreNotOverlapping(appointments)) {
            throw new OverlappingAppointmentException();
        }

        internalList.setAll(appointments);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAppointmentList // instanceof handles nulls
                && internalList.equals(((UniqueAppointmentList) other).internalList));
    }

    /**
     * Returns true if {@code appointments} contains only non-overlapping appointments.
     */
    private boolean appointmentsAreNotOverlapping(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            for (int j = i + 1; j < appointments.size(); j++) {
                if (appointments.get(i).isOverlapping(appointments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

package seedu.address.model.appointment;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

}

package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

/**
 * Represents the {@code Comparator} class for Appointment.
 */
public class AppointmentComparator implements Comparator<Appointment> {
    @Override
    public int compare(Appointment a1, Appointment a2) {
        requireAllNonNull(a1, a2);

        if (a1.isBefore(a2)) {
            return -1;
        }

        if (a1.isAfter(a2)) {
            return 1;
        }

        assert a1.isOverlapping(a2);
        return 0;
    }
}

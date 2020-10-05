package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;

/**
 * Represents the {@code Comparator} class for Appointment.
 * Guarantees: Does not compare overlapping appointments.
 */
public class AppointmentComparator implements Comparator<Appointment> {
    @Override
    public int compare(Appointment a1, Appointment a2) {
        requireAllNonNull(a1, a2);

        if (a1.isBefore(a2)) {
            return -1;
        }

        if (a2.isAfter(a2)) {
            return 1;
        }

        // Does not handle overlapping appointments
        assert a1.equals(a2);
        return 0;
    }
}

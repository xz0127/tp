package seedu.address.model.appointment.exceptions;

/**
 * Signals that the operation will result in overlapping appointments (Appointments are considered overlapped if they
 * have the overlapping time slot).
 */
public class OverlappingAppointmentException extends RuntimeException {
    public OverlappingAppointmentException() {
        super("Operation would result in overlapping appointments");
    }
}

package seedu.address.model.patient.exceptions;

/**
 * Signals that the operation is unable to find the specified patient.
 */
public class PatientNotFoundException extends RuntimeException {
    public PatientNotFoundException() {
        super("The patient is not found in the patient book");
    }
}

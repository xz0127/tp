package seedu.address.testutil;

import seedu.address.model.PatientBook;
import seedu.address.model.patient.Patient;

/**
 * A utility class to help with building Patientbook objects.
 * Example usage: <br>
 *     {@code PatientBook pb = new PatientBookBuilder().withPatient("John", "Doe").build();}
 */
public class PatientBookBuilder {

    private PatientBook patientBook;

    public PatientBookBuilder() {
        patientBook = new PatientBook();
    }

    public PatientBookBuilder(PatientBook patientBook) {
        this.patientBook = patientBook;
    }

    /**
     * Adds a new {@code Patient} to the {@code PatientBook} that we are building.
     */
    public PatientBookBuilder withPatient(Patient patient) {
        patientBook.addPatient(patient);
        return this;
    }

    public PatientBook build() {
        return patientBook;
    }
}

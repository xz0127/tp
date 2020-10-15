package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PatientBook;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.model.patient.Patient;

/**
 * An Immutable PatientBook that is serializable to JSON format.
 */
@JsonRootName(value = "patientbook")
class JsonSerializablePatientBook {

    public static final String MESSAGE_DUPLICATE_PATIENT = "Patients list contains duplicate patient(s).";

    private final List<JsonAdaptedPatient> patients = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializablePatientBook} with the given patients.
     */
    @JsonCreator
    public JsonSerializablePatientBook(@JsonProperty("patients") List<JsonAdaptedPatient> patients) {
        this.patients.addAll(patients);
    }

    /**
     * Converts a given {@code ReadOnlyPatientBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializablePatientBook}.
     */
    public JsonSerializablePatientBook(ReadOnlyPatientBook source) {
        patients.addAll(source.getPatientList().stream().map(JsonAdaptedPatient::new).collect(Collectors.toList()));
    }

    /**
     * Converts this patient book into the model's {@code PatientBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public PatientBook toModelType() throws IllegalValueException {
        PatientBook patientBook = new PatientBook();
        for (JsonAdaptedPatient jsonAdaptedPatient : patients) {
            Patient patient = jsonAdaptedPatient.toModelType();
            if (patientBook.hasPatient(patient)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PATIENT);
            }
            patientBook.addPatient(patient);
        }
        return patientBook;
    }

}

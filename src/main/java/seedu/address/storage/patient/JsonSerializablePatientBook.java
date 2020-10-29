package seedu.address.storage.patient;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.PatientBook;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.model.patient.Patient;
import seedu.address.storage.StorageStatsManager;

/**
 * An Immutable PatientBook that is serializable to JSON format.
 */
@JsonRootName(value = "patientbook")
class JsonSerializablePatientBook {

    public static final String MESSAGE_DUPLICATE_PATIENT = "Patient list contains duplicate patient(s).";

    private static final Logger logger = LogsCenter.getLogger(JsonSerializablePatientBook.class);

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
     */
    public PatientBook toModelType(StorageStatsManager statsManager) {
        PatientBook patientBook = new PatientBook();
        int nDataViolations = 0;

        for (JsonAdaptedPatient jsonAdaptedPatient : patients) {
            Patient patient;
            try {
                patient = jsonAdaptedPatient.toModelType();
            } catch (IllegalValueException ive) {
                logger.info("Data constraints violated: " + ive.getMessage());
                nDataViolations++;
                continue;
            }

            if (patientBook.hasPatient(patient)) {
                logger.info(MESSAGE_DUPLICATE_PATIENT);
                nDataViolations++;
                continue;
            }

            patientBook.addPatient(patient);
        }
        if (nDataViolations > 0) {
            logger.warning("Failed to read " + nDataViolations + " patient data!");
            statsManager.setRemovedPatientCount(nDataViolations);
        }
        return patientBook;
    }

}

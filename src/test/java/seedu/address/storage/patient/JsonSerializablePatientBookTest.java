package seedu.address.storage.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalPatients.ALICE;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.PatientBook;
import seedu.address.storage.StorageStatsManager;
import seedu.address.testutil.TypicalPatients;

public class JsonSerializablePatientBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializablePatientBookTest");
    private static final Path TYPICAL_PATIENTS_FILE = TEST_DATA_FOLDER.resolve("typicalPatientBook.json");
    private static final Path INVALID_PATIENT_FILE = TEST_DATA_FOLDER.resolve("invalidPatientBook.json");
    private static final Path DUPLICATE_PATIENT_FILE = TEST_DATA_FOLDER.resolve("duplicatePatientBook.json");

    @Test
    public void toModelType_typicalPatientsFile_success() throws Exception {
        JsonSerializablePatientBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PATIENTS_FILE,
                JsonSerializablePatientBook.class).get();
        PatientBook patientBookFromFile = dataFromFile.toModelType(new StorageStatsManager());
        PatientBook typicalPatientsPatientBook = TypicalPatients.getTypicalPatientBook();
        assertEquals(patientBookFromFile, typicalPatientsPatientBook);
    }

    @Test
    public void toModelType_invalidPatientFile_returnsEmptyPatientBook() throws Exception {
        JsonSerializablePatientBook dataFromFile = JsonUtil.readJsonFile(INVALID_PATIENT_FILE,
                JsonSerializablePatientBook.class).get();
        PatientBook patientBookFromFile = dataFromFile.toModelType(new StorageStatsManager());
        PatientBook expectedPatientBook = new PatientBook();
        assertEquals(patientBookFromFile, expectedPatientBook);
    }

    @Test
    public void toModelType_duplicatePatients_returnsOnePatientInBook() throws Exception {
        JsonSerializablePatientBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PATIENT_FILE,
                JsonSerializablePatientBook.class).get();
        PatientBook patientBookFromFile = dataFromFile.toModelType(new StorageStatsManager());
        PatientBook expectedPatientBook = new PatientBook();
        expectedPatientBook.addPatient(ALICE);
        assertEquals(patientBookFromFile, expectedPatientBook);
    }

}

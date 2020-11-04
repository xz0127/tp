package seedu.address.storage.patient;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.HOON;
import static seedu.address.testutil.TypicalPatients.IDA;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.PatientBook;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.model.patient.Patient;
import seedu.address.storage.StorageStatsManager;
import seedu.address.testutil.PatientBuilder;

public class JsonPatientBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonPatientBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readPatientBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readPatientBook(null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readPatientBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readPatientBook("notJsonFormatPatientBook.json"));
    }

    @Test
    public void readPatientBook_allInvalidPatientBook_returnEmptyBook() throws Exception {
        ReadOnlyPatientBook bookFromFile = readPatientBook("invalidPatientBook.json").get();
        assertEquals(bookFromFile, new PatientBook());
    }

    @Test
    public void readPatientBook_invalidAndValidPatientBook_returnUncorruptedValidBook() throws Exception {
        ReadOnlyPatientBook bookFromFile = readPatientBook("invalidAndValidPatientBook.json").get();

        PatientBook expectedBook = new PatientBook();
        Patient validPatient = new PatientBuilder().withName("Valid Patient")
                .withPhone("9482424").withAddress("4th street").withNric("S1234567I").build();
        expectedBook.addPatient(validPatient);

        assertEquals(bookFromFile, expectedBook);
    }

    @Test
    public void readAndSavePatientBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempPatientBook.json");
        PatientBook original = getTypicalPatientBook();
        StorageStatsManager statsManager = new StorageStatsManager();
        JsonPatientBookStorage jsonPatientBookStorage = new JsonPatientBookStorage(filePath, statsManager);

        // Save in new file and read back
        jsonPatientBookStorage.savePatientBook(original, filePath);
        ReadOnlyPatientBook readBack = jsonPatientBookStorage.readPatientBook(filePath).get();
        assertEquals(original, new PatientBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPatient(HOON);
        original.removePatient(ALICE);
        jsonPatientBookStorage.savePatientBook(original, filePath);
        readBack = jsonPatientBookStorage.readPatientBook(filePath).get();
        assertEquals(original, new PatientBook(readBack));

        // Save and read without specifying file path
        original.addPatient(IDA);
        jsonPatientBookStorage.savePatientBook(original); // file path not specified
        readBack = jsonPatientBookStorage.readPatientBook().get(); // file path not specified
        assertEquals(original, new PatientBook(readBack));

    }

    @Test
    public void saveBackupAndReadPatientBook_allInOrder_success() throws Exception {
        String jsonFileName = "TempPatientBook.json";
        String backupFolderName = "backup";

        Path filePath = testFolder.resolve(jsonFileName);
        PatientBook original = getTypicalPatientBook();
        JsonPatientBookStorage jsonPatientBookStorage =
                new JsonPatientBookStorage(filePath, new StorageStatsManager());

        Path backupFilePath = testFolder.resolve(backupFolderName + "/" + jsonFileName);
        // save and backup file and read back backup file
        jsonPatientBookStorage.savePatientBook(original, filePath);
        jsonPatientBookStorage.backupData(backupFolderName);
        ReadOnlyPatientBook readBack = jsonPatientBookStorage.readPatientBook(backupFilePath).get();
        assertEquals(original, new PatientBook(readBack));

        // Modify data, save, backup file, and read back backup file
        original.addPatient(HOON);
        original.removePatient(ALICE);
        jsonPatientBookStorage.savePatientBook(original, filePath);
        jsonPatientBookStorage.backupData(backupFolderName);
        readBack = jsonPatientBookStorage.readPatientBook(backupFilePath).get();
        assertEquals(original, new PatientBook(readBack));
    }

    @Test
    public void savePatientBook_nullPatientBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePatientBook(null, "SomeFile.json"));
    }

    @Test
    public void savePatientBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> savePatientBook(new PatientBook(), null));
    }

    @Test
    public void backupPatientBook_nullFileName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new JsonPatientBookStorage(TEST_DATA_FOLDER, new StorageStatsManager()).backupData(null));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    private java.util.Optional<ReadOnlyPatientBook> readPatientBook(String filePath) throws Exception {
        return new JsonPatientBookStorage(Paths.get(filePath), new StorageStatsManager())
                .readPatientBook(addToTestDataPathIfNotNull(filePath));
    }

    /**
     * Saves {@code patientBook} at the specified {@code filePath}.
     */
    private void savePatientBook(ReadOnlyPatientBook patientBook, String filePath) {
        try {
            new JsonPatientBookStorage(Paths.get(filePath), new StorageStatsManager())
                    .savePatientBook(patientBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

}

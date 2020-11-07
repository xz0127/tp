package seedu.address.storage.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.CARL_APPOINTMENT_2;
import static seedu.address.testutil.TypicalAppointments.HOON_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.IDA_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.storage.StorageStatsManager;
import seedu.address.testutil.AppointmentBuilder;

public class JsonAppointmentBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src",
            "test", "data", "JsonAppointmentBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAppointmentBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAppointmentBook(null, null));
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAppointmentBook("NonExistentFile.json", "archives").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readAppointmentBook(
                "notJsonFormatAppointmentBook.json", "archives"));
    }

    @Test
    public void readAppointmentBook_allInvalidAppointmentBook_returnEmptyBook() throws Exception {
        ReadOnlyAppointmentBook bookFromFile =
                readAppointmentBook("invalidAppointmentBook.json", "archives").get();
        assertEquals(bookFromFile, new AppointmentBook());
    }

    @Test
    public void readAppointmentBook_invalidAndValidAppointmentBook_returnUncorruptedValidBook() throws Exception {
        ReadOnlyAppointmentBook bookFromFile =
                readAppointmentBook("invalidAndValidAppointmentBook.json", "archives").get();

        AppointmentBook expectedBook = new AppointmentBook();
        expectedBook.addAppointment(CARL_APPOINTMENT_2);

        assertEquals(bookFromFile, expectedBook);
    }

    @Test
    public void readAppointmentBook_overlappingAppointmentBook_returnBookWithFirstOverlapped() throws Exception {
        ReadOnlyAppointmentBook bookFromFile =
                readAppointmentBook("overlappingAppointmentBook.json", "archives").get();

        AppointmentBook expectedBook = new AppointmentBook();
        Appointment firstAppointment = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(LocalTime.of(10, 0)).build();
        expectedBook.addAppointment(firstAppointment);

        assertEquals(bookFromFile, expectedBook);
    }

    @Test
    public void readAndSaveAppointmentBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAppointmentBook.json");
        Path archivePath = testFolder.resolve("archive");
        AppointmentBook original = getTypicalAppointmentBook();
        JsonAppointmentBookStorage jsonAppointmentBookStorage =
                new JsonAppointmentBookStorage(filePath, archivePath, new StorageStatsManager());

        // Save in new file and read back
        jsonAppointmentBookStorage.saveAppointmentBook(original, filePath);
        ReadOnlyAppointmentBook readBack = jsonAppointmentBookStorage.readAppointmentBook(filePath).get();
        assertEquals(original, new AppointmentBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addAppointment(HOON_APPOINTMENT);
        original.removeAppointment(ALICE_APPOINTMENT);
        jsonAppointmentBookStorage.saveAppointmentBook(original, filePath);
        readBack = jsonAppointmentBookStorage.readAppointmentBook(filePath).get();
        assertEquals(original, new AppointmentBook(readBack));

        // Save and read without specifying file path
        original.addAppointment(IDA_APPOINTMENT);
        jsonAppointmentBookStorage.saveAppointmentBook(original); // file path not specified
        readBack = jsonAppointmentBookStorage.readAppointmentBook().get(); // file path not specified
        assertEquals(original, new AppointmentBook(readBack));
    }

    @Test
    public void saveBackupAndReadAppointmentBook_allInOrder_success() throws Exception {
        String jsonFileName = "TempAppointmentBook.json";
        String backupFolderName = "backup";

        Path filePath = testFolder.resolve(jsonFileName);
        Path archivePath = testFolder.resolve("archive");
        AppointmentBook original = getTypicalAppointmentBook();
        JsonAppointmentBookStorage jsonAppointmentBookStorage =
                new JsonAppointmentBookStorage(filePath, archivePath, new StorageStatsManager());

        Path backupFilePath = testFolder.resolve(backupFolderName + "/" + jsonFileName);
        // save and backup file and read back backup file
        jsonAppointmentBookStorage.saveAppointmentBook(original, filePath);
        jsonAppointmentBookStorage.backupData(backupFolderName);
        ReadOnlyAppointmentBook readBack = jsonAppointmentBookStorage.readAppointmentBook(backupFilePath).get();
        assertEquals(original, new AppointmentBook(readBack));

        // Modify data, save, backup file, and read back backup file
        original.addAppointment(HOON_APPOINTMENT);
        original.removeAppointment(ALICE_APPOINTMENT);
        jsonAppointmentBookStorage.saveAppointmentBook(original, filePath);
        jsonAppointmentBookStorage.backupData(backupFolderName);
        readBack = jsonAppointmentBookStorage.readAppointmentBook(backupFilePath).get();
        assertEquals(original, new AppointmentBook(readBack));
    }

    @Test
    public void saveAppointmentBook_nullAppointmentBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAppointmentBook(null, "SomeFile.json"));
    }

    @Test
    public void saveAppointmentBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAppointmentBook(new AppointmentBook(), null));
    }

    @Test
    public void backupAppointmentBook_nullFileName_throwsNullPointerException() {
        assertThrows(NullPointerException.class, ()
            -> new JsonAppointmentBookStorage(TEST_DATA_FOLDER, TEST_DATA_FOLDER, new StorageStatsManager())
                .backupData(null));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    private java.util.Optional<ReadOnlyAppointmentBook> readAppointmentBook(String filePath, String archivePath)
            throws Exception {
        return new JsonAppointmentBookStorage(Paths.get(filePath), Paths.get(archivePath), new StorageStatsManager())
                .readAppointmentBook(addToTestDataPathIfNotNull(filePath));
    }

    /**
     * Saves {@code appointmentBook} at the specified {@code filePath}.
     */
    private void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook, String filePath) {
        try {
            new JsonAppointmentBookStorage(Paths.get(filePath), Paths.get(filePath), new StorageStatsManager())
                    .saveAppointmentBook(appointmentBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void archivePastAppointments_allInOrder_success() {
        Path filePath = testFolder.resolve("TempAppointmentBook.json");
        Path archivePath = testFolder.resolve("archive");
        AppointmentBook expected = getTypicalAppointmentBook();

        AppointmentBook original = new AppointmentBook(expected);
        Appointment expiredAppointment = new AppointmentBuilder(HOON_APPOINTMENT)
                .withDate(LocalDate.of(2019, 1, 1))
                .build();
        Appointment pastAppointment = new AppointmentBuilder(IDA_APPOINTMENT)
                .withDate(LocalDate.of(2019, 5, 1))
                .withDoneStatus(true).build();
        original.addAppointment(expiredAppointment);
        original.addAppointment(pastAppointment);

        StorageStatsManager statsManager = new StorageStatsManager();
        JsonAppointmentBookStorage jsonAppointmentBookStorage =
                new JsonAppointmentBookStorage(filePath, archivePath, statsManager);

        // Archive appointments
        ReadOnlyAppointmentBook filteredBook = jsonAppointmentBookStorage.archivePastAppointments(original);

        assertEquals(expected, new AppointmentBook(filteredBook));

        // Check archive status message
        String expectedMessage = "2 appointments archived, of which 1 is not done.";
        assertEquals(expectedMessage, statsManager.getArchiveStatusMessage());
    }
}

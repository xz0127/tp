package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.BENSON_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;

import java.nio.file.Path;
import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.AppointmentBook;
import seedu.address.model.PatientBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.storage.appointment.JsonAppointmentBookStorage;
import seedu.address.storage.patient.JsonPatientBookStorage;
import seedu.address.testutil.AppointmentBuilder;

public class StorageManagerTest {

    @TempDir
    public Path testFolder;

    private StorageManager storageManager;

    @BeforeEach
    public void setUp() {
        StorageStatsManager statsManager = new StorageStatsManager();
        JsonPatientBookStorage patientBookStorage =
                new JsonPatientBookStorage(getTempFilePath("pb"), statsManager);
        JsonAppointmentBookStorage appointmentBookStorage =
                new JsonAppointmentBookStorage(getTempFilePath("appt"), getTempFilePath("archive"),
                        statsManager);
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        storageManager = new StorageManager(patientBookStorage, appointmentBookStorage, userPrefsStorage, statsManager);
    }

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void prefsReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonUserPrefsStorage} class.
         * More extensive testing of UserPref saving/reading is done in {@link JsonUserPrefsStorageTest} class.
         */
        UserPrefs original = new UserPrefs();
        original.setGuiSettings(new GuiSettings(300, 600, 4, 6, 0.6));
        storageManager.saveUserPrefs(original);
        UserPrefs retrieved = storageManager.readUserPrefs().get();
        assertEquals(original, retrieved);
    }

    @Test
    public void patientBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonPatientBookStorage} class.
         * More extensive testing of PatientBook saving/reading is done in {@link JsonPatientBookStorageTest} class.
         */
        PatientBook original = getTypicalPatientBook();
        storageManager.savePatientBook(original);
        ReadOnlyPatientBook retrieved = storageManager.readPatientBook().get();
        assertEquals(original, new PatientBook(retrieved));
    }

    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }

    @Test
    public void getPatientBookFilePath() {
        assertNotNull(storageManager.getPatientBookFilePath());
    }

    @Test
    public void appointmentBookReadSave() throws Exception {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link JsonAppointmentBookStorage} class.
         * More extensive testing of AppointmentBook saving/reading is done in
         * {@link JsonAppointmentBookStorageTest} class.
         */
        AppointmentBook original = getTypicalAppointmentBook();
        storageManager.saveAppointmentBook(original);
        ReadOnlyAppointmentBook retrieved = storageManager.readAppointmentBook().get();
        assertEquals(original, new AppointmentBook(retrieved));
    }

    @Test
    public void getAppointmentBookFilePath() {
        assertNotNull(storageManager.getAppointmentBookFilePath());
    }

    @Test
    public void getAppointmentArchiveDirPath() {
        assertNotNull(storageManager.getAppointmentArchiveDirPath());
    }

    @Test
    public void getStatsManager() {
        assertNotNull(storageManager.getStatsManager());
    }

    @Test
    public void saveBackupRead() throws Exception {
        // save file (to be backed up)
        AppointmentBook appointmentBook = getTypicalAppointmentBook();
        PatientBook patientBook = getTypicalPatientBook();
        storageManager.saveAppointmentBook(appointmentBook);
        storageManager.savePatientBook(patientBook);

        // backup file
        storageManager.backupData();
        Path backupPatientBookPath = testFolder.resolve("backup").resolve("pb");
        Path backupAppointmentBookPath = testFolder.resolve("backup").resolve("appt");

        // check appointment backup
        ReadOnlyAppointmentBook retrievedAppointmentBackup =
                storageManager.readAppointmentBook(backupAppointmentBookPath).get();
        assertEquals(appointmentBook, new AppointmentBook(retrievedAppointmentBackup));

        // check patient backup
        ReadOnlyPatientBook retrievedPatientBackup =
                storageManager.readPatientBook(backupPatientBookPath).get();
        assertEquals(patientBook, retrievedPatientBackup);
    }

    @Test
    public void archiveAppointments_correctStatusMessage() {
        AppointmentBook appointmentBook = getTypicalAppointmentBook();
        Appointment overdueAppointment1 = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(LocalDate.now().minusDays(1))
                .withDoneStatus(true).build();
        Appointment overdueAppointment2 = new AppointmentBuilder(BENSON_APPOINTMENT)
                .withDate(LocalDate.of(2020, 1, 1))
                .withDoneStatus(false).build();

        appointmentBook.addAppointment(overdueAppointment1);
        appointmentBook.addAppointment(overdueAppointment2);
        storageManager.archivePastAppointments(appointmentBook);

        assertEquals(storageManager.getStatusMessage(), "2 appointments archived, of which 1 is not done.");
    }
}

package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of PatientBook and AppointmentBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private final PatientBookStorage patientBookStorage;
    private final AppointmentBookStorage appointmentBookStorage;
    private final UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code DataStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(PatientBookStorage patientBookStorage,
                          AppointmentBookStorage appointmentBookStorage,
                          UserPrefsStorage userPrefsStorage) {
        super();
        this.patientBookStorage = patientBookStorage;
        this.appointmentBookStorage = appointmentBookStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ PatientBook methods ==============================

    @Override
    public Path getPatientBookFilePath() {
        return patientBookStorage.getPatientBookFilePath();
    }

    @Override
    public Optional<ReadOnlyPatientBook> readPatientBook() throws DataConversionException, IOException {
        return readPatientBook(patientBookStorage.getPatientBookFilePath());
    }

    @Override
    public Optional<ReadOnlyPatientBook> readPatientBook(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return patientBookStorage.readPatientBook(filePath);
    }

    @Override
    public void savePatientBook(ReadOnlyPatientBook patientBook) throws IOException {
        savePatientBook(patientBook, patientBookStorage.getPatientBookFilePath());
    }

    @Override
    public void savePatientBook(ReadOnlyPatientBook patientBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        patientBookStorage.savePatientBook(patientBook, filePath);
    }


    // ================ AppointmentBook methods ==============================

    @Override
    public Path getAppointmentBookFilePath() {
        return appointmentBookStorage.getAppointmentBookFilePath();
    }

    @Override
    public Path getAppointmentArchiveDirPath() {
        return appointmentBookStorage.getAppointmentArchiveDirPath();
    }

    @Override
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook() throws DataConversionException, IOException {
        return readAppointmentBook(appointmentBookStorage.getAppointmentBookFilePath(),
                appointmentBookStorage.getAppointmentArchiveDirPath());
    }

    @Override
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook(Path filePath, Path archiveDirPath)
            throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return appointmentBookStorage.readAppointmentBook(filePath, archiveDirPath);
    }

    @Override
    public void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException {
        saveAppointmentBook(appointmentBook, appointmentBookStorage.getAppointmentBookFilePath());
    }

    @Override
    public void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        appointmentBookStorage.saveAppointmentBook(appointmentBook, filePath);
    }
}

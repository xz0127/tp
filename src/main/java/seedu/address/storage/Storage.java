package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.storage.appointment.AppointmentBookStorage;
import seedu.address.storage.patient.PatientBookStorage;

/**
 * API of the Storage component
 */
public interface Storage extends PatientBookStorage, AppointmentBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getPatientBookFilePath();

    @Override
    Optional<ReadOnlyPatientBook> readPatientBook() throws DataConversionException;

    @Override
    void savePatientBook(ReadOnlyPatientBook patientBook) throws IOException;

    @Override
    Path getAppointmentBookFilePath();

    @Override
    Optional<ReadOnlyAppointmentBook> readAppointmentBook() throws DataConversionException;

    @Override
    void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException;

    void backupData() throws IOException;

    @Override
    void backupData(String folderName) throws IOException;

    @Override
    StorageStatsManager getStatsManager();

    String getStatusMessage();
}

package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * API of the Storage component
 */
public interface Storage extends PatientBookStorage, AppointmentBookStorage, UserPrefsStorage {

    @Override
    Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException;

    @Override
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getPatientBookFilePath();

    @Override
    Optional<ReadOnlyPatientBook> readPatientBook() throws DataConversionException, IOException;

    @Override
    void savePatientBook(ReadOnlyPatientBook patientBook) throws IOException;

    @Override
    Path getAppointmentBookFilePath();

    @Override
    Optional<ReadOnlyAppointmentBook> readAppointmentBook() throws DataConversionException, IOException;

    @Override
    void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException;

}

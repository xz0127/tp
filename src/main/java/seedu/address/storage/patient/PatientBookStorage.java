package seedu.address.storage.patient;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.storage.StorageStatsManager;

/**
 * Represents a storage for {@link seedu.address.model.PatientBook}.
 */
public interface PatientBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getPatientBookFilePath();

    /**
     * Returns PatientBook data as a {@link ReadOnlyPatientBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataConversionException if there was any problem when reading from the storage.
     */
    Optional<ReadOnlyPatientBook> readPatientBook() throws DataConversionException;

    /**
     * @see #getPatientBookFilePath()
     */
    Optional<ReadOnlyPatientBook> readPatientBook(Path filePath) throws DataConversionException;

    /**
     * Saves the given {@link ReadOnlyPatientBook} to the storage.
     *
     * @param patientBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void savePatientBook(ReadOnlyPatientBook patientBook) throws IOException;

    /**
     * @see #savePatientBook(ReadOnlyPatientBook)
     */
    void savePatientBook(ReadOnlyPatientBook patientBook, Path filePath) throws IOException;

    /**
     * Makes a backup of the patient storage file.
     *
     * @param folderName the name of the backup folder.
     * @throws IOException if there was any problem writing to the backup file.
     */
    void backupData(String folderName) throws IOException;

    StorageStatsManager getStatsManager();
}

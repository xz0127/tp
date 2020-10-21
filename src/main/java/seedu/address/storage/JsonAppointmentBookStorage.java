package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAppointmentBook;

/**
 * A class to access AppointmentBook data stored as a json file on the hard disk.
 */
public class JsonAppointmentBookStorage implements AppointmentBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAppointmentBookStorage.class);

    private Path filePath;
    private Path archivePath;

    /**
     * @param filePath    the filePath for the appointment storage.
     * @param archivePath the directory path for the appointment archives.
     */
    public JsonAppointmentBookStorage(Path filePath, Path archivePath) {
        this.filePath = filePath;
        this.archivePath = archivePath;
    }

    public Path getAppointmentBookFilePath() {
        return filePath;
    }

    public Path getAppointmentArchiveDirPath() {
        return archivePath;
    }

    @Override
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook() throws DataConversionException {
        return readAppointmentBook(filePath, archivePath);
    }

    /**
     * Similar to {@link #readAppointmentBook()}.
     *
     * @param filePath       location of the data. Cannot be null.
     * @param archiveDirPath location of directory of appointment archives. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook(Path filePath, Path archiveDirPath)
            throws DataConversionException {
        requireNonNull(filePath);
        requireNonNull(archiveDirPath);

        AppointmentArchive appointmentArchive = new CsvAppointmentArchive(archiveDirPath);

        Optional<JsonSerializableAppointmentBook> jsonAppointmentBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAppointmentBook.class);
        if (jsonAppointmentBook.isEmpty()) {
            return Optional.empty();
        }

        Optional<AppointmentBook> book;
        try {
            book = Optional.of(jsonAppointmentBook.get().toModelType());
        } catch (IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new DataConversionException(ive);
        }

        return book.map(appointmentArchive::archiveAppointmentBook);
    }

    @Override
    public void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) throws IOException {
        saveAppointmentBook(appointmentBook, filePath);
    }

    /**
     * Similar to {@link #saveAppointmentBook(ReadOnlyAppointmentBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAppointmentBook(ReadOnlyAppointmentBook appointmentBook, Path filePath) throws IOException {
        requireNonNull(appointmentBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializableAppointmentBook(appointmentBook), filePath);
    }

    @Override
    public String getArchiveStatus() {
        return CsvAppointmentArchive.getArchiveStatistics();
    }

}

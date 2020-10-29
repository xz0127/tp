package seedu.address.storage.appointment;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.storage.StorageStatsManager;
import seedu.address.storage.archive.AppointmentArchive;
import seedu.address.storage.archive.CsvAppointmentArchive;

/**
 * A class to access AppointmentBook data stored as a json file on the hard disk.
 */
public class JsonAppointmentBookStorage implements AppointmentBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAppointmentBookStorage.class);

    private AppointmentArchive csvArchive;

    private Path filePath;
    private StorageStatsManager statsManager;

    /**
     * @param filePath the filePath for the appointment storage.
     * @param archivePath the directory path for the appointment archives.
     * @param statsManager the statistics handler for storage.
     */
    public JsonAppointmentBookStorage(Path filePath, Path archivePath, StorageStatsManager statsManager) {
        this.filePath = filePath;
        this.csvArchive = new CsvAppointmentArchive(archivePath, statsManager);
        this.statsManager = statsManager;
    }

    public Path getAppointmentBookFilePath() {
        return filePath;
    }

    /**
     * Returns the {@code Path} to the archive directory.
     */
    public Path getAppointmentArchiveDirPath() {
        return csvArchive.getArchiveDirectoryPath();
    }

    @Override
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook() throws DataConversionException {
        return readAppointmentBook(filePath);
    }

    /**
     * Similar to {@link #readAppointmentBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyAppointmentBook> readAppointmentBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAppointmentBook> jsonAppointmentBook = JsonUtil.readJsonFile(
                filePath, JsonSerializableAppointmentBook.class);
        if (jsonAppointmentBook.isEmpty()) {
            return Optional.empty();
        }

        return jsonAppointmentBook.map((book) -> book.toModelType(statsManager));
    }

    @Override
    public ReadOnlyAppointmentBook archivePastAppointments(ReadOnlyAppointmentBook appointmentBook) {
        return csvArchive.archivePastAppointments(appointmentBook);
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
    public void backupData(String folderName) throws IOException {
        requireNonNull(folderName);

        if (FileUtil.isFileExists(filePath)) {
            FileUtil.backupFileToFolder(filePath, folderName);
        }
    }

    @Override
    public StorageStatsManager getStatsManager() {
        return statsManager;
    }

}

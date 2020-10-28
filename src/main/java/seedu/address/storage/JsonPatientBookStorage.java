package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.ReadOnlyPatientBook;

/**
 * A class to access PatientBook data stored as a json file on the hard disk.
 */
public class JsonPatientBookStorage implements PatientBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonPatientBookStorage.class);

    private Path filePath;

    public JsonPatientBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getPatientBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<ReadOnlyPatientBook> readPatientBook() throws DataConversionException {
        return readPatientBook(filePath);
    }

    /**
     * Similar to {@link #readPatientBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public Optional<ReadOnlyPatientBook> readPatientBook(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializablePatientBook> jsonPatientBook = JsonUtil.readJsonFile(
                filePath, JsonSerializablePatientBook.class);
        if (jsonPatientBook.isEmpty()) {
            return Optional.empty();
        }

        // todo:
        // try {
        return jsonPatientBook.map(JsonSerializablePatientBook::toModelType);
        // } catch (IllegalValueException ive) {
        //     logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
        //     throw new DataConversionException(ive);
        // }
    }

    @Override
    public void savePatientBook(ReadOnlyPatientBook patientBook) throws IOException {
        savePatientBook(patientBook, filePath);
    }

    /**
     * Similar to {@link #savePatientBook(ReadOnlyPatientBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void savePatientBook(ReadOnlyPatientBook patientBook, Path filePath) throws IOException {
        requireNonNull(patientBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        JsonUtil.saveJsonFile(new JsonSerializablePatientBook(patientBook), filePath);
    }

    @Override
    public void backupData(String folderName) throws IOException {
        requireNonNull(folderName);

        if (FileUtil.isFileExists(filePath)) {
            FileUtil.backupFileToFolder(filePath, folderName);
        }
    }

}

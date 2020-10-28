package seedu.address.commons.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    public static boolean isFileExists(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via {@link Paths#get(String, String...)},
     * otherwise returns false.
     *
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ipe) {
            return false;
        }
        return true;
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     *
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path file) throws IOException {
        if (Files.exists(file)) {
            return;
        }

        createParentDirsOfFile(file);

        Files.createFile(file);
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path file) throws IOException {
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

    /**
     * Writes given string to a file by appending it onto the last line of the file.
     * Will create the file if it does not exist yet.
     */
    public static void appendToFile(Path file, String content) throws IOException {
        StandardOpenOption openOption = isFileExists(file)
                ? StandardOpenOption.APPEND
                : StandardOpenOption.CREATE;

        Files.write(file, content.getBytes(CHARSET), openOption);
    }

    /**
     * Copies data from {@code FilePath from} to {@code FilePath to}.
     * Will create the file if it does not exist yet.
     */
    public static void copyFile(Path from, Path to) throws IOException {
        Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES);
    }

    /**
     * Creates a backup of the indicated file in a nested folder of the same directory.
     * The created backup file will have the same name as the original file.
     *
     * @param folderName the name of the backup folder.
     * @param filePath   the file to create a backup of.
     */
    public static void backupFileToFolder(Path filePath, String folderName) throws IOException {
        Path backupFilePath;
        if (filePath.getNameCount() == 1) {
            backupFilePath = Path.of(folderName).resolve(filePath);
        } else {
            backupFilePath = filePath.getParent().resolve(folderName).resolve(filePath.getFileName());
        }

        createIfMissing(backupFilePath);
        copyFile(filePath, backupFilePath);
    }

}

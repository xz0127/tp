package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class FileUtilTest {

    @TempDir
    public Path testFolder;

    private Path getTempFilePath(String fileName) {
        return testFolder.resolve(fileName);
    }

    @Test
    public void isValidPath() {
        // valid path
        assertTrue(FileUtil.isValidPath("valid/file/path"));

        // invalid path
        assertFalse(FileUtil.isValidPath("a\0"));

        // null path -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> FileUtil.isValidPath(null));
    }

    @Test
    public void createIfMissing() throws IOException {
        Path filePath = getTempFilePath("createdFile");
        assertFalse(FileUtil.isFileExists(filePath));

        FileUtil.createIfMissing(filePath);

        assertTrue(FileUtil.isFileExists(filePath));
    }

    @Test
    public void readWriteToFile() throws IOException {
        Path filePath = getTempFilePath("readWriteTest");
        FileUtil.createIfMissing(filePath);

        String text = "File Util Tests";
        FileUtil.writeToFile(filePath, text);

        assertEquals(FileUtil.readFromFile(filePath), text);
    }

    @Test
    public void appendToFile() throws IOException {
        Path filePath = getTempFilePath("appendTest");
        FileUtil.createIfMissing(filePath);

        String line1 = "line 1\n";
        FileUtil.appendToFile(filePath, line1);
        assertEquals(FileUtil.readFromFile(filePath), line1);

        String line2 = "line 2";
        FileUtil.appendToFile(filePath, line2);
        assertEquals(FileUtil.readFromFile(filePath), line1 + line2);
    }

    @Test
    public void copyFile() throws IOException {
        Path originalFilePath = getTempFilePath("originalFile");
        Path copyFilePath = getTempFilePath("copiedFile");
        FileUtil.createIfMissing(originalFilePath);
        FileUtil.createIfMissing(copyFilePath);

        String text = "original";
        FileUtil.writeToFile(originalFilePath, text);
        FileUtil.writeToFile(copyFilePath, "to be overwritten");
        FileUtil.copyFile(originalFilePath, copyFilePath);

        assertEquals(FileUtil.readFromFile(originalFilePath), text);
        assertEquals(FileUtil.readFromFile(copyFilePath), text);
    }

    @Test
    public void backupFile() throws IOException {
        Path originalFilePath = getTempFilePath("originalFile");
        FileUtil.createIfMissing(originalFilePath);
        FileUtil.writeToFile(originalFilePath, "some text");


        String backupFolderName = "backup";
        Path backupFilePath = getTempFilePath(backupFolderName + "/originalFile");
        FileUtil.backupFileToFolder(originalFilePath, backupFolderName);

        assertEquals(FileUtil.readFromFile(originalFilePath), FileUtil.readFromFile(backupFilePath));
    }
}

package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.BENSON_APPOINTMENT;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.AppointmentBuilder;

public class CsvAppointmentArchiveTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src",
            "test", "data", "CsvAppointmentArchiveTest");

    @TempDir
    public Path testFolder;

    private final Appointment expiredAppointment = new AppointmentBuilder(ALICE_APPOINTMENT)
            .withDate(LocalDate.of(2010, 5, 5)).build();

    @Test
    public void readAppointments_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readArchive(null));
    }

    @Test
    public void read_missingFile_emptyList() throws Exception {
        assertTrue(readArchive("NonExistentFile.csv").isEmpty());
    }

    @Test
    public void read_notCsvFormat_exceptionThrown() {
        assertThrows(DataConversionException.class, () -> readArchive("notCsvFormat.csv"));
    }

    @Test
    public void read_validCsvFile_returnList() throws Exception {
        assertEquals(getSampleCsvData(), readArchive("validCsvArchive.csv"));
    }

    @Test
    public void read_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readArchive(null));
    }

    @Test
    public void archiveAppointmentBook_nullAppointmentBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveAppointmentBook(null));
    }

    @Test
    public void archiveAppointments_nullAppointmentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveAppointments(null, "SomeFile.csv"));
    }

    @Test
    public void archiveAppointments_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> archiveAppointments(getSampleCsvData(), null));
    }

    @Test
    public void archiveThenReadAppointments_allInOrder_success() throws Exception {
        CsvAppointmentArchive archive = new CsvAppointmentArchive(testFolder);
        String fileName = "test.csv";
        List<CsvAdaptedAppointment> originalData = getSampleCsvData();

        archive.saveAppointments(originalData, fileName);
        assertEquals(originalData, archive.readAppointments(fileName));

        // continuous write - append data
        archive.saveAppointments(originalData, fileName);
        assertEquals(4, archive.readAppointments(fileName).size());
    }

    @Test
    public void archiveAppointmentBook_validAppointmentBook_archiveSuccess() throws Exception {
        CsvAppointmentArchive archive = new CsvAppointmentArchive(testFolder);

        AppointmentBook appointmentBook = new AppointmentBook();
        appointmentBook.setAppointments(List.of(expiredAppointment, BENSON_APPOINTMENT));

        AppointmentBook expectedAppointment = new AppointmentBook(appointmentBook);
        expectedAppointment.removeAppointment(expiredAppointment);

        assertEquals(expectedAppointment, archive.archiveAppointmentBook(appointmentBook));

        Path archiveFile = testFolder.resolve("2010_MAY.csv");
        List<CsvAdaptedAppointment> csvData = archive.readAppointments(archiveFile);
        assertEquals(List.of(new CsvAdaptedAppointment(expiredAppointment)), csvData);
    }

    private List<CsvAdaptedAppointment> getSampleCsvData() {
        return List.of(new CsvAdaptedAppointment(expiredAppointment),
                new CsvAdaptedAppointment(BENSON_APPOINTMENT));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    private ReadOnlyAppointmentBook archiveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) {
        return new CsvAppointmentArchive(TEST_DATA_FOLDER).archiveAppointmentBook(appointmentBook);
    }

    private List<CsvAdaptedAppointment> readArchive(String fileName) throws Exception {
        return new CsvAppointmentArchive(TEST_DATA_FOLDER)
                .readAppointments(addToTestDataPathIfNotNull(fileName));
    }

    private void archiveAppointments(List<CsvAdaptedAppointment> appointments, String fileName) {
        try {
            new CsvAppointmentArchive(TEST_DATA_FOLDER)
                    .saveAppointments(appointments, addToTestDataPathIfNotNull(fileName));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

}

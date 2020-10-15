package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AppointmentBook;
import seedu.address.testutil.TypicalAppointments;

public class JsonSerializableAppointmentBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src",
            "test", "data", "JsonSerializableAppointmentBookTest");
    private static final Path TYPICAL_APPOINTMENT_FILE = TEST_DATA_FOLDER.resolve("typicalAppointmentBook.json");
    private static final Path INVALID_APPOINTMENT_FILE = TEST_DATA_FOLDER.resolve("invalidAppointmentBook.json");
    private static final Path DUPLICATE_APPOINTMENT_FILE = TEST_DATA_FOLDER.resolve("overlappingAppointmentBook.json");

    @Test
    public void toModelType_typicalAppointmentFile_success() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_APPOINTMENT_FILE,
                JsonSerializableAppointmentBook.class).get();
        AppointmentBook appointmentBookFromFile = dataFromFile.toModelType();
        AppointmentBook typicalAppointmentBook = TypicalAppointments.getTypicalAppointmentBook();
        assertEquals(appointmentBookFromFile, typicalAppointmentBook);
    }

    @Test
    public void toModelType_invalidAppointmentFile_throwsIllegalValueException() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(INVALID_APPOINTMENT_FILE,
                JsonSerializableAppointmentBook.class).get();
        assertThrows(IllegalValueException.class, dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicatePatients_throwsIllegalValueException() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_APPOINTMENT_FILE,
                JsonSerializableAppointmentBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAppointmentBook.MESSAGE_OVERLAPPING_APPOINTMENT,
                dataFromFile::toModelType);
    }

}

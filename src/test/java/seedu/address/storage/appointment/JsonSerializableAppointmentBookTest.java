package seedu.address.storage.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AppointmentBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.storage.StorageStatsManager;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.TypicalAppointments;

public class JsonSerializableAppointmentBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src",
            "test", "data", "JsonSerializableAppointmentBookTest");
    private static final Path TYPICAL_APPOINTMENT_FILE = TEST_DATA_FOLDER.resolve("typicalAppointmentBook.json");
    private static final Path INVALID_APPOINTMENT_FILE = TEST_DATA_FOLDER.resolve("invalidAppointmentBook.json");
    private static final Path OVERLAP_APPOINTMENT_FILE = TEST_DATA_FOLDER.resolve("overlappingAppointmentBook.json");

    @Test
    public void toModelType_typicalAppointmentFile_success() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_APPOINTMENT_FILE,
                JsonSerializableAppointmentBook.class).get();
        AppointmentBook appointmentBookFromFile = dataFromFile.toModelType(new StorageStatsManager());
        AppointmentBook typicalAppointmentBook = TypicalAppointments.getTypicalAppointmentBook();
        assertEquals(appointmentBookFromFile, typicalAppointmentBook);
    }

    @Test
    public void toModelType_invalidAppointmentFile_returnEmptyBook() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(INVALID_APPOINTMENT_FILE,
                JsonSerializableAppointmentBook.class).get();
        AppointmentBook appointmentBookFromFile = dataFromFile.toModelType(new StorageStatsManager());
        AppointmentBook expectedAppointmentBook = new AppointmentBook();
        assertEquals(expectedAppointmentBook, appointmentBookFromFile);
    }

    @Test
    public void toModelType_duplicateAppointments_returnsFirstUniqueAppointment() throws Exception {
        JsonSerializableAppointmentBook dataFromFile = JsonUtil.readJsonFile(OVERLAP_APPOINTMENT_FILE,
                JsonSerializableAppointmentBook.class).get();
        AppointmentBook appointmentBookFromFile = dataFromFile.toModelType(new StorageStatsManager());

        AppointmentBook expectedAppointmentBook = new AppointmentBook();
        Appointment firstAppointment = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(LocalTime.of(10, 0)).build();
        expectedAppointmentBook.addAppointment(firstAppointment);

        assertEquals(expectedAppointmentBook, appointmentBookFromFile);
    }

}

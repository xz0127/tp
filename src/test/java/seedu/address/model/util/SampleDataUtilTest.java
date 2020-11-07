package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReadOnlyPatientBook;

public class SampleDataUtilTest {
    @Test
    public void getSamplePatientBook_countPatient_correctCount() {
        ReadOnlyPatientBook samplePatientBook = SampleDataUtil.getSamplePatientBook();
        assertEquals(6, samplePatientBook.getPatientList().size());
    }

    @Test
    public void getSampleAppointmentBook_countAppointment_correctCount() {
        ReadOnlyAppointmentBook sampleAppointmentBook = SampleDataUtil.getSampleAppointmentBook();
        assertEquals(8, sampleAppointmentBook.getAppointmentList().size());
    }

    @Test
    public void sampleAppointmentAndPatientBook_checkConsistency_isValidModol() {
        ReadOnlyPatientBook samplePatientBook = SampleDataUtil.getSamplePatientBook();
        ReadOnlyAppointmentBook sampleAppointmentBook = SampleDataUtil.getSampleAppointmentBook();
        assertTrue(ModelManager.isValidModel(samplePatientBook, sampleAppointmentBook));
    }
}

package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class AppointmentIdTest {
    private final Date testDate = new Date(LocalDate.of(2020, 2, 2));
    private final Time testTime = new Time(LocalTime.NOON);
    private final Date testDate1 = new Date(LocalDate.of(2019, 11, 24));
    private final Time testTime2 = new Time(LocalTime.of(13, 30));

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AppointmentId(null, null));
    }

    @Test
    public void constructor_correctValue() {
        assertEquals("202002021200", new AppointmentId(testDate, testTime).toString());
        assertEquals("201911241200", new AppointmentId(testDate1, testTime).toString());
        assertEquals("202002021330", new AppointmentId(testDate, testTime2).toString());
        assertEquals("201911241330", new AppointmentId(testDate1, testTime2).toString());
    }

    @Test
    public void equals() {
        AppointmentId appointmentIdTest = new AppointmentId(testDate, testTime);

        // same values -> returns true
        AppointmentId appointmentIdTestCopy = new AppointmentId(testDate, testTime);
        assertTrue(appointmentIdTest.equals(appointmentIdTestCopy));

        // same object -> returns true
        assertTrue(appointmentIdTest.equals(appointmentIdTest));

        // null -> returns false
        assertFalse(appointmentIdTest.equals(null));

        // different type -> returns false
        assertFalse(appointmentIdTest.equals(5));

        // different dates -> returns false
        assertFalse(appointmentIdTest.equals(new AppointmentId(testDate1, testTime)));
        assertFalse(appointmentIdTest.equals(new AppointmentId(testDate, testTime2)));
        assertFalse(appointmentIdTest.equals(new AppointmentId(testDate1, testTime2)));
    }
}

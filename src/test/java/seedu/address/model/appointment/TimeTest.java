package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TimeTest {
    // valid local times
    private final LocalTime oneHourBeforeOpening = Time.OPENING_TIME.minusHours(1);
    private final LocalTime oneHourAfterClosing = Time.CLOSING_TIME.plusHours(1);

    // invalid local times
    private final LocalTime oneHourAfterOpening = Time.OPENING_TIME.plusHours(1);
    private final LocalTime oneHourBeforeClosing = Time.CLOSING_TIME.minusHours(1);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Time(oneHourBeforeOpening));
        assertThrows(IllegalArgumentException.class, () -> new Time(oneHourAfterClosing));
    }

    @Test
    public void isValidTime() {
        // null time
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid times
        assertFalse(Time.isValidTime(oneHourBeforeOpening));
        assertFalse(Time.isValidTime(oneHourAfterClosing));
        assertFalse(Time.isValidTime(LocalTime.MIDNIGHT));

        // valid times
        assertTrue(Time.isValidTime(LocalTime.NOON));
        assertTrue(Time.isValidTime(oneHourAfterOpening));
        assertTrue(Time.isValidTime(oneHourBeforeClosing));
    }

    @Test
    public void isBefore() {
        Time testTime = new Time(LocalTime.NOON);

        // null time check
        assertThrows(NullPointerException.class, () -> testTime.isBefore(null));

        // time is before input --> true
        assertTrue(testTime.isBefore(new Time(15, 0)));
        assertTrue(testTime.isBefore(new Time(oneHourBeforeClosing)));

        // time is equal to input --> false
        assertFalse(testTime.isBefore(new Time(LocalTime.NOON)));

        // time is after input --> false
        assertFalse(testTime.isBefore(new Time(11, 0)));
        assertFalse(testTime.isBefore(new Time(oneHourAfterOpening)));
    }

    @Test
    public void isAfter() {
        Time testTime = new Time(LocalTime.NOON);

        // null time check
        assertThrows(NullPointerException.class, () -> testTime.isAfter(null));

        // time is after input --> true
        assertTrue(testTime.isAfter(new Time(11, 0)));
        assertTrue(testTime.isAfter(new Time(oneHourAfterOpening)));

        // time is equal to input --> false
        assertFalse(testTime.isAfter(new Time(LocalTime.NOON)));

        // time is before input --> false
        assertFalse(testTime.isAfter(new Time(15, 0)));
        assertFalse(testTime.isAfter(new Time(oneHourBeforeClosing)));
    }

    @Test
    public void equals() {
        Time timeTest = new Time(LocalTime.NOON);

        // same values -> returns true
        Time timeTestCopy = new Time(LocalTime.NOON);
        assertTrue(timeTest.equals(timeTestCopy));

        // same object -> returns true
        assertTrue(timeTest.equals(timeTest));

        // null -> returns false
        assertFalse(timeTest.equals(null));

        // different type -> returns false
        assertFalse(timeTest.equals(5));

        // different dates -> returns false
        assertFalse(timeTest.equals(new Time(13, 0)));
    }
}

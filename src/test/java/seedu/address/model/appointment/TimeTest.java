package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class TimeTest {
    // valid local times
    private final LocalTime timeBeforeOpening = Time.OPENING_TIME.minusHours(1);
    private final LocalTime timeAfterClosing = Time.CLOSING_TIME.plusHours(1);

    // invalid local times
    private final LocalTime timeAfterOpening = Time.OPENING_TIME.plusHours(1);
    private final LocalTime timeBeforeClosing = Time.CLOSING_TIME.minusHours(1);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Time(null));
    }

    @Test
    public void constructor_invalidTime_throwsIllegalArgumentException() {
        LocalTime timeBeforeOpening = Time.OPENING_TIME.minusHours(1);
        assertThrows(IllegalArgumentException.class, () -> new Time(timeBeforeOpening));

        LocalTime timeAfterClosing = Time.CLOSING_TIME.plusHours(1);
        assertThrows(IllegalArgumentException.class, () -> new Time(timeAfterClosing));
    }

    @Test
    public void isValidTime() {
        // null time
        assertThrows(NullPointerException.class, () -> Time.isValidTime(null));

        // invalid times
        assertFalse(Time.isValidTime(timeBeforeOpening));
        assertFalse(Time.isValidTime(timeAfterClosing));
        assertFalse(Time.isValidTime(LocalTime.MIDNIGHT));

        // valid times
        assertTrue(Time.isValidTime(LocalTime.NOON));
        assertTrue(Time.isValidTime(timeAfterOpening));
        assertTrue(Time.isValidTime(timeBeforeClosing));
    }

    @Test
    public void isBefore() {
        Time testTime = new Time(LocalTime.NOON);

        // null time check
        assertThrows(NullPointerException.class, () -> testTime.isBefore(null));

        // time is before input
        assertTrue(testTime.isBefore(new Time(LocalTime.of(15, 0))));
        assertTrue(testTime.isBefore(new Time(timeBeforeClosing)));

        // time is after input
        assertFalse(testTime.isBefore(new Time(LocalTime.NOON)));
        assertFalse(testTime.isBefore(new Time(LocalTime.of(11, 0))));
        assertFalse(testTime.isBefore(new Time(timeAfterOpening)));
    }

    @Test
    public void isAfter() {
        Time testTime = new Time(LocalTime.NOON);

        // null time check
        assertThrows(NullPointerException.class, () -> testTime.isAfter(null));

        // time is before input
        assertTrue(testTime.isAfter(new Time(LocalTime.of(11, 0))));
        assertTrue(testTime.isAfter(new Time(timeAfterOpening)));

        // time is after input
        assertFalse(testTime.isAfter(new Time(LocalTime.NOON)));
        assertFalse(testTime.isAfter(new Time(LocalTime.of(15, 0))));
        assertFalse(testTime.isAfter(new Time(timeBeforeClosing)));
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
        assertFalse(timeTest.equals(new Time(LocalTime.of(13, 0))));
    }
}

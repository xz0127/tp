package seedu.address.model.interval;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.Time;

public class TimeIntervalTest {

    public static final Time OPENING_TIME = new Time(Time.OPENING_TIME);
    public static final Time CLOSING_TIME = new Time(Time.CLOSING_TIME);
    public static final Time NOON = new Time(12, 0);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TimeInterval(null, null));
        assertThrows(NullPointerException.class, ()
            -> new TimeInterval(new Time(Time.OPENING_TIME), null));
        assertThrows(NullPointerException.class, ()
            -> new TimeInterval(null, new Time(Time.CLOSING_TIME)));
    }

    @Test
    public void isValidInterval() {
        // null time
        assertThrows(NullPointerException.class, () -> TimeInterval.isValidInterval(null));

        // invalid interval
        assertFalse(TimeInterval.isValidInterval(new TimeInterval(CLOSING_TIME, OPENING_TIME)));
        assertFalse(TimeInterval.isValidInterval(new TimeInterval(CLOSING_TIME, NOON)));
        assertFalse(TimeInterval.isValidInterval(new TimeInterval(NOON, OPENING_TIME)));

        // valid interval
        assertTrue(TimeInterval.isValidInterval(new TimeInterval(OPENING_TIME, NOON)));
        assertTrue(TimeInterval.isValidInterval(new TimeInterval(NOON, CLOSING_TIME)));
        assertTrue(TimeInterval.isValidInterval(new TimeInterval(NOON, NOON)));
        assertTrue(TimeInterval.isValidInterval(new TimeInterval(OPENING_TIME, OPENING_TIME)));
        assertTrue(TimeInterval.isValidInterval(new TimeInterval(OPENING_TIME, CLOSING_TIME)));
    }

    @Test
    public void isZeroInterval() {
        TimeInterval intervalOne = new TimeInterval(OPENING_TIME, OPENING_TIME);
        TimeInterval intervalTwo = new TimeInterval(NOON, NOON);
        TimeInterval intervalThree = new TimeInterval(CLOSING_TIME, CLOSING_TIME);
        TimeInterval intervalFour = new TimeInterval(OPENING_TIME, CLOSING_TIME);
        TimeInterval intervalFive = new TimeInterval(OPENING_TIME, NOON);

        // zero interval
        assertTrue(intervalOne.isZeroInterval());
        assertTrue(intervalTwo.isZeroInterval());
        assertTrue(intervalThree.isZeroInterval());

        // non-zero interval
        assertFalse(intervalFour.isZeroInterval());
        assertFalse(intervalFive.isZeroInterval());
    }

    @Test
    public void equals() {
        TimeInterval timeIntervalTest = new TimeInterval(OPENING_TIME, CLOSING_TIME);

        // same values -> returns true
        TimeInterval timeIntervalTestCopy = new TimeInterval(OPENING_TIME, CLOSING_TIME);
        assertTrue(timeIntervalTest.equals(timeIntervalTestCopy));

        // same object -> returns true
        assertTrue(timeIntervalTest.equals(timeIntervalTest));

        // null -> returns false
        assertFalse(timeIntervalTest.equals(null));

        // different type -> returns false
        assertFalse(timeIntervalTest.equals(5));

        // different endTime -> returns false
        assertFalse(timeIntervalTest.equals(new TimeInterval(OPENING_TIME, NOON)));

        // different startTime -> returns false
        assertFalse(timeIntervalTest.equals(new TimeInterval(NOON, CLOSING_TIME)));
    }
}

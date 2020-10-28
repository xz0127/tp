package seedu.address.model.interval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.TypicalTimeIntervals;

public class TimeIntervalListTest {

    private final TimeIntervalList timeIntervalList = new TimeIntervalList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), timeIntervalList.getTimeIntervals());
    }

    @Test
    public void equalsTo_returnTrue() {
        TimeIntervalList timeIntervalListCopy = new TimeIntervalList();
        assertTrue(timeIntervalListCopy.equalsTo(timeIntervalList));

        timeIntervalList.add(TypicalTimeIntervals.INTERVAL_THREE);
        timeIntervalListCopy.add(TypicalTimeIntervals.INTERVAL_THREE);
        assertTrue(timeIntervalListCopy.equalsTo(timeIntervalList));
    }

    @Test
    public void equalsTo_returnFalse() {
        TimeIntervalList timeIntervalListCopy = new TimeIntervalList();
        timeIntervalList.add(TypicalTimeIntervals.INTERVAL_THREE);
        assertFalse(timeIntervalListCopy.equalsTo(timeIntervalList));

        timeIntervalList.add(TypicalTimeIntervals.INTERVAL_TWO);
        timeIntervalListCopy.add(TypicalTimeIntervals.INTERVAL_THREE);
        assertFalse(timeIntervalListCopy.equalsTo(timeIntervalList));
    }

    @Test
    public void clearZeroInterval_correctValue() {
        TimeIntervalList intervals = TypicalTimeIntervals.getTypicalTimeIntervalList().clearZeroIntervals();
        timeIntervalList.add(TypicalTimeIntervals.INTERVAL_ONE);
        timeIntervalList.add(TypicalTimeIntervals.INTERVAL_THREE);
        timeIntervalList.add(TypicalTimeIntervals.INTERVAL_FOUR);
        assertTrue(intervals.equalsTo(timeIntervalList));
    }

    @Test
    public void isAtLeastOneHour_returnTrue() {
        assertTrue(TypicalTimeIntervals.INTERVAL_ONE.isAtLeastOneHour());
        assertTrue(TypicalTimeIntervals.INTERVAL_SIX.isAtLeastOneHour());
    }

    @Test
    public void isAtLeastOneHour_returnFalse() {
        assertFalse(TypicalTimeIntervals.INTERVAL_TWO.isAtLeastOneHour());
        assertFalse(TypicalTimeIntervals.INTERVAL_THREE.isAtLeastOneHour());
        assertFalse(TypicalTimeIntervals.INTERVAL_FOUR.isAtLeastOneHour());
        assertFalse(TypicalTimeIntervals.INTERVAL_FIVE.isAtLeastOneHour());
    }
}

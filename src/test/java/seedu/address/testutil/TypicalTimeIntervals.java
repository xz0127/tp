package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.appointment.Time;
import seedu.address.model.interval.TimeInterval;
import seedu.address.model.interval.TimeIntervalList;

public class TypicalTimeIntervals {
    public static final TimeInterval INTERVAL_ONE = new TimeInterval(
        new Time(8, 0), new Time(9, 0));

    public static final TimeInterval INTERVAL_TWO = new TimeInterval(
        new Time(10, 0), new Time(10, 0));

    public static final TimeInterval INTERVAL_THREE = new TimeInterval(
        new Time(12, 15), new Time(13, 0));

    public static final TimeInterval INTERVAL_FOUR = new TimeInterval(
        new Time(15, 0), new Time(15, 30));

    public static final TimeInterval INTERVAL_FIVE = new TimeInterval(
        new Time(17, 0), new Time(17, 0));

    public static final TimeInterval INTERVAL_SIX = new TimeInterval(
        new Time(8, 0), new Time(22, 0));

    public static ArrayList<TimeInterval> getTypicalTimeIntervals() {
        ArrayList<TimeInterval> intervals = new ArrayList<>();
        intervals.add(INTERVAL_ONE);
        intervals.add(INTERVAL_TWO);
        intervals.add(INTERVAL_THREE);
        intervals.add(INTERVAL_FOUR);
        intervals.add(INTERVAL_FIVE);
        return intervals;
    }

    /**
     * Creates a list of time intervals containing all typical time intervals.
     * @return a {@code TimeIntervalList} containing all typical time intervals.
     */
    public static TimeIntervalList getTypicalTimeIntervalList() {
        ArrayList<TimeInterval> intervals = getTypicalTimeIntervals();
        return new TimeIntervalList(intervals);
    }
}

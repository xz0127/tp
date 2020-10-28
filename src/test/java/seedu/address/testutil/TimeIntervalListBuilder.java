package seedu.address.testutil;

import java.util.ArrayList;

import seedu.address.model.interval.TimeInterval;
import seedu.address.model.interval.TimeIntervalList;

/**
 * A utility class to help with building TimeIntervalList objects.
 */
public class TimeIntervalListBuilder {

    private TimeIntervalList timeIntervalList;

    /**
     * Creates a timeIntervalList object with an empty list of time intervals.
     */
    public TimeIntervalListBuilder() {
        ArrayList<TimeInterval> intervals = new ArrayList<>();
        timeIntervalList = new TimeIntervalList(intervals);
    }

    public TimeIntervalListBuilder(TimeIntervalList timeIntervalList) {
        this.timeIntervalList = timeIntervalList;
    }

    public TimeIntervalList build() {
        return timeIntervalList;
    }
}

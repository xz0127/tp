package seedu.address.model.interval;

import java.util.ArrayList;

/**
 * Represents a list of time intervals.
 */
public class TimeIntervalList {

    private ArrayList<TimeInterval> timeIntervals;

    /**
     * Construct a TimeIntervalList object.
     *
     * @param timeIntervals a list of time intervals.
     */
    public TimeIntervalList(ArrayList<TimeInterval> timeIntervals) {
        this.timeIntervals = timeIntervals;
    }

    public ArrayList<TimeInterval> getTimeIntervals() {
        return timeIntervals;
    }

    /**
     * Clears the empty intervals in the l.ist of time intervals
     *
     * @return a list of time intervals with no empty intervals.
     */
    public TimeIntervalList clearZeroIntervals() {
        ArrayList<TimeInterval> noZeroIntervalList = new ArrayList<>();
        for (TimeInterval timeInterval : timeIntervals) {
            if (!timeInterval.isZeroInterval()) {
                noZeroIntervalList.add(timeInterval);
            }
        }
        return new TimeIntervalList(noZeroIntervalList);
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        for (TimeInterval freeTimeInterval : timeIntervals) {
            message.append(freeTimeInterval.toString()).append("\n");
        }
        return message.toString();
    }
}

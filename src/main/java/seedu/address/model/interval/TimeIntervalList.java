package seedu.address.model.interval;

import static java.util.Objects.requireNonNull;

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

    public TimeIntervalList() {
        this.timeIntervals = new ArrayList<>();
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

    /**
     * Adds a {@code timeInterval} to the time intervals list.
     */
    public void add(TimeInterval timeInterval) {
        requireNonNull(timeInterval);

        timeIntervals.add(timeInterval);
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        if (timeIntervals.size() == 0) {
            message.append("Sorry no time intervals!");
        }
        for (TimeInterval freeTimeInterval : timeIntervals) {
            message.append(freeTimeInterval.toString()).append("\n");
        }
        return message.toString();
    }

    /**
     * Compares whether two timeIntervalList contain the same intervals.
     *
     * @param otherList another timeIntervalList.
     * @return true if two timeIntervalList contain the same intervals and false otherwise.
     */
    public boolean equalsTo(TimeIntervalList otherList) {

        if (otherList.getTimeIntervals().size() != timeIntervals.size()) {
            return false;
        }

        for (int i = 0; i < timeIntervals.size(); i++) {
            if (!timeIntervals.get(i).equals(otherList.timeIntervals.get(i))) {
                return false;
            }
        }
        return true;
    }
}

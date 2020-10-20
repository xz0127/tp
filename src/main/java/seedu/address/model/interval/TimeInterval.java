package seedu.address.model.interval;

import seedu.address.model.appointment.Time;

/**
 * Represents a time interval.
 */
public class TimeInterval {
    private Time startTime;
    private Time endTime;

    /**
     * Constructs an interval object.
     *
     * @param startTime starting time of the interval.
     * @param endTime end time of the interval.
     */
    public TimeInterval(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    @Override
    public String toString() {
        return "(" + startTime + "," + endTime + ")";
    }
}

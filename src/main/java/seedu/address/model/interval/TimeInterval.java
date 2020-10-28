package seedu.address.model.interval;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.util.TimeConversionUtil;
import seedu.address.model.appointment.Time;

/**
 * Represents a time interval.
 */
public class TimeInterval {
    private final Time startTime;
    private final Time endTime;

    /**
     * Constructs an interval object.
     *
     * @param startTime starting time of the interval.
     * @param endTime end time of the interval.
     */
    public TimeInterval(Time startTime, Time endTime) {
        requireAllNonNull(startTime, endTime);

        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Checks if the given {@code test} is valid.
     * The {@code LocalTime} is valid if the ending time is no prior to the starting time.
     *
     * @param test the time interval to test.
     * @return true if is valid, false otherwise.
     */
    public static boolean isValidInterval(TimeInterval test) {
        return !(test.endTime.isBefore(test.startTime));
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    /**
     * Checks if a specific time interval last for more than 60 minutes.
     * @return true if the interval is at least one hour and false otherwise.
     */
    public boolean isAtLeastOneHour() {
        int endTimeInInt = TimeConversionUtil.convertTimeToInt(endTime);
        int startTimeInInt = TimeConversionUtil.convertTimeToInt(startTime);
        return (endTimeInInt - startTimeInInt) >= 60;
    }

    /**
     * Check whether the interval is an empty interval.
     *
     * @return true if the start time is same as the end time and false otherwise.
     */
    public boolean isZeroInterval() {
        return startTime.equals(endTime);
    }

    @Override
    public String toString() {
        return startTime + " to " + endTime;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof TimeInterval // instanceof handles nulls
            && startTime.equals(((TimeInterval) other).startTime)
            && endTime.equals(((TimeInterval) other).endTime)); // state check
    }
}

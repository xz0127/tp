package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an appointment time.
 */
public class Time {
    /**
     * The formatter to convert the Time into the desired form for presentation
     **/
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("h:mm a");

    // Opens at 8am
    public static final LocalTime OPENING_TIME = LocalTime.of(8, 0);
    // Closes at 10pm
    public static final LocalTime CLOSING_TIME = LocalTime.of(22, 0);

    public static final String MESSAGE_CONSTRAINTS = "The appointment time period should fall within "
            + "the opening hours: from " + OPENING_TIME.format(TIME_FORMAT)
            + " to " + CLOSING_TIME.format(TIME_FORMAT)
            + "\nThe appointment duration should also be considered.";

    private final LocalTime value;

    /**
     * Create a {@code Time} that represents the time of an appointment.
     *
     * @param hour    the hour of the day, from 0 to 23
     * @param minutes the minute of the hour, from 0 to 59
     */
    public Time(int hour, int minutes) {
        this(LocalTime.of(hour, minutes));
    }

    /**
     * Create a {@code Time} that represents the time of an appointment.
     *
     * @param time the LocalTime containing the time.
     */
    public Time(LocalTime time) {
        requireNonNull(time);

        checkArgument(isValidTime(time), MESSAGE_CONSTRAINTS);
        value = time;
    }

    /**
     * Checks if the given {@code LocalTime} is valid.
     * The {@code LocalTime} is valid if it falls within the opening and closing time (inclusive).
     *
     * @param test the LocalTime to test.
     * @return true if is valid, false otherwise.
     */
    public static boolean isValidTime(LocalTime test) {
        return !(test.isBefore(OPENING_TIME) || test.isAfter(CLOSING_TIME));
    }

    public LocalTime getTime() {
        return value;
    }

    /**
     * Check if the appointment time comes before the input {@code Time}.
     *
     * @param inputTime the input time to be checked against
     * @return true if the appointment time comes before the input time, false otherwise
     */
    public boolean isBefore(Time inputTime) {
        return value.isBefore(inputTime.value);
    }

    /**
     * Check if the appointment time comes after the input {@code Time}.
     *
     * @param inputTime the input time to be checked against
     * @return true if the appointment time comes after the input time, false otherwise
     */
    public boolean isAfter(Time inputTime) {
        return value.isAfter(inputTime.value);
    }

    /**
     * Format appointment time as text for viewing
     */
    @Override
    public String toString() {
        return value.format(TIME_FORMAT);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Time // instanceof handles nulls
                && value.equals(((Time) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

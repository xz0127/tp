package seedu.address.commons.util;

import java.time.LocalTime;

import seedu.address.model.appointment.Time;

/**
 * Converts a time object to an integer time value and vice versa.
 */
public class TimeConversionUtil {

    /**
     * Converts a time into an integer time value.
     *
     * @param time a time object.
     * @return integer value  of the time.
     */
    public static int convertTimeToInt(Time time) {
        LocalTime value = time.getTime();
        int hours = value.getHour();
        int minutes = value.getMinute();
        return hours * 60 + minutes;
    }

    /**
     * Converts a time value into a time object.
     * @param timeValue time value in minutes.
     * @return the time object which is equivalent to the time value.
     */
    public static Time convertIntToTime(int timeValue) {
        int hours = timeValue / 60;
        int minutes = timeValue % 60;
        return new Time(hours, minutes);
    }
}

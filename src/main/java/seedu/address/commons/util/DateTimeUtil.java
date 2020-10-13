package seedu.address.commons.util;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * Helper class for handling date time comparison
 */
public class DateTimeUtil {

    /**
     * Same as {@link DateTimeUtil#isExpired(LocalDate, LocalTime, LocalDateTime)}
     */
    public static boolean isExpired(LocalDate date, LocalTime time) {
        return isExpired(date, time, LocalDateTime.now());
    }

    /**
     * Check if the given date and time is expired.
     * A given date and time is expired if it is before the current date and time.
     * Uses {@code LocalDateTime now} as the current datetime to compare against.
     *
     * @param date the given date.
     * @param time the given time.
     * @param now  the current datetime, usually {@code LocalDateTime.now()}.
     * @return true if given date and time is in the future, false otherwise.
     */
    public static boolean isExpired(LocalDate date, LocalTime time, LocalDateTime now) {
        requireAllNonNull(date, time, now);
        LocalDateTime simplifiedNow = now.truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime test = LocalDateTime.of(date, time);
        return test.isBefore(simplifiedNow);
    }

}

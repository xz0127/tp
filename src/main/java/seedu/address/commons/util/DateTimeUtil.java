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
     * Checks if the given date is expired by comparing with today's date.
     *
     * @param date the given date.
     * @return true if given date is before today's date, false otherwise.
     */
    public static boolean isExpiredByDay(LocalDate date) {
        return isExpired(date, LocalTime.MIDNIGHT, LocalDate.now().atStartOfDay());
    }

    /**
     * Checks if the given date and time is expired.
     * A given date and time is expired if it is before the current date and time.
     *
     * @param date the given date.
     * @param time the given time.
     * @return true if given date and time is not expired, false otherwise.
     */
    public static boolean isExpired(LocalDate date, LocalTime time) {
        return isExpired(date, time, LocalDateTime.now());
    }


    /**
     * Similar to {@link DateTimeUtil#isExpired(LocalDate, LocalTime)}.
     * Uses {@code LocalDateTime now} as the current datetime to compare against.
     *
     * @param now the current datetime, usually {@code LocalDateTime.now()}.
     */
    static boolean isExpired(LocalDate date, LocalTime time, LocalDateTime now) {
        requireAllNonNull(date, time, now);
        LocalDateTime simplifiedNow = now.truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime test = LocalDateTime.of(date, time);
        return test.isBefore(simplifiedNow);
    }

}

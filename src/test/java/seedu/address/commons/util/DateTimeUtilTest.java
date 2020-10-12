package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.util.DateTimeUtil.isExpired;
import static seedu.address.commons.util.DateTimeUtil.isExpiredByDay;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;


public class DateTimeUtilTest {
    private static final LocalDateTime currDateTime =
            LocalDateTime.of(2020, 1, 1, 15, 30);

    @Test
    public void isExpiredByDay_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> isExpiredByDay(null));
    }

    @Test
    public void isExpired_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> isExpired(null, null));
        assertThrows(NullPointerException.class, () -> isExpired(null, null, null));
    }

    @Test
    public void isExpiredByDay_returnFalse() {
        assertTrue(isExpiredByDay(LocalDate.of(2010, 1, 1)));
    }

    @Test
    public void isExpired_returnTrue() {
        LocalDate sameDay = currDateTime.toLocalDate();
        LocalTime sameTime = currDateTime.toLocalTime();

        // past day, any time
        assertTrue(isExpired(sameDay.minusDays(1), sameTime, currDateTime));
        assertTrue(isExpired(sameDay.minusWeeks(1), LocalTime.NOON, currDateTime));
        assertTrue(isExpired(sameDay.minusMonths(1), LocalTime.MIDNIGHT, currDateTime));

        // same day, past time
        assertTrue(isExpired(sameDay, sameTime.minusHours(1), currDateTime));
        assertTrue(isExpired(sameDay, sameTime.minusMinutes(1), currDateTime));
        assertTrue(isExpired(sameDay, sameTime.minusSeconds(1), currDateTime));
    }

    @Test
    public void isExpired_returnFalse() {
        LocalDate sameDay = currDateTime.toLocalDate();
        LocalTime sameTime = currDateTime.toLocalTime();

        // same day and time
        assertFalse(isExpired(sameDay, sameTime, currDateTime));

        // future day, any time
        assertFalse(isExpired(sameDay.plusDays(1), sameTime, currDateTime));
        assertFalse(isExpired(sameDay.plusWeeks(1), LocalTime.NOON, currDateTime));
        assertFalse(isExpired(sameDay.plusMonths(1), LocalTime.MIDNIGHT, currDateTime));

        // same day, future time
        assertFalse(isExpired(sameDay, sameTime.plusHours(1), currDateTime));
        assertFalse(isExpired(sameDay, sameTime.plusMinutes(1), currDateTime));
        assertFalse(isExpired(sameDay, sameTime.plusSeconds(1), currDateTime));
    }

}

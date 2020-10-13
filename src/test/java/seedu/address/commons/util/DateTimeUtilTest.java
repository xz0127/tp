package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;


public class DateTimeUtilTest {
    private static final LocalDateTime currDateTime =
            LocalDateTime.of(2020, 1, 1, 15, 30);

    @Test
    public void isExpired_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateTimeUtil.isExpired(null, null));
        assertThrows(NullPointerException.class, () -> DateTimeUtil.isExpired(null, null, null));
    }

    @Test
    public void isExpired_returnFalse() {
        LocalDate sameDay = currDateTime.toLocalDate();
        LocalTime sameTime = currDateTime.toLocalTime();

        // same day and time
        assertFalse(DateTimeUtil.isExpired(sameDay, sameTime, currDateTime));

        // future day, any time
        assertFalse(DateTimeUtil.isExpired(sameDay.plusDays(1), sameTime, currDateTime));
        assertFalse(DateTimeUtil.isExpired(sameDay.plusWeeks(1), LocalTime.NOON, currDateTime));
        assertFalse(DateTimeUtil.isExpired(sameDay.plusMonths(1), LocalTime.MIDNIGHT, currDateTime));

        // same day, future time
        assertFalse(DateTimeUtil.isExpired(sameDay, sameTime.plusHours(1), currDateTime));
        assertFalse(DateTimeUtil.isExpired(sameDay, sameTime.plusMinutes(1), currDateTime));
        assertFalse(DateTimeUtil.isExpired(sameDay, sameTime.plusSeconds(1), currDateTime));
    }

    @Test
    public void isExpired_returnTrue() {
        LocalDate sameDay = currDateTime.toLocalDate();
        LocalTime sameTime = currDateTime.toLocalTime();

        // past day, any time
        assertTrue(DateTimeUtil.isExpired(sameDay.minusDays(1), sameTime, currDateTime));
        assertTrue(DateTimeUtil.isExpired(sameDay.minusWeeks(1), LocalTime.NOON, currDateTime));
        assertTrue(DateTimeUtil.isExpired(sameDay.minusMonths(1), LocalTime.MIDNIGHT, currDateTime));

        // same day, past time
        assertTrue(DateTimeUtil.isExpired(sameDay, sameTime.minusHours(1), currDateTime));
        assertTrue(DateTimeUtil.isExpired(sameDay, sameTime.minusMinutes(1), currDateTime));
        assertTrue(DateTimeUtil.isExpired(sameDay, sameTime.minusSeconds(1), currDateTime));
    }
}

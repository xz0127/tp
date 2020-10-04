package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NaturalDayTest {
    @Test
    public void contains() {
        // true
        assertTrue(NaturalDay.MONDAY.contains("Monday"));
        assertTrue(NaturalDay.TUESDAY.contains("Tues"));
        assertTrue(NaturalDay.WEDNESDAY.contains("Wed"));
        assertTrue(NaturalDay.THURSDAY.contains("Thursday"));
        assertTrue(NaturalDay.FRIDAY.contains("friDay"));
        assertTrue(NaturalDay.SATURDAY.contains("SATURDAY"));
        assertTrue(NaturalDay.SUNDAY.contains("sun"));

        assertTrue(NaturalDay.TODAY.contains("tdy"));
        assertTrue(NaturalDay.TOMORROW.contains("tmr"));
        assertTrue(NaturalDay.YESTERDAY.contains("yesterday"));

        assertTrue(NaturalDay.MIDNIGHT.contains("midnight"));
        assertTrue(NaturalDay.NOON.contains("Noon"));
        assertTrue(NaturalDay.MORNING.contains("breakfast"));
        assertTrue(NaturalDay.EVENING.contains("eve"));
        assertTrue(NaturalDay.NIGHT.contains("night"));

        // false
        assertFalse(NaturalDay.MIDNIGHT.contains("Monday"));
        assertFalse(NaturalDay.EVENING.contains(""));
        assertFalse(NaturalDay.FRIDAY.contains(null));
        assertFalse(NaturalDay.WEDNESDAY.contains("123"));
    }

    @Test
    public void parse() {
        // null date
        assertThrows(NullPointerException.class, () -> NaturalDay.parse(null));

        // natural day found
        assertEquals(NaturalDay.MONDAY, NaturalDay.parse("Monday"));
        assertEquals(NaturalDay.TUESDAY, NaturalDay.parse("Tues"));
        assertEquals(NaturalDay.WEDNESDAY, NaturalDay.parse("Wed"));
        assertEquals(NaturalDay.THURSDAY, NaturalDay.parse("Thursday"));
        assertEquals(NaturalDay.FRIDAY, NaturalDay.parse("friDay"));
        assertEquals(NaturalDay.SATURDAY, NaturalDay.parse("SATURDAY"));
        assertEquals(NaturalDay.SUNDAY, NaturalDay.parse("sun"));

        assertEquals(NaturalDay.TODAY, NaturalDay.parse("tdy"));
        assertEquals(NaturalDay.TOMORROW, NaturalDay.parse("tmr"));
        assertEquals(NaturalDay.YESTERDAY, NaturalDay.parse("yesterday"));

        assertEquals(NaturalDay.MIDNIGHT, NaturalDay.parse("midnight"));
        assertEquals(NaturalDay.NOON, NaturalDay.parse("Noon"));
        assertEquals(NaturalDay.MORNING, NaturalDay.parse("breakfast"));
        assertEquals(NaturalDay.EVENING, NaturalDay.parse("eve"));
        assertEquals(NaturalDay.NIGHT, NaturalDay.parse("night"));

        // no natural day found
        assertNull(NaturalDay.parse(""));
        assertNull(NaturalDay.parse("123"));
    }
}

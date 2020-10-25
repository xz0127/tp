package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateTest {
    private final LocalDate currDate = LocalDate.of(2020, 5, 5);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void toStringBasedOn() {
        LocalDate testDate = LocalDate.of(2020, 6, 6);

        assertEquals("Saturday, Jun 06 2020", new Date(testDate).toStringBasedOn(currDate));
        assertEquals("Saturday, Jun 06 2020 (Today)", new Date(testDate).toStringBasedOn(testDate));
    }

    @Test
    public void isBefore() {
        Date testDate = new Date(2020, 6, 6);

        // null date check
        assertThrows(NullPointerException.class, () -> testDate.isBefore(null));

        // date is before input --> true
        assertTrue(testDate.isBefore(new Date(2020, 7, 6)));
        assertTrue(testDate.isBefore(new Date(2020, 6, 7)));

        // date is same as input --> false
        assertFalse(testDate.isBefore(testDate));
        assertFalse(testDate.isBefore(new Date(2020, 6, 6)));

        // date is after input --> false
        assertFalse(testDate.isBefore(new Date(2020, 5, 6)));
        assertFalse(testDate.isBefore(new Date(2020, 6, 5)));
    }

    @Test
    public void isAfter() {
        Date testDate = new Date(2020, 6, 6);

        // null date check
        assertThrows(NullPointerException.class, () -> testDate.isAfter(null));

        // date is after input --> true
        assertTrue(testDate.isAfter(new Date(2020, 5, 6)));
        assertTrue(testDate.isAfter(new Date(2020, 6, 5)));

        // date is same as input --> false
        assertFalse(testDate.isAfter(testDate));
        assertFalse(testDate.isAfter(new Date(2020, 6, 6)));

        // date is before input --> false
        assertFalse(testDate.isAfter(new Date(2020, 7, 6)));
        assertFalse(testDate.isAfter(new Date(2020, 6, 7)));
    }

    @Test
    public void isInSameWeek() {
        Date testDate = new Date(2020, 10, 19);

        // date is after input --> true
        assertTrue(testDate.isInSameWeek(new Date(2020, 10, 19)));
        assertTrue(testDate.isInSameWeek(new Date(2020, 10, 25)));


        // date is before input --> false
        assertFalse(testDate.isInSameWeek(new Date(2020, 7, 6)));
        assertFalse(testDate.isInSameWeek(new Date(2020, 6, 7)));
        assertFalse(testDate.isInSameWeek(new Date(2020, 10, 18)));
        assertFalse(testDate.isInSameWeek(new Date(2020, 10, 26)));
    }

    @Test
    public void equals() {
        Date dateTest = new Date(2020, 10, 10);

        // same values -> returns true
        Date dateTestCopy = new Date(2020, 10, 10);
        assertTrue(dateTest.equals(dateTestCopy));

        // same object -> returns true
        assertTrue(dateTest.equals(dateTest));

        // null -> returns false
        assertFalse(dateTest.equals(null));

        // different type -> returns false
        assertFalse(dateTest.equals(5));

        // different dates -> returns false
        assertFalse(dateTest.equals(new Date(2020, 6, 6)));
    }
}

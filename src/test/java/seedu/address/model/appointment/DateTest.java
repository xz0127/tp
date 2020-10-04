package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DateTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Date(LocalDate.of(2010, 1, 1)));
    }

    @Test
    public void isValidDate() {
        LocalDate currDate = LocalDate.of(2020, 5, 5);

        // null date
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null, currDate));

        // invalid dates
        assertFalse(Date.isValidDate(LocalDate.of(2019, 5, 5), currDate));
        assertFalse(Date.isValidDate(LocalDate.of(2020, 1, 5), currDate));
        assertFalse(Date.isValidDate(LocalDate.of(2020, 5, 4), currDate));
        assertFalse(Date.isValidDate(LocalDate.of(2019, 4, 4), currDate));

        // valid dates
        assertTrue(Date.isValidDate(LocalDate.of(2021, 5, 5), currDate));
        assertTrue(Date.isValidDate(LocalDate.of(2020, 6, 5), currDate));
        assertTrue(Date.isValidDate(LocalDate.of(2020, 5, 6), currDate));
        assertTrue(Date.isValidDate(LocalDate.of(2020, 5, 5), currDate));
    }

    @Test
    public void toStringBasedOn() {
        LocalDate testDate = LocalDate.of(2020, 1, 1);
        LocalDate currDate = LocalDate.of(2020, 5, 5);

        assertEquals("", new Date(testDate).toStringBasedOn(currDate));
        assertEquals("Today", new Date(testDate).toStringBasedOn(testDate));
    }

    @Test
    public void equals() {
        Date dateTest = new Date(LocalDate.of(2020, 1, 1));

        // same values -> returns true
        Date dateTestCopy = new Date(LocalDate.of(2020, 1, 1));
        assertTrue(dateTest.equals(dateTestCopy));

        // same object -> returns true
        assertTrue(dateTest.equals(dateTest));

        // null -> returns false
        assertFalse(dateTest.equals(null));

        // different type -> returns false
        assertFalse(dateTest.equals(5));

        // different dates -> returns false
        assertFalse(dateTest.equals(new Date(LocalDate.of(2020, 2, 2))));
    }
}

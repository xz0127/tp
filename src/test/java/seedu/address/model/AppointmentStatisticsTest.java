package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.AppointmentStatistics.STATISTICS_MESSAGE;

import org.junit.jupiter.api.Test;

public class AppointmentStatisticsTest {
    @Test
    public void toString_allOk() {
        AppointmentBook newAppointmentBook = new AppointmentBook();

        assertEquals(String.format(STATISTICS_MESSAGE, 0, 0, 0, 0),
                newAppointmentBook.getAppointmentBookStatistics().toString());
    }

    @Test
    public void getStats() {
        AppointmentStatistics stats = new AppointmentStatistics(5, 2, 3, 6);
        assertEquals(5, stats.getNumOfDoneApptInToday());
        assertEquals(2, stats.getNumOfUpcomingApptInToday());
        assertEquals(3, stats.getNumOfDoneApptInThisWeek());
        assertEquals(6, stats.getNumOfUpcomingApptInThisWeek());
    }

    @Test
    public void equals() {
        AppointmentStatistics originalStats = new AppointmentStatistics(0, 0, 0, 0);

        // same object -> returns true
        assertTrue(originalStats.equals(originalStats));

        // same values -> returns true
        AppointmentStatistics copyStats = new AppointmentStatistics(0, 0, 0, 0);
        assertTrue(originalStats.equals(copyStats));

        // different types -> returns false
        assertFalse(originalStats.equals(1));

        // null -> returns false
        assertFalse(originalStats.equals(null));

        // different num of done in today -> returns false
        AppointmentStatistics differentStats = new AppointmentStatistics(1, 0, 0, 0);
        assertFalse(originalStats.equals(differentStats));

        // different num of ucpoming in today -> returns false
        differentStats = new AppointmentStatistics(0, 1, 0, 0);
        assertFalse(originalStats.equals(differentStats));

        // different num of done in the week -> returns false
        differentStats = new AppointmentStatistics(0, 0, 1, 0);
        assertFalse(originalStats.equals(differentStats));

        // different num of done in the week -> returns false
        differentStats = new AppointmentStatistics(0, 0, 0, 1);
        assertFalse(originalStats.equals(differentStats));

    }
}

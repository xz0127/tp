package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

}

package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AppointmentStatisticsTest {
    @Test
    public void toStringBasedOn() {
        AppointmentBook newAppointmentBook = new AppointmentBook();

        assertEquals("Today: \n"
                + "0 upcoming  |  "
                + "0 done \n" + "This Week:\n" + "0 upcoming  |  "
                + "0 done", newAppointmentBook.getAppointmentBookStatistics().toString());
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

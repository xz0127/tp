package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AppointmentStatisticsTest {
    @Test
    public void toStringBasedOn() {
        AppointmentBook b = new AppointmentBook();

        assertEquals("Today: \n"
                + "0 upcoming  |  "
                + "0 done \n" + "This Week:\n" + "0 upcoming  |  "
                + "0 done", b.getAppointmentBookStatistics().toString());
    }

    @Test
    public void getStats() {
        AppointmentStatistics stats = new AppointmentStatistics(5, 2, 3, 6);
        assertEquals(5, stats.getDoneToday());
        assertEquals(2, stats.getUpcomingToday());
        assertEquals(3, stats.getDoneThisWeek());
        assertEquals(6, stats.getUpcomingThisWeek());
    }

}

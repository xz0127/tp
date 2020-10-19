package seedu.address.model;

public class AppointmentStatistics {
    private int totalToday;
    private int doneToday;
    private int upcomingToday;
    private int upcomingThisWeek;

    public AppointmentStatistics(int totalToday, int doneToday, int upcomingToday, int upcomingThisWeek) {
        this.totalToday = totalToday;
        this.doneToday = doneToday;
        this.upcomingToday = upcomingToday;
        this.upcomingThisWeek = upcomingThisWeek;
    }

    public int getUpcomingToday() {
        return this.upcomingToday;
    }

    public int getDoneToday() {
        return this.doneToday;
    }

    public int getUpcomingThisWeek() {
        return this.upcomingThisWeek;
    }

    public String toString() {
        return "Today: \n" + getUpcomingToday() +
                " upcoming appointments\n" + getDoneToday() +
                " done appointments\n" +
                "This Week:\n" + getUpcomingThisWeek() +
                " upcoming appointments";
    }
}

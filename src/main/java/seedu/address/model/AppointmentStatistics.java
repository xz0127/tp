package seedu.address.model;

/**
 * Collects related statistics of appointment book.
 */
public class AppointmentStatistics {
    private int doneToday;
    private int upcomingToday;
    private int doneThisWeek;
    private int upcomingThisWeek;

    /**
     * Creates a AppointmentStatistics with the {@code totalToday}, {@code doneToday}
     * {@code upcomingToday}, and {@code upcomingThisWeek}.
     */
    public AppointmentStatistics(int doneToday, int upcomingToday, int doneThisWeek, int upcomingThisWeek) {
        this.doneToday = doneToday;
        this.upcomingToday = upcomingToday;
        this.doneThisWeek = doneThisWeek;
        this.upcomingThisWeek = upcomingThisWeek;
    }

    /**
     * Returns the number of upcoming appointments in today.
     */
    public int getUpcomingToday() {
        return this.upcomingToday;
    }

    /**
     * Returns the number of done appointments in today.
     */
    public int getDoneToday() {
        return this.doneToday;
    }

    /**
     * Returns the number of upcoming appointments in this week.
     */
    public int getUpcomingThisWeek() {
        return this.upcomingThisWeek;
    }

    /**
     * Returns the number of done appointments in this week.
     */
    public int getDoneThisWeek() {
        return this.doneThisWeek;
    }

    @Override
    public String toString() {
        return "Today: \n" + getUpcomingToday()
                + " upcoming  |  " + getDoneToday() + " done \n"
                + "This Week:\n" + getUpcomingThisWeek() + " upcoming  |  " + getDoneThisWeek() + " done";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentStatistics // instanceof handles nulls
                && doneToday == ((AppointmentStatistics) other).getDoneToday()
                && upcomingToday == ((AppointmentStatistics) other).getUpcomingToday()
                && upcomingThisWeek == ((AppointmentStatistics) other).getUpcomingThisWeek());
    }
}

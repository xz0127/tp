package seedu.address.model;

/**
 * Collects related statistics of appointment book.
 */
public class AppointmentStatistics {
    private int totalToday;
    private int doneToday;
    private int upcomingToday;
    private int upcomingThisWeek;

    /**
     * Creates a AppointmentStatistics with the {@code totalToday}, {@code doneToday}
     * {@code upcomingToday}, and {@code upcomingThisWeek}.
     */
    public AppointmentStatistics(int totalToday, int doneToday, int upcomingToday, int upcomingThisWeek) {
        this.totalToday = totalToday;
        this.doneToday = doneToday;
        this.upcomingToday = upcomingToday;
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

    @Override
    public String toString() {
        return "Today: \n" + getUpcomingToday()
                + " upcoming  |  " + getDoneToday()
                + " done \n" + "This Week:\n" + getUpcomingThisWeek() + " upcoming";
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

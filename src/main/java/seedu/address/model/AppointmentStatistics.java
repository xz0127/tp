package seedu.address.model;

/**
 * Collects related statistics of appointment book.
 */
public class AppointmentStatistics {

    public static final String STATISTICS_MESSAGE = "Today: %d done | %d remaining\n"
            + "This Week: %d done | %d remaining";

    private int numOfDoneApptInToday;
    private int numOfUpcomingApptInToday;
    private int numOfDoneApptInThisWeek;
    private int numOfUpcomingApptInThisWeek;

    /**
     * Creates an AppointmentStatistics with the {@code totalToday}, {@code doneToday}
     * {@code upcomingToday}, and {@code upcomingThisWeek}.
     */
    public AppointmentStatistics(int numOfDoneApptInToday, int numOfUpcomingApptInToday,
                                 int numOfDoneApptInThisWeek, int numOfUpcomingApptInThisWeek) {
        this.numOfDoneApptInToday = numOfDoneApptInToday;
        this.numOfUpcomingApptInToday = numOfUpcomingApptInToday;
        this.numOfDoneApptInThisWeek = numOfDoneApptInThisWeek;
        this.numOfUpcomingApptInThisWeek = numOfUpcomingApptInThisWeek;
    }

    /**
     * Returns the number of upcoming appointments in today.
     */
    public int getNumOfUpcomingApptInToday() {
        return this.numOfUpcomingApptInToday;
    }

    /**
     * Returns the number of appointments done in today.
     */
    public int getNumOfDoneApptInToday() {
        return this.numOfDoneApptInToday;
    }

    /**
     * Returns the number of upcoming appointments in this week.
     */
    public int getNumOfUpcomingApptInThisWeek() {
        return this.numOfUpcomingApptInThisWeek;
    }

    /**
     * Returns the number of appointments done in this week.
     */
    public int getNumOfDoneApptInThisWeek() {
        return this.numOfDoneApptInThisWeek;
    }

    @Override
    public String toString() {
        return String.format(STATISTICS_MESSAGE, getNumOfDoneApptInToday(), getNumOfUpcomingApptInToday(),
                getNumOfDoneApptInThisWeek(), getNumOfUpcomingApptInThisWeek());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentStatistics // instanceof handles nulls
                && numOfDoneApptInToday == ((AppointmentStatistics) other).getNumOfDoneApptInToday()
                && numOfUpcomingApptInToday == ((AppointmentStatistics) other).getNumOfUpcomingApptInToday()
                && numOfDoneApptInThisWeek == ((AppointmentStatistics) other).getNumOfDoneApptInThisWeek()
                && numOfUpcomingApptInThisWeek == ((AppointmentStatistics) other).getNumOfUpcomingApptInThisWeek());
    }
}

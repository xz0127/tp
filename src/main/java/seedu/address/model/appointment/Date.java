package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an upcoming appointment date.
 */
public class Date {
    /**
     * The formatter to convert the Date into the desired form for presentation
     **/
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("EEEE, MMM dd uuuu");
    public static final String MESSAGE_CONSTRAINTS = "The date be an upcoming date. "
            + "It cannot be a date that is already passed.";

    private final LocalDate value;

    /**
     * Create a {@code Date} that represents the date of an appointment.
     *
     * @param date the {@code LocalDate} containing the date.
     */
    public Date(LocalDate date) {
        this(date, LocalDate.now());
    }

    /**
     * Create a {@code Date} that represents the date of an appointment.
     *
     * @param date the {@code LocalDate} containing the date.
     * @param now  the current {@code LocalDate}
     */
    public Date(LocalDate date, LocalDate now) {
        requireNonNull(date);

        checkArgument(isValidDate(date, now), MESSAGE_CONSTRAINTS);
        value = date;
    }

    /**
     * Checks if the given {@code LocalDate} is valid.
     * The {@code LocalDate} is valid if it is a Date that is not passed yet.
     *
     * @param test     the {@code LocalDate} to test.
     * @param currDate the current {@code LocalDate}.
     * @return true if it is vald, false otherwise.
     */
    public static boolean isValidDate(LocalDate test, LocalDate currDate) {
        return !test.isBefore(currDate);
    }

    /**
     * Creates a date string based on the currDate.
     *
     * @param currDate the current {@code LocalDate}.
     * @return the string representation of the appointment {@code Date}.
     */
    protected String toStringBasedOn(LocalDate currDate) {
        if (value.isEqual(currDate)) {
            // Simplify date to "Today"
            return "Today";
        }

        return value.format(DATE_FORMAT);
    }

    /**
     * Format appointment date as text for viewing
     */
    @Override
    public String toString() {
        return toStringBasedOn(LocalDate.now());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Date // instanceof handles nulls
                && value.equals(((Date) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;

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

    private final LocalDate value;

    /**
     * Create a {@code Date} that represents the date of an appointment.
     *
     * @param date the {@code LocalDate} containing the date.
     */
    public Date(LocalDate date) {
        requireNonNull(date);

        value = date;
    }

    public LocalDate getDate() {
        return value;
    }

    /**
     * Check if the appointment date comes before the input {@code Date}.
     *
     * @param inputDate the input date to be checked against
     * @return true if the appointment date comes before the input date, false otherwise
     */
    public boolean isBefore(Date inputDate) {
        return value.isBefore(inputDate.value);
    }

    /**
     * Check if the appointment date comes after the input {@code Date}.
     *
     * @param inputDate the input date to be checked against
     * @return true if the appointment date comes after the input date, false otherwise
     */
    public boolean isAfter(Date inputDate) {
        return value.isAfter(inputDate.value);
    }

    /**
     * Creates a date string based on the currDate.
     *
     * @param currDate the current {@code LocalDate}.
     * @return the string representation of the appointment {@code Date}.
     */
    protected String toStringBasedOn(LocalDate currDate) {
        String toDisplay = value.format(DATE_FORMAT);

        if (value.isEqual(currDate)) {
            // Simplify date to "Today"
            toDisplay += " (Today)";
        }

        return toDisplay;
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

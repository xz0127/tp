package seedu.address.model.appointment;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Tests that an {@code Appointment}'s {@code Date} matches the Date given.
 */
public class DateMatchesPredicate implements Predicate<Appointment> {
    private final Date date;

    public DateMatchesPredicate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return this.date;
    }

    @Override
    public boolean test(Appointment appointment) {
        return Stream.of(date).anyMatch(date -> date.equals(appointment.getDate()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof DateMatchesPredicate
                && date.equals(((DateMatchesPredicate) other).date));
    }
}

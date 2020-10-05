package seedu.address.model.appointment;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.format.DateTimeFormatter;

public class AppointmentId {
    public static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("uuuuMMdd");
    public static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HHmm");

    private final String value;

    AppointmentId(Date date, Time time) {
        requireAllNonNull(date, time);

        value = date.getDate().format(DATE_FORMAT) + time.getTime().format(TIME_FORMAT);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentId // instanceof handles nulls
                && value.equals(((AppointmentId) other).value)); // state check
    }

    @Override
    public String toString() {
        return value;
    }
}

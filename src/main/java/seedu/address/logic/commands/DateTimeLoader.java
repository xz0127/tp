package seedu.address.logic.commands;

import java.util.Optional;

import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;

/**
 * Stores the details to the appointment to be assigned.
 * Each non=empty field value will replace the corresponding field value of the Appointment.
 */
public class DateTimeLoader {
    private Date date;
    private Time time;

    public DateTimeLoader() {}

    /**
     * Copies constructor.
     */
    public DateTimeLoader(DateTimeLoader toCopy) {
        setAppointmentDate(toCopy.date);
        setAppointmentTime(toCopy.time);
    }

    /**
     * Sets {@code Date} of this {@code DateTimeBuilder} object.
     */
    public void setAppointmentDate(Date date) {
        this.date = date;
    }

    /**
     * Gets {@code Date} of this {@code DateTimeBuilder} object.
     */
    public Optional<Date> getDate() {
        return Optional.ofNullable(date);
    }

    /**
     * Sets {@code Time} of this {@code DateTimeBuilder} object.
     */
    public void setAppointmentTime(Time time) {
        this.time = time;
    }

    /**
     * Gets {@code Time} of this {@code DateTimeBuilder} object.
     */
    public Optional<Time> getTime() {
        return Optional.ofNullable(time);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DateTimeLoader)) {
            return false;
        }

        // state check
        DateTimeLoader loader = (DateTimeLoader) other;

        return getDate().equals(loader.getDate())
                && getTime().equals(loader.getTime());
    }
}

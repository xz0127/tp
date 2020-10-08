package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.logic.commands.AssignCommand.DateTimeLoader;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;

/**
 * A utility class to help with building AssignLoader objects.
 */
public class DateTimeLoaderBuilder {
    private DateTimeLoader loader;

    public DateTimeLoaderBuilder() {
        loader = new DateTimeLoader();
    }

    public DateTimeLoaderBuilder(DateTimeLoader loader) {
        this.loader = new DateTimeLoader(loader);
    }

    /**
     * Returns an {@code DateTimeLoaderBuilder} with fields containing {@code appointment}'s details
     */
    public DateTimeLoaderBuilder(Appointment appointment) {
        loader = new DateTimeLoader();
        loader.setAppointmentDate(appointment.getDate());
        loader.setAppointmentTime(appointment.getStartTime());
    }

    /**
     * Sets the {@code date} of the {@code AssignLoader} that we are building.
     */
    public DateTimeLoaderBuilder withDate(String date) {
        loader.setAppointmentDate(new Date(LocalDate.parse(date)));
        return this;
    }

    /**
     * Sets the {@code time} of the {@code AssignLoader} that we are building.
     */
    public DateTimeLoaderBuilder withTime(String time) {
        loader.setAppointmentTime(new Time(LocalTime.parse(time)));
        return this;
    }

    public DateTimeLoader build() {
        return loader;
    }
}

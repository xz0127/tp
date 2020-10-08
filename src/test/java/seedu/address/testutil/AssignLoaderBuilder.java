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
public class AssignLoaderBuilder {
    private DateTimeLoader loader;

    public AssignLoaderBuilder() {
        loader = new DateTimeLoader();
    }

    public AssignLoaderBuilder(DateTimeLoader loader) {
        this.loader = new DateTimeLoader(loader);
    }

    /**
     * Returns an {@code AssignLoaderBuilder} with fields containing {@code appointment}'s details
     */
    public AssignLoaderBuilder(Appointment appointment) {
        loader = new DateTimeLoader();
        loader.setAppointmentDate(appointment.getDate());
        loader.setAppointmentTime(appointment.getStartTime());
    }

    /**
     * Sets the {@code date} of the {@code AssignLoader} that we are building.
     */
    public AssignLoaderBuilder withDate(String date) {
        loader.setAppointmentDate(new Date(LocalDate.parse(date)));
        return this;
    }

    /**
     * Sets the {@code time} of the {@code AssignLoader} that we are building.
     */
    public AssignLoaderBuilder withTime(String time) {
        loader.setAppointmentTime(new Time(LocalTime.parse(time)));
        return this;
    }

    public DateTimeLoader build() {
        return loader;
    }
}

package seedu.address.testutil;

import seedu.address.logic.commands.DateTimeLoader;
import seedu.address.logic.parser.DateParserUtil;
import seedu.address.logic.parser.TimeParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;

/**
 * A utility class to help with building DateTimeLoader objects.
 */
public class DateTimeLoaderBuilder {
    private final DateTimeLoader loader;

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
     * Sets the {@code date} of the {@code DateTimeLoader} that we are building.
     */
    public DateTimeLoaderBuilder withDate(String date) {
        try {
            loader.setAppointmentDate(new Date(DateParserUtil.parse(date)));
        } catch (ParseException e) {
            // ignore, fallthrough expected
        }
        return this;
    }

    /**
     * Sets the {@code time} of the {@code DateTimeLoader} that we are building.
     */
    public DateTimeLoaderBuilder withTime(String time) {
        try {
            loader.setAppointmentTime(new Time(TimeParserUtil.parse(time)));
        } catch (ParseException e) {
            // ignore, fallthrough expected
        }
        return this;
    }

    public DateTimeLoader build() {
        return loader;
    }
}

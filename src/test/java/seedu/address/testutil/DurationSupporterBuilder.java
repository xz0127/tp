package seedu.address.testutil;

import seedu.address.logic.commands.AssignCommand.DurationSupporter;
import seedu.address.logic.parser.DateParserUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.TimeParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;

/**
 * A utility class to help with building DurationSupporter object.
 */
public class DurationSupporterBuilder {
    private final DurationSupporter loader;

    public DurationSupporterBuilder() {
        loader = new DurationSupporter();
    }

    public DurationSupporterBuilder(DurationSupporter loader) {
        this.loader = new DurationSupporter(loader);
    }

    /**
     * Returns an {@code DurationSupporterBuilder} with fields containing {@code Appointment}'s details
     */
    public DurationSupporterBuilder(Appointment appointment) {
        loader = new DurationSupporter();
        loader.setAppointmentDate(appointment.getDate());
        loader.setAppointmentTime(appointment.getStartTime());
        loader.setAppointmentDuration(appointment.getDuration());
    }

    /**
     * Sets the {@code Time} of the {@code DurationSupporterBuilder} that we are building.
     */
    public DurationSupporterBuilder withDate(String date) {
        try {
            loader.setAppointmentDate(new Date(DateParserUtil.parse(date)));
        } catch (ParseException e) {
            // ignore, fallthrough expected
        }
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code DurationSupporterBuilder} that we are building.
     */
    public DurationSupporterBuilder withTime(String time) {
        try {
            loader.setAppointmentTime(new Time(TimeParserUtil.parse(time)));
        } catch (ParseException e) {
            // ignore, fallthrough expected
        }
        return this;
    }

    /**
     * Sets the {@code Duration} of the {@code DurationSupporterBuilder} that we are building.
     */
    public DurationSupporterBuilder withDuration(String duration) {
        try {
            loader.setAppointmentDuration(ParserUtil.parseDuration(duration));
        } catch (ParseException e) {
            // ignore, fallthrough expected
        }
        return this;
    }

    public DurationSupporter build() {
        return loader;
    }
}

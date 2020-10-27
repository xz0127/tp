package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EXPIRED_DATE_TIME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.appointment.Appointment.CREATION_OFFSET_MINUTES;

import java.time.Duration;
import java.time.LocalTime;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.commands.ChangeCommand;
import seedu.address.logic.commands.ChangeCommand.EditAppointmentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;


/**
 * Parses input arguments and creates a new ChangeCommand object.
 */
public class ChangeCommandParser implements Parser<ChangeCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ChangeCommand
     * and returns an ChangeCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public ChangeCommand parse(String arg) throws ParseException {
        requireNonNull(arg);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arg, PREFIX_DATE, PREFIX_TIME, PREFIX_DURATION);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE), pe);
        }

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editAppointmentDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            editAppointmentDescriptor.setStartTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            editAppointmentDescriptor.setDuration(ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get()));
        }

//        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
//        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
//        Duration duration = ParserUtil.parseDuration(argMultimap.getValue(PREFIX_DURATION).get());

        // Expired date time can only be confirmed when date and time are put together.
//        LocalTime timeWithLeeway = time.getTime().plusMinutes(CREATION_OFFSET_MINUTES);
//        if (DateTimeUtil.isExpired(date.getDate(), timeWithLeeway)) {
//            throw new ParseException(MESSAGE_EXPIRED_DATE_TIME);
//        }

//        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor(date, time, duration);

        return new ChangeCommand(index, editAppointmentDescriptor);
    }
}

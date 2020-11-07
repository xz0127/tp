package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeCommand;
import seedu.address.logic.commands.ChangeCommand.EditAppointmentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ChangeCommand object.
 */
public class ChangeCommandParser implements Parser<ChangeCommand> {

    public static final String MULTIPLE_DATES_DETECTED = "Please provide only 1 date input when rescheduling your "
            + "appointment.";
    public static final String MULTIPLE_TIME_DETECTED = "Please provide only 1 time input when rescheduling your "
            + "appointment.";
    public static final String MULTIPLE_DURATIONS_DETECTED = "Please provide only 1 duration input when rescheduling "
            + "your appointment.";

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ChangeCommand.MESSAGE_USAGE), pe);
        }

        EditAppointmentDescriptor editAppointmentDescriptor = new EditAppointmentDescriptor();

        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            if (!argMultimap.isSingleValue(PREFIX_DATE)) {
                throw new ParseException(MULTIPLE_DATES_DETECTED);
            }
            editAppointmentDescriptor.setDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }
        if (argMultimap.getValue(PREFIX_TIME).isPresent()) {
            if (!argMultimap.isSingleValue(PREFIX_TIME)) {
                throw new ParseException(MULTIPLE_TIME_DETECTED);
            }
            editAppointmentDescriptor.setStartTime(ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get()));
        }
        if (argMultimap.getValue(PREFIX_DURATION).isPresent()) {
            if (!argMultimap.isSingleValue(PREFIX_DURATION)) {
                throw new ParseException(MULTIPLE_DURATIONS_DETECTED);
            }
            editAppointmentDescriptor.setDuration(ParserUtil.parseDuration(argMultimap
                                                                            .getValue(PREFIX_DURATION).get()));
        }

        return new ChangeCommand(index, editAppointmentDescriptor);
    }
}

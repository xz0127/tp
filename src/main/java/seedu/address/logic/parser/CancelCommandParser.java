package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.CancelCommand;
import seedu.address.logic.commands.DateTimeLoader;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;

/**
 * Parses input arguments and creates a new CancelCommand object
 */
public class CancelCommandParser implements Parser<CancelCommand> {
    /**
     * Parses the given {@code String} of arguments based on the CancelCommand
     * and returns a CancelCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public CancelCommand parse(String arg) throws ParseException {
        requireNonNull(arg);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arg, PREFIX_DATE, PREFIX_TIME);
        if (argMultimap.getValue(PREFIX_DATE).isEmpty()
                && argMultimap.getValue(PREFIX_TIME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getValue(PREFIX_DATE).isEmpty()) {
            throw new ParseException(String.format(CancelCommand.DATE_MISSING, CancelCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getValue(PREFIX_TIME).isEmpty()) {
            throw new ParseException(String.format(CancelCommand.TIME_MISSING, CancelCommand.MESSAGE_USAGE));
        }

        DateTimeLoader dateTimeLoader = new DateTimeLoader();
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        dateTimeLoader.setAppointmentDate(date);
        dateTimeLoader.setAppointmentTime(time);

        return new CancelCommand(dateTimeLoader);
    }
}


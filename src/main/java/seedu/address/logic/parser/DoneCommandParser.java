package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.logic.commands.DateTimeLoader;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;

/**
 * Parses input arguments and creates a new DoneCommand object
 */
public class DoneCommandParser implements Parser<DoneCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DoneCommand
     * and returns an DoneCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DoneCommand parse(String arg) throws ParseException {
        requireNonNull(arg);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arg, PREFIX_DATE, PREFIX_TIME);
        if (argMultimap.getValue(PREFIX_DATE).isEmpty()
                && argMultimap.getValue(PREFIX_TIME).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DoneCommand.MESSAGE_USAGE));
        }
        if (argMultimap.getValue(PREFIX_DATE).isEmpty()) {
            throw new ParseException(DoneCommand.DATE_MISSING);
        }
        if (argMultimap.getValue(PREFIX_TIME).isEmpty()) {
            throw new ParseException(DoneCommand.TIME_MISSING);
        }

        DateTimeLoader dateTimeLoader = new DateTimeLoader();
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        Time time = ParserUtil.parseTime(argMultimap.getValue(PREFIX_TIME).get());
        dateTimeLoader.setAppointmentDate(date);
        dateTimeLoader.setAppointmentTime(time);

        return new DoneCommand(dateTimeLoader);
    }
}

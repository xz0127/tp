package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_EXPIRED_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.DateMatchesPredicate;

/**
 * Parse input arguments and creates a new ViewCommand object.
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewCommand
     * and returns a ViewCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public ViewCommand parse(String arg) throws ParseException {
        if (arg.equals("")) {
            return new ViewCommand(PREDICATE_SHOW_ALL_APPOINTMENTS);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(arg, PREFIX_DATE);

        if (argMultimap.getValue(PREFIX_DATE).isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE)
            );
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        if (DateTimeUtil.isExpiredByDay(date.getDate())) {
            throw new ParseException(MESSAGE_EXPIRED_DATE);
        }

        DateMatchesPredicate predicate = new DateMatchesPredicate(date);

        return new ViewCommand(predicate);
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_EXPIRED_TIME_SLOTS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.time.LocalDate;

import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.commands.AvailableCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.DateMatchesPredicate;

/**
 * Parse input arguments and creates a new AvailableCommand object.
 */
public class AvailableCommandParser implements Parser<AvailableCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of AvailableCommand
     * and returns an AvailableCommand for execution.
     * @param userInput full user input string
     * @return an AvailableCommand object
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AvailableCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_DATE);

        if (argMultimap.getValue(PREFIX_DATE).isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AvailableCommand.MESSAGE_USAGE)
            );
        }

        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());

        if (DateTimeUtil.isExpiredByDay(date.getDate())) {
            throw new ParseException(MESSAGE_EXPIRED_TIME_SLOTS);
        }

        LocalDate today = LocalDate.now();
        boolean isToday = date.getDate().equals(today);
        DateMatchesPredicate predicate = new DateMatchesPredicate(date);

        return new AvailableCommand(predicate, isToday);
    }
}

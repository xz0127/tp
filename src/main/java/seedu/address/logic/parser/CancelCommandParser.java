package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CancelCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
        try {
            Index index = ParserUtil.parseIndex(arg);
            return new CancelCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelCommand.MESSAGE_USAGE), pe);
        }
    }
}


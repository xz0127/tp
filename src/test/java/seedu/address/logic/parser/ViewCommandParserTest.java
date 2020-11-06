package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_EXPIRED_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGN_DATE;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC_EXPIRED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.DateMatchesPredicate;


public class ViewCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE);

    private final ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_noArgs_returnsViewCommand() {
        ViewCommand expectedCommand = new ViewCommand(PREDICATE_SHOW_ALL_APPOINTMENTS);
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_validArgs_returnsViewCommand() throws ParseException {
        ViewCommand expectedCommand = new ViewCommand(preparePredicate(VALID_DATE));
        assertParseSuccess(parser, ASSIGN_DATE, expectedCommand);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid prefix
        assertParseFailure(parser, " a/" + VALID_DATE, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, INVALID_DATE_DESC, DateParserUtil.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_expiredDate_failure() {
        assertParseFailure(parser, INVALID_DATE_DESC_EXPIRED, MESSAGE_EXPIRED_DATE);
    }

    @Test
    public void parse_emptyDate_failure() {
        assertParseFailure(parser, EMPTY_DATE_DESC, DateParserUtil.MESSAGE_EMPTY_DATE);
    }

    /**
     * Parses {@code input} into a {@code DateMatchesPredicate}.
     */
    private DateMatchesPredicate preparePredicate(String input) throws ParseException {
        return new DateMatchesPredicate(ParserUtil.parseDate(input));
    }
}

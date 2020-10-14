package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGN_DATE;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGN_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC_LETTERS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC_CLOSED;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC_LETTERS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CancelCommand;
import seedu.address.logic.commands.DateTimeLoader;
import seedu.address.model.appointment.Time;
import seedu.address.testutil.DateTimeLoaderBuilder;

public class CancelCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, CancelCommand.MESSAGE_USAGE);

    private final CancelCommandParser parser = new CancelCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no date specified
        assertParseFailure(parser, ASSIGN_TIME, CancelCommand.DATE_MISSING);

        // no time specified
        assertParseFailure(parser, ASSIGN_DATE, CancelCommand.TIME_MISSING);

        // no date or time specified, empty arguments
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, INVALID_DATE_DESC + ASSIGN_TIME, DateParserUtil.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_DATE_DESC_LETTERS + ASSIGN_TIME, DateParserUtil.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_TIME_DESC + ASSIGN_DATE, TimeParserUtil.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_TIME_DESC_CLOSED + ASSIGN_DATE, Time.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_TIME_DESC_LETTERS + ASSIGN_DATE, TimeParserUtil.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, INVALID_TIME_DESC + INVALID_DATE_DESC,
                DateParserUtil.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, INVALID_TIME_DESC_CLOSED + INVALID_DATE_DESC,
                DateParserUtil.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldPresents_success() {
        String userInput = ASSIGN_DATE + ASSIGN_TIME;

        DateTimeLoader loader = new DateTimeLoaderBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).build();

        CancelCommand expectedCommand = new CancelCommand(loader);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

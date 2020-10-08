package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGN_DATE;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGN_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGN_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignCommand;

public class AssignCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE);

    private AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, ASSIGN_DATE_TIME, MESSAGE_INVALID_FORMAT);

        // no date specified
        assertParseFailure(parser, "1" + ASSIGN_TIME, AssignCommand.DATE_MISSING);

        // no time specified
        assertParseFailure(parser, "1" + ASSIGN_DATE, AssignCommand.TIME_MISSING);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + ASSIGN_DATE_TIME, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + ASSIGN_DATE_TIME, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix
        assertParseFailure(parser, "1 o/" + VALID_DATE + ASSIGN_TIME, MESSAGE_INVALID_FORMAT);
    }

    // todo: add more time format check test
}

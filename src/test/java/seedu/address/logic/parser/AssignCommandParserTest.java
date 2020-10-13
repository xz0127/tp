package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_EXPIRED_DATE_TIME;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGN_DATE;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGN_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.ASSIGN_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC_EXPIRED;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC_CLOSED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.DateTimeLoader;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.testutil.DateTimeLoaderBuilder;

public class AssignCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AssignCommand.MESSAGE_USAGE);

    private final AssignCommandParser parser = new AssignCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, ASSIGN_DATE_TIME, MESSAGE_INVALID_FORMAT);

        // no date specified
        assertParseFailure(parser, "1" + ASSIGN_TIME, AssignCommand.DATE_MISSING);

        // no time specified
        assertParseFailure(parser, "1" + ASSIGN_DATE, AssignCommand.TIME_MISSING);

        // no index and no date or time specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
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

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + ASSIGN_TIME, DateParserUtil.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_DATE_DESC_EXPIRED + ASSIGN_TIME, MESSAGE_EXPIRED_DATE_TIME);

        assertParseFailure(parser, "1" + INVALID_TIME_DESC + ASSIGN_DATE, TimeParserUtil.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TIME_DESC_CLOSED + ASSIGN_DATE, Time.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + INVALID_TIME_DESC + INVALID_DATE_DESC,
                DateParserUtil.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TIME_DESC_CLOSED + INVALID_DATE_DESC,
                DateParserUtil.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TIME_DESC + INVALID_DATE_DESC_EXPIRED,
                TimeParserUtil.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TIME_DESC_CLOSED + INVALID_DATE_DESC_EXPIRED,
                Time.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Index targetIndex = INDEX_SECOND_PATIENT;
        String userInput = targetIndex.getOneBased() + ASSIGN_DATE + ASSIGN_TIME;

        DateTimeLoader loader = new DateTimeLoaderBuilder().withDate(VALID_DATE).withTime(VALID_TIME).build();

        AssignCommand expectedCommand = new AssignCommand(targetIndex, loader);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

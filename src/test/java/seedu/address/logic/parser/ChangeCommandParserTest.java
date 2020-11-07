package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.CHANGE_DATE;
import static seedu.address.logic.commands.CommandTestUtil.CHANGE_DATE_TIME_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.CHANGE_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.CHANGE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DURATION_NEGATIVE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DURATION_NON_INTEGER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIME_DESC_CLOSED;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.logic.parser.ChangeCommandParser.MULTIPLE_DATES_DETECTED;
import static seedu.address.logic.parser.ChangeCommandParser.MULTIPLE_DURATIONS_DETECTED;
import static seedu.address.logic.parser.ChangeCommandParser.MULTIPLE_TIME_DETECTED;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangeCommand;
import seedu.address.logic.commands.ChangeCommand.EditAppointmentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Time;

public class ChangeCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangeCommand.MESSAGE_USAGE);

    private final ChangeCommandParser parser = new ChangeCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, CHANGE_DATE_TIME_DURATION, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleParts_failure() {
        // multiple dates specified
        assertParseFailure(parser, "1" + CHANGE_DATE_TIME_DURATION + " " + PREFIX_DATE + VALID_DATE,
                MULTIPLE_DATES_DETECTED);

        // multiple times specified
        assertParseFailure(parser, "1" + CHANGE_DATE_TIME_DURATION + " " + PREFIX_TIME + VALID_TIME,
                MULTIPLE_TIME_DETECTED);

        // multiple durations specified
        assertParseFailure(parser, "1" + CHANGE_DATE_TIME_DURATION + " " + PREFIX_DURATION + VALID_DURATION,
                MULTIPLE_DURATIONS_DETECTED);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + CHANGE_DATE_TIME_DURATION, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, "0" + CHANGE_DATE_TIME_DURATION, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeCommand.MESSAGE_USAGE));

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeCommand.MESSAGE_USAGE));

        // invalid prefix
        assertParseFailure(parser, "1 o/" + VALID_DATE + CHANGE_TIME, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ChangeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_DATE_DESC + CHANGE_TIME, DateParserUtil.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + INVALID_TIME_DESC + CHANGE_DATE, TimeParserUtil.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TIME_DESC_CLOSED + CHANGE_DATE, Time.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + INVALID_DATE_DESC, DateParserUtil.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + INVALID_TIME_DESC,
                TimeParserUtil.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TIME_DESC_CLOSED,
                Time.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_DURATION_NEGATIVE_DESC,
                ParserUtil.MESSAGE_INVALID_DURATION);
        assertParseFailure(parser, "1" + INVALID_DURATION_NON_INTEGER_DESC,
                ParserUtil.MESSAGE_INVALID_DURATION);
    }

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        Index targetIndex = INDEX_SECOND_PATIENT;
        String userInput = targetIndex.getOneBased() + CHANGE_DATE + CHANGE_TIME + CHANGE_DURATION;

        EditAppointmentDescriptor editAppointmentDescriptor =
                new EditAppointmentDescriptor();
        editAppointmentDescriptor.setDuration(ParserUtil.parseDuration(VALID_DURATION));
        editAppointmentDescriptor.setDate(ParserUtil.parseDate(VALID_DATE));
        editAppointmentDescriptor.setStartTime(ParserUtil.parseTime(VALID_TIME));

        ChangeCommand expectedCommand = new ChangeCommand(targetIndex, editAppointmentDescriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}

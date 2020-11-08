package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.patient.Remark;

public class RemarkCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

    private final RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no remark specified
        //assertParseFailure(parser, " 1 ", MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, PREFIX_REMARK + VALID_REMARK_AMY, MESSAGE_INVALID_FORMAT);

        // no index and no index and remark specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        // invalid index
        assertParseFailure(parser, INVALID_REMARK_INDEX,
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldPresents_success() {
        String userInput = " 1 " + REMARK_DESC_AMY;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PATIENT, new Remark(VALID_REMARK_AMY));
        assertParseSuccess(parser, userInput, expectedCommand);

        String userInput2 = " 2 " + REMARK_DESC_BOB;
        RemarkCommand expectedCommand2 = new RemarkCommand(INDEX_SECOND_PATIENT, new Remark(VALID_REMARK_BOB));
        assertParseSuccess(parser, userInput2, expectedCommand2);

        String userInput3 = " 1 " + REMARK_DESC_EMPTY;
        RemarkCommand expectedCommand3 = new RemarkCommand(INDEX_FIRST_PATIENT, new Remark(""));
        assertParseSuccess(parser, userInput3, expectedCommand3);
    }
}

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindPatientDescriptor;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgsOfNames_returnsFindCommand() {
        FindPatientDescriptor descriptor = new FindPatientDescriptor();
        descriptor.setNamePredicate(new String[]{"Alice", "Bob"});
        FindCommand expectedFindCommand = new FindCommand(descriptor);
        // no leading white space and trailing
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_NAME + " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsOfPhone_returnsFindCommand() {
        FindPatientDescriptor descriptor = new FindPatientDescriptor();
        descriptor.setPhonePredicate(new String[]{"12345678", "87654321"});
        FindCommand expectedFindCommand = new FindCommand(descriptor);
        // no leading white space and trailing
        assertParseSuccess(parser, " " + PREFIX_PHONE + "12345678 87654321", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_PHONE + " \n 12345678 \n \t 87654321  \t", expectedFindCommand);
    }

    @Test
    public void parse_validArgsOfNric_returnsFindCommand() {
        FindPatientDescriptor descriptor = new FindPatientDescriptor();
        descriptor.setNricPredicate(new String[]{"S1234567I", "S7654321Q"});
        FindCommand expectedFindCommand = new FindCommand(descriptor);
        // no leading white space and trailing
        assertParseSuccess(parser, " " + PREFIX_NRIC + "S1234567I S7654321Q", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " " + PREFIX_NRIC + " \n S1234567I \n \t S7654321Q  \t", expectedFindCommand);
    }
}

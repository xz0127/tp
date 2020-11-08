package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.commands.AvailableCommand;
import seedu.address.logic.commands.CancelCommand;
import seedu.address.logic.commands.ChangeCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DoneCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindCommand.FindPatientDescriptor;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.DateMatchesPredicate;
import seedu.address.model.appointment.Time;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Remark;
import seedu.address.testutil.EditPatientDescriptorBuilder;
import seedu.address.testutil.PatientBuilder;
import seedu.address.testutil.PatientUtil;

public class NuudleParserTest {

    private final NuudleParser parser = new NuudleParser();

    @Test
    public void parseCommand_add() throws Exception {
        Patient patient = new PatientBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(PatientUtil.getAddCommand(patient));
        assertEquals(new AddCommand(patient), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
                DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PATIENT.getOneBased());
        assertEquals(new DeleteCommand(INDEX_FIRST_PATIENT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Patient patient = new PatientBuilder().build();
        EditPatientDescriptor descriptor = new EditPatientDescriptorBuilder(patient).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PATIENT.getOneBased() + " " + PatientUtil.getEditPatientDescriptorDetails(descriptor));
        assertEquals(new EditCommand(INDEX_FIRST_PATIENT, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " "
                        + PREFIX_NAME + String.join(" ", keywords) + " "
                        + PREFIX_PHONE + "88889999 "
                        + PREFIX_NRIC + "S1234567I");
        FindPatientDescriptor descriptor = new FindPatientDescriptor();
        descriptor.setNamePredicate(new String[]{"foo", "bar", "baz"});
        descriptor.setNricPredicate(new String[]{"S1234567I"});
        descriptor.setPhonePredicate(new String[]{"88889999"});
        assertEquals(new FindCommand(descriptor), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_remark() throws Exception {
        Remark remark = new Remark("NuudleParserRemarkTest");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PATIENT.getOneBased() + " " + PREFIX_REMARK + "NuudleParserRemarkTest");
        assertEquals(new RemarkCommand(INDEX_FIRST_PATIENT, remark), command);
    }

    @Test
    public void parseCommand_assign() throws Exception {
        AssignCommand.DurationSupporter supporter = new AssignCommand.DurationSupporter();
        supporter.setAppointmentDate(new Date(LocalDate.now().plusDays(1)));
        supporter.setAppointmentTime(new Time(LocalTime.NOON));
        supporter.setAppointmentDuration(Duration.ofMinutes(30));

        AssignCommand command = (AssignCommand) parser.parseCommand(AssignCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PATIENT.getOneBased() + " " + PREFIX_DATE + "tomorrow "
                + PREFIX_TIME + "12:00PM " + PREFIX_DURATION + "30 ");

        assertEquals(new AssignCommand(INDEX_FIRST_PATIENT, supporter), command);
    }

    @Test
    public void parseCommand_change() throws Exception {
        ChangeCommand.EditAppointmentDescriptor descriptor = new ChangeCommand.EditAppointmentDescriptor(
                new Date(LocalDate.now().plusDays(1)),
                new Time(LocalTime.NOON),
                Duration.ofMinutes(30));

        ChangeCommand command = (ChangeCommand) parser.parseCommand(ChangeCommand.COMMAND_WORD + " "
                + INDEX_FIRST_APPOINTMENT.getOneBased() + " " + PREFIX_DATE + "tomorrow "
                + PREFIX_TIME + "12:00PM " + PREFIX_DURATION + "30 ");

        assertEquals(new ChangeCommand(INDEX_FIRST_APPOINTMENT, descriptor), command);
    }

    @Test
    public void parseCommand_done() throws Exception {
        DoneCommand command = (DoneCommand) parser.parseCommand(DoneCommand.COMMAND_WORD + " "
                + INDEX_FIRST_APPOINTMENT.getOneBased());
        assertEquals(new DoneCommand(INDEX_FIRST_APPOINTMENT), command);
    }

    @Test
    public void parseCommand_view() throws Exception {
        DateMatchesPredicate predicate = new DateMatchesPredicate(new Date(LocalDate.now().plusDays(1)));

        ViewCommand command = (ViewCommand) parser.parseCommand(ViewCommand.COMMAND_WORD + " "
                + PREFIX_DATE + "tomorrow");
        assertEquals(new ViewCommand(predicate), command);
    }

    @Test
    public void parseCommand_avail() throws Exception {
        DateMatchesPredicate predicate = new DateMatchesPredicate(new Date(LocalDate.now().plusDays(1)));

        AvailableCommand command = (AvailableCommand) parser.parseCommand(AvailableCommand.COMMAND_WORD + " "
                + PREFIX_DATE + "tomorrow");
        assertEquals(new AvailableCommand(predicate, false), command);
    }

    @Test
    public void parseCommand_cancel() throws Exception {
        CancelCommand command = (CancelCommand) parser.parseCommand(CancelCommand.COMMAND_WORD + " "
                + INDEX_FIRST_APPOINTMENT.getOneBased());
        assertEquals(new CancelCommand(INDEX_FIRST_APPOINTMENT), command);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD + " 3") instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD + " 3") instanceof RedoCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}

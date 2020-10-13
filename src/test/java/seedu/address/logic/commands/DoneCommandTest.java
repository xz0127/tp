package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DIFF_DATE;
import static seedu.address.logic.commands.CommandTestUtil.SAME_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.DateTimeLoaderBuilder;


public class DoneCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalAppointmentBook(), new UserPrefs());

    @Test
    public void execute_validDateTimeInput_success() {
        DateTimeLoader loader = new DateTimeLoaderBuilder()
                .withDate("1 Jan 2020").withTime("9am").build();
        Appointment appointmentToMark = ALICE_APPOINTMENT;
        Appointment markedAppointment = ALICE_APPOINTMENT.markAsDone();
        DoneCommand doneCommand = new DoneCommand(loader);

        String expectedMessage = String.format(DoneCommand.MESSAGE_MARK_DONE_SUCCESS, appointmentToMark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(),
                model.getAppointmentBook(), new UserPrefs());
        expectedModel.setAppointment(appointmentToMark, markedAppointment);

        assertCommandSuccess(doneCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidDateTimeInput_fail() {
        DateTimeLoader loader = new DateTimeLoaderBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).build();
        DoneCommand doneCommand = new DoneCommand(loader);

        assertCommandFailure(doneCommand, model, DoneCommand.APPOINTMENT_DOES_NOT_EXISTS);
    }

    @Test
    public void execute_markTheAlreadyDoneAppointment_fail() {
        DateTimeLoader loader = new DateTimeLoaderBuilder()
                .withDate("1 Jan 2020").withTime("9am").build();
        Appointment appointmentToMark = ALICE_APPOINTMENT;
        Appointment markedAppointment = ALICE_APPOINTMENT.markAsDone();
        model.setAppointment(appointmentToMark, markedAppointment);
        DoneCommand doneCommand = new DoneCommand(loader);

        assertCommandFailure(doneCommand, model, DoneCommand.APPOINTMENT_HAS_BEEN_MARKED);
    }

    @Test
    public void equals() {
        DateTimeLoader loader = new DateTimeLoaderBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).build();
        final DoneCommand standardCommand = new DoneCommand(loader);

        // same values -> returns true
        DoneCommand commandWithSameValues = new DoneCommand(loader);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different DateTimeLoader -> returns false
        DateTimeLoader difLoader = new DateTimeLoaderBuilder()
                .withDate(DIFF_DATE).withTime(SAME_TIME).build();
        assertFalse(standardCommand.equals(new DoneCommand(difLoader)));
    }
}

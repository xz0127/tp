package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_APPOINTMENT;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;


public class DoneCommandTest {
    private Model model = new ModelManager(getTypicalPatientBook(), getTypicalAppointmentBook(), new UserPrefs());

    @Test
    public void execute_validDateTimeInput_success() {
        Appointment appointmentToMark = ALICE_APPOINTMENT;
        Appointment markedAppointment = ALICE_APPOINTMENT.markAsDone();
        DoneCommand doneCommand = new DoneCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(DoneCommand.MESSAGE_MARK_DONE_SUCCESS, appointmentToMark);

        ModelManager expectedModel = new ModelManager(model.getPatientBook(),
                model.getAppointmentBook(), new UserPrefs());
        expectedModel.setAppointment(appointmentToMark, markedAppointment);

        assertCommandSuccess(doneCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_markTheAlreadyDoneAppointment_fail() {
        Appointment appointmentToMark = ALICE_APPOINTMENT;
        Appointment markedAppointment = ALICE_APPOINTMENT.markAsDone();
        model.setAppointment(appointmentToMark, markedAppointment);
        DoneCommand doneCommand = new DoneCommand(INDEX_FIRST_APPOINTMENT);

        assertCommandFailure(doneCommand, model, DoneCommand.APPOINTMENT_HAS_BEEN_MARKED);
    }

    @Test
    public void equals() {
        final DoneCommand standardCommand = new DoneCommand(INDEX_FIRST_APPOINTMENT);

        // same values -> returns true
        DoneCommand commandWithSameValues = new DoneCommand(INDEX_FIRST_APPOINTMENT);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different DateTimeLoader -> returns false
        assertFalse(standardCommand.equals(new DoneCommand(INDEX_FOURTH_APPOINTMENT)));
    }
}

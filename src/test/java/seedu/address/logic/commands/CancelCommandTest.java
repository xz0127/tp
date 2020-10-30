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

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;

public class CancelCommandTest {
    private Model model = new ModelManager(getTypicalPatientBook(), getTypicalAppointmentBook(), new UserPrefs());

    @Test
    public void execute_validIndex_success() {
        Appointment appointmentToCancel = ALICE_APPOINTMENT;
        CancelCommand cancelCommand = new CancelCommand(INDEX_FIRST_APPOINTMENT);

        String expectedMessage = String.format(CancelCommand.MESSAGE_MARK_CANCEL_SUCCESS, appointmentToCancel);

        ModelManager expectedModel = new ModelManager(model.getPatientBook(),
                model.getAppointmentBook(), new UserPrefs());
        expectedModel.deleteAppointment(appointmentToCancel);
        expectedModel.commitPatientBook();
        expectedModel.commitAppointmentBook();

        assertCommandSuccess(cancelCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        CancelCommand cancelCommand = new CancelCommand(outOfBoundIndex);

        assertCommandFailure(cancelCommand, model, Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final CancelCommand standardCommand = new CancelCommand(INDEX_FIRST_APPOINTMENT);

        // same values -> returns true
        CancelCommand commandWithSameValues = new CancelCommand(INDEX_FIRST_APPOINTMENT);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different DateTimeLoader -> returns false
        assertFalse(standardCommand.equals(new CancelCommand(INDEX_FOURTH_APPOINTMENT)));
    }
}

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
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.DateTimeLoaderBuilder;


public class CancelCommandTest {
    private Model model = new ModelManager(getTypicalPatientBook(), getTypicalAppointmentBook(), new UserPrefs());

    @Test
    public void execute_validDateTimeInput_success() {
        DateTimeLoader loader = new DateTimeLoaderBuilder()
                .withDate("1 Jan 2020").withTime("9am").build();
        Appointment appointmentToCancel = ALICE_APPOINTMENT;
        CancelCommand cancelCommand = new CancelCommand(loader);

        String expectedMessage = String.format(CancelCommand.MESSAGE_MARK_CANCEL_SUCCESS, appointmentToCancel);

        ModelManager expectedModel = new ModelManager(model.getPatientBook(),
                model.getAppointmentBook(), new UserPrefs());
        expectedModel.deleteAppointment(appointmentToCancel);

        assertCommandSuccess(cancelCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_appointmentWithDateTimeAbsent_fail() {
        DateTimeLoader loader = new DateTimeLoaderBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).build();
        CancelCommand cancelCommand = new CancelCommand(loader);

        assertCommandFailure(cancelCommand, model, CancelCommand.APPOINTMENT_DOES_NOT_EXISTS);
    }

    @Test
    public void equals() {
        DateTimeLoader loader = new DateTimeLoaderBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).build();
        final CancelCommand standardCommand = new CancelCommand(loader);

        // same values -> returns true
        CancelCommand commandWithSameValues = new CancelCommand(loader);
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
        assertFalse(standardCommand.equals(new CancelCommand(difLoader)));
    }
}

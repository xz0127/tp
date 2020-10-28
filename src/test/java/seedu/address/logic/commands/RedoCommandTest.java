package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.deleteFirstAppointment;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class RedoCommandTest {
    private final Model model = new ModelManager(getTypicalPatientBook(), getTypicalAppointmentBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(model.getPatientBook(),
            model.getAppointmentBook(), new UserPrefs());

    @BeforeEach
    public void setUp() {
        // set up of both models' undo/redo history
        deleteFirstAppointment(model);
        deleteFirstAppointment(model);
        model.undoAppointmentBook();
        model.undoPatientBook();
        model.undoAppointmentBook();
        model.undoPatientBook();

        deleteFirstAppointment(expectedModel);
        deleteFirstAppointment(expectedModel);
        expectedModel.undoPatientBook();
        expectedModel.undoAppointmentBook();
        expectedModel.undoPatientBook();
        expectedModel.undoAppointmentBook();
    }

    @Test
    public void execute() {
        // multiple redoable states in model
        expectedModel.redoAppointmentBook();
        expectedModel.redoPatientBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // single redoable state in model
        expectedModel.redoAppointmentBook();
        expectedModel.redoPatientBook();
        assertCommandSuccess(new RedoCommand(), model, RedoCommand.MESSAGE_SUCCESS, expectedModel);

        // no redoable state in model
        assertCommandFailure(new RedoCommand(), model, RedoCommand.MESSAGE_FAILURE);
    }
}

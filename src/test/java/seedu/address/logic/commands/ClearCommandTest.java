package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientBook;
import seedu.address.model.UserPrefs;

public class ClearCommandTest {

    @Test
    public void execute_emptyBooks_success() {
        Model model = new ModelManager();
        Model expectedModel = new ModelManager();
        expectedModel.commitPatientBook();
        expectedModel.commitAppointmentBook();
        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_nonEmptyBooks_success() {
        Model model = new ModelManager(getTypicalPatientBook(), getTypicalAppointmentBook(), new UserPrefs());

        Model expectedModel = new ModelManager(getTypicalPatientBook(), getTypicalAppointmentBook(), new UserPrefs());
        expectedModel.setAppointmentBook(new AppointmentBook());
        expectedModel.setPatientBook(new PatientBook());
        expectedModel.commitPatientBook();
        expectedModel.commitAppointmentBook();

        assertCommandSuccess(new ClearCommand(), model, ClearCommand.MESSAGE_SUCCESS, expectedModel);
    }

}

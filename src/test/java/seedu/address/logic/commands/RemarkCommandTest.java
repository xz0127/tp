package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPatientAtIndex;
import static seedu.address.testutil.RemarkUtil.STRING_REMARK_4;
import static seedu.address.testutil.RemarkUtil.STRING_REMARK_5;
import static seedu.address.testutil.RemarkUtil.WORDS_ONE_NINETY_NINE;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PATIENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PATIENT;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.PatientBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Remark;
import seedu.address.testutil.PatientBuilder;

class RemarkCommandTest {

    private static final String REMARK_STUB = "Some remark";

    private Model model = new ModelManager(getTypicalPatientBook(), getTypicalAppointmentBook(), new UserPrefs());

    @Test
    public void execute_addRemarkUnfilteredList_success() {
        Patient firstPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(firstPatient).withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PATIENT,
                new Remark(editedPatient.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientBook(model.getPatientBook()),
                new AppointmentBook(model.getAppointmentBook()), new UserPrefs());
        expectedModel.setPatient(firstPatient, editedPatient);
        expectedModel.commitPatientBook();
        expectedModel.commitAppointmentBook();

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_deleteRemarkUnfilteredList_success() {
        Patient firstPatient = model.getFilteredPatientList().get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(firstPatient).withRemark("").build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PATIENT,
                new Remark(editedPatient.getRemark().toString()));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientBook(model.getPatientBook()),
                new AppointmentBook(model.getAppointmentBook()), new UserPrefs());
        expectedModel.setPatient(firstPatient, editedPatient);
        expectedModel.commitPatientBook();
        expectedModel.commitAppointmentBook();

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);

        Patient firstPatient = model.getFilteredPatientList()
                .get(INDEX_FIRST_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(firstPatient)
                .withRemark(REMARK_STUB).build();

        RemarkCommand remarkCommand = new RemarkCommand(INDEX_FIRST_PATIENT,
                new Remark(editedPatient.getRemark().value));

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPatient);

        Model expectedModel = new ModelManager(new PatientBook(model.getPatientBook()),
                new AppointmentBook(model.getAppointmentBook()), new UserPrefs());
        showPatientAtIndex(expectedModel, INDEX_FIRST_PATIENT);
        expectedModel.setPatient(firstPatient, editedPatient);
        expectedModel.commitPatientBook();
        expectedModel.commitAppointmentBook();
        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPatientIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPatientList().size() + 1);
        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of patient book
     */
    @Test
    public void execute_invalidPatientIndexFilteredList_failure() {
        showPatientAtIndex(model, INDEX_FIRST_PATIENT);
        Index outOfBoundIndex = INDEX_SECOND_PATIENT;
        // ensures that outOfBoundIndex is still in bounds of patient book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getPatientBook().getPatientList().size());

        RemarkCommand remarkCommand = new RemarkCommand(outOfBoundIndex, new Remark(VALID_REMARK_BOB));
        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals_sameObject_success() {
        Patient samplePatient = model.getFilteredPatientList()
                .get(INDEX_SECOND_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(samplePatient).withRemark(WORDS_ONE_NINETY_NINE).build();
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_SECOND_PATIENT,
                new Remark(editedPatient.getRemark().value));
        assertTrue(remarkCommand.equals(remarkCommand));
    }

    @Test
    public void equals_sameContent_success() {
        Patient samplePatient = model.getFilteredPatientList()
                .get(INDEX_SECOND_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(samplePatient).withRemark(WORDS_ONE_NINETY_NINE).build();
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_SECOND_PATIENT,
                new Remark(editedPatient.getRemark().value));
        RemarkCommand anotherRemarkCommand = new RemarkCommand(INDEX_SECOND_PATIENT, new Remark(WORDS_ONE_NINETY_NINE));
        assertTrue(remarkCommand.equals(anotherRemarkCommand));

        RemarkCommand anotherRemarkCommand2 = new RemarkCommand(INDEX_SECOND_PATIENT,
                new Remark(editedPatient.getRemark().value));
        assertTrue(remarkCommand.equals(anotherRemarkCommand2));
    }

    @Test
    public void equals_differentContent_fail() {
        Patient samplePatient = model.getFilteredPatientList()
                .get(INDEX_SECOND_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(samplePatient)
                .withRemark(WORDS_ONE_NINETY_NINE).build();
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_SECOND_PATIENT,
                new Remark(editedPatient.getRemark().value));
        RemarkCommand anotherRemarkCommand = new RemarkCommand(INDEX_SECOND_PATIENT, new Remark(STRING_REMARK_4));
        assertFalse(remarkCommand.equals(anotherRemarkCommand));

        RemarkCommand anotherRemarkCommand2 = new RemarkCommand(INDEX_SECOND_PATIENT, new Remark(STRING_REMARK_5));
        assertFalse(remarkCommand.equals(anotherRemarkCommand2));

        RemarkCommand anotherRemarkCommand3 = new RemarkCommand(INDEX_SECOND_PATIENT,
                new Remark(samplePatient.getRemark().value));
        assertFalse(remarkCommand.equals(anotherRemarkCommand3));

        // different index
        anotherRemarkCommand3 = new RemarkCommand(INDEX_FIRST_PATIENT,
                new Remark(editedPatient.getRemark().value));
        assertFalse(remarkCommand.equals(anotherRemarkCommand3));
    }

    @Test
    public void equals_differentType_fail() {
        final DoneCommand standardCommand = new DoneCommand(INDEX_FIRST_PATIENT);

        Patient samplePatient = model.getFilteredPatientList()
                .get(INDEX_SECOND_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(samplePatient).withRemark(WORDS_ONE_NINETY_NINE).build();
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_SECOND_PATIENT,
                new Remark(editedPatient.getRemark().value));

        assertFalse(remarkCommand.equals(standardCommand));
    }

    @Test
    public void equals_null_fail() {
        Patient samplePatient = model.getFilteredPatientList()
                .get(INDEX_SECOND_PATIENT.getZeroBased());
        Patient editedPatient = new PatientBuilder(samplePatient).withRemark(WORDS_ONE_NINETY_NINE).build();
        RemarkCommand remarkCommand = new RemarkCommand(INDEX_SECOND_PATIENT,
                new Remark(editedPatient.getRemark().value));

        assertFalse(remarkCommand.equals(null));
    }
}

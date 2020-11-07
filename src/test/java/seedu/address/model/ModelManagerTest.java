package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT_2;
import static seedu.address.testutil.TypicalAppointments.BENSON_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.CARL_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.exceptions.NoRedoableStateException;
import seedu.address.model.exceptions.NoUndoableStateException;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.testutil.AppointmentBookBuilder;
import seedu.address.testutil.PatientBookBuilder;

public class ModelManagerTest {

    private ModelManager modelManager = new ModelManager();

    @Test
    public void constructor() {
        assertEquals(new UserPrefs(), modelManager.getUserPrefs());
        assertEquals(new GuiSettings(), modelManager.getGuiSettings());
        assertEquals(new PatientBook(), new PatientBook(modelManager.getPatientBook()));
        assertEquals(new AppointmentBook(), new AppointmentBook(modelManager.getAppointmentBook()));
    }

    @Test
    public void setUserPrefs_nullUserPrefs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setUserPrefs(null));
    }

    @Test
    public void setUserPrefs_validUserPrefs_copiesUserPrefs() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setPatientBookFilePath(Paths.get("address/book/file/path"));
        userPrefs.setAppointmentBookFilePath(Paths.get("appointment/book/file/path"));
        userPrefs.setGuiSettings(new GuiSettings(1, 2, 3, 4, 0.5));
        modelManager.setUserPrefs(userPrefs);
        assertEquals(userPrefs, modelManager.getUserPrefs());

        // Modifying userPrefs should not modify modelManager's userPrefs
        UserPrefs oldUserPrefs = new UserPrefs(userPrefs);
        userPrefs.setPatientBookFilePath(Paths.get("new/address/book/file/path"));
        userPrefs.setAppointmentBookFilePath(Paths.get("new/appointment/book/file/path"));
        assertEquals(oldUserPrefs, modelManager.getUserPrefs());
    }

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setGuiSettings(null));
    }

    @Test
    public void setGuiSettings_validGuiSettings_setsGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(1, 2, 3, 4, 0.5);
        modelManager.setGuiSettings(guiSettings);
        assertEquals(guiSettings, modelManager.getGuiSettings());
    }

    @Test
    public void setPatientBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setPatientBookFilePath(null));
    }

    @Test
    public void setPatientBookFilePath_validPath_setsPatientBookFilePath() {
        Path path = Paths.get("address/book/file/path");
        modelManager.setPatientBookFilePath(path);
        assertEquals(path, modelManager.getPatientBookFilePath());
    }

    @Test
    public void setAppointmentBookFilePath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setAppointmentBookFilePath(null));
    }

    @Test
    public void setAppointmentBookFilePath_validPath_setsAppointmentBookFilePath() {
        Path path = Paths.get("appointment/book/file/path");
        modelManager.setAppointmentBookFilePath(path);
        assertEquals(path, modelManager.getAppointmentBookFilePath());
    }

    @Test
    public void setArchiveDirPath_nullPath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.setArchiveDirPath(null));
    }

    @Test
    public void setArchiveDirPath_validPath_setsArchiveDirPath() {
        Path path = Paths.get("archive/directory/path");
        modelManager.setArchiveDirPath(path);
        assertEquals(path, modelManager.getArchiveDirPath());
    }

    @Test
    public void hasPatient_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasPatient(null));
    }

    @Test
    public void hasPatient_patientNotInPatientBook_returnsFalse() {
        assertFalse(modelManager.hasPatient(ALICE));
    }

    @Test
    public void hasPatient_patientInPatientBook_returnsTrue() {
        modelManager.addPatient(ALICE);
        assertTrue(modelManager.hasPatient(ALICE));
    }

    @Test
    public void hasAppointment_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> modelManager.hasAppointment(null));
    }

    @Test
    public void hasAppointment_appointmentNotInAppointmentBook_returnsFalse() {
        assertFalse(modelManager.hasAppointment(ALICE_APPOINTMENT));
    }

    @Test
    public void hasAppointment_appointmentInAppointmentBook_returnsTrue() {
        modelManager.addAppointment(ALICE_APPOINTMENT);
        assertTrue(modelManager.hasAppointment(ALICE_APPOINTMENT));
    }

    @Test
    public void getFilteredPatientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPatientList().remove(0));
    }

    @Test
    public void getFilteredAppointmentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredAppointmentList().remove(0));
    }

    @Test
     public void undoAndRedoPatientBook_undoRedoSuccessful() {
        ModelManager modelManager = new ModelManager();
        assertFalse(modelManager.canUndoPatientBook());
        assertFalse(modelManager.canRedoPatientBook());
        modelManager.commitPatientBook();

        modelManager.setPatientBook(getTypicalPatientBook());
        assertTrue(modelManager.canUndoPatientBook());
        assertFalse(modelManager.canRedoPatientBook());
        modelManager.undoPatientBook();

        assertFalse(modelManager.canUndoPatientBook());
        assertTrue(modelManager.canRedoPatientBook());
        modelManager.redoPatientBook();
    }

    @Test
    public void undoAndRedoPatientBook_initialState_throwsNoUndoableStateException() {
        ModelManager modelManager = new ModelManager();
        assertFalse(modelManager.canUndoPatientBook());
        assertThrows(NoUndoableStateException.class, () -> modelManager.undoPatientBook());
        assertFalse(modelManager.canRedoPatientBook());
        assertThrows(NoRedoableStateException.class, () -> modelManager.redoPatientBook());
    }

    @Test
    public void undoAndRedoAppointmentBook_canUndo_undoSuccessful() {
        ModelManager modelManager = new ModelManager();
        assertFalse(modelManager.canUndoAppointmentBook());
        assertFalse(modelManager.canRedoAppointmentBook());
        modelManager.commitAppointmentBook();

        modelManager.setAppointmentBook(getTypicalAppointmentBook());
        assertTrue(modelManager.canUndoAppointmentBook());
        assertFalse(modelManager.canRedoAppointmentBook());
        modelManager.undoAppointmentBook();

        assertFalse(modelManager.canUndoAppointmentBook());
        assertTrue(modelManager.canRedoAppointmentBook());
        modelManager.redoAppointmentBook();
    }

    @Test
    public void undoAndRedoAppointmentBook_initialState_throwsNoUndoableStateException() {
        ModelManager modelManager = new ModelManager();
        assertFalse(modelManager.canUndoAppointmentBook());
        assertThrows(NoUndoableStateException.class, () -> modelManager.undoAppointmentBook());
        assertFalse(modelManager.canRedoAppointmentBook());
        assertThrows(NoRedoableStateException.class, () -> modelManager.redoAppointmentBook());
    }

    @Test
    public void isValidModel_inconsistentPatientAndAppointmentBook_returnsFalse() {
        PatientBook patientBook = new PatientBookBuilder().withPatient(ALICE).withPatient(BENSON).build();
        AppointmentBook appointmentBook = new AppointmentBookBuilder()
                .withAppointment(ALICE_APPOINTMENT).withAppointment(CARL_APPOINTMENT).build();

        assertFalse(ModelManager.isValidModel(patientBook, appointmentBook));
    }

    @Test
    public void isValidModel_consistentPatientAndAppointmentBook_returnsFalse() {
        PatientBook patientBook = new PatientBookBuilder().withPatient(ALICE).withPatient(BENSON).build();
        AppointmentBook appointmentBook = new AppointmentBookBuilder().withAppointment(ALICE_APPOINTMENT)
                .withAppointment(ALICE_APPOINTMENT_2).withAppointment(BENSON_APPOINTMENT).build();

        assertTrue(ModelManager.isValidModel(patientBook, appointmentBook));

        patientBook = getTypicalPatientBook();
        appointmentBook = getTypicalAppointmentBook();

        assertTrue(ModelManager.isValidModel(patientBook, appointmentBook));
    }

    @Test
    public void getSyncedAppointmentBook_inconsistentModel_successful() {
        PatientBook patientBook = new PatientBookBuilder().withPatient(ALICE).build();
        AppointmentBook appointmentBook = getTypicalAppointmentBook();

        AppointmentBook expectedBook = new AppointmentBook();
        expectedBook.setAppointments(appointmentBook.getAppointmentList()
                .filtered(appointment -> appointment.hasPatient(ALICE)));

        assertFalse(ModelManager.isValidModel(patientBook, appointmentBook));
        ReadOnlyAppointmentBook actualBook = ModelManager.getSyncedAppointmentBook(patientBook, appointmentBook);

        assertTrue(ModelManager.isValidModel(patientBook, actualBook));
        assertEquals(actualBook, expectedBook);
    }

    @Test
    public void equals() {
        PatientBook patientBook = new PatientBookBuilder().withPatient(ALICE).withPatient(BENSON).build();
        AppointmentBook appointmentBook = new AppointmentBookBuilder()
                .withAppointment(ALICE_APPOINTMENT).withAppointment(BENSON_APPOINTMENT).build();
        PatientBook differentPatientBook = new PatientBook();
        AppointmentBook differentAppointmentBook = new AppointmentBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(patientBook, appointmentBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(patientBook, appointmentBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different patientBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentPatientBook, appointmentBook, userPrefs)));

        // different appointmentBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(patientBook, differentAppointmentBook, userPrefs)));

        // different filteredPatientList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPatientList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(patientBook, appointmentBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        // different filteredAppointmentList -> returns false
        modelManager.updateFilteredAppointmentList(appointment -> appointment.hasPatient(ALICE));
        assertFalse(modelManager.equals(new ModelManager(patientBook, appointmentBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPatientBookFilePath(Paths.get("differentFilePath"));
        differentUserPrefs.setAppointmentBookFilePath(Paths.get("differentFilePath2"));
        assertFalse(modelManager.equals(new ModelManager(patientBook, appointmentBook, differentUserPrefs)));
    }
}

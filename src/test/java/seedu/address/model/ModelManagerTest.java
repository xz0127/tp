package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.BOB_APPOINTMENT;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.testutil.PatientBookBuilder;
import seedu.address.testutil.AppointmentBookBuilder;

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
    public void getFilteredPatientList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> modelManager.getFilteredPatientList().remove(0));
    }

    @Test
    public void equals() {
        PatientBook patientBook = new PatientBookBuilder().withPatient(ALICE).withPatient(BENSON).build();
        AppointmentBook appointmentBook = new AppointmentBookBuilder()
                .withAppointment(ALICE_APPOINTMENT).withAppointment(BOB_APPOINTMENT).build();
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

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredPatientList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(patientBook, appointmentBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);

        // different userPrefs -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPatientBookFilePath(Paths.get("differentFilePath"));
        differentUserPrefs.setAppointmentBookFilePath(Paths.get("differentFilePath2"));
        assertFalse(modelManager.equals(new ModelManager(patientBook, appointmentBook, differentUserPrefs)));
    }
}

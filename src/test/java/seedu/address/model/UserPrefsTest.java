package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.GuiSettings;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setPatientBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setPatientBookFilePath(null));
    }

    @Test
    public void setAppointmentBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAppointmentBookFilePath(null));
    }

    @Test
    public void equals() {
        UserPrefs defaultUserPrefs = new UserPrefs();
        // same object -> returns true
        assertTrue(defaultUserPrefs.equals(defaultUserPrefs));

        // same values -> returns true
        UserPrefs userPrefsCopy = new UserPrefs(defaultUserPrefs.getGuiSettings(),
                defaultUserPrefs.getPatientBookFilePath(),
                defaultUserPrefs.getAppointmentBookFilePath(),
                defaultUserPrefs.getArchiveDirectoryPath());
        assertTrue(defaultUserPrefs.equals(userPrefsCopy));

        // different types -> returns false
        assertFalse(defaultUserPrefs.equals(1));

        // null -> returns false
        assertFalse(defaultUserPrefs.equals(null));

        // different GUI settings -> returns false
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setGuiSettings(new GuiSettings(1.0, 1.0, 1, 1, 1.0));
        assertFalse(defaultUserPrefs.equals(differentUserPrefs));

        // different patient book file path -> returns false
        differentUserPrefs = new UserPrefs();
        differentUserPrefs.setPatientBookFilePath(Path.of("different", "path"));
        assertFalse(defaultUserPrefs.equals(differentUserPrefs));

        // different appointment book file path -> returns false
        differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAppointmentBookFilePath(Path.of("different", "path"));
        assertFalse(defaultUserPrefs.equals(differentUserPrefs));

        // different archive book file path -> returns false
        differentUserPrefs = new UserPrefs();
        differentUserPrefs.setArchiveDirectoryPath(Path.of("different", "path"));
        assertFalse(defaultUserPrefs.equals(differentUserPrefs));

    }
}

package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.GuiSettings;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {

    private GuiSettings guiSettings = new GuiSettings();
    private Path patientBookFilePath = Paths.get("data" , "patientbook.json");
    private Path appointmentBookFilePath = Paths.get("data" , "appointmentbook.json");
    private Path archiveDirectoryPath = Paths.get("data", "archives");

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with specified values.
     * For any null input value, the default values will be used instead.
     */
    @JsonCreator
    public UserPrefs(@JsonProperty("guiSettings") GuiSettings guiSettings,
                     @JsonProperty("patientBookFilePath") Path patientBookFilePath,
                     @JsonProperty("appointmentBookFilePath") Path appointmentBookFilePath,
                     @JsonProperty("archiveDirectoryPath") Path archiveDirectoryPath) {
        if (guiSettings != null) {
            this.guiSettings = guiSettings;
        }

        if (patientBookFilePath != null) {
            this.patientBookFilePath = patientBookFilePath;
        }

        if (appointmentBookFilePath != null) {
            this.appointmentBookFilePath = appointmentBookFilePath;
        }

        if (archiveDirectoryPath != null) {
            this.archiveDirectoryPath = archiveDirectoryPath;
        }
    }

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setPatientBookFilePath(newUserPrefs.getPatientBookFilePath());
        setAppointmentBookFilePath(newUserPrefs.getAppointmentBookFilePath());
    }

    @Override
    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public Path getPatientBookFilePath() {
        return patientBookFilePath;
    }

    public void setPatientBookFilePath(Path patientBookFilePath) {
        requireNonNull(patientBookFilePath);
        this.patientBookFilePath = patientBookFilePath;
    }

    public Path getAppointmentBookFilePath() {
        return appointmentBookFilePath;
    }

    public void setAppointmentBookFilePath(Path appointmentBookFilePath) {
        requireNonNull(appointmentBookFilePath);
        this.appointmentBookFilePath = appointmentBookFilePath;
    }

    public Path getArchiveDirectoryPath() {
        return archiveDirectoryPath;
    }

    public void setArchiveDirectoryPath(Path archiveDirectoryPath) {
        requireNonNull(archiveDirectoryPath);
        this.archiveDirectoryPath = archiveDirectoryPath;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && patientBookFilePath.equals(o.patientBookFilePath)
                && appointmentBookFilePath.equals(o.appointmentBookFilePath)
                && archiveDirectoryPath.equals(o.archiveDirectoryPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, patientBookFilePath, appointmentBookFilePath, archiveDirectoryPath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal Patient data file location : " + patientBookFilePath);
        sb.append("\nLocal Appointment data file location : " + appointmentBookFilePath);
        sb.append("\nLocal Archive data file location : " + archiveDirectoryPath);
        return sb.toString();
    }

}

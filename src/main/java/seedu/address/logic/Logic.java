package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Saves the model into storage.
     *
     * @throws IOException If an error occurs during saving
     */
    void saveData() throws IOException;

    /**
     * Returns the PatientBook.
     *
     * @see seedu.address.model.Model#getPatientBook()
     */
    ReadOnlyPatientBook getPatientBook();

    /** Returns an unmodifiable view of the filtered list of patients */
    ObservableList<Patient> getFilteredPatientList();

    /**
     * Returns the user prefs' patient book file path.
     */
    Path getPatientBookFilePath();

    /**
     * Returns the AppointmentBook.
     *
     * @see seedu.address.model.Model#getAppointmentBook()
     */
    ReadOnlyAppointmentBook getAppointmentBook();

    /** Returns an unmodifiable view of the filtered list of appointments */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Returns the user prefs' appointment book file path.
     */
    Path getAppointmentBookFilePath();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Gets the storage load status message.
     */
    String getStorageStatus();

    /** Returns an unmodifiable view of the command history list */
    ObservableList<String> getCommandHistory();
}

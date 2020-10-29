package seedu.address.model;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Patient> PREDICATE_SHOW_ALL_PATIENTS = unused -> true;
    Predicate<Appointment> PREDICATE_SHOW_ALL_APPOINTMENTS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' patient book file path.
     */
    Path getPatientBookFilePath();

    /**
     * Sets the user prefs' patient book file path.
     */
    void setPatientBookFilePath(Path patientBookFilePath);

    /**
     * Returns the user prefs' appointment book file path.
     */
    Path getAppointmentBookFilePath();

    /**
     * Sets the user prefs' appointment book file path.
     */
    void setAppointmentBookFilePath(Path appointmentBookFilePath);

    /**
     * Returns the user prefs' appointment directory path.
     */
    Path getArchiveDirPath();

    /**
     * Sets the user prefs' archive directory path.
     */
    void setArchiveDirPath(Path archiveDirPath);

    /**
     * Replaces patient book data with the data in {@code patientBook}.
     */
    void setPatientBook(ReadOnlyPatientBook patientBook);

    /**
     * Returns the PatientBook
     */
    ReadOnlyPatientBook getPatientBook();

    /**
     * Returns true if a patient with the same identity as {@code patient} exists in the patient book.
     */
    boolean hasPatient(Patient patient);

    /**
     * Returns true if the time slot of an appointment hasOverlaps {@code appointment} in the appointment book.
     */
    boolean hasOverlappingAppointment(Appointment appointment);

    /**
     * Returns true if the time slot of an appointment is the same as another {@code appointment} in the appointment
     * book.
     */
    boolean hasAppointment(Appointment appointment);

    /**
     * Deletes the given patient.
     * The patient must exist in the patient book.
     */
    void deletePatient(Patient target);

    /**
     * Deletes the given appointment.
     * The appointment must exist in the appointment book.
     */
    void deleteAppointment(Appointment target);

    /**
     * Adds the given patient.
     * {@code patient} must not already exist in the patient book.
     */
    void addPatient(Patient patient);

    /**
     * Adds the given appointment.
     * {@code appointment} must not already exist in the appointment book.
     */
    void addAppointment(Appointment appointment);

    /**
     * Replaces the given patient {@code target} with {@code editedPatient}.
     * {@code target} must exist in the patient book.
     * The patient identity of {@code editedPatient} must not be the same as another existing patient
     * in the patient book.
     */
    void setPatient(Patient target, Patient editedPatient);

    /**
     * Updates the relevant appointments upon the editing of a given {@code target} with {@code editedPatient}.
     */
    void updateAppointmentsWithPatient(Patient target, Patient editedPatient);

    /**
     * Deletes the relevant appointments upon the deletion of a given {@code target}.
     */
    void deleteAppointmentsWithPatient(Patient target);

    /**
     * Replaces the given {@code target} with {@code editedAppointment}.
     * {@code target} must exist in the appointment book.
     * The appointment details of {@code editedAppointment} must not be the same as another existing appointment
     * in the appointment book.
     */
    void setAppointment(Appointment target, Appointment editedAppointment);

    /**
     * Finds available time slots from the {@code appointmentList}.
     * Appointments in {@code appointmentList} must take place on the same date.
     * Returns the available time slots in string format.
     */
    String findAvailableTimeSlots(List<Appointment> appointmentList, boolean isToday);

    /**
     * Returns an unmodifiable view of the filtered patient list.
     */
    ObservableList<Patient> getFilteredPatientList();

    /**
     * Updates the filter of the filtered patient list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPatientList(Predicate<Patient> predicate);

    /**
     * Updates the filter of the filtered appointment list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredAppointmentList(Predicate<Appointment> predicate);

    /**
     * Replaces appointment book data with the data in {@code appointmentBook}.
     */
    void setAppointmentBook(ReadOnlyAppointmentBook appointmentBook);

    /**
     * Returns the AppointmentBook.
     */
    ReadOnlyAppointmentBook getAppointmentBook();

    /**
     * Returns an unmodifiable view of the filtered appointment list.
     */
    ObservableList<Appointment> getFilteredAppointmentList();

    /**
     * Returns true if the model has previous appointment book states to restore.
     */
    boolean canUndoAppointmentBook();

    /**
     * Returns true if the model has previous patient book states to restore.
     */
    boolean canUndoPatientBook();

    /**
     * Returns true if the model has undone appointment book states to restore.
     */
    boolean canRedoAppointmentBook();

    /**
     * Returns true if the model has undone patient book states to restore.
     */
    boolean canRedoPatientBook();

    /**
     * Restores the model's appointment book to its previous state.
     */
    void undoAppointmentBook();

    /**
     * Restores the model's patient book to its previous state.
     */
    void undoPatientBook();

    /**
     * Restores the model's appointment book to its previously undone state.
     */
    void redoAppointmentBook();

    /**
     * Restores the model's patient book to its previously undone state.
     */
    void redoPatientBook();

    /**
     * Saves the current appointment book state for undo/redo.
     */
    void commitAppointmentBook();

    /**
     * Saves the current patient book state for undo/redo.
     */
    void commitPatientBook();
}

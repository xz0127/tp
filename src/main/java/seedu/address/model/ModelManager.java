package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;

/**
 * Represents the in-memory model of the patient book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final VersionedPatientBook versionedPatientBook;
    private final VersionedAppointmentBook versionedAppointmentBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Patient> filteredPatients;
    private final FilteredList<Appointment> filteredAppointments;

    /**
     * Initializes a ModelManager with the given patientBook and userPrefs.
     */
    public ModelManager(ReadOnlyPatientBook patientBook, ReadOnlyAppointmentBook appointmentBook,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(patientBook, userPrefs);

        logger.fine("Initializing with patient book: " + patientBook + " and appointment book" + appointmentBook
                + " and user prefs " + userPrefs);

        this.versionedPatientBook = new VersionedPatientBook(patientBook);
        this.versionedAppointmentBook = new VersionedAppointmentBook(appointmentBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPatients = new FilteredList<>(this.versionedPatientBook.getPatientList());
        filteredAppointments = new FilteredList<>(this.versionedAppointmentBook.getAppointmentList());
    }

    public ModelManager() {
        this(new PatientBook(), new AppointmentBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getPatientBookFilePath() {
        return userPrefs.getPatientBookFilePath();
    }

    @Override
    public void setPatientBookFilePath(Path patientBookFilePath) {
        requireNonNull(patientBookFilePath);
        userPrefs.setPatientBookFilePath(patientBookFilePath);
    }

    @Override
    public Path getAppointmentBookFilePath() {
        return userPrefs.getAppointmentBookFilePath();
    }

    @Override
    public void setAppointmentBookFilePath(Path appointmentBookFilePath) {
        requireNonNull(appointmentBookFilePath);
        userPrefs.setAppointmentBookFilePath(appointmentBookFilePath);
    }

    @Override
    public Path getArchiveDirPath() {
        return userPrefs.getArchiveDirectoryPath();
    }

    @Override
    public void setArchiveDirPath(Path archiveDirPath) {
        requireNonNull(archiveDirPath);
        userPrefs.setArchiveDirectoryPath(archiveDirPath);
    }
    //=========== PatientBook ================================================================================

    @Override
    public void setPatientBook(ReadOnlyPatientBook patientBook) {
        this.versionedPatientBook.resetData(patientBook);
    }

    @Override
    public ReadOnlyPatientBook getPatientBook() {
        return versionedPatientBook;
    }

    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return versionedPatientBook.hasPatient(patient);
    }

    @Override
    public void deletePatient(Patient target) {
        versionedPatientBook.removePatient(target);
    }

    @Override
    public void addPatient(Patient patient) {
        versionedPatientBook.addPatient(patient);
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);

        versionedPatientBook.setPatient(target, editedPatient);
    }

    //=========== AppointmentBook ================================================================================
    @Override
    public boolean hasOverlappingAppointment(Appointment appointment) {
        return versionedAppointmentBook.hasOverlapsWith(appointment);
    }

    @Override
    public boolean hasAppointment(Appointment appointment) {
        return versionedAppointmentBook.hasAppointment(appointment);
    }

    @Override
    public void addAppointment(Appointment appointment) {
        versionedAppointmentBook.addAppointment(appointment);
    }

    @Override
    public void deleteAppointment(Appointment target) {
        versionedAppointmentBook.removeAppointment(target);
    }

    @Override
    public void setAppointmentBook(ReadOnlyAppointmentBook appointmentBook) {
        this.versionedAppointmentBook.resetData(appointmentBook);
    }

    @Override
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);

        versionedAppointmentBook.setAppointment(target, editedAppointment);
    }

    @Override
    public String findAvailableTimeSlots(List<Appointment> appointmentList, boolean isToday) {
        requireNonNull(appointmentList);

        return versionedAppointmentBook.findAvailableTimeSlots(appointmentList, isToday);
    }

    @Override
    public ReadOnlyAppointmentBook getAppointmentBook() {
        return versionedAppointmentBook;
    }

    //=========== Patient-related Appointment Operations =============================================================
    @Override
    public void updateAppointmentsWithPatient(Patient target, Patient editedPatient) {
        requireNonNull(editedPatient);

        versionedAppointmentBook.updateAppointmentsWithPatients(target, editedPatient);
    }

    @Override
    public void deleteAppointmentsWithPatient(Patient target) {
        versionedAppointmentBook.deleteAppointmentsWithPatients(target);
    }

    //=========== Filtered Appointment List Accessors =================================================================

    /**
     * Returns an unmodifiable view of the list of {@code Appointment} backed by the internal list of
     * {@code versionedAppointmentBook}
     */
    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return filteredAppointments;
    }

    @Override
    public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
        requireNonNull(predicate);
        filteredAppointments.setPredicate(predicate);
    }

    //=========== Filtered Patient List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Patient} backed by the internal list of
     * {@code versionedPatientBook}
     */
    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return filteredPatients;
    }

    @Override
    public void updateFilteredPatientList(Predicate<Patient> predicate) {
        requireNonNull(predicate);
        filteredPatients.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAppointmentBook() {
        return versionedAppointmentBook.canUndo();
    }

    @Override
    public boolean canUndoPatientBook() {
        return versionedPatientBook.canUndo();
    }

    @Override
    public boolean canRedoAppointmentBook() {
        return versionedAppointmentBook.canRedo();
    }

    @Override
    public boolean canRedoPatientBook() {
        return versionedPatientBook.canRedo();
    }

    @Override
    public void undoAppointmentBook() {
        versionedAppointmentBook.undo();
    }

    @Override
    public void undoPatientBook() {
        versionedPatientBook.undo();
    }

    @Override
    public void redoAppointmentBook() {
        versionedAppointmentBook.redo();
    }

    @Override
    public void redoPatientBook() {
        versionedPatientBook.redo();
    }

    @Override
    public void commitAppointmentBook() {
        versionedAppointmentBook.commit();
    }

    @Override
    public void commitPatientBook() {
        versionedPatientBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedPatientBook.equals(other.versionedPatientBook)
                && versionedAppointmentBook.equals(other.versionedAppointmentBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPatients.equals(other.filteredPatients)
                && filteredAppointments.equals(other.filteredAppointments);
    }

    //=========== Model Validation =============================================================

    /**
     * Checks if the {@code readOnlyPatientBook} is consistent with the {@code readOnlyAppointmentBook} data.
     *
     * @param readOnlyPatientBook the patients data.
     * @param readOnlyAppointmentBook the appointments data.
     * @return true if the two books are valid, false otherwise.
     */
    public static boolean isValidModel(ReadOnlyPatientBook readOnlyPatientBook,
                                       ReadOnlyAppointmentBook readOnlyAppointmentBook) {
        requireAllNonNull(readOnlyPatientBook, readOnlyAppointmentBook);

        List<Appointment> appointmentList = readOnlyAppointmentBook.getAppointmentList();
        PatientBook patientBook = new PatientBook(readOnlyPatientBook);

        for (Appointment appointment : appointmentList) {
            if (!patientBook.hasPatient(appointment.getPatient())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Makes an appointment book that only consists of appointment data that is consistent
     * with {@code readOnlyPatientBook}.
     *
     * @param readOnlyPatientBook     the patients data.
     * @param readOnlyAppointmentBook the appointments data.
     * @return the Appointment Book that consists of the appointments that are consistent with the Patient Book.
     */
    public static ReadOnlyAppointmentBook getSyncedAppointmentBook(ReadOnlyPatientBook readOnlyPatientBook,
                                                   ReadOnlyAppointmentBook readOnlyAppointmentBook) {
        requireAllNonNull(readOnlyPatientBook, readOnlyAppointmentBook);
        assert !isValidModel(readOnlyPatientBook, readOnlyAppointmentBook);

        List<Appointment> appointmentList = readOnlyAppointmentBook.getAppointmentList();
        AppointmentBook appointmentBook = new AppointmentBook();
        PatientBook patientBook = new PatientBook(readOnlyPatientBook);

        for (Appointment appointment : appointmentList) {
            if (patientBook.hasPatient(appointment.getPatient())) {
                appointmentBook.addAppointment(appointment);
            }
        }
        return appointmentBook;
    }
}

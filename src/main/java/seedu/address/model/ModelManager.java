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

    private final PatientBook patientBook;
    private final AppointmentBook appointmentBook;
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

        this.patientBook = new PatientBook(patientBook);
        this.appointmentBook = new AppointmentBook(appointmentBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPatients = new FilteredList<>(this.patientBook.getPatientList());
        filteredAppointments = new FilteredList<>(this.appointmentBook.getAppointmentList());
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

    //=========== PatientBook ================================================================================

    @Override
    public void setPatientBook(ReadOnlyPatientBook patientBook) {
        this.patientBook.resetData(patientBook);
    }

    @Override
    public ReadOnlyPatientBook getPatientBook() {
        return patientBook;
    }

    @Override
    public boolean hasPatient(Patient patient) {
        requireNonNull(patient);
        return patientBook.hasPatient(patient);
    }

    @Override
    public void deletePatient(Patient target) {
        patientBook.removePatient(target);
    }

    @Override
    public void addPatient(Patient patient) {
        patientBook.addPatient(patient);
        updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
    }

    @Override
    public void setPatient(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);

        patientBook.setPatient(target, editedPatient);
    }

    //=========== AppointmentBook ================================================================================
    @Override
    public boolean hasOverlappingAppointment(Appointment appointment) {
        return appointmentBook.hasOverlapsWith(appointment);
    }

    @Override
    public boolean hasAppointment(Appointment appointment) {
        return appointmentBook.hasAppointment(appointment);
    }

    @Override
    public void addAppointment(Appointment appointment) {
        appointmentBook.addAppointment(appointment);
        updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
    }

    @Override
    public void deleteAppointment(Appointment target) {
        appointmentBook.removeAppointment(target);
    }

    @Override
    public void setAppointmentBook(ReadOnlyAppointmentBook appointmentBook) {
        this.appointmentBook.resetData(appointmentBook);
    }

    @Override
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);

        appointmentBook.setAppointment(target, editedAppointment);
    }

    @Override
    public String findAvailableTimeSlots(List<Appointment> appointmentList) {
        requireNonNull(appointmentList);

        return appointmentBook.findAvailableTimeSlots(appointmentList);
    }

    @Override
    public ReadOnlyAppointmentBook getAppointmentBook() {
        return appointmentBook;
    }

    //=========== Patient-related Appointment Operations =============================================================
    @Override
    public void updateAppointmentsWithPatient(Patient target, Patient editedPatient) {
        requireNonNull(editedPatient);

        appointmentBook.updateAppointmentsWithPatients(target, editedPatient);
    }

    @Override
    public void deleteAppointmentsWithPatient(Patient target) {
        appointmentBook.deleteAppointmentsWithPatients(target);
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
        return patientBook.equals(other.patientBook)
                && appointmentBook.equals(other.appointmentBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPatients.equals(other.filteredPatients)
                && filteredAppointments.equals(other.filteredAppointments);
    }

    //=========== Model Validation =============================================================

    /**
     * Checks if the {@code readOnlyPatientBook} is consistent with the {@code appointmentBook} data.
     *
     * @param readOnlyPatientBook the patients data
     * @param appointmentBook the appointments data
     * @return true if the two books are valid, false otherwise
     */
    public static boolean isValidModel(ReadOnlyPatientBook readOnlyPatientBook,
                                       ReadOnlyAppointmentBook appointmentBook) {
        List<Appointment> appointmentList = appointmentBook.getAppointmentList();
        PatientBook patientBook = new PatientBook(readOnlyPatientBook);

        for (Appointment appointment : appointmentList) {
            if (!patientBook.hasPatient(appointment.getPatient())) {
                return false;
            }
        }
        return true;
    }
}

package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.DateMatchesPredicate;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.patient.Patient;

/**
 * Wraps all data at the appointment-book level
 * Overlaps are not allowed (by .isOverlapping comparison)
 */
public class AppointmentBook implements ReadOnlyAppointmentBook {

    private final UniqueAppointmentList appointments;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        appointments = new UniqueAppointmentList();
    }

    public AppointmentBook() {}

    /**
     * Creates an AppointmentBook using the Appointments in the {@code toBeCopied}
     */
    public AppointmentBook(ReadOnlyAppointmentBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the appointments list with {@code appointments}.
     * {@code appointments} must not contain overlapping appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
    }

    /**
     * Resets the existing data of this {@code AppointmentBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAppointmentBook newData) {
        requireNonNull(newData);

        setAppointments(newData.getAppointmentList());
    }

    //// appointment-level operations

    /**
     * Adds an appointment to the appointment book.
     * The appointment must not overlap with appointments in the appointment book.
     */
    public void addAppointment(Appointment a) {
        appointments.add(a);
    }

    /**
     * Replaces the given appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the appointment book.
     * The identity of {@code editedAppointment} must not be the same as another existing appointment
     * in the appointment book.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireNonNull(editedAppointment);

        appointments.setAppointment(target, editedAppointment);
    }

    /**
     * Removes {@code key} from this {@code AppointmentBook}.
     * {@code key} must exist in the appointment book.
     */
    public void removeAppointment(Appointment key) {
        requireNonNull(key);
        appointments.remove(key);
    }

    /**
     * Checks if an appointment hasOverlaps with {@code appointments}.
     */
    public boolean hasOverlapsWith(Appointment appointment) {
        requireNonNull(appointment);
        return appointments.hasOverlaps(appointment);
    }

    /**
     * Checks if the appointment list already contains the appointment.
     */
    public boolean hasAppointment(Appointment a) {
        requireNonNull(a);
        return appointments.hasCompleteOverlaps(a);
    }

    // patient-related appointment operations

    /**
     * Updates all appointments which contain the given {@code target}
     * in the list with {@code editedPatient}.
     * {@code target} must exist in at least one of the appointments in the appointment book.
     *
     * @param target specific patient who has been updated.
     * @param editedPatient the patient after the update.
     */
    public void updateAppointmentsWithPatients(Patient target, Patient editedPatient) {
        requireNonNull(editedPatient);
        appointments.updateAppointmentsWithPatients(target, editedPatient);
    }

    /**
     * Deletes the relevant appointments in the appointment book upon the deletion of a given {@code target}.
     *
     * @param target the specific patient deleted.
     */
    public void deleteAppointmentsWithPatients(Patient target) {
        requireNonNull(target);
        appointments.deleteAppointmentsWithPatients(target);
    }

    // util methods

    @Override
    public String toString() {
        return appointments.asUnmodifiableObservableList().size() + " appointments";
        // TODO: refine later
    }

    @Override
    public ObservableList<Appointment> getAppointmentList() {
        return appointments.asUnmodifiableObservableList();
    }

    public AppointmentStatistics getAppointmentBookStatistics() {
        Date td = new Date(LocalDate.now());
        int upcomingThisWeek = appointments.asUnmodifiableObservableList()
                .filtered(appointment -> appointment.isInSameWeek(td))
                .filtered(appointment -> !appointment.getIsDoneStatus()).size();
        DateMatchesPredicate p = new DateMatchesPredicate(td);
        ObservableList<Appointment> today = appointments.asUnmodifiableObservableList().filtered(p);
        int totalToday = today.size();
        int doneToday = today.filtered(appointment -> appointment.getIsDoneStatus()).size();
        int upcomingToday = totalToday - doneToday;
        return new AppointmentBook.AppointmentStatistics(totalToday, doneToday, upcomingToday, upcomingThisWeek);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AppointmentBook // instanceof handles nulls
                && appointments.equals(((AppointmentBook) other).appointments));
    }

    @Override
    public int hashCode() {
        return appointments.hashCode();
    }

    public static class AppointmentStatistics {
        private int totalToday;
        private int doneToday;
        private int upcomingToday;
        private int upcomingThisWeek;

        public AppointmentStatistics(int totalToday, int doneToday, int upcomingToday, int upcomingThisWeek) {
            this.totalToday = totalToday;
            this.doneToday = doneToday;
            this.upcomingToday = upcomingToday;
            this.upcomingThisWeek = upcomingThisWeek;
        }

    }
}

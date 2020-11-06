package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.ScheduleManager;
import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;
import seedu.address.model.patient.Patient;

/**
 * A list of appointments that enforces uniqueness between its elements and does not allow nulls.
 * An appointment is considered unique by comparing using {@code Appointment#isOverlapping(Appointment)}.
 * As such, adding and updating of appointment uses Appointment#isOverlapping(Appointment) for equality so as
 * to ensure that the appointment being added or updated is unique in terms of identity in the UniqueAppointmentList.
 * However, the removal of a patient uses Appointment#equals(Object) so as to ensure that the patient with
 * exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Appointment#isOverlapping(Appointment)
 */
public class UniqueAppointmentList implements Iterable<Appointment> {
    // todo: UniqueAppointmentListTest
    private final ObservableList<Appointment> internalList = FXCollections.observableArrayList();
    private final ObservableList<Appointment> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList.sorted());

    /**
     * Returns true if the list contains an appointment that has an overlap with the given argument.
     */
    public boolean hasOverlaps(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isOverlapping);
    }

    /**
     * Returns true if the list contains an appointment that completely overlaps with the appointment toCheck and
     * shares the same patient.
     */
    public boolean hasCompleteOverlaps(Appointment toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(appointment ->
                appointment.startAtSameTime(toCheck.getDate(), toCheck.getStartTime())
                && appointment.getEndTime().equals(toCheck.getEndTime())
                && appointment.getPatient().equals(toCheck.getPatient())
        );
    }

    /**
     * Adds an appointment to the list.
     * The appointment must not overlap with existing appointments in the list.
     */
    public void add(Appointment toAdd) {
        requireNonNull(toAdd);
        if (hasOverlaps(toAdd)) {
            throw new OverlappingAppointmentException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the appointment {@code target} in the list with {@code editedAppointment}.
     * {@code target} must exist in the list.
     * The appointment identity of {@code editedAppointment} must not be the same as
     * another existing appointment in the list.
     */
    public void setAppointment(Appointment target, Appointment editedAppointment) {
        requireAllNonNull(target, editedAppointment);
        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new AppointmentNotFoundException();
        }
        try {
            this.remove(target);
            this.add(editedAppointment);
        } catch (OverlappingAppointmentException ex) {
            internalList.add(index, target);
            throw new OverlappingAppointmentException();
        }
    }

    /**
     * Removes the equivalent appointment from the list.
     * The appointment must exist in the list.
     */
    public void remove(Appointment toRemove) {
        requireAllNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new AppointmentNotFoundException();
        }
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Appointment> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    public void setAppointments(UniqueAppointmentList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code appointments}.
     * {@code appointments} must not contain overlapping appointments.
     */
    public void setAppointments(List<Appointment> appointments) {
        requireAllNonNull(appointments);
        if (!appointmentsAreNotOverlapping(appointments)) {
            throw new OverlappingAppointmentException();
        }

        internalList.setAll(appointments);
    }

    /**
     * Deletes the relevant appointments upon the deletion of the {@code target}.
     */
    public void deleteAppointmentsWithPatients(Patient target) {
        requireAllNonNull(target);
        List<Appointment> newAppointmentList = new ArrayList<>();
        for (Appointment appointment : internalList) {
            if (!appointment.hasPatient(target)) {
                newAppointmentList.add(appointment);
            }
        }
        internalList.setAll(newAppointmentList);
    }

    /**
     * Updates the relevant appointments in the appointment book upon the update of {@code target} details
     * with {@code editedPatient}.
     */
    public void updateAppointmentsWithPatients(Patient target, Patient editedPatient) {
        requireAllNonNull(target, editedPatient);
        List<Appointment> newAppointmentList = new ArrayList<>();
        for (Appointment appointment : internalList) {
            if (appointment.hasPatient(target)) {
                newAppointmentList.add(appointment.setPatient(editedPatient));
            } else {
                newAppointmentList.add(appointment);
            }
        }
        internalList.setAll(newAppointmentList);
    }

    /**
     * Finds the available time slots based on the {@code appointmentList} on a specified date
     * and output as a string message.
     */
    public String findAvailableTimeSlots(List<Appointment> appointmentList, boolean isToday) {
        ScheduleManager scheduleManager = new ScheduleManager(appointmentList, isToday);
        // ArrayList<TimeInterval> availableTimeIntervals = scheduleManager.findFreeSlots();
        return scheduleManager.findFreeSlots();
    }

    @Override
    public Iterator<Appointment> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueAppointmentList // instanceof handles nulls
                && internalUnmodifiableList.equals(((UniqueAppointmentList) other).internalUnmodifiableList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code appointments} contains only non-overlapping appointments.
     */
    private boolean appointmentsAreNotOverlapping(List<Appointment> appointments) {
        for (int i = 0; i < appointments.size() - 1; i++) {
            for (int j = i + 1; j < appointments.size(); j++) {
                if (appointments.get(i).isOverlapping(appointments.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

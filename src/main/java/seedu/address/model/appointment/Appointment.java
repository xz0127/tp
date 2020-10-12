package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

import seedu.address.model.patient.Patient;

/**
 * Represents an Appointment in the appointment book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {
    // Duration of an appointment in hours
    public static final Duration DEFAULT_DURATION = Duration.ofHours(1);

    // Identity fields
    private final Date date;
    private final Time startTime;
    private final Time endTime;
    private boolean isDone;
    // todo: add more support for appointmentId
    private final AppointmentId appointmentId;

    // Data field
    private final Optional<Patient> patient;

    /**
     * Create an appointment without adding a patient.
     * Every field must be present and non-null.
     */
    public Appointment(Date date, Time startTime) {
        this(date, startTime, null);
    }

    /**
     * Create an appointment with the patient.
     */
    public Appointment(Date date, Time startTime, Patient patient) {
        requireAllNonNull(date, startTime);
        this.date = date;
        this.startTime = startTime;
        this.endTime = new Time(startTime.getTime().plus(DEFAULT_DURATION));

        assert startTime.isBefore(endTime);

        this.appointmentId = new AppointmentId(date, startTime);
        this.patient = Optional.ofNullable(patient);
        this.isDone = false;
    }

    /**
     * Create an appointment (with / without the patient) which is done.
     */
    public Appointment(Date date, Time startTime, Optional<Patient> patient, boolean isDone) {
        requireAllNonNull(date, startTime);
        this.date = date;
        this.startTime = startTime;
        this.endTime = new Time(startTime.getTime().plus(DEFAULT_DURATION));

        assert startTime.isBefore(endTime);

        this.appointmentId = new AppointmentId(date, startTime);
        this.patient = patient;
        this.isDone = isDone;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public AppointmentId getAppointmentId() {
        return appointmentId;
    }

    public Optional<Patient> getPatient() {
        return patient;
    }

    public boolean getIsDoneStatus() {
        return isDone;
    }
    public Appointment setPatient(Patient p) {
        requireNonNull(p);
        return new Appointment(date, startTime, p);
    }

    public Appointment markAsDone() {
        return new Appointment(date, startTime, patient, true);
    }

    /**
     * Checks if the appointment has {@code Patient other}.
     * Other must be non-null.
     *
     * @param other the patient to check in the appointment.
     * @return true if {@other Patient other} is in the Appointment, false otherwise.
     */
    public boolean hasPatient(Patient other) {
        requireNonNull(other);
        return patient.map(p -> p.isSamePatient(other)).orElse(false);
    }

    /**
     * Returns true if both appointments have overlapping appointment time slot.
     * This defines a weaker notion of equality between two appointments
     */
    public boolean isOverlapping(Appointment otherAppointment) {
        requireNonNull(otherAppointment);

        if (otherAppointment == this) {
            return true;
        }

        if (!otherAppointment.getDate().equals(getDate())) {
            return false;
        }

        // Assumption: start1 < end1
        // overlap if end1 > start2 and end2 > start1
        return (getEndTime().isAfter(otherAppointment.getStartTime()))
                && otherAppointment.getEndTime().isAfter(getStartTime());
    }

    /**
     * Returns true if both appointments start at the same time.
     * @param otherAppointment
     */
    public boolean startAtSameTime(Appointment otherAppointment) {
        requireNonNull(otherAppointment);

        if (otherAppointment == this) {
            return true;
        }

        return getDate().equals(otherAppointment.getDate())
                && getStartTime().equals(otherAppointment.getStartTime());
    }

    /**
     * Checks if this appointment comes before the given appointment input.
     *
     * @param otherAppointment the appointment to check against.
     * @return true if this appointment comes before, false otherwise.
     */
    public boolean isBefore(Appointment otherAppointment) {
        requireNonNull(otherAppointment);

        return getDate().isBefore(otherAppointment.getDate())
                || (getDate().equals(otherAppointment.getDate())
                && !(getEndTime().isAfter(otherAppointment.getStartTime()))); // End1 <= Start2
    }

    /**
     * Checks if this appointment comes after the given appointment input.
     *
     * @param otherAppointment the appointment to check against.
     * @return true if this appointment comes after, false otherwise.
     */
    public boolean isAfter(Appointment otherAppointment) {
        requireNonNull(otherAppointment);

        return getDate().isAfter(otherAppointment.getDate())
                || (getDate().equals(otherAppointment.getDate())
                && !(otherAppointment.getEndTime().isAfter(getStartTime()))); // End2 <= Start1
    }

    /**
     * Returns true if both appointments have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Appointment)) {
            return false;
        }

        Appointment otherAppointment = (Appointment) other;
        return otherAppointment.getStartTime().equals(getStartTime())
                && otherAppointment.getEndTime().equals(getEndTime())
                && otherAppointment.getDate().equals(getDate())
                && otherAppointment.getAppointmentId().equals(getAppointmentId())
                && otherAppointment.getPatient().equals(getPatient())
                && otherAppointment.getIsDoneStatus() == this.getIsDoneStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, date, appointmentId, patient);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Date: ")
                .append(getDate())
                .append(", from ")
                .append(getStartTime())
                .append(" to ")
                .append(getEndTime());

        getPatient().ifPresent(p -> builder
                .append("\nPatient: ")
                .append(p.getName())
                .append(" Contact: ")
                .append(p.getPhone())
        );

        return builder.toString();
    }
}

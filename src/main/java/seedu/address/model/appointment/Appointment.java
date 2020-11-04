package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.util.Objects;

import seedu.address.model.patient.Patient;

/**
 * Represents an Appointment in the appointment book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment implements Comparable<Appointment> {
    // Duration of an appointment in hours
    public static final Duration DEFAULT_DURATION = Duration.ofHours(1);
    // Creation offset in minutes. Used to allow creation of "last-minute" appointments.
    public static final int CREATION_OFFSET_MINUTES = 20;

    public static final String MESSAGE_CONSTRAINTS = "The appointment start time should be before the end time.";

    // Identity fields
    private final Date date;
    private final Time startTime;
    private final Time endTime;
    private final boolean isDone;

    // Data field
    private final Patient patient;

    /**
     * Create an appointment using the default duration.
     * Every field must be present and not null.
     */
    public Appointment(Date date, Time startTime, Patient patient) {
        this(date, startTime, new Time(startTime.getTime().plus(DEFAULT_DURATION)), patient);
    }

    /**
     * Create an appointment with specified end time.
     * Every field must be present and not null.
     */
    public Appointment(Date date, Time startTime, Time endTime, Patient patient) {
        this(date, startTime, endTime, patient, false);
    }

    /**
     * Create an appointment with specified end time and end status.
     * Every field must be present and not null.
     */
    public Appointment(Date date, Time startTime, Time endTime, Patient patient, boolean isDone) {
        requireAllNonNull(date, startTime, endTime, patient);
        checkArgument(startTime.isBefore(endTime), MESSAGE_CONSTRAINTS);

        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;

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

    public Duration getDuration() {
        return Duration.between(startTime.getTime(), endTime.getTime());
    }

    public Patient getPatient() {
        return patient;
    }

    public boolean getIsDoneStatus() {
        return isDone;
    }

    public Appointment setPatient(Patient p) {
        requireNonNull(p);
        return new Appointment(date, startTime, endTime, p, isDone);
    }

    public Appointment markAsDone() {
        return new Appointment(date, startTime, endTime, patient, true);
    }

    /**
     * Checks if the appointment has {@code Patient other}.
     *
     * @param other the patient to check in the appointment.
     * @return true if {@code Patient other} is in the Appointment, false otherwise.
     */
    public boolean hasPatient(Patient other) {
        return patient.isSamePatient(other);
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
     * Returns true if both appointments start at the given date and time.
     *
     * @param d given date
     * @param t given time
     */
    public boolean startAtSameTime(Date d, Time t) {
        requireAllNonNull(d, t);

        return getDate().equals(d)
                && getStartTime().equals(t);
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
     * Checks if this appointment is in the same week to week of the given date.
     *
     * @param date the given date to check against.
     * @return true if this appointment is in the same week of the given date, false otherwise.
     */
    public boolean isInSameWeek(Date date) {
        requireNonNull(date);

        return getDate().isInSameWeek(date);
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
                && otherAppointment.getPatient().equals(getPatient())
                && otherAppointment.getIsDoneStatus() == this.getIsDoneStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, date, patient);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getDate())
                .append(", from ")
                .append(getStartTime())
                .append(" to ")
                .append(getEndTime())
                .append("\nPatient: ")
                .append(getPatient().getName())
                .append("; Contact: ")
                .append(getPatient().getPhone());

        return builder.toString();
    }

    @Override
    public int compareTo(Appointment other) {
        requireAllNonNull(other);

        if (isBefore(other)) {
            return -1;
        }

        if (isAfter((other))) {
            return 1;
        }

        assert isOverlapping(other);
        return 0;
    }
}

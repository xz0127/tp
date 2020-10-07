package seedu.address.model.appointment;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.Duration;
import java.util.Objects;

/**
 * Represents an Appointment in the appointment book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Appointment {
    // Duration of an appointment in hours
    private static final Duration DEFAULT_DURATION = Duration.ofHours(1);

    // Identity fields
    private final Date date;
    private final Time startTime;
    private final Time endTime;
    // todo: add more support for appointmentId
    private final AppointmentId appointmentId;

    // Data field
    // todo: Change String object to IC object
    private final String patientId;

    /**
     * Every field must be present and not null.
     */
    public Appointment(Date date, Time startTime, String patientId) {
        requireAllNonNull(date, startTime, patientId);
        this.date = date;
        this.startTime = startTime;
        this.endTime = new Time(startTime.getTime().plus(DEFAULT_DURATION));

        assert startTime.isBefore(endTime);

        this.appointmentId = new AppointmentId(date, startTime);
        this.patientId = patientId;
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

    public String getPatientId() {
        return patientId;
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
     * This defines a stronger notion of equality between two persons.
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
                && otherAppointment.getPatientId().equals(getPatientId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, date, appointmentId, patientId);
    }

    @Override
    public String toString() {
        return getAppointmentId() + " Date: " + getDate()
                + " Time: from " + getStartTime() + " to " + getEndTime()
                + " Patient IC: " + getPatientId();
    }
}
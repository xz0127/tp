package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.person.Nric;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final LocalDate DEFAULT_DATE = LocalDate.of(2020, 12, 23);
    public static final LocalTime DEFAULT_TIME = LocalTime.of(13, 30);
    public static final Nric DEFAULT_PATIENT_IC = new Nric("S1234567A");

    private Date date;
    private Time startTime;
    private Nric patientId;

    /**
     * Creates an {@code AppointmentBuilder} with the default details.
     */
    public AppointmentBuilder() {
        date = new Date(DEFAULT_DATE);
        startTime = new Time(DEFAULT_TIME);
        patientId = DEFAULT_PATIENT_IC;
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        date = appointmentToCopy.getDate();
        startTime = appointmentToCopy.getStartTime();
        patientId = appointmentToCopy.getPatientId();
    }

    /**
     * Sets the {@code Date} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDate(LocalDate date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the start time of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withStartTime(LocalTime time) {
        this.startTime = new Time(time);
        return this;
    }

    /**
     * Sets the patient's {@code IC} for the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPatientId(Nric patientId) {
        this.patientId = patientId;
        return this;
    }

    public Appointment build() {
        return new Appointment(date, startTime, patientId);
    }

}

package seedu.address.testutil;

import static seedu.address.testutil.TypicalPatients.ALICE;

import java.time.LocalDate;
import java.time.LocalTime;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.patient.Patient;

/**
 * A utility class to help with building Appointment objects.
 */
public class AppointmentBuilder {

    public static final LocalDate DEFAULT_DATE = LocalDate.of(2020, 12, 23);
    public static final LocalTime DEFAULT_TIME = LocalTime.of(13, 30);
    public static final Patient DEFAULT_PATIENT = ALICE;

    private Date date;
    private Time startTime;
    private Patient patient;

    /**
     * Creates an {@code AppointmentBuilder} with the default details.
     */
    public AppointmentBuilder() {
        date = new Date(DEFAULT_DATE);
        startTime = new Time(DEFAULT_TIME);
        patient = DEFAULT_PATIENT;
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        date = appointmentToCopy.getDate();
        startTime = appointmentToCopy.getStartTime();
        patient = appointmentToCopy.getPatient();
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
     * Sets the {@code Patient} for the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    public Appointment build() {
        return new Appointment(date, startTime, patient);
    }

}

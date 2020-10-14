package seedu.address.testutil;

import static seedu.address.testutil.TypicalPatients.ALICE;

import java.time.Duration;
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
    public static final LocalTime DEFAULT_START_TIME = LocalTime.of(13, 30);
    public static final Duration DEFAULT_DURATION = Duration.ofHours(1);
    public static final Patient DEFAULT_PATIENT = ALICE;
    public static final boolean DEFAULT_DONE_STATUS = false;

    private Date date;
    private Time startTime;
    private Time endTime;
    private Patient patient;
    private boolean isDone;

    /**
     * Creates an {@code AppointmentBuilder} with the default details.
     */
    public AppointmentBuilder() {
        date = new Date(DEFAULT_DATE);
        startTime = new Time(DEFAULT_START_TIME);
        endTime = new Time(DEFAULT_START_TIME.plus(DEFAULT_DURATION));
        patient = DEFAULT_PATIENT;
        isDone = DEFAULT_DONE_STATUS;
    }

    /**
     * Initializes the AppointmentBuilder with the data of {@code appointmentToCopy}.
     */
    public AppointmentBuilder(Appointment appointmentToCopy) {
        date = appointmentToCopy.getDate();
        startTime = appointmentToCopy.getStartTime();
        endTime = appointmentToCopy.getEndTime();
        patient = appointmentToCopy.getPatient();
        isDone = appointmentToCopy.getIsDoneStatus();
    }

    /**
     * Sets the {@code Date} of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDate(LocalDate date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Same as {@link #withTime(LocalTime, LocalTime)} but using the {@code DEFAULT_DURATION}
     */
    public AppointmentBuilder withTime(LocalTime startTime) {
        return withTime(startTime, startTime.plus(DEFAULT_DURATION));
    }

    /**
     * Sets the start time and end time of the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withTime(LocalTime startTime, LocalTime endTime) {
        this.startTime = new Time(startTime);
        this.endTime = new Time(endTime);
        return this;
    }

    /**
     * Sets the {@code Patient} for the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withPatient(Patient patient) {
        this.patient = patient;
        return this;
    }

    /**
     * Sets the done status for the {@code Appointment} that we are building.
     */
    public AppointmentBuilder withDoneStatus(boolean isDone) {
        this.isDone = isDone;
        return this;
    }

    /**
     * Build the {@code Appointment}.
     */
    public Appointment build() {
        return new Appointment(date, startTime, endTime, patient, isDone);
    }

}

package seedu.address.storage.appointment;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.patient.Patient;
import seedu.address.storage.patient.JsonAdaptedPatient;

/**
 * Jackson-friendly version of {@link Appointment}.
 */
class JsonAdaptedAppointment {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Appointment's %s field is missing or corrupted!";
    public static final String TIME_IN_WRONG_ORDER = "Appointment start time is not before end time!";

    private final LocalDate date;
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final Boolean isDone;
    private final JsonAdaptedPatient patient;

    /**
     * Constructs a {@code JsonAdaptedAppointment} with the given appointment details.
     */
    @JsonCreator
    public JsonAdaptedAppointment(@JsonProperty("date") LocalDate date,
                                  @JsonProperty("startTime") LocalTime startTime,
                                  @JsonProperty("endTime") LocalTime endTime,
                                  @JsonProperty("isDone") Boolean isDone,
                                  @JsonProperty("patient") JsonAdaptedPatient patient) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDone = isDone;
        this.patient = patient;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public JsonAdaptedAppointment(Appointment source) {
        date = source.getDate().getDate();
        startTime = source.getStartTime().getTime();
        endTime = source.getEndTime().getTime();
        isDone = source.getIsDoneStatus();
        patient = new JsonAdaptedPatient(source.getPatient());
    }

    /**
     * Converts this Jackson-friendly adapted appointment object into the model's {@code Appointment} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Appointment.
     */
    public Appointment toModelType() throws IllegalValueException {

        if (startTime == null || endTime == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }

        if (!Time.isValidTime(startTime) || !Time.isValidTime(endTime)) {
            throw new IllegalValueException(Time.MESSAGE_CONSTRAINTS);
        }

        if (!startTime.isBefore(endTime)) {
            throw new IllegalValueException(TIME_IN_WRONG_ORDER);
        }

        final Time modelStartTime = new Time(startTime);
        final Time modelEndTime = new Time(endTime);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }

        final Date modelDate = new Date(date);

        if (isDone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Done Status"));
        }

        final boolean modelDoneStatus = isDone;

        if (patient == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Patient.class.getSimpleName()));
        }

        final Patient modelPatient = patient.toModelType();

        return new Appointment(modelDate, modelStartTime, modelEndTime, modelPatient, modelDoneStatus);
    }

}

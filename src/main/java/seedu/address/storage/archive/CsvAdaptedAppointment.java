package seedu.address.storage.archive;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import seedu.address.model.appointment.Appointment;

/**
 * Csv-friendly version of {@link Appointment}.
 * Contains minimal Appointment data for archive purposes.
 */
@JsonPropertyOrder({"date", "startTime", "endTime", "isDone", "patient"})
public class CsvAdaptedAppointment {
    @JsonFormat(pattern = "yyy-MM-dd")
    private LocalDate date;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;

    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;

    @JsonUnwrapped
    private CsvAdaptedPatient patient;

    private Boolean isDone;

    /*
     * Empty constructor is used in place of @JsonCreator to allow deserialization to reconstruct
     * patient before reconstructing appointment.
     *
     * Issue is mentioned here:
     * https://github.com/FasterXML/jackson-databind/issues/1467
     */
    public CsvAdaptedAppointment() {
    }

    /**
     * Constructs a {@code CsvAdaptedAppointment} with the given appointment details.
     */
    public CsvAdaptedAppointment(LocalDate date,
                                 LocalTime startTime,
                                 LocalTime endTime,
                                 Boolean isDone,
                                 CsvAdaptedPatient patient) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isDone = isDone;
        this.patient = patient;
    }

    /**
     * Converts a given {@code Appointment} into this class for Jackson use.
     */
    public CsvAdaptedAppointment(Appointment source) {
        date = source.getDate().getDate();
        startTime = source.getStartTime().getTime();
        endTime = source.getEndTime().getTime();
        isDone = source.getIsDoneStatus();
        patient = new CsvAdaptedPatient(source.getPatient());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CsvAdaptedAppointment)) {
            return false;
        }

        CsvAdaptedAppointment otherAppointment = (CsvAdaptedAppointment) other;
        return otherAppointment.patient.equals(patient)
                && otherAppointment.date.equals(date)
                && otherAppointment.isDone.equals(isDone)
                && otherAppointment.startTime.equals(startTime)
                && otherAppointment.endTime.equals(endTime);
    }
}

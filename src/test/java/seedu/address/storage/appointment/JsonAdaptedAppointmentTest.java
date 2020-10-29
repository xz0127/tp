package seedu.address.storage.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.appointment.JsonAdaptedAppointment.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.storage.appointment.JsonAdaptedAppointment.TIME_IN_WRONG_ORDER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.BENSON_APPOINTMENT;
import static seedu.address.testutil.TypicalPatients.BENSON;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.patient.Patient;
import seedu.address.storage.patient.JsonAdaptedPatient;


public class JsonAdaptedAppointmentTest {
    private static final LocalTime INVALID_START_TIME_ORDER = LocalTime.of(15, 0);
    private static final LocalTime INVALID_END_TIME_ORDER = LocalTime.of(10, 0);
    private static final LocalTime INVALID_TIME = LocalTime.of(23, 59);
    private static final JsonAdaptedPatient INVALID_PATIENT =
            new JsonAdaptedPatient("R@chel", "+651234", " ", null, "a123456G", "");

    private static final LocalDate VALID_DATE = BENSON_APPOINTMENT.getDate().getDate();
    private static final LocalTime VALID_START_TIME = BENSON_APPOINTMENT.getStartTime().getTime();
    private static final LocalTime VALID_END_TIME = BENSON_APPOINTMENT.getEndTime().getTime();
    private static final JsonAdaptedPatient VALID_PATIENT = new JsonAdaptedPatient(BENSON);
    private static final Boolean VALID_DONE_STATUS = false;

    @Test
    public void toModelType_validAppointmentDetails_returnsAppointment() throws Exception {
        JsonAdaptedAppointment patient = new JsonAdaptedAppointment(BENSON_APPOINTMENT);
        assertEquals(BENSON_APPOINTMENT, patient.toModelType());
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(null, VALID_START_TIME, VALID_END_TIME, VALID_DONE_STATUS, VALID_PATIENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidStartTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_DATE, INVALID_TIME, VALID_END_TIME, VALID_DONE_STATUS, VALID_PATIENT);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullStartTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_DATE, null, VALID_END_TIME, VALID_DONE_STATUS, VALID_PATIENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidEndTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_DATE, VALID_START_TIME, INVALID_TIME, VALID_DONE_STATUS,
                        VALID_PATIENT);
        String expectedMessage = Time.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_nullEndTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_DATE, VALID_START_TIME, null, VALID_DONE_STATUS, VALID_PATIENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_endTimeBeforeStartTime_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_DATE, INVALID_START_TIME_ORDER, INVALID_END_TIME_ORDER,
                        VALID_DONE_STATUS, VALID_PATIENT);
        assertThrows(IllegalValueException.class, TIME_IN_WRONG_ORDER, appointment::toModelType);
    }

    @Test
    public void toModelType_nullDoneStatus_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_DATE, VALID_START_TIME, VALID_END_TIME, null, VALID_PATIENT);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, "Done Status");
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

    @Test
    public void toModelType_invalidPatient_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_DONE_STATUS,
                        INVALID_PATIENT);
        assertThrows(IllegalValueException.class, appointment::toModelType);
    }

    @Test
    public void toModelType_nullPatient_throwsIllegalValueException() {
        JsonAdaptedAppointment appointment =
                new JsonAdaptedAppointment(VALID_DATE, VALID_START_TIME, VALID_END_TIME, VALID_DONE_STATUS, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Patient.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, appointment::toModelType);
    }

}

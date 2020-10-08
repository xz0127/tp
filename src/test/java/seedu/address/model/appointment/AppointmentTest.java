package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.APPOINTMENT_ONE;
import static seedu.address.testutil.TypicalAppointments.APPOINTMENT_TWO;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Nric;
import seedu.address.testutil.AppointmentBuilder;

public class AppointmentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appointment(null, null, null));
    }

    @Test
    public void isOverlapping() {
        // null check
        assertThrows(NullPointerException.class, () -> APPOINTMENT_ONE.isOverlapping(null));

        // same object -> returns true
        assertTrue(APPOINTMENT_ONE.isOverlapping(APPOINTMENT_ONE));

        // different date -> returns false
        Appointment editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withDate(LocalDate.of(2019, 12, 20)).build();
        assertFalse(APPOINTMENT_ONE.isOverlapping(editedOne));

        // different date and time -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withDate(LocalDate.of(2019, 12, 20))
                .withStartTime(LocalTime.of(13, 30)).build();
        assertFalse(APPOINTMENT_ONE.isOverlapping(editedOne));

        // earlier date -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withDate(APPOINTMENT_ONE.getDate().getDate().minusDays(1))
                .build();
        assertFalse(APPOINTMENT_ONE.isOverlapping(editedOne));

        // earlier start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().minusHours(1))
                .build();
        assertFalse(APPOINTMENT_ONE.isOverlapping(editedOne));

        // earlier start time, same date (overlapping) -> returns true
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().minusMinutes(30))
                .build();
        assertTrue(APPOINTMENT_ONE.isOverlapping(editedOne));

        // same date, same time, different patient id -> returns true
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withPatientId(new Nric("T0134567B")).build();
        assertTrue(APPOINTMENT_ONE.isOverlapping(editedOne));

        // later start time, same date (overlapping) -> returns true
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().plusMinutes(30))
                .build();
        assertTrue(APPOINTMENT_ONE.isOverlapping(editedOne));

        // later start time, same date (not overlapping) -> returns true
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().plusHours(1))
                .build();
        assertFalse(APPOINTMENT_ONE.isOverlapping(editedOne));

        // later date -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withDate(APPOINTMENT_ONE.getDate().getDate().plusDays(1))
                .build();
        assertFalse(APPOINTMENT_ONE.isOverlapping(editedOne));
    }

    @Test
    public void isBefore() {
        // null check
        assertThrows(NullPointerException.class, () -> APPOINTMENT_ONE.isBefore(null));

        // same object -> returns false
        assertFalse(APPOINTMENT_ONE.isBefore(APPOINTMENT_ONE));

        // earlier date -> returns false
        Appointment editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withDate(APPOINTMENT_ONE.getDate().getDate().minusDays(1))
                .build();
        assertFalse(APPOINTMENT_ONE.isBefore(editedOne));

        // earlier start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().minusHours(1))
                .build();
        assertFalse(APPOINTMENT_ONE.isBefore(editedOne));

        // earlier start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().minusMinutes(30))
                .build();
        assertFalse(APPOINTMENT_ONE.isBefore(editedOne));

        // same start time and same date, different patientId -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withPatientId(new Nric("T0134567B")).build();
        assertFalse(APPOINTMENT_ONE.isBefore(editedOne));

        // later start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().plusMinutes(30))
                .build();
        assertFalse(APPOINTMENT_ONE.isBefore(editedOne));

        // later start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().plusHours(1))
                .build();
        assertTrue(APPOINTMENT_ONE.isBefore(editedOne));

        // earlier date -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withDate(APPOINTMENT_ONE.getDate().getDate().plusDays(1))
                .build();
        assertTrue(APPOINTMENT_ONE.isBefore(editedOne));
    }

    @Test
    public void isAfter() {
        // null check
        assertThrows(NullPointerException.class, () -> APPOINTMENT_ONE.isAfter(null));

        // same object -> returns false
        assertFalse(APPOINTMENT_ONE.isAfter(APPOINTMENT_ONE));

        // earlier date -> returns false
        Appointment editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withDate(APPOINTMENT_ONE.getDate().getDate().minusDays(1))
                .build();
        assertTrue(APPOINTMENT_ONE.isAfter(editedOne));

        // earlier start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().minusHours(1))
                .build();
        assertTrue(APPOINTMENT_ONE.isAfter(editedOne));

        // earlier start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().minusMinutes(30))
                .build();
        assertFalse(APPOINTMENT_ONE.isAfter(editedOne));

        // same start time and same date, different patientId -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withPatientId(new Nric("T0134567B")).build();
        assertFalse(APPOINTMENT_ONE.isAfter(editedOne));

        // later start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().plusMinutes(30))
                .build();
        assertFalse(APPOINTMENT_ONE.isAfter(editedOne));

        // later start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(APPOINTMENT_ONE.getStartTime().getTime().plusHours(1))
                .build();
        assertFalse(APPOINTMENT_ONE.isAfter(editedOne));

        // earlier date -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withDate(APPOINTMENT_ONE.getDate().getDate().plusDays(1))
                .build();
        assertFalse(APPOINTMENT_ONE.isAfter(editedOne));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Appointment appointmentOneCopy = new AppointmentBuilder(APPOINTMENT_ONE).build();
        assertTrue(APPOINTMENT_ONE.equals(appointmentOneCopy));

        // same object -> returns true
        assertTrue(APPOINTMENT_ONE.equals(APPOINTMENT_ONE));

        // null -> returns false
        assertFalse(APPOINTMENT_ONE.equals(null));

        // different type -> returns false
        assertFalse(APPOINTMENT_ONE.equals(5));

        // different Appointment -> returns false
        assertFalse(APPOINTMENT_ONE.equals(APPOINTMENT_TWO));

        // different date -> returns false
        Appointment editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withDate(LocalDate.of(2010, 10, 10)).build();
        assertFalse(APPOINTMENT_ONE.equals(editedOne));

        // different time -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withStartTime(LocalTime.of(15, 15)).build();
        assertFalse(APPOINTMENT_ONE.equals(editedOne));

        // different patient -> returns false
        editedOne = new AppointmentBuilder(APPOINTMENT_ONE)
                .withPatientId(new Nric("T0134567B")).build();
        assertFalse(APPOINTMENT_ONE.equals(editedOne));
    }
}

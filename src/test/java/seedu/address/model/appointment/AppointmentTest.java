package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.BENSON_APPOINTMENT;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BOB;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.patient.Patient;
import seedu.address.testutil.AppointmentBuilder;
import seedu.address.testutil.PatientBuilder;

public class AppointmentTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Appointment(null, null, null));
        assertThrows(NullPointerException.class, ()
            -> new Appointment(null, null, null, null));
    }

    @Test
    public void constructor_invalidStartAndEndTime_throwsIllegalArgumentException() {
        LocalDate date = LocalDate.of(2024, 1, 1);
        LocalTime time = LocalTime.of(13, 0);

        // same start and end time
        assertThrows(IllegalArgumentException.class, ()
            -> new Appointment(new Date(date), new Time(time), new Time(time), ALICE));

        // start time > end time
        assertThrows(IllegalArgumentException.class, ()
            -> new Appointment(new Date(date), new Time(time), new Time(time.minusHours(1)), ALICE));
    }

    @Test
    public void getIsDoneStatus() {
        assertFalse(ALICE_APPOINTMENT.getIsDoneStatus());

        Appointment doneAppointment = ALICE_APPOINTMENT.markAsDone();
        assertTrue(doneAppointment.getIsDoneStatus());
    }

    @Test
    public void hasPatient_returnTrue() {
        assertTrue(ALICE_APPOINTMENT.hasPatient(ALICE));

        Patient editedAlice = new PatientBuilder(ALICE).withTags("others").build();
        assertTrue(ALICE_APPOINTMENT.hasPatient(editedAlice));

        // different tags
        editedAlice = new PatientBuilder(ALICE).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertTrue(ALICE_APPOINTMENT.hasPatient(editedAlice));

        // different address
        editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE_APPOINTMENT.hasPatient(editedAlice));
    }

    @Test
    public void hasPatient_returnFalse() {
        // null -> returns false
        assertFalse(ALICE_APPOINTMENT.hasPatient(null));

        // different phone -> returns true
        Patient editedAlice = new PatientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE_APPOINTMENT.hasPatient(editedAlice));

        // different name -> returns true
        editedAlice = new PatientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePatient(editedAlice));

        // different nric -> returns false
        editedAlice = new PatientBuilder(ALICE).withNric(VALID_NRIC_BOB).build();
        assertFalse(ALICE.isSamePatient(editedAlice));
    }

    @Test
    public void setPatient() {
        assertThrows(NullPointerException.class, () -> ALICE_APPOINTMENT.setPatient(null));

        assertTrue(ALICE_APPOINTMENT.setPatient(BOB).hasPatient(BOB));

        assertFalse(ALICE_APPOINTMENT.setPatient(BOB).hasPatient(ALICE));
    }

    @Test
    public void startAtSameTime() {
        assertTrue(ALICE_APPOINTMENT.startAtSameTime(ALICE_APPOINTMENT.getDate(), ALICE_APPOINTMENT.getStartTime()));

        assertFalse(ALICE_APPOINTMENT.startAtSameTime(ALICE_APPOINTMENT.getDate(), ALICE_APPOINTMENT.getEndTime()));
    }

    @Test
    public void isOverlapping() {
        // null check
        assertThrows(NullPointerException.class, () -> ALICE_APPOINTMENT.isOverlapping(null));

        // same object -> returns true
        assertTrue(ALICE_APPOINTMENT.isOverlapping(ALICE_APPOINTMENT));

        // different date -> returns false
        Appointment editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(LocalDate.of(2019, 12, 20)).build();
        assertFalse(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // different date and time -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(LocalDate.of(2019, 12, 20))
                .withTime(LocalTime.of(13, 30), LocalTime.of(14, 0))
                .build();
        assertFalse(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // earlier date -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(ALICE_APPOINTMENT.getDate().getDate().minusDays(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // earlier start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().minusHours(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // earlier start time, same date (overlapping) -> returns true
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().minusMinutes(30))
                .build();
        assertTrue(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // same date, same time, different patient -> returns true
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withPatient(BOB).build();
        assertTrue(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // later start time, same date (overlapping) -> returns true
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().plusMinutes(30))
                .build();
        assertTrue(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // later start time, same date (not overlapping) -> returns true
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().plusHours(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // later date -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(ALICE_APPOINTMENT.getDate().getDate().plusDays(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isOverlapping(editedOne));
    }

    @Test
    public void isBefore() {
        // null check
        assertThrows(NullPointerException.class, () -> ALICE_APPOINTMENT.isBefore(null));

        // same object -> returns false
        assertFalse(ALICE_APPOINTMENT.isBefore(ALICE_APPOINTMENT));

        // earlier date -> returns false
        Appointment editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(ALICE_APPOINTMENT.getDate().getDate().minusDays(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isBefore(editedOne));

        // earlier start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().minusHours(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isBefore(editedOne));

        // earlier start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().minusMinutes(30))
                .build();
        assertFalse(ALICE_APPOINTMENT.isBefore(editedOne));

        // same start time and same date, different patientId -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withPatient(BOB).build();
        assertFalse(ALICE_APPOINTMENT.isBefore(editedOne));

        // later start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().plusMinutes(30))
                .build();
        assertFalse(ALICE_APPOINTMENT.isBefore(editedOne));

        // later start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().plusHours(1))
                .build();
        assertTrue(ALICE_APPOINTMENT.isBefore(editedOne));

        // earlier date -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(ALICE_APPOINTMENT.getDate().getDate().plusDays(1))
                .build();
        assertTrue(ALICE_APPOINTMENT.isBefore(editedOne));
    }

    @Test
    public void isAfter() {
        // null check
        assertThrows(NullPointerException.class, () -> ALICE_APPOINTMENT.isAfter(null));

        // same object -> returns false
        assertFalse(ALICE_APPOINTMENT.isAfter(ALICE_APPOINTMENT));

        // earlier date -> returns false
        Appointment editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(ALICE_APPOINTMENT.getDate().getDate().minusDays(1))
                .build();
        assertTrue(ALICE_APPOINTMENT.isAfter(editedOne));

        // earlier start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().minusHours(1))
                .build();
        assertTrue(ALICE_APPOINTMENT.isAfter(editedOne));

        // earlier start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().minusMinutes(30))
                .build();
        assertFalse(ALICE_APPOINTMENT.isAfter(editedOne));

        // same start time and same date, different patientId -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withPatient(BOB).build();
        assertFalse(ALICE_APPOINTMENT.isAfter(editedOne));

        // later start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().plusMinutes(30))
                .build();
        assertFalse(ALICE_APPOINTMENT.isAfter(editedOne));

        // later start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().plusHours(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isAfter(editedOne));

        // earlier date -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(ALICE_APPOINTMENT.getDate().getDate().plusDays(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isAfter(editedOne));
    }

    @Test
    public void isInSameWeek() {
        // null check
        assertThrows(NullPointerException.class, () -> ALICE_APPOINTMENT.isInSameWeek(null));

        // same object -> returns true
        assertTrue(ALICE_APPOINTMENT.isInSameWeek(ALICE_APPOINTMENT.getDate()));

        // earlier date -> returns true
        Appointment editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(ALICE_APPOINTMENT.getDate().getDate().minusDays(1))
                .build();
        assertTrue(ALICE_APPOINTMENT.isInSameWeek(editedOne.getDate()));

        // earlier start time, same date (not overlapping) -> returns true
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().minusHours(1))
                .build();
        assertTrue(ALICE_APPOINTMENT.isInSameWeek(editedOne.getDate()));

        // earlier start time, same date (overlapping) -> returns true
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(ALICE_APPOINTMENT.getStartTime().getTime().minusMinutes(30))
                .build();
        assertTrue(ALICE_APPOINTMENT.isInSameWeek(editedOne.getDate()));

        // earlier date -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(ALICE_APPOINTMENT.getDate().getDate().plusDays(2))
                .build();
        assertFalse(ALICE_APPOINTMENT.isInSameWeek(editedOne.getDate()));

        // earlier date -> returns true
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(ALICE_APPOINTMENT.getDate().getDate().plusDays(1))
                .build();
        assertTrue(ALICE_APPOINTMENT.isInSameWeek(editedOne.getDate()));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Appointment appointmentOneCopy = new AppointmentBuilder(ALICE_APPOINTMENT).build();
        assertTrue(ALICE_APPOINTMENT.equals(appointmentOneCopy));

        // same object -> returns true
        assertTrue(ALICE_APPOINTMENT.equals(ALICE_APPOINTMENT));

        // null -> returns false
        assertFalse(ALICE_APPOINTMENT.equals(null));

        // different type -> returns false
        assertFalse(ALICE_APPOINTMENT.equals(5));

        // different Appointment -> returns false
        assertFalse(ALICE_APPOINTMENT.equals(BENSON_APPOINTMENT));

        // different date -> returns false
        Appointment editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(LocalDate.of(2010, 10, 10)).build();
        assertFalse(ALICE_APPOINTMENT.equals(editedOne));

        // different time -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(LocalTime.of(15, 15)).build();
        assertFalse(ALICE_APPOINTMENT.equals(editedOne));

        // different patient -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withPatient(BOB).build();
        assertFalse(ALICE_APPOINTMENT.equals(editedOne));
    }

    @Test
    public void compareTo_appointmentIsBefore_returnNegative() {
        // different appointment date
        Appointment appointmentBefore = new AppointmentBuilder()
                .withDate(LocalDate.of(2020, 2, 2)).build();
        Appointment appointmentAfter = new AppointmentBuilder()
                .withDate(LocalDate.of(2020, 2, 3)).build();
        assertTrue(appointmentBefore.compareTo(appointmentAfter) < 0);

        // different appointment time, same date (not overlapping)
        appointmentBefore = new AppointmentBuilder()
                .withTime(LocalTime.of(9, 0), LocalTime.of(10, 0)).build();
        appointmentAfter = new AppointmentBuilder()
                .withTime(LocalTime.of(10, 30), LocalTime.of(11, 0)).build();
        assertTrue(appointmentBefore.compareTo(appointmentAfter) < 0);
    }

    @Test
    public void compare_appointmentIsAfter_returnPositive() {
        // different appointment date
        Appointment appointmentAfter = new AppointmentBuilder()
                .withDate(LocalDate.of(2020, 2, 3)).build();
        Appointment appointmentBefore = new AppointmentBuilder()
                .withDate(LocalDate.of(2020, 2, 2)).build();
        assertEquals(1, appointmentAfter.compareTo(appointmentBefore));

        // different appointment time, same date (not overlapping)
        appointmentAfter = new AppointmentBuilder()
                .withTime(LocalTime.of(10, 0), LocalTime.of(11, 0)).build();
        appointmentBefore = new AppointmentBuilder()
                .withTime(LocalTime.of(9, 0), LocalTime.of(10, 0)).build();
        assertEquals(1, appointmentAfter.compareTo(appointmentBefore));
    }

    @Test
    public void compare_appointmentIsOverlapping_returnZero() {
        // different appointment time, but overlapping
        Appointment appointmentBefore = new AppointmentBuilder()
                .withTime(LocalTime.of(17, 0), LocalTime.of(18, 0)).build();
        Appointment appointmentAfter = new AppointmentBuilder()
                .withTime(LocalTime.of(17, 30), LocalTime.of(18, 40)).build();
        assertEquals(0, appointmentAfter.compareTo(appointmentAfter));

        // different appointment time, but overlapping
        appointmentBefore = new AppointmentBuilder()
                .withTime(LocalTime.of(17, 0), LocalTime.of(19, 0)).build();
        appointmentAfter = new AppointmentBuilder()
                .withTime(LocalTime.of(17, 30), LocalTime.of(18, 30)).build();
        assertEquals(0, appointmentBefore.compareTo(appointmentAfter));

        // different appointment time, but overlapping
        assertEquals(0, appointmentAfter.compareTo(appointmentBefore));

        // same appointment time
        assertEquals(0, appointmentAfter.compareTo(appointmentAfter));
        assertEquals(0, appointmentBefore.compareTo(appointmentBefore));
    }
}

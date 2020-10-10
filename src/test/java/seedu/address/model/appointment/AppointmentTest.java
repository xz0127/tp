package seedu.address.model.appointment;

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
import static seedu.address.testutil.TypicalAppointments.BOB_APPOINTMENT;
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
        assertThrows(NullPointerException.class, () -> new Appointment(null, null));
        assertThrows(NullPointerException.class, () -> new Appointment(null, null, null));
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

        // different phone -> returns false
        Patient editedAlice = new PatientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE_APPOINTMENT.hasPatient(editedAlice));

        // different name -> returns false
        editedAlice = new PatientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.isSamePatient(editedAlice));

        // different nric -> returns false
        editedAlice = new PatientBuilder(ALICE).withName(VALID_NRIC_BOB).build();
        assertFalse(ALICE.isSamePatient(editedAlice));
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
                .withStartTime(LocalTime.of(13, 30)).build();
        assertFalse(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // earlier date -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(ALICE_APPOINTMENT.getDate().getDate().minusDays(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // earlier start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().minusHours(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // earlier start time, same date (overlapping) -> returns true
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().minusMinutes(30))
                .build();
        assertTrue(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // same date, same time, different patient -> returns true
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withPatient(BOB).build();
        assertTrue(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // later start time, same date (overlapping) -> returns true
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().plusMinutes(30))
                .build();
        assertTrue(ALICE_APPOINTMENT.isOverlapping(editedOne));

        // later start time, same date (not overlapping) -> returns true
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().plusHours(1))
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
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().minusHours(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isBefore(editedOne));

        // earlier start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().minusMinutes(30))
                .build();
        assertFalse(ALICE_APPOINTMENT.isBefore(editedOne));

        // same start time and same date, different patientId -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withPatient(BOB).build();
        assertFalse(ALICE_APPOINTMENT.isBefore(editedOne));

        // later start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().plusMinutes(30))
                .build();
        assertFalse(ALICE_APPOINTMENT.isBefore(editedOne));

        // later start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().plusHours(1))
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
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().minusHours(1))
                .build();
        assertTrue(ALICE_APPOINTMENT.isAfter(editedOne));

        // earlier start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().minusMinutes(30))
                .build();
        assertFalse(ALICE_APPOINTMENT.isAfter(editedOne));

        // same start time and same date, different patientId -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withPatient(BOB).build();
        assertFalse(ALICE_APPOINTMENT.isAfter(editedOne));

        // later start time, same date (overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().plusMinutes(30))
                .build();
        assertFalse(ALICE_APPOINTMENT.isAfter(editedOne));

        // later start time, same date (not overlapping) -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withStartTime(ALICE_APPOINTMENT.getStartTime().getTime().plusHours(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isAfter(editedOne));

        // earlier date -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(ALICE_APPOINTMENT.getDate().getDate().plusDays(1))
                .build();
        assertFalse(ALICE_APPOINTMENT.isAfter(editedOne));
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
        assertFalse(ALICE_APPOINTMENT.equals(BOB_APPOINTMENT));

        // different date -> returns false
        Appointment editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(LocalDate.of(2010, 10, 10)).build();
        assertFalse(ALICE_APPOINTMENT.equals(editedOne));

        // different time -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withStartTime(LocalTime.of(15, 15)).build();
        assertFalse(ALICE_APPOINTMENT.equals(editedOne));

        // different patient -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withPatient(BOB).build();
        assertFalse(ALICE_APPOINTMENT.equals(editedOne));
    }
}

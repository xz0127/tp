package seedu.address.storage.archive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.BENSON_APPOINTMENT;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BOB;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.AppointmentBuilder;

public class CsvAdaptedAppointmentTest {

    @Test
    public void constructor() {
        CsvAdaptedAppointment appointment = new CsvAdaptedAppointment(ALICE_APPOINTMENT);
        CsvAdaptedAppointment constructedAppointment = new CsvAdaptedAppointment(ALICE_APPOINTMENT.getDate().getDate(),
                ALICE_APPOINTMENT.getStartTime().getTime(), ALICE_APPOINTMENT.getEndTime().getTime(),
                ALICE_APPOINTMENT.getIsDoneStatus(), new CsvAdaptedPatient(ALICE));

        assertEquals(appointment, constructedAppointment);
    }

    @Test
    public void equals() {
        CsvAdaptedAppointment aliceAppointment = new CsvAdaptedAppointment(ALICE_APPOINTMENT);
        CsvAdaptedAppointment aliceAppointmentCopy = new CsvAdaptedAppointment(
                new AppointmentBuilder(ALICE_APPOINTMENT).build());

        // same values -> returns true
        assertTrue(aliceAppointment.equals(aliceAppointmentCopy));

        // same object -> returns true
        assertTrue(aliceAppointment.equals(aliceAppointment));

        // null -> returns false
        assertFalse(aliceAppointment.equals(null));

        // different type -> returns false
        assertFalse(aliceAppointment.equals(5));

        // different Appointment -> returns false
        assertFalse(aliceAppointment.equals(new CsvAdaptedAppointment(BENSON_APPOINTMENT)));

        // different date -> returns false
        Appointment editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(LocalDate.of(2010, 10, 10)).build();
        assertFalse(aliceAppointment.equals(new CsvAdaptedAppointment(editedOne)));

        // different time -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withTime(LocalTime.of(15, 15)).build();
        assertFalse(aliceAppointment.equals(new CsvAdaptedAppointment(editedOne)));

        // different patient -> returns false
        editedOne = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withPatient(BOB).build();
        assertFalse(aliceAppointment.equals(new CsvAdaptedAppointment(editedOne)));
    }
}

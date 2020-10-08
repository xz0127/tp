package seedu.address.testutil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Nric;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment APPOINTMENT_ONE = new AppointmentBuilder()
            .withDate(LocalDate.of(2020, 1, 1))
            .withStartTime(LocalTime.of(9, 0))
            .withPatientId(new Nric("S9234567A")).build();
    public static final Appointment APPOINTMENT_TWO = new AppointmentBuilder()
            .withDate(LocalDate.of(2020, 5, 12))
            .withStartTime(LocalTime.of(11, 30))
            .withPatientId(new Nric("T0034567B")).build();
    public static final Appointment APPOINTMENT_THREE = new AppointmentBuilder()
            .withDate(LocalDate.of(2020, 1, 1))
            .withStartTime(LocalTime.of(20, 0))
            .withPatientId(new Nric("S9734567H")).build();
    public static final Appointment APPOINTMENT_FOUR = new AppointmentBuilder()
            .withDate(LocalDate.of(2020, 5, 22))
            .withStartTime(LocalTime.of(14, 0))
            .withPatientId(new Nric("T0234567K")).build();
    public static final Appointment APPOINTMENT_FIVE = new AppointmentBuilder()
            .withDate(LocalDate.of(2020, 5, 22))
            .withStartTime(LocalTime.of(15, 0))
            .withPatientId(new Nric("S9234567A")).build();
    public static final Appointment APPOINTMENT_SIX = new AppointmentBuilder()
            .withDate(LocalDate.of(2020, 4, 1))
            .withStartTime(LocalTime.of(11, 30))
            .withPatientId(new Nric("T0034567B")).build();

    private TypicalAppointments() {
    } // prevents instantiation

    //     /**
    //      * Returns an {@code AppointmentBook} with all the typical Appointments.
    //      */
    //     public static AppointmentBook getTypicalAppointmentBook() {
    //         AppointmentBook ab = new AppointmentBook();
    //         for (Appointment Appointment : getTypicalAppointments()) {
    //             ab.addAppointment(Appointment);
    //         }
    //         return ab;
    //     }

    public static List<Appointment> getTypicalAppointments() {
        return new ArrayList<>(Arrays.asList(
                APPOINTMENT_ONE, APPOINTMENT_TWO, APPOINTMENT_THREE,
                APPOINTMENT_FOUR, APPOINTMENT_FIVE, APPOINTMENT_SIX));
    }
}

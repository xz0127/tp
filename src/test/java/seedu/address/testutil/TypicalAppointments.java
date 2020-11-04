package seedu.address.testutil;

import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.HOON;
import static seedu.address.testutil.TypicalPatients.IDA;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.address.model.AppointmentBook;
import seedu.address.model.appointment.Appointment;

/**
 * A utility class containing a list of {@code Appointment} objects to be used in tests.
 */
public class TypicalAppointments {

    public static final Appointment ALICE_APPOINTMENT = new AppointmentBuilder()
            .withDate(LocalDate.of(2050, 1, 1))
            .withTime(LocalTime.of(9, 0))
            .withPatient(ALICE).build();
    public static final Appointment BENSON_APPOINTMENT = new AppointmentBuilder()
            .withDate(LocalDate.of(2050, 5, 12))
            .withTime(LocalTime.of(11, 30))
            .withPatient(BENSON).build();
    public static final Appointment CARL_APPOINTMENT = new AppointmentBuilder()
            .withDate(LocalDate.of(2050, 1, 1))
            .withTime(LocalTime.of(20, 0))
            .withPatient(CARL).build();
    public static final Appointment ALICE_APPOINTMENT_2 = new AppointmentBuilder()
            .withDate(LocalDate.of(2050, 5, 22))
            .withTime(LocalTime.of(15, 0))
            .withPatient(ALICE).build();
    public static final Appointment BENSON_APPOINTMENT_2 = new AppointmentBuilder()
            .withDate(LocalDate.of(2050, 4, 1))
            .withTime(LocalTime.of(11, 30))
            .withPatient(BENSON).build();
    public static final Appointment CARL_APPOINTMENT_2 = new AppointmentBuilder()
            .withDate(LocalDate.of(2050, 5, 22))
            .withTime(LocalTime.of(14, 0))
            .withPatient(CARL).build();

    // Manually added
    public static final Appointment HOON_APPOINTMENT = new AppointmentBuilder().withPatient(HOON)
            .withDate(LocalDate.of(2050, 2, 5))
            .withTime(LocalTime.of(13, 0)).build();
    public static final Appointment IDA_APPOINTMENT = new AppointmentBuilder().withPatient(IDA)
            .withDate(LocalDate.of(2050, 2, 5))
            .withTime(LocalTime.of(19, 30)).build();

    private TypicalAppointments() {
    } // prevents instantiation

    /**
     * Returns an {@code AppointmentBook} with all the typical Appointments.
     */
    public static AppointmentBook getTypicalAppointmentBook() {
        AppointmentBook ab = new AppointmentBook();
        for (Appointment appointment : getTypicalAppointments()) {
            ab.addAppointment(appointment);
        }
        return ab;
    }

    /**
     * Returns an {@code AppointmentBook} with all the typical Appointments happening on 1-Jan-2050.
     */
    public static AppointmentBook getSecondTypicalAppointmentBook() {
        AppointmentBook ab = new AppointmentBook();
        for (Appointment appointment : getSecondTypicalAppointments()) {
            ab.addAppointment(appointment);
        }
        return ab;
    }

    /**
     * Returns an {@code AppointmentBook} with all the typical Appointments happening on 22-May-2050.
     */
    public static AppointmentBook getThirdTypicalAppointmentBook() {
        AppointmentBook ab = new AppointmentBook();
        for (Appointment appointment : getThirdTypicalAppointments()) {
            ab.addAppointment(appointment);
        }
        return ab;
    }

    public static List<Appointment> getTypicalAppointments() {
        List<Appointment> appointmentList = Arrays.asList(
                ALICE_APPOINTMENT, BENSON_APPOINTMENT, CARL_APPOINTMENT,
                CARL_APPOINTMENT_2, ALICE_APPOINTMENT_2, BENSON_APPOINTMENT_2
        );
        Collections.sort(appointmentList);
        return appointmentList;
    }

    public static List<Appointment> getSecondTypicalAppointments() {
        List<Appointment> appointmentList = Arrays.asList(
            ALICE_APPOINTMENT, CARL_APPOINTMENT
        );
        Collections.sort(appointmentList);
        return appointmentList;
    }

    public static List<Appointment> getThirdTypicalAppointments() {
        List<Appointment> appointmentList = Arrays.asList(
            ALICE_APPOINTMENT_2, CARL_APPOINTMENT_2
        );
        Collections.sort(appointmentList);
        return appointmentList;
    }
}

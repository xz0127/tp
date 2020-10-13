package seedu.address.testutil;

import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BENSON;
import static seedu.address.testutil.TypicalPatients.CARL;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AppointmentBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.AppointmentComparator;

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

    public static List<Appointment> getTypicalAppointments() {
        List<Appointment> appointmentList = Arrays.asList(
                ALICE_APPOINTMENT, BENSON_APPOINTMENT, CARL_APPOINTMENT,
                CARL_APPOINTMENT_2, ALICE_APPOINTMENT_2, BENSON_APPOINTMENT_2
        );
        appointmentList.sort(new AppointmentComparator());
        return appointmentList;
    }
}

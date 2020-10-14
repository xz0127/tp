package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.AppointmentBuilder;

public class AppointmentComparatorTest {
    private final AppointmentComparator comparator = new AppointmentComparator();

    @Test
    public void compare_appointmentIsBefore_returnNegative() {
        // different appointment date
        Appointment appointmentBefore = new AppointmentBuilder()
                .withDate(LocalDate.of(2020, 2, 2)).build();
        Appointment appointmentAfter = new AppointmentBuilder()
                .withDate(LocalDate.of(2020, 2, 3)).build();
        assertEquals(-1, comparator.compare(appointmentBefore, appointmentAfter));

        // different appointment time, same date (not overlapping)
        appointmentBefore = new AppointmentBuilder()
                .withTime(LocalTime.of(9, 0), LocalTime.of(10, 0)).build();
        appointmentAfter = new AppointmentBuilder()
                .withTime(LocalTime.of(10, 30), LocalTime.of(11, 0)).build();
        assertEquals(-1, comparator.compare(appointmentBefore, appointmentAfter));
    }

    @Test
    public void compare_appointmentIsAfter_returnPositive() {
        // different appointment date
        Appointment appointmentAfter = new AppointmentBuilder()
                .withDate(LocalDate.of(2020, 2, 3)).build();
        Appointment appointmentBefore = new AppointmentBuilder()
                .withDate(LocalDate.of(2020, 2, 2)).build();
        assertEquals(1, comparator.compare(appointmentAfter, appointmentBefore));

        // different appointment time, same date (not overlapping)
        appointmentAfter = new AppointmentBuilder()
                .withTime(LocalTime.of(10, 0), LocalTime.of(11, 0)).build();
        appointmentBefore = new AppointmentBuilder()
                .withTime(LocalTime.of(9, 0), LocalTime.of(10, 0)).build();
        assertEquals(1, comparator.compare(appointmentAfter, appointmentBefore));
    }

    @Test
    public void compare_appointmentIsOverlapping_returnZero() {
        // different appointment time, but overlapping
        Appointment appointmentBefore = new AppointmentBuilder()
                .withTime(LocalTime.of(17, 0), LocalTime.of(18, 0)).build();
        Appointment appointmentAfter = new AppointmentBuilder()
                .withTime(LocalTime.of(17, 30), LocalTime.of(18, 40)).build();
        assertEquals(0, comparator.compare(appointmentBefore, appointmentAfter));

        // different appointment time, but overlapping
        appointmentBefore = new AppointmentBuilder()
                .withTime(LocalTime.of(17, 0), LocalTime.of(19, 0)).build();
        appointmentAfter = new AppointmentBuilder()
                .withTime(LocalTime.of(17, 30), LocalTime.of(18, 30)).build();
        assertEquals(0, comparator.compare(appointmentBefore, appointmentAfter));

        // different appointment time, but overlapping
        assertEquals(0, comparator.compare(appointmentAfter, appointmentBefore));

        // same appointment time
        assertEquals(0, comparator.compare(appointmentAfter, appointmentAfter));
        assertEquals(0, comparator.compare(appointmentBefore, appointmentBefore));
    }
}

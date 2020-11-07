package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.model.appointment.Time;
import seedu.address.model.interval.PointOfTimeType;
import seedu.address.model.interval.TimeInterval;
import seedu.address.model.interval.TimeIntervalList;
import seedu.address.testutil.TimeIntervalListBuilder;
import seedu.address.testutil.TypicalAppointments;
import seedu.address.testutil.TypicalTimeIntervals;

public class ScheduleManagerTest {

    private ScheduleManager scheduleManager = new ScheduleManager(
        TypicalAppointments.getSecondTypicalAppointmentBook().getAppointmentList(), false);

    private ScheduleManager secondScheduleManager = new ScheduleManager(
        TypicalAppointments.getThirdTypicalAppointmentBook().getAppointmentList(), false);

    private TimeIntervalList intervals = TypicalTimeIntervals.getTypicalTimeIntervalList();

    @Test
    public void isBeforeOperationHour() {

        // null time check
        assertThrows(NullPointerException.class, () -> scheduleManager.isBeforeOperationHour(null));

        // input time is before opening time --> true
        assertTrue(scheduleManager.isBeforeOperationHour(LocalTime.of(7, 0)));
        assertTrue(scheduleManager.isBeforeOperationHour(LocalTime.of(7, 59)));

        // input time is equal to opening time --> false
        assertFalse(scheduleManager.isBeforeOperationHour(LocalTime.of(8, 0)));

        // input time is after opening time --> false
        assertFalse(scheduleManager.isBeforeOperationHour(LocalTime.of(8, 1)));
        assertFalse(scheduleManager.isBeforeOperationHour(LocalTime.of(12, 0)));
    }

    @Test
    public void isAfterOperationHour() {

        // null time check
        assertThrows(NullPointerException.class, () -> scheduleManager.isAfterOperationHour(null));

        // input time is after closing time --> true
        assertTrue(scheduleManager.isAfterOperationHour(LocalTime.of(22, 1)));
        assertTrue(scheduleManager.isAfterOperationHour(LocalTime.of(23, 59)));

        // input time is equal to closing time --> false
        assertFalse(scheduleManager.isAfterOperationHour(LocalTime.of(22, 0)));

        // input time is before closing time --> false
        assertFalse(scheduleManager.isAfterOperationHour(LocalTime.of(8, 0)));
        assertFalse(scheduleManager.isAfterOperationHour(LocalTime.of(12, 0)));
    }

    @Test
    public void isWithinOperationHour() {

        // null time check
        assertThrows(NullPointerException.class, () -> scheduleManager.isWithinOperationHour(null));

        // input time is within operation time --> true
        assertTrue(scheduleManager.isWithinOperationHour(LocalTime.of(8, 0)));
        assertTrue(scheduleManager.isWithinOperationHour(LocalTime.of(8, 1)));
        assertTrue(scheduleManager.isWithinOperationHour(LocalTime.of(12, 0)));
        assertTrue(scheduleManager.isWithinOperationHour(LocalTime.of(17, 30)));
        assertTrue(scheduleManager.isWithinOperationHour(LocalTime.of(21, 59)));
        assertTrue(scheduleManager.isWithinOperationHour(LocalTime.of(22, 0)));

        // input time is after closing time --> false
        assertFalse(scheduleManager.isWithinOperationHour(LocalTime.of(22, 1)));
        assertFalse(scheduleManager.isWithinOperationHour(LocalTime.of(23, 59)));

        // input time is before opening time --> false
        assertFalse(scheduleManager.isWithinOperationHour(LocalTime.of(7, 59)));
        assertFalse(scheduleManager.isWithinOperationHour(LocalTime.of(0, 0)));
    }

    @Test
    public void constructOperationTimeIntervals_correctValue() {
        TimeIntervalList timeIntervalListOne = scheduleManager.constructOperationTimeIntervals();
        TimeIntervalList ls = new TimeIntervalList();
        ls.add(new TimeInterval(
            new Time(8, 0), new Time(22, 0)));
        TimeIntervalList timeIntervalListTwo = new TimeIntervalListBuilder(ls).build();
        assertTrue(timeIntervalListOne.equalsTo(timeIntervalListTwo));
    }

    @Test
    public void constructAppointmentTimeInterval_correctValue() {
        TimeIntervalList timeIntervalListOne = scheduleManager.constructAppointmentTimeInterval();
        TimeIntervalList lsOne = new TimeIntervalList();
        lsOne.add(new TimeInterval(
            new Time(9, 0), new Time(10, 0)));
        lsOne.add(new TimeInterval(
            new Time(20, 0), new Time(21, 0)));
        TimeIntervalList timeIntervalListTwo = new TimeIntervalListBuilder(lsOne).build();
        assertTrue(timeIntervalListOne.equalsTo(timeIntervalListTwo));

        TimeIntervalList timeIntervalListThree = secondScheduleManager.constructAppointmentTimeInterval();
        TimeIntervalList lsTwo = new TimeIntervalList();
        lsTwo.add(new TimeInterval(
            new Time(14, 0), new Time(15, 0)));
        lsTwo.add(new TimeInterval(
            new Time(15, 0), new Time(16, 0)));
        TimeIntervalList timeIntervalListFour = new TimeIntervalListBuilder(lsTwo).build();
        assertTrue(timeIntervalListThree.equalsTo(timeIntervalListFour));
    }

    @Test
    public void annotateOperationTimeInterval_correctValue() {
        TimeIntervalList ls = new TimeIntervalList();
        ls.add(new TimeInterval(
            new Time(8, 0), new Time(22, 0)));
        TimeIntervalList timeIntervalList = new TimeIntervalListBuilder(ls).build();
        scheduleManager.annotateOperationTimeInterval(timeIntervalList);
        assertTrue(scheduleManager
            .getAnnotatedPointList().get(0).getType().equals(PointOfTimeType.TimeIntervalStart));
        assertTrue(scheduleManager
            .getAnnotatedPointList().get(1).getType().equals(PointOfTimeType.TimeIntervalEnd));
    }
    @Test
    public void getNextAvailableSlot_correctValue() {
        TimeInterval interval = TypicalTimeIntervals.INTERVAL_ONE;
        assertEquals(scheduleManager.getNextAvailableSlot(intervals), interval.toString());
    }

    @Test
    public void findFreeSlot_correctValue() {
        String actualMessage = scheduleManager.findFreeSlots();
        TimeIntervalList timeIntervalList = new TimeIntervalList();
        timeIntervalList.add(new TimeInterval(new Time(8, 0), new Time(9, 0)));
        timeIntervalList.add(new TimeInterval(new Time(10, 0), new Time(20, 0)));
        timeIntervalList.add(new TimeInterval(new Time(21, 0), new Time(22, 0)));
        String expectedMessage = timeIntervalList.toString();
        expectedMessage += Messages.MESSAGE_NEXT_AVAILABLE_TIME_SLOT;
        expectedMessage += new TimeInterval(new Time(8, 0), new Time(9, 0)).toString();
        assertEquals(actualMessage, expectedMessage);

        String actualMessageTwo = secondScheduleManager.findFreeSlots();
        TimeIntervalList timeIntervalListTwo = new TimeIntervalList();
        timeIntervalListTwo.add(new TimeInterval(new Time(8, 0), new Time(14, 0)));
        timeIntervalListTwo.add(new TimeInterval(new Time(16, 0), new Time(22, 0)));
        String expectedMessageTwo = timeIntervalListTwo.toString();
        expectedMessageTwo += Messages.MESSAGE_NEXT_AVAILABLE_TIME_SLOT;
        expectedMessageTwo += new TimeInterval(new Time(8, 0), new Time(9, 0)).toString();
        assertEquals(actualMessageTwo, expectedMessageTwo);
    }
}

package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.Time;
import seedu.address.model.interval.PointOfTimeType;
import seedu.address.model.interval.TimeInterval;
import seedu.address.model.interval.TimeIntervalList;
import seedu.address.testutil.TimeIntervalListBuilder;
import seedu.address.testutil.TypicalAppointments;

public class ScheduleManagerTest {

    private ScheduleManager scheduleManager = new ScheduleManager(
        TypicalAppointments.getSecondTypicalAppointmentBook().getAppointmentList());

    @Test
    public void constructOperationTimeIntervals_correctValue() {
        TimeIntervalList timeIntervalListOne = scheduleManager.constructOperationTimeIntervals();
        TimeIntervalListBuilder timeIntervalListBuilder = new TimeIntervalListBuilder();
        TimeIntervalList timeIntervalListTwo = timeIntervalListBuilder.build();
        timeIntervalListTwo.add(new TimeInterval(
            new Time(8, 0), new Time(22, 0)));
        assertTrue(timeIntervalListOne.equalsTo(timeIntervalListTwo));
    }

    @Test
    public void constructAppointmentTimeInterval_correctValue() {
        TimeIntervalList timeIntervalListOne = scheduleManager.constructAppointmentTimeInterval();
        TimeIntervalListBuilder timeIntervalListBuilder = new TimeIntervalListBuilder();
        TimeIntervalList timeIntervalListTwo = timeIntervalListBuilder.build();
        timeIntervalListTwo.add(new TimeInterval(
            new Time(9, 0), new Time(10, 0)));
        timeIntervalListTwo.add(new TimeInterval(
            new Time(20, 0), new Time(21, 0)));
        timeIntervalListTwo.add(new TimeInterval(
            new Time(11, 30), new Time(12, 30)));
        timeIntervalListTwo.add(new TimeInterval(
            new Time(14, 0), new Time(15, 0)));
        timeIntervalListTwo.add(new TimeInterval(
            new Time(15, 0), new Time(16, 0)));
        assertTrue(timeIntervalListOne.equalsTo(timeIntervalListTwo));
    }

    @Test
    public void annotateOperationTimeInterval_correctValue() {
        TimeIntervalListBuilder timeIntervalListBuilder = new TimeIntervalListBuilder();
        TimeIntervalList timeIntervalList = timeIntervalListBuilder.build();
        timeIntervalList.add(new TimeInterval(
            new Time(8, 0), new Time(22, 0)));
        scheduleManager.annotateOperationTimeInterval(timeIntervalList);
        assertTrue(scheduleManager
            .getAnnotatedPointList().get(0).getType().equals(PointOfTimeType.TimeIntervalStart));
        assertTrue(scheduleManager
            .getAnnotatedPointList().get(1).getType().equals(PointOfTimeType.TimeIntervalEnd));
    }

    @Test
    public void findFreeSlot_correctValue() {
        String actualMessage = scheduleManager.findFreeSlots();
        TimeIntervalList timeIntervalList = new TimeIntervalList();
        timeIntervalList.add(new TimeInterval(new Time(8, 0), new Time(9, 0)));
        timeIntervalList.add(new TimeInterval(new Time(10, 0), new Time(11, 30)));
        timeIntervalList.add(new TimeInterval(new Time(12, 30), new Time(14, 0)));
        timeIntervalList.add(new TimeInterval(new Time(16, 0), new Time(20, 0)));
        timeIntervalList.add(new TimeInterval(new Time(21, 0), new Time(22, 0)));
        String expectedMessage = timeIntervalList.toString();
        assertEquals(actualMessage, expectedMessage);
    }
}

package seedu.address.model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.util.TimeConversionUtil;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Time;
import seedu.address.model.interval.AnnotatedPointOfTime;
import seedu.address.model.interval.PointOfTimeType;
import seedu.address.model.interval.TimeInterval;
import seedu.address.model.interval.TimeIntervalList;

/**
 * Represents a schedule manager which finds available time slots.
 */
public class ScheduleManager {

    //Creates time object based on the opening time.
    public static final Time OPEN_TIME = new Time(Time.OPENING_TIME);
    // Creates time object based on the closing time.
    public static final Time CLOSE_TIME = new Time(Time.CLOSING_TIME);
    // Constructs a time interval based on the opening time and closing time.
    public static final TimeInterval OPERATION_TIME = new TimeInterval(OPEN_TIME, CLOSE_TIME);

    private ArrayList<AnnotatedPointOfTime> annotatedPointList;

    private ObservableList<Appointment> appointmentList;

    private TimeIntervalList availableTimeSlots;

    private boolean isToday;

    /**
     * Constructs a schedule manager to find available time slots.
     *
     * @param appointmentList a list of appointment.
     * @param isToday whether the scheduling is for today.
     */
    public ScheduleManager(List<Appointment> appointmentList, boolean isToday) {
        this.annotatedPointList = new ArrayList<>();
        this.availableTimeSlots = new TimeIntervalList();
        this.appointmentList = (ObservableList<Appointment>) appointmentList;
        this.isToday = isToday;
    }

    public ArrayList<AnnotatedPointOfTime> getAnnotatedPointList() {
        return annotatedPointList;
    }

    /**
     * Checks whether {@code time} is within operation hours.
     *
     * @return true if {@code time} is within operation hours, false otherwise.
     */
    public boolean isWithinOperationHour(LocalTime time) {
        return !isBeforeOperationHour(time) && !isAfterOperationHour(time);
    }

    /**
     * Checks whether {@code time} is before opening hour.
     *
     * @return true is {@code time} is before opening hour, false otherwise.
     */
    public boolean isBeforeOperationHour(LocalTime time) {
        return time.isBefore(OPEN_TIME.getTime());
    }

    /**
     * Checks whether {@code time} is after closing hour.
     *
     * @return true is {@code time} is after closing hour, false otherwise.
     */
    public boolean isAfterOperationHour(LocalTime time) {
        return time.isAfter(CLOSE_TIME.getTime());
    }

    /**
     * Constructs the operation time interval based on the operation time of the clinic.
     *
     * @return a TimeIntervalList containing the operation time intervals of the clinic.
     */
    public TimeIntervalList constructOperationTimeIntervals() {
        ArrayList<TimeInterval> operationTimeIntervals = new ArrayList<>();
        LocalTime currentTime = LocalTime.now();
        if (isToday && isWithinOperationHour(currentTime)) {
            Time currTime = new Time(currentTime);
            TimeInterval todayInterval = new TimeInterval(currTime, CLOSE_TIME);
            operationTimeIntervals.add(todayInterval);
        } else if (!(isToday && isAfterOperationHour(currentTime))) {
            operationTimeIntervals.add(OPERATION_TIME);
        }
        return new TimeIntervalList(operationTimeIntervals);
    }

    /**
     * Constructs the appointment time intervals based on the appointment time on a specific date.
     *
     * @return a TimeIntervalList containing the appointment time intervals on a specific date.
     */
    public TimeIntervalList constructAppointmentTimeInterval() {
        ArrayList<TimeInterval> appointmentTimeIntervals = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            TimeInterval appointmentInterval = new TimeInterval(appointment.getStartTime(), appointment.getEndTime());
            appointmentTimeIntervals.add(appointmentInterval);
        }
        return new TimeIntervalList(appointmentTimeIntervals);
    }

    /**
     * Add a list of annotated points to the annotated point list based on
     * the opening and closing time interval of the clinic.
     *
     * @param operationTimeIntervals list of time intervals of clinic operation time.
     */
    public void annotateOperationTimeInterval(TimeIntervalList operationTimeIntervals) {
        for (TimeInterval operationInterval : operationTimeIntervals.getTimeIntervals()) {
            annotatedPointList.add(new AnnotatedPointOfTime(
                TimeConversionUtil.convertTimeToInt(
                    operationInterval.getStartTime()), PointOfTimeType.TimeIntervalStart));

            annotatedPointList.add(new AnnotatedPointOfTime(
                TimeConversionUtil.convertTimeToInt(
                    operationInterval.getEndTime()), PointOfTimeType.TimeIntervalEnd));
        }
    }

    /**
     * Adds a list of annotated points to the annotated point list based on the appointments time.
     *
     * @param appointmentTimeIntervals a list of appointment time intervals.
     */
    public void annotateAppointmentTime(TimeIntervalList appointmentTimeIntervals) {
        for (TimeInterval appointmentTimeInterval : appointmentTimeIntervals.getTimeIntervals()) {
            annotatedPointList.add(new AnnotatedPointOfTime(
                TimeConversionUtil.convertTimeToInt(
                    appointmentTimeInterval.getStartTime()), PointOfTimeType.AppointmentStart));

            annotatedPointList.add(new AnnotatedPointOfTime(
                TimeConversionUtil.convertTimeToInt(
                    appointmentTimeInterval.getEndTime()), PointOfTimeType.AppointmentEnd));
        }
    }

    /**
     * Arranges the list of annotated points for scheduling.
     */
    public void queueAnnotatedPoints() {
        // sort all the points in the list
        Collections.sort(annotatedPointList);
        //return annotatedPointList;
    }

    /**
     * Finds non-overlapping intervals in a queue of annotated points.
     *
     * @param queue queue of annotated points.
     * @return a TimeIntervalList of intervals.
     */
    public TimeIntervalList findNonOverlappingIntervals(ArrayList<AnnotatedPointOfTime> queue) {
        ArrayList<TimeInterval> nonOverLappingIntervals = new ArrayList<>();

        // iterate over the queue
        boolean isNoneOverlapping = false;
        boolean isAppointment = false;
        int intervalStart = 0;

        for (AnnotatedPointOfTime point : queue) {
            switch (point.getType()) {
            case TimeIntervalStart:
                if (!isAppointment) {
                    intervalStart = point.getTimeValue();
                }
                isNoneOverlapping = true;
                break;

            case TimeIntervalEnd:
                if (!isAppointment) {
                    nonOverLappingIntervals.add(new TimeInterval(
                        TimeConversionUtil.convertIntToTime(intervalStart),
                        TimeConversionUtil.convertIntToTime(point.getTimeValue())));
                }
                isNoneOverlapping = false;
                break;

            case AppointmentStart:
                if (isNoneOverlapping) {
                    nonOverLappingIntervals.add(new TimeInterval(
                        TimeConversionUtil.convertIntToTime(intervalStart),
                        TimeConversionUtil.convertIntToTime(point.getTimeValue())));
                }
                isAppointment = true;
                break;

            case AppointmentEnd:
                if (isNoneOverlapping) {
                    intervalStart = point.getTimeValue();
                }
                isAppointment = false;
                break;

            default:
                break;
            }
        }
        return new TimeIntervalList(nonOverLappingIntervals);
    }

    /**
     * Finds the available time slots based on the appointment list of a specified date.
     *
     * @return a string message of available time intervals and the upcoming available time slot on a specified date.
     */
    public String findFreeSlots() {
        TimeIntervalList operationTimeIntervals = constructOperationTimeIntervals();
        TimeIntervalList appointmentTimeIntervals = constructAppointmentTimeInterval();

        annotateOperationTimeInterval(operationTimeIntervals);
        annotateAppointmentTime(appointmentTimeIntervals);

        queueAnnotatedPoints();
        availableTimeSlots = findNonOverlappingIntervals(annotatedPointList);
        TimeIntervalList noEmptyIntervalList = availableTimeSlots.clearZeroIntervals();
        String message = noEmptyIntervalList.toString();
        message += Messages.MESSAGE_NEXT_AVAILABLE_TIME_SLOT;
        message += getNextAvailableSlot(noEmptyIntervalList);
        return message;
    }

    /**
     * Finds the next available time slot for 1 hour appointment on a specified date.
     *
     * @return a string message of the next available time.
     */
    public String getNextAvailableSlot(TimeIntervalList availableTimeSlots) {
        for (TimeInterval timeInterval : availableTimeSlots.getTimeIntervals()) {
            if (timeInterval.isAtLeastOneHour()) {
                Time startTime = timeInterval.getStartTime();
                Time endTime = new Time(startTime.getTime().plusHours(1));
                TimeInterval nextInterval = new TimeInterval(startTime, endTime);
                return nextInterval.toString();
            }
        }
        return "Sorry we cannot find any slot available for 1 hour appointment.";
    }
}

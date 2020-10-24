package seedu.address.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.collections.ObservableList;
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

    //    public static final TimeConversionUtil TIME_CONVERTER = new TimeConversionUtil();

    private ArrayList<AnnotatedPointOfTime> annotatedPointList;

    private ObservableList<Appointment> appointmentList;

    /**
     * Constructs a schedule manager to find available time slots.
     *
     * @param appointmentList a list of appointment.
     */
    public ScheduleManager(List<Appointment> appointmentList) {
        this.annotatedPointList = new ArrayList<>();
        this.appointmentList = (ObservableList<Appointment>) appointmentList;
    }

    /**
     * Constructs the operation time interval based on the operation time of the clinic.
     *
     * @return a TimeIntervalList containing the operation time intervals of the clinic.
     */
    public TimeIntervalList constructOperationTimeIntervals() {
        ArrayList<TimeInterval> operationTimeIntervals = new ArrayList<>();
        operationTimeIntervals.add(OPERATION_TIME);
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
     * Add a list of annotated points to the annotated point list based on the appointments time.
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
     * Arrange the list of annotated points for scheduling.
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
     * @return a string message of available time intervals.
     */
    public String findFreeSlots() {
        TimeIntervalList operationTimeIntervals = constructOperationTimeIntervals();
        TimeIntervalList appointmentTimeIntervals = constructAppointmentTimeInterval();

        annotateOperationTimeInterval(operationTimeIntervals);
        annotateAppointmentTime(appointmentTimeIntervals);

        queueAnnotatedPoints();
        TimeIntervalList availableTimeSlots = findNonOverlappingIntervals(annotatedPointList);
        return availableTimeSlots.clearZeroIntervals().toString();
    }
}

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
     * @return a list containing the operation time intervals of the clinic.
     */
    public ArrayList<TimeInterval> constructOperationTimeIntervals() {
        ArrayList<TimeInterval> operationTimeIntervals = new ArrayList<>();
        operationTimeIntervals.add(OPERATION_TIME);
        return operationTimeIntervals;
    }

    /**
     * Constructs the appointment time intervals based on the appointment time on a specific date.
     *
     * @return a list containing the appointment time intervals on a specific date.
     */
    public ArrayList<TimeInterval> constructAppointmentTimeInterval() {
        ArrayList<TimeInterval> appointmentTimeIntervals = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            TimeInterval appointmentInterval = new TimeInterval(appointment.getStartTime(), appointment.getEndTime());
            appointmentTimeIntervals.add(appointmentInterval);
        }
        return appointmentTimeIntervals;
    }

    /**
     * Add a list of annotated points to the annotated point list based on
     * the opening and closing time interval of the clinic.
     *
     * @param operationTimeIntervals list of time intervals of clinic operation time.
     */
    public void annotateOperationTimeInterval(ArrayList<TimeInterval> operationTimeIntervals) {
        for (TimeInterval operationInterval : operationTimeIntervals) {
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
    public void annotateAppointmentTime(ArrayList<TimeInterval> appointmentTimeIntervals) {
        for (TimeInterval appointmentTimeInterval : appointmentTimeIntervals) {
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
     * @return an array list of intervals.
     */
    public ArrayList<TimeInterval> findNonOverlappingIntervals(ArrayList<AnnotatedPointOfTime> queue) {
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
        return nonOverLappingIntervals;
    }

    /**
     * Clears the empty intervals in the list of time intervals.
     *
     * @param timeIntervals lists of time intervals.
     * @return a list of time intervals with no empty intervals.
     */
    public ArrayList<TimeInterval> clearZeroIntervals(ArrayList<TimeInterval> timeIntervals) {
        ArrayList<TimeInterval> noZeroIntervalList = new ArrayList<>();
        for (TimeInterval timeInterval : timeIntervals) {
            if (!timeInterval.isZeroInterval()) {
                noZeroIntervalList.add(timeInterval);
            }
        }
        return noZeroIntervalList;
    }

    /**
     * Finds the available time slots based on the appointment list of a specified date.
     *
     * @return List of available time intervals.
     */
    public ArrayList<TimeInterval> findFreeSlots() {
        ArrayList<TimeInterval> operationTimeIntervals = constructOperationTimeIntervals();
        ArrayList<TimeInterval> appointmentTimeIntervals = constructAppointmentTimeInterval();

        annotateOperationTimeInterval(operationTimeIntervals);
        annotateAppointmentTime(appointmentTimeIntervals);

        queueAnnotatedPoints();
        ArrayList<TimeInterval> availableTimeSlots = findNonOverlappingIntervals(annotatedPointList);
        return clearZeroIntervals(availableTimeSlots);
    }

    /**
     * Output the free time slots as a string message.
     *
     * @param freeTimeIntervals list of available time intervals.
     * @return a string message of the free time slots.
     */
    public String outputAsString(ArrayList<TimeInterval> freeTimeIntervals) {
        StringBuilder message = new StringBuilder();
        for (TimeInterval freeTimeInterval : freeTimeIntervals) {
            message.append(freeTimeInterval.toString()).append("\n");
        }
        return message.toString();
    }
}

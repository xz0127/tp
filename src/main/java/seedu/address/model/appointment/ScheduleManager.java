package seedu.address.model.appointment;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.commons.util.TimeConversionUtil;
import seedu.address.model.interval.AnnotatedPointOfTime;
import seedu.address.model.interval.PointOfTimeType;
import seedu.address.model.interval.TimeInterval;

/**
 * Represents a schedule manager which finds available time slots.
 */
public class ScheduleManager {
    //    // Creates time object based on the opening time.
    //    public static final Time OPEN_TIME = new Time(Time.OPENING_TIME);
    //    // Creates time object based on the closing time.
    //    public static final Time CLOSE_TIME = new Time(Time.CLOSING_TIME);
    //    // Constructs a time interval based on the opening time and closing time.
    //    public static final TimeInterval OPERATION_TIME = new TimeInterval(OPEN_TIME, CLOSE_TIME);

    //    public static final TimeConversionUtil TIME_CONVERTER = new TimeConversionUtil();

    private ArrayList<AnnotatedPointOfTime> annotatedPointList;

    /**
     * Constructs a schedule manager to find available time slots.
     */
    public ScheduleManager() {
        this.annotatedPointList = new ArrayList<>();
    }

    /**
     * Add a list of annotated points to the annotated point list based on the opening and closing time of the clinic.
     */
    public void annotateOperationTimeInterval(ArrayList<TimeInterval> operationTimeList) {
        for (TimeInterval operationTimeInterval : operationTimeList) {
            annotatedPointList.add(new AnnotatedPointOfTime(
                TimeConversionUtil.convertTimeToInt(
                    operationTimeInterval.getStartTime()), PointOfTimeType.TimeIntervalStart));

            annotatedPointList.add(new AnnotatedPointOfTime(
                TimeConversionUtil.convertTimeToInt(
                    operationTimeInterval.getEndTime()), PointOfTimeType.TimeIntervalEnd));
        }
    }

    /**
     * Add a list of annotated points to the annotated point list based on the appointments time.
     */
    public void annotateAppointmentTime(ArrayList<TimeInterval> appointmentTimeList) {
        for (TimeInterval appointmentTimeInterval : appointmentTimeList) {
            annotatedPointList.add(new AnnotatedPointOfTime(
                TimeConversionUtil.convertTimeToInt(
                    appointmentTimeInterval.getStartTime()), PointOfTimeType.TimeIntervalStart));

            annotatedPointList.add(new AnnotatedPointOfTime(
                TimeConversionUtil.convertTimeToInt(
                    appointmentTimeInterval.getEndTime()), PointOfTimeType.TimeIntervalEnd));
        }
    }

    /**
     * Arrange the list of annotated points for scheduling.
     */
    public ArrayList<AnnotatedPointOfTime> queueAnnotatedPoints() {
        // sort all the points in the list
        Collections.sort(annotatedPointList);
        return annotatedPointList;
    }

    /**
     * Finds non-overlapping intervals in a queue of annotated points.
     * @param queue queue of annotated points.
     * @return an array list of intervals.
     */
    public ArrayList<TimeInterval> findFreeSlotIntervals(ArrayList<AnnotatedPointOfTime> queue) {
        ArrayList<TimeInterval> nonOverLappingIntervals = new ArrayList<>();

        // iterate over the queue
        boolean isFreeSlot = false;
        boolean isAppointment = false;
        int intervalStart = 0;

        for (AnnotatedPointOfTime point : queue) {
            switch (point.getType()) {
            case TimeIntervalStart:
                if (!isAppointment) {
                    intervalStart = point.getTimeValue();
                }
                isFreeSlot = true;
                break;

            case TimeIntervalEnd:
                if (!isAppointment) {
                    nonOverLappingIntervals.add(new TimeInterval(
                        TimeConversionUtil.convertIntToTime(intervalStart),
                        TimeConversionUtil.convertIntToTime(point.getTimeValue())));
                }
                isFreeSlot = false;
                break;

            case AppointmentStart:
                if (isFreeSlot) {
                    nonOverLappingIntervals.add(new TimeInterval(
                        TimeConversionUtil.convertIntToTime(intervalStart),
                        TimeConversionUtil.convertIntToTime(point.getTimeValue())));
                }
                isAppointment = true;
                break;

            case AppointmentEnd:
                if (isFreeSlot) {
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

    //    /**
    //     * Finds free slots in a day. Events are assumed to have a duration of 1 hour.
    //     * @return an array list containing free slot intervals.
    //     */
    //    public ArrayList<Interval> findFreeSlots() {
    //        ArrayList<Interval> interval = new ArrayList<>();
    //        interval.add(new Interval(0, 1439));
    //        ArrayList<Interval> remove = new ArrayList<>();
    //
    //        for (int i = 0; i < this.taskList.getSize(); i++) {
    //            if (this.taskList.get(i) instanceof Event) {
    //                Task event = this.taskList.get(i);
    //                int dayOfYear = ((Event) event).getTime().getDayOfYear();
    //                if (dayOfYear == this.startingTime.getDayOfYear()) {
    //                    LocalDateTime startingTime = ((Event) event).getTime();
    //                    int startPoint = this.timeConverter.convertTimeToInt(startingTime);
    //                    int endPoint = startPoint + 60;
    //                    remove.add(new Interval(startPoint, endPoint));
    //                }
    //            }
    //        }
    //
    //        ArrayList<AnnotatedPoint> queue = initQueue(interval, remove);
    //
    //        return doSweep(queue);
    //   }
}

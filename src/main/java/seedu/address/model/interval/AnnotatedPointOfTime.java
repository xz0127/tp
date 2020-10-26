package seedu.address.model.interval;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a point of time with the annotation of type of this point of time.
 */
public class AnnotatedPointOfTime implements Comparable<AnnotatedPointOfTime> {
    private final int timeValue;
    private final PointOfTimeType type;

    /**
     * Constructs an annotated point of time object.
     *
     * @param timeValue integer value of the point of time.
     * @param type type of the point of time.
     */
    public AnnotatedPointOfTime(int timeValue, PointOfTimeType type) {
        requireAllNonNull(timeValue, type);

        this.timeValue = timeValue;
        this.type = type;
    }

    public int getTimeValue() {
        return timeValue;
    }

    public PointOfTimeType getType() {
        return type;
    }

    /**Compares an annotated point of time to {@code other} based on time value and type of point of time.
     *
     * @param other another annotated point for comparison.
     * @return -1 if this annotated point is prior to other point and 1 if otherwise.
     */
    @Override
    public int compareTo(AnnotatedPointOfTime other) {
        requireAllNonNull(other);

        if (other.timeValue == this.timeValue) {
            return this.type.ordinal() < other.type.ordinal() ? -1 : 1;
        } else {
            return this.timeValue < other.timeValue ? -1 : 1;
        }
    }
}

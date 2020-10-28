package seedu.address.model.interval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AnnotatedPointOfTimeTest {

    public static final AnnotatedPointOfTime POINT_ONE = new AnnotatedPointOfTime(
        100, PointOfTimeType.AppointmentEnd);

    public static final AnnotatedPointOfTime POINT_TWO = new AnnotatedPointOfTime(
        1000, PointOfTimeType.TimeIntervalEnd);

    public static final AnnotatedPointOfTime POINT_THREE = new AnnotatedPointOfTime(
        10, PointOfTimeType.TimeIntervalStart);

    public static final AnnotatedPointOfTime POINT_FOUR = new AnnotatedPointOfTime(
        100, PointOfTimeType.AppointmentStart);

    public static final AnnotatedPointOfTime POINT_FIVE = new AnnotatedPointOfTime(
        100, PointOfTimeType.TimeIntervalStart);

    public static final AnnotatedPointOfTime POINT_SIX = new AnnotatedPointOfTime(
        100, PointOfTimeType.TimeIntervalEnd);

    public static final AnnotatedPointOfTime POINT_SEVEN = new AnnotatedPointOfTime(
        1000, PointOfTimeType.TimeIntervalStart);

    public static final AnnotatedPointOfTime POINT_EIGHT = new AnnotatedPointOfTime(
        10, PointOfTimeType.TimeIntervalEnd);

    @Test
    public void constructor_null_throwsNullPointerException() {

        // both fields empty
        assertThrows(NullPointerException.class, () -> new AnnotatedPointOfTime((Integer) null, null));

        // empty time value
        assertThrows(NullPointerException.class, () -> new AnnotatedPointOfTime(1, null));

        // empty type of point
        assertThrows(NullPointerException.class, ()
            -> new AnnotatedPointOfTime((Integer) null, PointOfTimeType.AppointmentEnd));
    }

    @Test
    public void getTimeValue() {
        assertEquals(POINT_ONE.getTimeValue(), 100);
        assertEquals(POINT_TWO.getTimeValue(), 1000);
        assertEquals(POINT_THREE.getTimeValue(), 10);
    }

    @Test
    public void getType() {
        assertEquals(POINT_ONE.getType(), PointOfTimeType.AppointmentEnd);
        assertEquals(POINT_TWO.getType(), PointOfTimeType.TimeIntervalEnd);
        assertEquals(POINT_THREE.getType(), PointOfTimeType.TimeIntervalStart);
    }

    @Test
    public void compareTo_correctValue() {

        // same value -> compares point type
        assertEquals(POINT_ONE.compareTo(POINT_FOUR), -1);
        assertEquals(POINT_ONE.compareTo(POINT_FIVE), -1);
        assertEquals(POINT_ONE.compareTo(POINT_SIX), 1);
        assertEquals(POINT_FIVE.compareTo(POINT_SIX), 1);
        assertEquals(POINT_FOUR.compareTo(POINT_SIX), 1);

        // same value and point type -> returns 1
        assertEquals(POINT_ONE.compareTo(POINT_ONE), 1);
        assertEquals(POINT_FOUR.compareTo(POINT_FOUR), 1);

        // same point type -> compares value
        assertEquals(POINT_THREE.compareTo(POINT_SEVEN), -1);
        assertEquals(POINT_SIX.compareTo(POINT_EIGHT), 1);

        // different value and point type
        assertEquals(POINT_TWO.compareTo(POINT_THREE), 1);
        assertEquals(POINT_TWO.compareTo(POINT_FOUR), 1);
        assertEquals(POINT_THREE.compareTo(POINT_FOUR), -1);
    }
}

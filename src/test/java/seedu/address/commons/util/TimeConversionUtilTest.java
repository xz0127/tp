package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.util.TimeConversionUtil.convertIntToTime;
import static seedu.address.commons.util.TimeConversionUtil.convertTimeToInt;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.Time;

public class TimeConversionUtilTest {

    // valid local times
    private static final Time OPENING_TIME = new Time(Time.OPENING_TIME);
    private static final Time CLOSING_TIME = new Time(Time.CLOSING_TIME);
    private static final Time OPENING_TIME_PLUS = new Time(8, 1);
    private static final Time CLOSING_TIME_MINUS = new Time(21, 59);
    private static final Time NOON = new Time(12, 0);

    @Test
    public void convertTimeToInt_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> convertTimeToInt(null));
    }

    @Test
    public void convertIntToTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> convertIntToTime((Integer) null));
    }

    @Test
    public void convertIntToTime_correctTime() {

        //normal operating time
        assertEquals(convertIntToTime(1320), CLOSING_TIME);
        assertEquals(convertIntToTime(480), OPENING_TIME);
        assertEquals(convertIntToTime(481), OPENING_TIME_PLUS);
        assertEquals(convertIntToTime(1319), CLOSING_TIME_MINUS);
        assertEquals(convertIntToTime(720), NOON);
    }

    @Test
    public void convertTimeToInt_correctValue() {

        //integer within normal operating time
        assertEquals(convertTimeToInt(CLOSING_TIME), 1320);
        assertEquals(convertTimeToInt(OPENING_TIME), 480);
        assertEquals(convertTimeToInt(OPENING_TIME_PLUS), 481);
        assertEquals(convertTimeToInt(CLOSING_TIME_MINUS), 1319);
        assertEquals(convertTimeToInt(NOON), 720);
    }
}

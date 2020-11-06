package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class TimeParserUtilTest {

    @Test
    void parse_invalidTimeString_throwsParseException() {
        assertThrows(ParseException.class, TimeParserUtil.MESSAGE_EMPTY_TIME, ()
            -> TimeParserUtil.parse("  "));
    }

    @Test
    void parse() throws Exception {
        // null time
        assertThrows(NullPointerException.class, () -> TimeParserUtil.parse(null));

        // formatted time
        assertEquals(LocalTime.of(14, 45), TimeParserUtil.parse("02:45PM"));

        // natural time
        assertEquals(LocalTime.of(8, 0), TimeParserUtil.parse("morning"));
    }

    @Test
    void parseByTimeFormat() {
        // null time
        assertThrows(NullPointerException.class, () -> TimeParserUtil.parseByTimeFormat(null));

        // 24h format
        assertEquals(LocalTime.of(23, 59), TimeParserUtil.parseByTimeFormat("2359"));
        assertEquals(LocalTime.of(2, 11), TimeParserUtil.parseByTimeFormat("0211"));

        // 12h time format with min
        assertEquals(LocalTime.of(0, 55), TimeParserUtil.parseByTimeFormat("12:55am"));
        assertEquals(LocalTime.of(12, 55), TimeParserUtil.parseByTimeFormat("12:55pm"));
        assertEquals(LocalTime.of(2, 35), TimeParserUtil.parseByTimeFormat("02:35AM"));
        assertEquals(LocalTime.of(14, 35), TimeParserUtil.parseByTimeFormat("2:35PM"));
        // with spacing
        assertEquals(LocalTime.of(0, 55), TimeParserUtil.parseByTimeFormat("12:55 am"));
        assertEquals(LocalTime.of(2, 35), TimeParserUtil.parseByTimeFormat("02:35 AM"));

        // 12h time format with no min given
        assertEquals(LocalTime.of(3, 0), TimeParserUtil.parseByTimeFormat("3am"));
        assertEquals(LocalTime.of(15, 0), TimeParserUtil.parseByTimeFormat("3pM"));
        assertEquals(LocalTime.of(10, 0), TimeParserUtil.parseByTimeFormat("10AM"));
        assertEquals(LocalTime.of(22, 0), TimeParserUtil.parseByTimeFormat("10PM"));
        // with spacing
        assertEquals(LocalTime.of(10, 0), TimeParserUtil.parseByTimeFormat("10 AM"));
        assertEquals(LocalTime.of(22, 0), TimeParserUtil.parseByTimeFormat("10 PM"));

        // return null
        assertNull(TimeParserUtil.parseByNaturalTime(""));
    }

    @Test
    void parseByNaturalTime() {
        // null time
        assertThrows(NullPointerException.class, () -> TimeParserUtil.parseByNaturalTime(null));

        // Morning
        assertEquals(LocalTime.of(8, 0), TimeParserUtil.parseByNaturalTime("morning"));
        assertEquals(LocalTime.of(8, 0), TimeParserUtil.parseByNaturalTime("sunrise"));
        assertEquals(LocalTime.of(8, 0), TimeParserUtil.parseByNaturalTime("breakfast"));

        // Noon
        assertEquals(LocalTime.of(12, 0), TimeParserUtil.parseByNaturalTime("NOON"));
        assertEquals(LocalTime.of(12, 0), TimeParserUtil.parseByNaturalTime("afterNoon"));
        assertEquals(LocalTime.of(12, 0), TimeParserUtil.parseByNaturalTime("after-noon"));
        assertEquals(LocalTime.of(12, 0), TimeParserUtil.parseByNaturalTime("lunch"));

        // Evening
        assertEquals(LocalTime.of(19, 0), TimeParserUtil.parseByNaturalTime("eve"));
        assertEquals(LocalTime.of(19, 0), TimeParserUtil.parseByNaturalTime("eVeNiNg"));
        assertEquals(LocalTime.of(19, 0), TimeParserUtil.parseByNaturalTime("dinner"));

        // Night
        assertEquals(LocalTime.of(22, 0), TimeParserUtil.parseByNaturalTime("night"));
        assertEquals(LocalTime.of(22, 0), TimeParserUtil.parseByNaturalTime("bedTime"));
        assertEquals(LocalTime.of(22, 0), TimeParserUtil.parseByNaturalTime("Bed-Time"));

        // Midnight
        assertEquals(LocalTime.of(23, 59), TimeParserUtil.parseByNaturalTime("midnight"));

        // return null
        assertNull(TimeParserUtil.parseByNaturalTime(""));
        assertNull(TimeParserUtil.parseByNaturalTime("abcde"));
    }
}

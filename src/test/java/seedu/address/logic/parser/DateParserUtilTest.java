package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.DayOfWeek;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.exceptions.ParseException;

public class DateParserUtilTest {
    private final int currYear = LocalDate.now().getYear();
    // Default/now --> Monday
    private final LocalDate currDate = LocalDate.of(2020, 9, 7);

    @Test
    void parse_invalidDateString_throwsParseException() {
        assertThrows(ParseException.class, DateParserUtil.MESSAGE_EMPTY_DATE, ()
            -> DateParserUtil.parse(""));
    }

    @Test
    void parse() throws Exception {
        // null date
        assertThrows(NullPointerException.class, () -> DateParserUtil.parse(null));
        assertThrows(NullPointerException.class, () -> DateParserUtil.parse(null, currDate));

        // formatted date
        assertEquals(LocalDate.of(currYear, 8, 12), DateParserUtil.parse("12/08", currDate));

        // natural date
        assertEquals(LocalDate.of(2020, 9, 14), DateParserUtil.parse("monday", currDate));
    }

    @Test
    void parseByDateFormat() {
        // dependent on current year

        // null date
        assertThrows(NullPointerException.class, () -> DateParserUtil.parseByDateFormat(null));

        // d[/][-]M[[/][-]uuuu] --> d/M[/uuuu]
        assertEquals(LocalDate.of(currYear, 8, 12), DateParserUtil.parseByDateFormat("12/08"));
        assertEquals(LocalDate.of(2019, 8, 12), DateParserUtil.parseByDateFormat("12/08/2019"));
        assertEquals(LocalDate.of(currYear, 8, 25), DateParserUtil.parseByDateFormat("25/8"));
        assertEquals(LocalDate.of(2019, 8, 25), DateParserUtil.parseByDateFormat("25/8/2019"));


        // d[/][-]M[[/][-]uuuu] --> d-M[-uuuu]
        assertEquals(LocalDate.of(currYear, 8, 12), DateParserUtil.parseByDateFormat("12-08"));
        assertEquals(LocalDate.of(2019, 8, 12), DateParserUtil.parseByDateFormat("12-08-2019"));
        assertEquals(LocalDate.of(currYear, 8, 12), DateParserUtil.parseByDateFormat("12-8"));
        assertEquals(LocalDate.of(2019, 8, 12), DateParserUtil.parseByDateFormat("12-8-2019"));


        // M[/][-]d[[/][-]uuuu] --> M/d[/uuuu]
        // Must use an obvious month to differentiate month from dayOfMonth
        assertEquals(LocalDate.of(currYear, 12, 25), DateParserUtil.parseByDateFormat("12/25"));
        assertEquals(LocalDate.of(2019, 12, 25), DateParserUtil.parseByDateFormat("12/25/2019"));
        // If month is not differentiable from dayOfMonth
        assertEquals(LocalDate.of(currYear, 5, 12), DateParserUtil.parseByDateFormat("12/5"));
        assertEquals(LocalDate.of(2019, 5, 12), DateParserUtil.parseByDateFormat("12/5/2019"));


        // M[/][-]d[[/][-]uuuu] --> M-d[-uuuu]
        // Must use an obvious month to differentiate month from dayOfMonth
        assertEquals(LocalDate.of(currYear, 12, 25), DateParserUtil.parseByDateFormat("12-25"));
        assertEquals(LocalDate.of(2019, 12, 25), DateParserUtil.parseByDateFormat("12-25-2019"));
        // If month is not differentiable from dayOfMonth
        assertEquals(LocalDate.of(currYear, 5, 12), DateParserUtil.parseByDateFormat("12-5"));
        assertEquals(LocalDate.of(2019, 5, 12), DateParserUtil.parseByDateFormat("12-5-2019"));


        // uuuu[/][-]M[/][-]d --> uuuu/M/d (compulsory year)
        assertEquals(LocalDate.of(2019, 5, 25), DateParserUtil.parseByDateFormat("2019/05/25"));
        assertEquals(LocalDate.of(2020, 5, 25), DateParserUtil.parseByDateFormat("2020/5/25"));


        // uuuu[/][-]M[/][-]d --> uuuu-M-d (compulsory year)
        assertEquals(LocalDate.of(2019, 5, 25), DateParserUtil.parseByDateFormat("2019-05-25"));
        assertEquals(LocalDate.of(2020, 5, 25), DateParserUtil.parseByDateFormat("2020-5-25"));


        // d-MMM[-uuuu] (Short month)
        assertEquals(LocalDate.of(currYear, 8, 12), DateParserUtil.parseByDateFormat("12-Aug"));
        assertEquals(LocalDate.of(2019, 8, 12), DateParserUtil.parseByDateFormat("12-Aug-2019"));
        // Insensitive case
        assertEquals(LocalDate.of(currYear, 2, 12), DateParserUtil.parseByDateFormat("12-FEB"));
        assertEquals(LocalDate.of(2019, 3, 12), DateParserUtil.parseByDateFormat("12-mAR-2019"));

        // d-MMMM[-uuuu] (Long month)
        assertEquals(LocalDate.of(currYear, 8, 12), DateParserUtil.parseByDateFormat("12-August"));
        assertEquals(LocalDate.of(2019, 8, 12), DateParserUtil.parseByDateFormat("12-August-2019"));
        // Insensitive case
        assertEquals(LocalDate.of(currYear, 2, 12), DateParserUtil.parseByDateFormat("12-FEBruarY"));
        assertEquals(LocalDate.of(2019, 3, 12), DateParserUtil.parseByDateFormat("12-mARCh-2019"));

        // return null
        assertNull(DateParserUtil.parseByDateFormat(""));
    }

    @Test
    void parseByNaturalDay() {
        // null date
        assertThrows(NullPointerException.class, () -> DateParserUtil.parseByNaturalDay(null, currDate));

        // today
        assertEquals(LocalDate.of(2020, 9, 7), DateParserUtil.parseByNaturalDay("tdy", currDate));
        assertEquals(LocalDate.of(2020, 9, 7), DateParserUtil.parseByNaturalDay("today", currDate));
        assertEquals(0, DateParserUtil.parseByNaturalDay("TODAY", currDate).compareTo(currDate));

        // tomorrow
        assertEquals(LocalDate.of(2020, 9, 8), DateParserUtil.parseByNaturalDay("tmr", currDate));
        assertEquals(LocalDate.of(2020, 9, 8), DateParserUtil.parseByNaturalDay("tomorrow", currDate));
        assertEquals(1, DateParserUtil.parseByNaturalDay("TOMORROW", currDate).compareTo(currDate));

        // day of weeks
        assertEquals(LocalDate.of(2020, 9, 14), DateParserUtil.parseByNaturalDay("mon", currDate));
        assertEquals(LocalDate.of(2020, 9, 14), DateParserUtil.parseByNaturalDay("MONDAY", currDate));
        assertEquals(DayOfWeek.MONDAY, DateParserUtil.parseByNaturalDay("MONDAY", currDate).getDayOfWeek());

        assertEquals(LocalDate.of(2020, 9, 8), DateParserUtil.parseByNaturalDay("Tues", currDate));
        assertEquals(LocalDate.of(2020, 9, 8), DateParserUtil.parseByNaturalDay("tue", currDate));
        assertEquals(LocalDate.of(2020, 9, 8), DateParserUtil.parseByNaturalDay("tuesday", currDate));
        assertEquals(DayOfWeek.TUESDAY, DateParserUtil.parseByNaturalDay("TUESDAY", currDate).getDayOfWeek());

        assertEquals(LocalDate.of(2020, 9, 9), DateParserUtil.parseByNaturalDay("weD", currDate));
        assertEquals(LocalDate.of(2020, 9, 9), DateParserUtil.parseByNaturalDay("wednesday", currDate));
        assertEquals(DayOfWeek.WEDNESDAY, DateParserUtil.parseByNaturalDay("WEDNESDAY", currDate).getDayOfWeek());

        assertEquals(LocalDate.of(2020, 9, 10), DateParserUtil.parseByNaturalDay("tHu", currDate));
        assertEquals(LocalDate.of(2020, 9, 10), DateParserUtil.parseByNaturalDay("Thurs", currDate));
        assertEquals(LocalDate.of(2020, 9, 10), DateParserUtil.parseByNaturalDay("thursday", currDate));
        assertEquals(DayOfWeek.THURSDAY, DateParserUtil.parseByNaturalDay("THURSDAY", currDate).getDayOfWeek());

        assertEquals(LocalDate.of(2020, 9, 11), DateParserUtil.parseByNaturalDay("fri", currDate));
        assertEquals(LocalDate.of(2020, 9, 11), DateParserUtil.parseByNaturalDay("friday", currDate));
        assertEquals(DayOfWeek.FRIDAY, DateParserUtil.parseByNaturalDay("FRIDAY", currDate).getDayOfWeek());

        assertEquals(LocalDate.of(2020, 9, 12), DateParserUtil.parseByNaturalDay("sat", currDate));
        assertEquals(LocalDate.of(2020, 9, 12), DateParserUtil.parseByNaturalDay("saturday", currDate));
        assertEquals(DayOfWeek.SATURDAY, DateParserUtil.parseByNaturalDay("SATURDAY", currDate).getDayOfWeek());

        assertEquals(LocalDate.of(2020, 9, 13), DateParserUtil.parseByNaturalDay("sun", currDate));
        assertEquals(LocalDate.of(2020, 9, 13), DateParserUtil.parseByNaturalDay("sundaY", currDate));
        assertEquals(DayOfWeek.SUNDAY, DateParserUtil.parseByNaturalDay("SUNDAY", currDate).getDayOfWeek());

        // return null
        assertNull(DateParserUtil.parseByNaturalDay("", currDate));
        assertNull(DateParserUtil.parseByNaturalDay("abcde", currDate));
    }
}

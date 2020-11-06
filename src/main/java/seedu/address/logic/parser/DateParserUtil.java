package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.NaturalDay;

/**
 * Contains utility methods used for parsing date strings in the various *Parser classes.
 * Helper class for {@link ParserUtil#parseDate(String)}.
 */
public class DateParserUtil {
    public static final String MESSAGE_CONSTRAINTS = "The Date input must adhere to our formatting conventions "
            + "or adopt a recognised natural date language.\n"
            + "An example of a properly formatted date includes (but is not limited to): 02 Aug 2020\n"
            + "An example of a natural date language is 'today' or 'wednesday'(any day of the week)"
            + "Please visit our user guide"
            + " for more date/time variants."
            + "\nType help in the input box for the link!";

    public static final String MESSAGE_EMPTY_DATE = "The Date input should not be empty.\n"
            + "You can try entering a date with the format such as:\n"
            + "01 Jan 2000\n"
            + "See more date format on our User Guide.";

    /**
     * List of known date formats that parser accepts.
     **/
    private static final List<DateTimeFormatter> KNOWN_DATE_FORMATS = createDateFormats();

    /**
     * Creates the list of formatter that accepts a specified list of known date patterns.
     * This method should only be used once to initialise the formatters used by the parser.
     *
     * @return list of DateTimeFormatter with acceptable date format.
     */
    private static List<DateTimeFormatter> createDateFormats() {
        // List of acceptable date format with optional year/month
        List<String> knownDatePatterns = Arrays.asList(
                "d[/][-]M[[/][-]uuuu]", "M[/][-]d[[/][-]uuuu]",
                "uuuu[/][-]M[/][-]d", "d[-][ ]MMM[[-][ ]uuuu]",
                "d[-][ ]MMMM[[-][ ]uuuu]"
        );

        LocalDate currTime = LocalDate.now();
        List<DateTimeFormatter> knownFormats = new ArrayList<>();
        // Create a formatter for each known patterns to be used for parsing dates
        for (int i = 0; i < knownDatePatterns.size(); i++) {
            knownFormats.add(new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern(knownDatePatterns.get(i))
                    .parseDefaulting(ChronoField.YEAR, currTime.getYear())
                    .toFormatter()
            );
        }
        return knownFormats;
    }

    /**
     * Parses the {@code String dateString} into the implied {@code LocalDate}.
     *
     * @param dateString the string containing a date.
     * @return the {@code LocalDate} indicated by the string, null if no date is indicated.
     * @throws ParseException if the given {@code dateString} is invalid (cannot be parsed).
     */
    public static LocalDate parse(String dateString) throws ParseException {
        return parse(dateString, LocalDate.now());
    }

    /**
     * Similar to {@link #parse(String)}
     * Used to inject a {@code LocalDate currDate} as 'today's date'.
     *
     * @param currDate the current date to be referenced against. Usually contains {@code LocalDate.now()}.
     */
    static LocalDate parse(String dateString, LocalDate currDate) throws ParseException {
        requireAllNonNull(dateString, currDate);

        if (dateString.isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_DATE);
        }

        LocalDate formattedDate = parseByDateFormat(dateString);
        if (formattedDate != null) {
            return formattedDate;
        }

        LocalDate naturalDate = parseByNaturalDay(dateString, currDate);
        if (naturalDate != null) {
            return naturalDate;
        }

        throw new ParseException(MESSAGE_CONSTRAINTS);
    }

    /**
     * Parse the {@code String dateString} by checking with predefined date formats.
     * Expects the date to match one of the formatter in {@code KNOWN_DATE_FORMATS}.
     * If the {@code String dateString} can be inferred as 2 different dates, best effort
     * parsing is used and the most probable formatter is used (in order of priority).
     * For example, "2-3-2014" could mean 2 Mar 2014 or 3 Feb 2014, in this case 2 Mar 2014
     * will be used.
     *
     * @param dateString the string containing a date.
     * @return the {@code LocalDate} that is represented in the string, or null if
     * the string does not match any known natural date language.
     */
    static LocalDate parseByDateFormat(String dateString) {
        requireNonNull(dateString);

        for (int i = 0; i < KNOWN_DATE_FORMATS.size(); i++) {
            try {
                return LocalDate.parse(dateString, KNOWN_DATE_FORMATS.get(i));
            } catch (DateTimeParseException ex) {
                // Ignore exception, fallthrough expected
            }
        }
        return null;
    }

    /**
     * Parse the {@code String dateString} by checking with known natural date language.
     * Expects the time to match one of the date language defined in {@code NaturalDay}.
     * {@code LocalDate currDate} is used for injecting the current {@code LocalDate}.
     *
     * @param dateString the string containing a date.
     * @param currDate   the current date to be referenced against. Usually contains {@code LocalDate.now()}.
     * @return the {@code LocalDate} that is represented in the string, or null if
     * the string does not match any known natural date language.
     */
    static LocalDate parseByNaturalDay(String dateString, LocalDate currDate) {
        requireAllNonNull(dateString, currDate);

        // Parse for natural date
        NaturalDay day = NaturalDay.parse(dateString);

        if (day == null) {
            return null;
        }

        switch (day) {
        case TODAY:
            return currDate;
        case TOMORROW:
            return currDate.plusDays(1);
        case MONDAY:
            return currDate.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        case TUESDAY:
            return currDate.with(TemporalAdjusters.next(DayOfWeek.TUESDAY));
        case WEDNESDAY:
            return currDate.with(TemporalAdjusters.next(DayOfWeek.WEDNESDAY));
        case THURSDAY:
            return currDate.with(TemporalAdjusters.next(DayOfWeek.THURSDAY));
        case FRIDAY:
            return currDate.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        case SATURDAY:
            return currDate.with(TemporalAdjusters.next(DayOfWeek.SATURDAY));
        case SUNDAY:
            return currDate.with(TemporalAdjusters.next(DayOfWeek.SUNDAY));
        default:
            return null;
        }
    }
}

package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.NaturalDay;

/**
 * Contains utility methods used for parsing time strings in the various *Parser classes.
 * Helper class for {@link ParserUtil#parseTime(String)}.
 */
public class TimeParserUtil {
    public static final String MESSAGE_CONSTRAINTS = "The Time input must adhere to our formatting conventions "
            + "or adopt a recognised natural time language.\n"
            + "An example of a properly formatted time includes (but is not limited to): 2 PM\n"
            + "An example of a natural time language is 'morning' (8am) or 'evening' (7pm)"
            + "Please visit our user guide"
            + " for more date/time variants."
            + "\nType help in the input box for the link!";

    public static final String MESSAGE_EMPTY_TIME = "The Time input should not be empty.\n"
            + "You can try entering a time with the format such as:\n"
            + "1pm\n"
            + "See more time format on our User Guide";

    /**
     * List of known time formats that parser accepts.
     **/
    private static final List<DateTimeFormatter> KNOWN_TIME_FORMATS = createTimeFormats();

    /**
     * Creates the list of formatter that accepts a specified list of known time patterns.
     * This method should only be used once to initialise the formatters used by the parser.
     *
     * @return list of DateTimeFormatter with acceptable time format.
     */
    private static List<DateTimeFormatter> createTimeFormats() {
        // List of acceptable date format with optional year/month
        List<String> knownTimePatterns = Arrays.asList("HHmm", "h[:][.]mm[ ]a", "h[ ]a");

        List<DateTimeFormatter> knownFormats = new ArrayList<>();
        // Create a formatter for each known patterns to be used for parsing dates
        for (int i = 0; i < knownTimePatterns.size(); i++) {
            knownFormats.add(new DateTimeFormatterBuilder()
                    .parseCaseInsensitive()
                    .appendPattern(knownTimePatterns.get(i))
                    .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
                    .toFormatter()
            );
        }
        return knownFormats;
    }

    /**
     * Parse the {@code String timeString} into the implied {@code LocalTime}.
     *
     * @param timeString the string containing a time.
     * @return the {@code LocalTime} indicated by the string, or null if no time is indicated.
     * @throws ParseException if the given {@code timeString} is invalid (cannot be parsed).
     */
    public static LocalTime parse(String timeString) throws ParseException {
        requireNonNull(timeString);

        if (timeString.isBlank()) {
            throw new ParseException(MESSAGE_EMPTY_TIME);
        }

        LocalTime formattedTime = parseByTimeFormat(timeString);
        if (formattedTime != null) {
            return formattedTime;
        }

        LocalTime naturalTime = parseByNaturalTime(timeString);
        if (naturalTime != null) {
            return naturalTime;
        }

        throw new ParseException(MESSAGE_CONSTRAINTS);
    }

    /**
     * Parse the {@code String timeString} by checking with predefined time formats.
     * Expects the time to match one of the formatter in {@code KNOWN_TIME_FORMATS}.
     *
     * @param timeString the string containing a time.
     * @return the {@code LocalTime} that is represented in the string, or null if
     * the string does not match any known format.
     */
    static LocalTime parseByTimeFormat(String timeString) {
        requireNonNull(timeString);

        for (int i = 0; i < KNOWN_TIME_FORMATS.size(); i++) {
            try {
                return LocalTime.parse(timeString, KNOWN_TIME_FORMATS.get(i));
            } catch (DateTimeParseException ex) {
                // Ignore exception, fallthrough expected
            }
        }
        return null;
    }

    /**
     * Parse the {@code String timeString} by checking with known natural time language.
     * Expects the time to match one of the time language defined in {@code NaturalDay}.
     *
     * @param timeString the string containing a time.
     * @return the {@code LocalTime} that is represented by the user, or null if
     * the string does not match any known natural time language.
     */
    static LocalTime parseByNaturalTime(String timeString) {
        requireNonNull(timeString);

        // Parse for natural time
        NaturalDay time = NaturalDay.parse(timeString);

        if (time == null) {
            return null;
        }

        switch (time) {
        case MORNING:
            // Default: 8am
            return LocalTime.of(8, 0);
        case NOON:
            // Default: 12pm
            return LocalTime.of(12, 0);
        case EVENING:
            // Default: 7pm
            return LocalTime.of(19, 0);
        case NIGHT:
            // Default: 10pm
            return LocalTime.of(22, 0);
        case MIDNIGHT:
            // Default: 11:59pm/ 2359
            return LocalTime.of(23, 59);
        default:
            return null;
        }
    }
}

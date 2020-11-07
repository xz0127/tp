package seedu.address.logic.parser;

import static java.time.temporal.ChronoUnit.MINUTES;
import static java.util.Objects.requireNonNull;
import static seedu.address.model.appointment.Time.CLOSING_TIME;
import static seedu.address.model.appointment.Time.OPENING_TIME;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {
    public static final int MIN_DURATION = 10; // Appointments cannot have a duration that is lesser than 10 mins.
    public static final String MESSAGE_INVALID_INDEX = "Index must be a positive integer that is more than 0.";
    public static final String MESSAGE_INVALID_DURATION = "Duration must be a positive integer that is more than or "
            + "equals to 10 mins.\nDuration provided must also result in an appointment end time that falls within the"
            + " operational hours of the clinic on the same day.";
    public static final String MESSAGE_EMPTY_DURATION = "Duration must not be empty if you have entered the prefix";
    public static final String MESSAGE_MAX_DURATION = "Duration must be smaller than the number of minutes\n"
            + "in the working hours for one day";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String nric} into a {@code Nric}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Nric} is invalid.
     */
    public static Nric parseNric(String nric) throws ParseException {
        requireNonNull(nric);
        String trimmedNric = nric.trim();
        if (!Nric.isValidNric(trimmedNric)) {
            throw new ParseException(Nric.MESSAGE_CONSTRAINTS);
        }
        return new Nric(trimmedNric);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String date} into a {@code Date}.
     * Leading and trailing whitespace will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();

        // Parses the date into a LocalDate
        LocalDate parsedDate = DateParserUtil.parse(trimmedDate);

        return new Date(parsedDate);
    }

    /**
     * Parses {@code String time} into a {@code Time}.
     * Leading and trailing whitespace will be trimmed.
     *
     * @throws ParseException if the given {@code time} is invalid
     */
    public static Time parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();

        LocalTime parsedTime = TimeParserUtil.parse(trimmedTime);

        if (!Time.isValidTime(parsedTime)) {
            throw new ParseException(Time.MESSAGE_CONSTRAINTS);
        }

        return new Time(parsedTime);
    }

    /**
     * Parses a {@code String remark} into a {@code Remark}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code Remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        String trimmedRemark = remark.trim();
        if (!Remark.isValidRemark(trimmedRemark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(trimmedRemark);
    }

    /**
     * Parses a {@code String duration} into a {@code Duration}.
     * @throws ParseException ParseException if the given {@code Duration} is not a positive integer string.
     */
    public static Duration parseDuration(String durationString) throws ParseException {
        // null duration will use the default one hour duration.
        String trimmedDuration = durationString.trim();
        Duration duration;
        Duration minDuration = Duration.of(MIN_DURATION, MINUTES);
        Duration maxDuration = Duration.between(OPENING_TIME, CLOSING_TIME);

        if (trimmedDuration.isEmpty()) {
            throw new ParseException(MESSAGE_EMPTY_DURATION);
        }

        try {
            duration = Duration.of(Integer.parseInt(trimmedDuration), MINUTES);
        } catch (NumberFormatException e) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }

        if (duration.isNegative() || duration.isZero()) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }

        if (duration.compareTo(minDuration) < 0) {
            throw new ParseException(MESSAGE_INVALID_DURATION);
        }

        if (duration.compareTo(maxDuration) > 0) {
            throw new ParseException(MESSAGE_MAX_DURATION);
        }
        return duration;
    }
}

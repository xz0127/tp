package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a Patient's remark in the patient book.
 * Guarantees: immutable; is valid.
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Each remark have a capacity of up to 200 words and can take "
            + "all alphanumeric and symbol characters.\n"
            + "Only use a blank remark to delete an existing remark from the patient.\n"
            + "Existing remarks for the patient will also be overridden with the new input.";
    public static final String VALIDATION_REGEX = "^\\W*(?:\\w+\\b\\W*){0,200}$";
    public final String value;

    /**
     * Constructs an {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidRemark(remark), MESSAGE_CONSTRAINTS);
        value = remark;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * Returns true if a given string is a valid Remark.
     */
    public static boolean isValidRemark(String test) {
        requireNonNull(test);
        Pattern pattern = Pattern.compile(VALIDATION_REGEX);
        Matcher matcher = pattern.matcher(test);
        return matcher.matches();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Remark // instanceof handles nulls
                && value.equals(((Remark) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Patient's remark in the patient book.
 * Guarantees: immutable; is valid.
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Remark can take any values, and it should not be blank."
            + "Only use a blank remark when removing an existing remark from the patient.";
    public static final String VALIDATION_REGEX = ".*";

    public final String value;

    /**
     * Constructs an {@code Address}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
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
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Address // instanceof handles nulls
                && value.equals(((Address) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

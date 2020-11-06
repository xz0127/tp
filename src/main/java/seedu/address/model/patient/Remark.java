package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's remark in the patient book.
 * Guarantees: immutable; is valid.
 */
public class Remark {

    public static final String MESSAGE_CONSTRAINTS = "Each remark has an unlimited capacity and "
            + "can take all alphanumeric and symbol characters.\n"
            + "Only use a blank remark to delete an existing remark from the patient.\n"
            + "Existing remarks for the patient will also be overridden with the new input.";
    public final String value;

    /**
     * Constructs a {@code Remark}.
     *
     * @param remark A valid remark.
     */
    public Remark(String remark) {
        requireNonNull(remark);
        checkArgument(isValidRemark(remark), MESSAGE_CONSTRAINTS);
        assert isValidRemark(remark) : "Invalid Remark";
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
        return true;
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

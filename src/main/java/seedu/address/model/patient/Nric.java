package seedu.address.model.patient;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Patient's Nric in the patient book.
 * Guarantees: immutable; is valid as declared in {@link #isValidNric(String)}
 */
public class Nric {


    public static final String MESSAGE_CONSTRAINTS =
            "Nric should only contain 7 numbers leading and ending with an alphabet in uppercase";
    public static final String VALIDATION_REGEX = "[STFG]\\d{7}[A-Z]";
    public final String value;

    /**
     * Constructs a {@code Nric}.
     *
     * @param nric A valid Nric.
     */
    public Nric(String nric) {
        requireNonNull(nric);
        checkArgument(isValidNric(nric), MESSAGE_CONSTRAINTS);
        assert isValidNric(nric) : "Invalid nric";
        value = nric;
    }

    /**
     * Returns true if a given string is a valid Nric.
     */
    public static boolean isValidNric(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Nric // instanceof handles nulls
                && value.equals(((Nric) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}

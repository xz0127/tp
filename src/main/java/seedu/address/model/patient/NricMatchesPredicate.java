package seedu.address.model.patient;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


public class NricMatchesPredicate implements Predicate<Patient> {
    private final List<String> nrics;

    public NricMatchesPredicate(List<String> nrics) {
        this.nrics = nrics;
    }

    @Override
    public boolean test(Patient patient) {
        return nrics.stream()
                .anyMatch(nric -> StringUtil.containsWordIgnoreCase(patient.getNric().toString(), nric));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof NricMatchesPredicate
                && nrics.equals(((NricMatchesPredicate) other).nrics));
    }
}

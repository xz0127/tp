package seedu.address.model.patient;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


public class PhoneMatchesPredicate implements Predicate<Patient> {
    private final List<String> phones;

    public PhoneMatchesPredicate(List<String> phones) {
        this.phones = phones;
    }

    @Override
    public boolean test(Patient patient) {
        return phones.stream()
                .anyMatch(phone -> StringUtil.containsWordIgnoreCase(patient.getPhone().toString(), phone));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof PhoneMatchesPredicate
                && phones.equals(((PhoneMatchesPredicate) other).phones));
    }
}

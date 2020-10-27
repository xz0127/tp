package seedu.address.model.patient;

import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * todo: use this class to replace the {@code NricMatchesPredicate} and {@code PhoneMatchesPredicate}.
 * Tests that an {@code Patient}'s elements matches the given element.
 *
 * @param <T> The type of the given element. Under this stage, only
 *            {@code Phone} and {@code Nric} are allowed.
 */
public class ElementMatchesPredicate<T> implements Predicate<Patient> {
    private final T element;

    public ElementMatchesPredicate(T element) {
        this.element = element;
    }

    public T getElement() {
        return this.element;
    }

    @Override
    public boolean test(Patient patient) {
        return Stream.of(element).anyMatch(
            element -> {
                if (element instanceof Phone) {
                    return element.equals(patient.getPhone());
                } else if (element instanceof Nric) {
                    return element.equals(patient.getNric());
                } else {
                    return false;
                }
            }
        );
    }

    // todo: implement equals method
}

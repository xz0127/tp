package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PatientBuilder;


public class PhoneMatchesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateList = Collections.singletonList("12345678");
        List<String> secondPredicateList = Collections.singletonList("87654321");

        PhoneMatchesPredicate firstPredicate = new PhoneMatchesPredicate(firstPredicateList);
        PhoneMatchesPredicate secondPredicate = new PhoneMatchesPredicate(secondPredicateList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PhoneMatchesPredicate firstPredicateCopy = new PhoneMatchesPredicate(firstPredicateList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different patient -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_phoneMatches_returnsTrue() {
        // one phone
        PhoneMatchesPredicate predicate = new PhoneMatchesPredicate(Collections.singletonList("12345678"));
        assertTrue(predicate.test(new PatientBuilder().withPhone("12345678").build()));

        // multiple phones but only one matches
        predicate = new PhoneMatchesPredicate(Arrays.asList("12345678", "87654321"));
        assertTrue(predicate.test(new PatientBuilder().withPhone("12345678").build()));
    }

    @Test
    public void tes_phoneNotMatch_returnFalse() {
        // zero phone
        PhoneMatchesPredicate predicate = new PhoneMatchesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PatientBuilder().withPhone("12345678").build()));

        // non-matching phone
        predicate = new PhoneMatchesPredicate(Collections.singletonList("87654321"));
        assertFalse(predicate.test(new PatientBuilder().withPhone("12345678").build()));
    }
}

package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PatientBuilder;

public class NricMatchesPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateList = Collections.singletonList("S1234567I");
        List<String> secondPredicateList = Collections.singletonList("S7654321I");

        NricMatchesPredicate firstPredicate = new NricMatchesPredicate(firstPredicateList);
        NricMatchesPredicate secondPredicate = new NricMatchesPredicate(secondPredicateList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        NricMatchesPredicate firstPredicateCopy = new NricMatchesPredicate(firstPredicateList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different patient -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nricMatches_returnsTrue() {
        // one nric
        NricMatchesPredicate predicate = new NricMatchesPredicate(Collections.singletonList("S1234567I"));
        assertTrue(predicate.test(new PatientBuilder().withNric("S1234567I").build()));

        // multiple nric but only one matches
        predicate = new NricMatchesPredicate(Arrays.asList("S1234567I", "S7654321I"));
        assertTrue(predicate.test(new PatientBuilder().withNric("S1234567I").build()));

        // case insensitive
        predicate = new NricMatchesPredicate(Arrays.asList("S1234567i", "S7654321I"));
        assertTrue(predicate.test(new PatientBuilder().withNric("S1234567I").build()));
    }

    @Test
    public void test_nricNotMatch_returnFalse() {
        // zero nric
        NricMatchesPredicate predicate = new NricMatchesPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PatientBuilder().withNric("S1234567I").build()));

        // non-matching nric
        predicate = new NricMatchesPredicate(Collections.singletonList("S7654321I"));
        assertFalse(predicate.test(new PatientBuilder().withNric("S1234567I").build()));
    }
}

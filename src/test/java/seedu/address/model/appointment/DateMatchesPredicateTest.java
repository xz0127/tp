package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.AppointmentBuilder;

public class DateMatchesPredicateTest {

    @Test
    public void equals() {
        Date firstPredicateDate = new Date(2020, 12, 12);
        Date secondPredicateDate = new Date(2020, 12, 2);

        DateMatchesPredicate firstPredicate = new DateMatchesPredicate(firstPredicateDate);
        DateMatchesPredicate secondPredicate = new DateMatchesPredicate(secondPredicateDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        DateMatchesPredicate firstPredicateCopy = new DateMatchesPredicate(firstPredicateDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different date -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateMatches_returnsTrue() {
        DateMatchesPredicate predicate = new DateMatchesPredicate(new Date(2020, 12, 12));
        assertTrue(predicate.test(
                new AppointmentBuilder().withDate(LocalDate.of(2020, 12, 12)).build()
        ));
    }

    @Test
    public void test_dateDoesNotMatch_returnsFalse() {
        DateMatchesPredicate predicate = new DateMatchesPredicate(new Date(2020, 12, 2));
        assertFalse(predicate.test(
                new AppointmentBuilder().withDate(LocalDate.of(2020, 12, 12)).build()
        ));
    }

}

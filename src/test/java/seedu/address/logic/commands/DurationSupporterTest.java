package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.SUPPORTER;
import static seedu.address.logic.commands.CommandTestUtil.SUPPORTER_DIFF_DATE;
import static seedu.address.logic.commands.CommandTestUtil.SUPPORTER_DIFF_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.SUPPORTER_DIFF_TIME;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AssignCommand.DurationSupporter;

public class DurationSupporterTest {
    @Test
    public void equals() {
        //same values -> returns true
        DurationSupporter supporter = new DurationSupporter(SUPPORTER);
        assertTrue(SUPPORTER.equals(supporter));

        // same object -> returns true
        assertTrue(SUPPORTER.equals(SUPPORTER));

        // null -> returns false
        assertFalse(SUPPORTER.equals(null));

        // different type -> returns false
        assertFalse(SUPPORTER.equals(5));

        // different date -> returns false
        assertFalse(SUPPORTER.equals(SUPPORTER_DIFF_DATE));

        // different time -> returns false
        assertFalse(SUPPORTER.equals(SUPPORTER_DIFF_TIME));

        // different duration -> returns false
        assertFalse(SUPPORTER.equals(SUPPORTER_DIFF_DURATION));
    }
}

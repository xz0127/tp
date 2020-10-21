package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.LOADER;
import static seedu.address.logic.commands.CommandTestUtil.LOADER_DIFF_DATE;
import static seedu.address.logic.commands.CommandTestUtil.LOADER_DIFF_TIME;

import org.junit.jupiter.api.Test;

public class DateTimeLoaderTest {

    @Test
    public void equals() {
        // same values -> returns true
        DateTimeLoader loader = new DateTimeLoader(LOADER);
        assertTrue(LOADER.equals(loader));

        // same object -> returns true
        assertTrue(LOADER.equals(LOADER));

        // null -> returns false
        assertFalse(LOADER.equals(null));

        // different type -> returns false
        assertFalse(LOADER.equals(5));

        // different date -> returns false
        assertFalse(LOADER.equals(LOADER_DIFF_DATE));

        // different time -> returns false
        assertFalse(LOADER.equals(LOADER_DIFF_TIME));
    }
}

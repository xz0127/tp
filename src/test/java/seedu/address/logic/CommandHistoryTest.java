package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CommandHistoryTest {
    private final CommandHistory commandHistory = new CommandHistory();

    @Test
    public void add_nullInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> commandHistory.add(null));
    }

    @Test
    public void add_emptyInput_ignoredNoChange() {
        commandHistory.add("");
        assertEquals(commandHistory.asUnmodifiableObservableList().size(), 0);
    }

    @Test
    public void add_validInput_addedToList() {
        commandHistory.add("Test input");
        assertEquals(commandHistory.asUnmodifiableObservableList().size(), 1);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> commandHistory.asUnmodifiableObservableList().add("Test"));
    }
}

package seedu.address.model.tag;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

    @Test
    public void equals() {
        String validTagName = "Test";
        Tag originalTag = new Tag(validTagName);

        // same object -> returns true
        assertTrue(originalTag.equals(originalTag));

        // same values -> returns true
        Tag copyTag = new Tag(validTagName);
        assertTrue(copyTag.equals(copyTag));

        // different types -> returns false
        assertFalse(copyTag.equals(1));

        // null -> returns false
        assertFalse(copyTag.equals(null));

        // different value -> returns false
        assertFalse(copyTag.equals(new Tag("Diff")));
    }

}

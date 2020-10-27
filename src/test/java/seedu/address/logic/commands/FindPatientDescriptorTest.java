package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.FIND_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FIND_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand.FindPatientDescriptor;
import seedu.address.testutil.FindPatientDescriptorBuilder;

public class FindPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        FindPatientDescriptor descriptorWithSameValues = new FindPatientDescriptor(FIND_DESC_AMY);
        assertTrue(FIND_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(FIND_DESC_AMY.equals(FIND_DESC_AMY));

        // null -> returns false
        assertFalse(FIND_DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(FIND_DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(FIND_DESC_AMY.equals(FIND_DESC_BOB));

        // different name -> returns false
        FindPatientDescriptor editedAmy =
                new FindPatientDescriptorBuilder(FIND_DESC_AMY).withName(new String[]{VALID_NAME_BOB}).build();
        assertFalse(FIND_DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy =
                new FindPatientDescriptorBuilder(FIND_DESC_AMY).withPhone(new String[]{VALID_PHONE_BOB}).build();
        assertFalse(FIND_DESC_AMY.equals(editedAmy));

        // different nric -> returns false
        editedAmy =
                new FindPatientDescriptorBuilder(FIND_DESC_AMY).withNric(new String[]{VALID_NRIC_BOB}).build();
        assertFalse(FIND_DESC_AMY.equals(editedAmy));
    }
}

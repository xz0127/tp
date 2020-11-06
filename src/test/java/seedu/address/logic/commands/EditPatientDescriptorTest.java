package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EDIT_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.EditCommand.EditPatientDescriptor;
import seedu.address.testutil.EditPatientDescriptorBuilder;

public class EditPatientDescriptorTest {

    @Test
    public void equals() {
        // same values -> returns true
        EditPatientDescriptor descriptorWithSameValues = new EditPatientDescriptor(EDIT_DESC_AMY);
        assertTrue(EDIT_DESC_AMY.equals(descriptorWithSameValues));

        // same object -> returns true
        assertTrue(EDIT_DESC_AMY.equals(EDIT_DESC_AMY));

        // null -> returns false
        assertFalse(EDIT_DESC_AMY.equals(null));

        // different types -> returns false
        assertFalse(EDIT_DESC_AMY.equals(5));

        // different values -> returns false
        assertFalse(EDIT_DESC_AMY.equals(EDIT_DESC_BOB));

        // different name -> returns false
        EditPatientDescriptor editedAmy =
                new EditPatientDescriptorBuilder(EDIT_DESC_AMY).withName(VALID_NAME_BOB).build();
        assertFalse(EDIT_DESC_AMY.equals(editedAmy));

        // different phone -> returns false
        editedAmy = new EditPatientDescriptorBuilder(EDIT_DESC_AMY).withPhone(VALID_PHONE_BOB).build();
        assertFalse(EDIT_DESC_AMY.equals(editedAmy));

        // different address -> returns false
        editedAmy = new EditPatientDescriptorBuilder(EDIT_DESC_AMY).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(EDIT_DESC_AMY.equals(editedAmy));

        // different nric -> returns false
        editedAmy = new EditPatientDescriptorBuilder(EDIT_DESC_AMY).withNric(VALID_NRIC_BOB).build();
        assertFalse(EDIT_DESC_AMY.equals(editedAmy));

        // different remark -> returns false
        editedAmy = new EditPatientDescriptorBuilder(EDIT_DESC_AMY).withRemark(VALID_REMARK_BOB).build();
        assertFalse(EDIT_DESC_AMY.equals(editedAmy));

        // different tags -> returns false
        editedAmy = new EditPatientDescriptorBuilder(EDIT_DESC_AMY).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(EDIT_DESC_AMY.equals(editedAmy));
    }
}

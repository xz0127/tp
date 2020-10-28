package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.FIND_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.FIND_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.AMY;

import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand.FindPatientDescriptor;
import seedu.address.model.patient.Patient;
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

    @Test
    public void execute_getOrPredicate() {
        Predicate<Patient> empty = unused -> false;
        FindPatientDescriptorBuilder descriptorBuilder = new FindPatientDescriptorBuilder();

        // empty descriptor
        FindPatientDescriptor emptyDescriptor = descriptorBuilder.build();
        assertEquals(empty.test(AMY), emptyDescriptor.getOrPredicate().test(AMY));

        // only NamePredicate in the descriptor is present
        FindPatientDescriptor descriptorWithName =
                descriptorBuilder.withName(new String[]{"amy"}).build();
        Predicate<Patient> predWithName = descriptorWithName.getNamePredicate().get();
        assertEquals(descriptorWithName.getOrPredicate().test(AMY), predWithName.test(AMY));
        assertEquals(descriptorWithName.getOrPredicate().test(ALICE), predWithName.test(ALICE));


        // both NamePredicate and NricPredicate in the descriptor are present
        FindPatientDescriptor descriptorWithNameNric =
                descriptorBuilder.withNric(new String[]{AMY.getNric().toString()}).build();
        Predicate<Patient> predWithNameNric = predWithName.or(descriptorWithNameNric.getNricPredicate().get());
        assertEquals(descriptorWithNameNric.getOrPredicate().test(AMY), predWithNameNric.test(AMY));
        assertEquals(descriptorWithNameNric.getOrPredicate().test(ALICE), predWithNameNric.test(ALICE));

        // all predicates in the descriptor are present where the phone number is from ALICE.
        FindPatientDescriptor descriptorWithAll =
                descriptorBuilder.withPhone(new String[]{ALICE.getPhone().toString()}).build();
        Predicate<Patient> predWithAll = predWithNameNric.or(descriptorWithAll.getPhonePredicate().get());
        assertEquals(descriptorWithAll.getOrPredicate().test(AMY), predWithAll.test(AMY));
        assertEquals(descriptorWithAll.getOrPredicate().test(ALICE), predWithAll.test(ALICE));
    }
}

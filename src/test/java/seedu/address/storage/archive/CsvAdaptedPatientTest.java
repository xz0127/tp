package seedu.address.storage.archive;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_BOB;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;

public class CsvAdaptedPatientTest {

    @Test
    public void constructor() {
        CsvAdaptedPatient patient = new CsvAdaptedPatient(ALICE);
        CsvAdaptedPatient constructedPatient = new CsvAdaptedPatient(ALICE.getName().fullName, ALICE.getPhone().value,
                ALICE.getAddress().value, ALICE.getRemark().value);

        assertEquals(patient, constructedPatient);
    }

    @Test
    public void equals() {
        // same values -> returns true
        CsvAdaptedPatient alice = new CsvAdaptedPatient(ALICE);
        CsvAdaptedPatient aliceCopy = new CsvAdaptedPatient(new PatientBuilder(ALICE).build());

        assertTrue(alice.equals(aliceCopy));

        // same object -> returns true
        assertTrue(alice.equals(alice));

        // null -> returns false
        assertFalse(alice.equals(null));

        // different type -> returns false
        assertFalse(alice.equals(5));

        // different patient -> returns false
        assertFalse(alice.equals(new CsvAdaptedPatient(BOB)));

        // different name -> returns false
        Patient editedAlice = new PatientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(alice.equals(new CsvAdaptedPatient(editedAlice)));

        // different phone -> returns false
        editedAlice = new PatientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(alice.equals(new CsvAdaptedPatient(editedAlice)));

        // different address -> returns false
        editedAlice = new PatientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(alice.equals(new CsvAdaptedPatient(editedAlice)));

        // different remark -> returns false
        editedAlice = new PatientBuilder(ALICE).withRemark(VALID_REMARK_BOB).build();
        assertFalse(alice.equals(new CsvAdaptedPatient(editedAlice)));
    }
}

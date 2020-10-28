package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.PatientBook;
import seedu.address.model.patient.Patient;

/**
 * A utility class containing a list of {@code Patient} objects to be used in tests.
 */
public class TypicalPatients {

    public static final Patient ALICE = new PatientBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253").withNric("S1234567I")
            .withTags("friends").withRemark("This is a test remark :)").build();
    public static final Patient BENSON = new PatientBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25").withNric("S1234567O")
            .withPhone("98765432").withRemark("He can't take beer!")
            .withTags("owesMoney", "friends").build();
    public static final Patient CARL = new PatientBuilder().withName("Carl Kurz").withPhone("95352563")
            .withNric("S1234567P").withAddress("wall street").build();
    public static final Patient DANIEL = new PatientBuilder().withName("Daniel Meier").withPhone("87652533")
            .withNric("S1234567L")
            .withAddress("10th street").withTags("friends").build();
    public static final Patient ELLE = new PatientBuilder().withName("Elle Meyer").withPhone("9482224")
            .withNric("T1234567P").withAddress("michegan ave").build();
    public static final Patient FIONA = new PatientBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withNric("T0234567P").withAddress("little tokyo").build();
    public static final Patient GEORGE = new PatientBuilder().withName("George Best").withPhone("9482442")
            .withNric("T0034567P").withAddress("4th street").withRemark("This is a test remark :)").build();

    // Manually added
    public static final Patient HOON = new PatientBuilder().withName("Hoon Meier").withPhone("8482424")
            .withAddress("little india").withNric("T0004567P").build();
    public static final Patient IDA = new PatientBuilder().withName("Ida Mueller").withPhone("8482131")
            .withAddress("chicago ave").withNric("T0030567P").build();

    // Manually added - Patient's details found in {@code CommandTestUtil}
    public static final Patient AMY = new PatientBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withTags(VALID_TAG_FRIEND).withNric(VALID_NRIC_AMY).build();
    public static final Patient BOB = new PatientBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withNric(VALID_NRIC_BOB)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPatients() {} // prevents instantiation

    /**
     * Returns an {@code PatientBook} with all the typical patients.
     */
    public static PatientBook getTypicalPatientBook() {
        PatientBook pb = new PatientBook();
        for (Patient patient : getTypicalPatients()) {
            pb.addPatient(patient);
        }
        return pb;
    }

    public static List<Patient> getTypicalPatients() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}

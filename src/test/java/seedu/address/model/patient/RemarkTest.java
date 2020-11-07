package seedu.address.model.patient;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.RemarkUtil.SAMPLE_REMARK_1;
import static seedu.address.testutil.RemarkUtil.SAMPLE_REMARK_2;
import static seedu.address.testutil.RemarkUtil.SAMPLE_REMARK_3;
import static seedu.address.testutil.RemarkUtil.SAMPLE_REMARK_4;
import static seedu.address.testutil.RemarkUtil.SAMPLE_REMARK_5;
import static seedu.address.testutil.RemarkUtil.SAMPLE_REMARK_6;
import static seedu.address.testutil.RemarkUtil.SAMPLE_REMARK_7;
import static seedu.address.testutil.RemarkUtil.SAMPLE_REMARK_8;
import static seedu.address.testutil.RemarkUtil.STRING_REMARK_1;
import static seedu.address.testutil.RemarkUtil.STRING_REMARK_2;
import static seedu.address.testutil.RemarkUtil.STRING_REMARK_3;
import static seedu.address.testutil.RemarkUtil.STRING_REMARK_4;
import static seedu.address.testutil.RemarkUtil.STRING_REMARK_5;
import static seedu.address.testutil.RemarkUtil.STRING_REMARK_6;
import static seedu.address.testutil.RemarkUtil.STRING_REMARK_7;
import static seedu.address.testutil.RemarkUtil.STRING_REMARK_8;
import static seedu.address.testutil.RemarkUtil.WORDS_ONE_NINETY_NINE;
import static seedu.address.testutil.RemarkUtil.WORDS_TWO_HUNDRED;

import org.junit.jupiter.api.Test;

public class RemarkTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Remark(null));
    }

    @Test
    public void isValidRemark_success() {
        assertTrue(Remark.isValidRemark("")); // empty string
        assertTrue(Remark.isValidRemark(" ")); // spaces only, resetting remark
        assertTrue(Remark.isValidRemark("^")); // only non-alphanumeric characters
        assertTrue(Remark.isValidRemark("peter*")); // contains non-alphanumeric characters

        assertTrue(Remark.isValidRemark("#$%^&")); // contains symbols
        assertTrue(Remark.isValidRemark("peter jack")); // alphabets only
        assertTrue(Remark.isValidRemark("12345")); // numbers only
        assertTrue(Remark.isValidRemark("peter the 2nd")); // alphanumeric characters
        assertTrue(Remark.isValidRemark("Capital Tan")); // with capital letters
        assertTrue(Remark.isValidRemark("David Roger Jackson Ray Jr 2nd")); // long names

        assertTrue(Remark.isValidRemark(WORDS_ONE_NINETY_NINE)); // large input
        assertTrue(Remark.isValidRemark(WORDS_TWO_HUNDRED));
    }

    @Test
    public void isValidRemark_null_failure() {
        // null remark
        assertThrows(NullPointerException.class, () -> Remark.isValidRemark(null));
    }

    @Test
    public void equals_success() {
        assertTrue(SAMPLE_REMARK_1.equals(new Remark(STRING_REMARK_1)));
        assertTrue(SAMPLE_REMARK_2.equals(new Remark(STRING_REMARK_2)));
        assertTrue(SAMPLE_REMARK_3.equals(new Remark(STRING_REMARK_3)));
        assertTrue(SAMPLE_REMARK_4.equals(new Remark(STRING_REMARK_4)));
        assertTrue(SAMPLE_REMARK_5.equals(new Remark(STRING_REMARK_5)));
        assertTrue(SAMPLE_REMARK_6.equals(new Remark(STRING_REMARK_6)));
        assertTrue(SAMPLE_REMARK_7.equals(new Remark(STRING_REMARK_7)));
        assertTrue(SAMPLE_REMARK_8.equals(new Remark(STRING_REMARK_8)));

        assertTrue(SAMPLE_REMARK_1.equals(SAMPLE_REMARK_1));
        assertTrue(SAMPLE_REMARK_7.equals(SAMPLE_REMARK_7));
    }

    @Test
    public void equals_failure() {
        assertFalse(SAMPLE_REMARK_1.equals(new Remark(STRING_REMARK_2)));
        assertFalse(SAMPLE_REMARK_2.equals(new Remark(STRING_REMARK_3)));
        assertFalse(SAMPLE_REMARK_3.equals(new Remark(STRING_REMARK_4)));
        assertFalse(SAMPLE_REMARK_4.equals(new Remark(STRING_REMARK_5)));
        assertFalse(SAMPLE_REMARK_5.equals(new Remark(STRING_REMARK_6)));
        assertFalse(SAMPLE_REMARK_6.equals(new Remark(STRING_REMARK_7)));
        assertFalse(SAMPLE_REMARK_7.equals(new Remark(STRING_REMARK_8)));
        assertFalse(SAMPLE_REMARK_8.equals(new Remark(STRING_REMARK_1)));
    }

    @Test
    public void hashCode_success() {
        assertTrue(SAMPLE_REMARK_1.hashCode() == STRING_REMARK_1.hashCode());
        assertTrue(SAMPLE_REMARK_3.hashCode() == STRING_REMARK_3.hashCode());
        assertTrue(SAMPLE_REMARK_8.hashCode() == STRING_REMARK_8.hashCode());
    }

    @Test
    public void toString_success() {
        assertTrue(SAMPLE_REMARK_1.toString() == STRING_REMARK_1);
        assertTrue(SAMPLE_REMARK_3.toString() == STRING_REMARK_3);
        assertTrue(SAMPLE_REMARK_8.toString() == STRING_REMARK_8);
    }
}

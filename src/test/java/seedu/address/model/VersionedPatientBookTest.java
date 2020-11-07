package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPatients.AMY;
import static seedu.address.testutil.TypicalPatients.BOB;
import static seedu.address.testutil.TypicalPatients.CARL;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.exceptions.NoRedoableStateException;
import seedu.address.model.exceptions.NoUndoableStateException;
import seedu.address.testutil.PatientBookBuilder;

public class VersionedPatientBookTest {

    private final ReadOnlyPatientBook patientBookWithAmy = new PatientBookBuilder().withPatient(AMY).build();
    private final ReadOnlyPatientBook patientBookWithBob = new PatientBookBuilder().withPatient(BOB).build();
    private final ReadOnlyPatientBook patientBookWithCarl = new PatientBookBuilder().withPatient(CARL).build();
    private final ReadOnlyPatientBook emptyPatientBook = new PatientBookBuilder().build();

    @Test
    public void commit_singlePatientBook_noStatesRemovedCurrentStateSaved() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(emptyPatientBook);

        versionedPatientBook.commit();
        assertPatientBookListStatus(versionedPatientBook,
                Collections.singletonList(emptyPatientBook),
                emptyPatientBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multiplPatientBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);

        versionedPatientBook.commit();
        assertPatientBookListStatus(versionedPatientBook,
                Arrays.asList(emptyPatientBook, patientBookWithAmy, patientBookWithBob),
                patientBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void commit_multiplPatientBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedPatientBook, 2);

        versionedPatientBook.commit();
        assertPatientBookListStatus(versionedPatientBook,
                Collections.singletonList(emptyPatientBook),
                emptyPatientBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multiplPatientBookPointerAtEndOfStateList_returnsTrue() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);

        assertTrue(versionedPatientBook.canUndo());
    }

    @Test
    public void canUndo_multiplPatientBookPointerAtStartOfStateList_returnsTrue() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedPatientBook, 1);

        assertTrue(versionedPatientBook.canUndo());
    }

    @Test
    public void canUndo_singlPatientBook_returnsFalse() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(emptyPatientBook);

        assertFalse(versionedPatientBook.canUndo());
    }

    @Test
    public void canUndo_multiplPatientBookPointerAtStartOfStateList_returnsFalse() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedPatientBook, 2);

        assertFalse(versionedPatientBook.canUndo());
    }

    @Test
    public void canRedo_multiplPatientBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedPatientBook, 1);

        assertTrue(versionedPatientBook.canRedo());
    }

    @Test
    public void canRedo_multiplPatientBookPointerAtStartOfStateList_returnsTrue() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedPatientBook, 2);

        assertTrue(versionedPatientBook.canRedo());
    }

    @Test
    public void canRedo_singlPatientBook_returnsFalse() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(emptyPatientBook);

        assertFalse(versionedPatientBook.canRedo());
    }

    @Test
    public void canRedo_multiplPatientBookPointerAtEndOfStateList_returnsFalse() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);

        assertFalse(versionedPatientBook.canRedo());
    }

    @Test
    public void undo_multiplPatientBookPointerAtEndOfStateList_success() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);

        versionedPatientBook.undo();
        assertPatientBookListStatus(versionedPatientBook,
                Collections.singletonList(emptyPatientBook),
                patientBookWithAmy,
                Collections.singletonList(patientBookWithBob));
    }

    @Test
    public void undo_multiplPatientBookPointerNotAtStartOfStateList_success() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedPatientBook, 1);

        versionedPatientBook.undo();
        assertPatientBookListStatus(versionedPatientBook,
                Collections.emptyList(),
                emptyPatientBook,
                Arrays.asList(patientBookWithAmy, patientBookWithBob));
    }

    @Test
    public void undo_singlPatientBook_throwsNoUndoableStateException() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(emptyPatientBook);

        assertThrows(NoUndoableStateException.class, versionedPatientBook::undo);
    }

    @Test
    public void undo_multiplPatientBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedPatientBook, 2);

        assertThrows(NoUndoableStateException.class, versionedPatientBook::undo);
    }

    @Test
    public void redo_multiplPatientBookPointerNotAtEndOfStateList_success() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedPatientBook, 1);

        versionedPatientBook.redo();
        assertPatientBookListStatus(versionedPatientBook,
                Arrays.asList(emptyPatientBook, patientBookWithAmy), patientBookWithBob,
                Collections.emptyList());
    }

    @Test
    public void redo_multiplPatientBookPointerAtStartOfStateList_success() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);
        shiftCurrentStatePointerLeftwards(versionedPatientBook, 2);

        versionedPatientBook.redo();
        assertPatientBookListStatus(versionedPatientBook,
                Collections.singletonList(emptyPatientBook),
                patientBookWithAmy,
                Collections.singletonList(patientBookWithBob));
    }

    @Test
    public void redo_singlPatientBook_throwsNoRedoableStateException() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(emptyPatientBook);

        assertThrows(NoRedoableStateException.class, versionedPatientBook::redo);
    }

    @Test
    public void redo_multiplPatientBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(
                emptyPatientBook, patientBookWithAmy, patientBookWithBob);

        assertThrows(NoRedoableStateException.class, versionedPatientBook::redo);
    }

    @Test
    public void equals() {
        VersionedPatientBook versionedPatientBook = preparePatientBookList(patientBookWithAmy,
                patientBookWithBob);

        // same values -> returns true
        VersionedPatientBook copy = preparePatientBookList(patientBookWithAmy, patientBookWithBob);
        assertTrue(versionedPatientBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedPatientBook.equals(versionedPatientBook));

        // null -> returns false
        assertFalse(versionedPatientBook.equals(null));

        // different types -> returns false
        assertFalse(versionedPatientBook.equals(1));

        // different current state -> returns false
        VersionedPatientBook differentCurrentState = preparePatientBookList(patientBookWithAmy, patientBookWithCarl);
        assertFalse(versionedPatientBook.equals(differentCurrentState));

        // different state list -> returns false
        VersionedPatientBook differentPatientBookList = preparePatientBookList(patientBookWithBob, patientBookWithBob);
        assertFalse(versionedPatientBook.equals(differentPatientBookList));

        // different current pointer index -> returns false
        versionedPatientBook = preparePatientBookList(patientBookWithBob, patientBookWithBob);
        VersionedPatientBook differentCurrentStatePointer = preparePatientBookList(
                patientBookWithBob, patientBookWithBob);
        shiftCurrentStatePointerLeftwards(differentCurrentStatePointer, 1);
        assertFalse(versionedPatientBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedPatientBook
     * } is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedPatientBook
     * #currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedPatientBook
     * #currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertPatientBookListStatus(VersionedPatientBook versionedPatientBook,
                                             List<ReadOnlyPatientBook> expectedStatesBeforePointer,
                                             ReadOnlyPatientBook expectedCurrentState,
                                             List<ReadOnlyPatientBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new PatientBook(versionedPatientBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedPatientBook.canUndo()) {
            versionedPatientBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyPatientBook expectedPatientBook : expectedStatesBeforePointer) {
            assertEquals(expectedPatientBook, new PatientBook(versionedPatientBook));
            versionedPatientBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyPatientBook expectedPatientBook : expectedStatesAfterPointer) {
            versionedPatientBook.redo();
            assertEquals(expectedPatientBook, new PatientBook(versionedPatientBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedPatientBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedPatientBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedPatientBook} with the {@code patientBookStates} added into it, and the
     * {@code VersionedPatientBook#currentStatePointer} at the end of list.
     */
    private VersionedPatientBook preparePatientBookList(ReadOnlyPatientBook... patientBookStates) {
        assertFalse(patientBookStates.length == 0);

        VersionedPatientBook versionedPatientBook = new VersionedPatientBook(patientBookStates[0]);
        for (int i = 1; i < patientBookStates.length; i++) {
            versionedPatientBook.resetData(patientBookStates[i]);
            versionedPatientBook.commit();
        }

        return versionedPatientBook;
    }

    /**
     * Shifts the {@code versionedPatientBook
     * #currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedPatientBook versionedPatientBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedPatientBook.undo();
        }
    }
}

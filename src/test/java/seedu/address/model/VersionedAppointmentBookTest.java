package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.BENSON_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.HOON_APPOINTMENT;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.exceptions.NoRedoableStateException;
import seedu.address.model.exceptions.NoUndoableStateException;
import seedu.address.testutil.AppointmentBookBuilder;

public class VersionedAppointmentBookTest {

    private final ReadOnlyAppointmentBook appointmentBookWithAliceAppointment = new AppointmentBookBuilder()
            .withAppointment(ALICE_APPOINTMENT).build();
    private final ReadOnlyAppointmentBook appointmentBookWithBensonAppointment = new AppointmentBookBuilder()
            .withAppointment(BENSON_APPOINTMENT).build();
    private final ReadOnlyAppointmentBook appointmentBookWithHoonAppointment = new AppointmentBookBuilder()
            .withAppointment(HOON_APPOINTMENT).build();
    private final ReadOnlyAppointmentBook emptyAppointmentBook = new AppointmentBookBuilder().build();

    @Test
    public void commit_singleAppointmentBook_noStatesRemovedCurrentStateSaved() {
        VersionedAppointmentBook versionedAppointmentBook =
                prepareAppointmentBookList(emptyAppointmentBook);

        versionedAppointmentBook
                .commit();
        assertAppointmentBookListStatus(versionedAppointmentBook,
                Collections.singletonList(emptyAppointmentBook),
                emptyAppointmentBook,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAppointmentBookPointerAtEndOfStateList_noStatesRemovedCurrentStateSaved() {
        VersionedAppointmentBook versionedAppointmentBook =
                prepareAppointmentBookList(
                        emptyAppointmentBook, appointmentBookWithAliceAppointment,
                        appointmentBookWithBensonAppointment);

        versionedAppointmentBook
                .commit();
        assertAppointmentBookListStatus(versionedAppointmentBook,
                Arrays.asList(emptyAppointmentBook, appointmentBookWithAliceAppointment,
                        appointmentBookWithBensonAppointment),
                appointmentBookWithBensonAppointment,
                Collections.emptyList());
    }

    @Test
    public void commit_multipleAppointmentBookPointerNotAtEndOfStateList_statesAfterPointerRemovedCurrentStateSaved() {
        VersionedAppointmentBook versionedAppointmentBook =
                prepareAppointmentBookList(emptyAppointmentBook, appointmentBookWithAliceAppointment,
                        appointmentBookWithBensonAppointment);
        shiftCurrentStatePointerLeftwards(versionedAppointmentBook, 2);

        versionedAppointmentBook
                .commit();
        assertAppointmentBookListStatus(versionedAppointmentBook,
                Collections.singletonList(emptyAppointmentBook),
                emptyAppointmentBook,
                Collections.emptyList());
    }

    @Test
    public void canUndo_multipleAppointmentBookPointerAtEndOfStateList_returnsTrue() {
        VersionedAppointmentBook versionedAppointmentBook =
                prepareAppointmentBookList(emptyAppointmentBook, appointmentBookWithAliceAppointment,
                        appointmentBookWithBensonAppointment);

        assertTrue(versionedAppointmentBook.canUndo());
    }

    @Test
    public void canUndo_multipleAppointmentBookPointerAtStartOfStateList_returnsTrue() {
        VersionedAppointmentBook versionedAppointmentBook =
                prepareAppointmentBookList(emptyAppointmentBook, appointmentBookWithAliceAppointment,
                        appointmentBookWithBensonAppointment);
        shiftCurrentStatePointerLeftwards(versionedAppointmentBook, 1);

        assertTrue(versionedAppointmentBook.canUndo());
    }

    @Test
    public void canUndo_singleAppointmentBook_returnsFalse() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(emptyAppointmentBook);

        assertFalse(versionedAppointmentBook.canUndo());
    }

    @Test
    public void canUndo_multipleAppointmentBookPointerAtStartOfStateList_returnsFalse() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(emptyAppointmentBook,
                appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment);
        shiftCurrentStatePointerLeftwards(versionedAppointmentBook, 2);

        assertFalse(versionedAppointmentBook.canUndo());
    }

    @Test
    public void canRedo_multipleAppointmentBookPointerNotAtEndOfStateList_returnsTrue() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(emptyAppointmentBook,
                appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment);
        shiftCurrentStatePointerLeftwards(versionedAppointmentBook, 1);

        assertTrue(versionedAppointmentBook.canRedo());
    }

    @Test
    public void canRedo_multipleAppointmentBookPointerAtStartOfStateList_returnsTrue() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(emptyAppointmentBook,
                appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment);
        shiftCurrentStatePointerLeftwards(versionedAppointmentBook, 2);

        assertTrue(versionedAppointmentBook.canRedo());
    }

    @Test
    public void canRedo_singleAppointmentBook_returnsFalse() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(emptyAppointmentBook);

        assertFalse(versionedAppointmentBook.canRedo());
    }

    @Test
    public void canRedo_multipleAppointmentBookPointerAtEndOfStateList_returnsFalse() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(
                emptyAppointmentBook, appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment);

        assertFalse(versionedAppointmentBook.canRedo());
    }

    @Test
    public void undo_multipleAppointmentBookPointerAtEndOfStateList_success() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(
                emptyAppointmentBook, appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment);

        versionedAppointmentBook.undo();
        assertAppointmentBookListStatus(versionedAppointmentBook,
                Collections.singletonList(emptyAppointmentBook),
                appointmentBookWithAliceAppointment,
                Collections.singletonList(appointmentBookWithBensonAppointment));
    }

    @Test
    public void undo_multipleAppointmentBookPointerNotAtStartOfStateList_success() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(
                emptyAppointmentBook, appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment);
        shiftCurrentStatePointerLeftwards(versionedAppointmentBook, 1);

        versionedAppointmentBook.undo();
        assertAppointmentBookListStatus(versionedAppointmentBook,
                Collections.emptyList(),
                emptyAppointmentBook,
                Arrays.asList(appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment));
    }

    @Test
    public void undo_singleAppointmentBook_throwsNoUndoableStateException() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(emptyAppointmentBook);

        assertThrows(NoUndoableStateException.class, versionedAppointmentBook::undo);
    }

    @Test
    public void undo_multipleAppointmentBookPointerAtStartOfStateList_throwsNoUndoableStateException() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(
                emptyAppointmentBook, appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment);
        shiftCurrentStatePointerLeftwards(versionedAppointmentBook, 2);

        assertThrows(NoUndoableStateException.class, versionedAppointmentBook::undo);
    }

    @Test
    public void redo_multipleAppointmentBookPointerNotAtEndOfStateList_success() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(
                emptyAppointmentBook, appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment);
        shiftCurrentStatePointerLeftwards(versionedAppointmentBook, 1);

        versionedAppointmentBook.redo();
        assertAppointmentBookListStatus(versionedAppointmentBook,
                Arrays.asList(emptyAppointmentBook, appointmentBookWithAliceAppointment),
                appointmentBookWithBensonAppointment,
                Collections.emptyList());
    }

    @Test
    public void redo_multipleAppointmentBookPointerAtStartOfStateList_success() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(
                emptyAppointmentBook, appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment);
        shiftCurrentStatePointerLeftwards(versionedAppointmentBook, 2);

        versionedAppointmentBook.redo();
        assertAppointmentBookListStatus(versionedAppointmentBook,
                Collections.singletonList(emptyAppointmentBook),
                appointmentBookWithAliceAppointment,
                Collections.singletonList(appointmentBookWithBensonAppointment));
    }

    @Test
    public void redo_singleAppointmentBook_throwsNoRedoableStateException() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(emptyAppointmentBook);

        assertThrows(NoRedoableStateException.class, versionedAppointmentBook::redo);
    }

    @Test
    public void redo_multipleAppointmentBookPointerAtEndOfStateList_throwsNoRedoableStateException() {
        VersionedAppointmentBook versionedAppointmentBook = prepareAppointmentBookList(
                emptyAppointmentBook, appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment);

        assertThrows(NoRedoableStateException.class, versionedAppointmentBook::redo);
    }

    @Test
    public void equals() {
        VersionedAppointmentBook versionedAppointmentBook =
                prepareAppointmentBookList(appointmentBookWithAliceAppointment, appointmentBookWithBensonAppointment);

        // same values -> returns true
        VersionedAppointmentBook copy = prepareAppointmentBookList(appointmentBookWithAliceAppointment,
                appointmentBookWithBensonAppointment);
        assertTrue(versionedAppointmentBook.equals(copy));

        // same object -> returns true
        assertTrue(versionedAppointmentBook.equals(versionedAppointmentBook));

        // null -> returns false
        assertFalse(versionedAppointmentBook.equals(null));

        // different types -> returns false
        assertFalse(versionedAppointmentBook.equals(1));

        // different current state -> returns false
        VersionedAppointmentBook differentCurrentState = prepareAppointmentBookList(
                appointmentBookWithAliceAppointment, appointmentBookWithHoonAppointment);
        assertFalse(versionedAppointmentBook.equals(differentCurrentState));

        // different state list -> returns false
        VersionedAppointmentBook differentAppointmentBookList = prepareAppointmentBookList(
                appointmentBookWithBensonAppointment, appointmentBookWithBensonAppointment);
        assertFalse(versionedAppointmentBook.equals(differentAppointmentBookList));

        // different current pointer index -> returns false
        versionedAppointmentBook = prepareAppointmentBookList(
                appointmentBookWithBensonAppointment, appointmentBookWithBensonAppointment);
        VersionedAppointmentBook differentCurrentStatePointer = prepareAppointmentBookList(
                appointmentBookWithBensonAppointment, appointmentBookWithBensonAppointment);
        shiftCurrentStatePointerLeftwards(differentCurrentStatePointer, 1);
        assertFalse(versionedAppointmentBook.equals(differentCurrentStatePointer));
    }

    /**
     * Asserts that {@code versionedAppointmentBook
     * } is currently pointing at {@code expectedCurrentState},
     * states before {@code versionedAppointmentBook
     * #currentStatePointer} is equal to {@code expectedStatesBeforePointer},
     * and states after {@code versionedAppointmentBook
     * #currentStatePointer} is equal to {@code expectedStatesAfterPointer}.
     */
    private void assertAppointmentBookListStatus(VersionedAppointmentBook versionedAppointmentBook,
                                                 List<ReadOnlyAppointmentBook> expectedStatesBeforePointer,
                                                 ReadOnlyAppointmentBook expectedCurrentState,
                                                 List<ReadOnlyAppointmentBook> expectedStatesAfterPointer) {
        // check state currently pointing at is correct
        assertEquals(new AppointmentBook(versionedAppointmentBook), expectedCurrentState);

        // shift pointer to start of state list
        while (versionedAppointmentBook.canUndo()) {
            versionedAppointmentBook.undo();
        }

        // check states before pointer are correct
        for (ReadOnlyAppointmentBook expectedAppointmentBook : expectedStatesBeforePointer) {
            assertEquals(expectedAppointmentBook, new AppointmentBook(versionedAppointmentBook));
            versionedAppointmentBook.redo();
        }

        // check states after pointer are correct
        for (ReadOnlyAppointmentBook expectedAppointmentBook : expectedStatesAfterPointer) {
            versionedAppointmentBook.redo();
            assertEquals(expectedAppointmentBook, new AppointmentBook(versionedAppointmentBook));
        }

        // check that there are no more states after pointer
        assertFalse(versionedAppointmentBook.canRedo());

        // revert pointer to original position
        expectedStatesAfterPointer.forEach(unused -> versionedAppointmentBook.undo());
    }

    /**
     * Creates and returns a {@code VersionedAppointmentBook} with the {@code appointmentBookStates} added into it,
     * and the {@code VersionedAppointmentBook#currentStatePointer} at the end of list.
     */
    private VersionedAppointmentBook prepareAppointmentBookList(ReadOnlyAppointmentBook... appointmentBookStates) {
        assertFalse(appointmentBookStates.length == 0);

        VersionedAppointmentBook versionedAppointmentBook = new VersionedAppointmentBook(appointmentBookStates[0]);
        for (int i = 1; i < appointmentBookStates.length; i++) {
            versionedAppointmentBook.resetData(appointmentBookStates[i]);
            versionedAppointmentBook.commit();
        }

        return versionedAppointmentBook;
    }

    /**
     * Shifts the {@code versionedAppointmentBook
     * #currentStatePointer} by {@code count} to the left of its list.
     */
    private void shiftCurrentStatePointerLeftwards(VersionedAppointmentBook versionedAppointmentBook, int count) {
        for (int i = 0; i < count; i++) {
            versionedAppointmentBook.undo();
        }
    }
}

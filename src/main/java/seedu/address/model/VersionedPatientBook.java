package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

public class VersionedPatientBook extends PatientBook {
    private final List<ReadOnlyPatientBook> patientBookStateList;
    private int currentStatePointer;

    /**
     * Initialize a versioned patient book with the given initial state.
     */
    public VersionedPatientBook(ReadOnlyPatientBook initialState) {
        super(initialState);

        patientBookStateList = new ArrayList<>();
        patientBookStateList.add(new PatientBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code PatientBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        patientBookStateList.add(new PatientBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        patientBookStateList.subList(currentStatePointer + 1, patientBookStateList.size()).clear();
    }

    /**
     * Restores the patient book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(patientBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the patient book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(patientBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has patient book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has patient book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < patientBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedPatientBook)) {
            return false;
        }

        VersionedPatientBook versionedPatientBook = (VersionedPatientBook) other;

        // state check
        return super.equals(versionedPatientBook)
                && patientBookStateList.equals(versionedPatientBook.patientBookStateList)
                && currentStatePointer == versionedPatientBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of patientBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of patientBookState list, unable to redo.");
        }
    }
}

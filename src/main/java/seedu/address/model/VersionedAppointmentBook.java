package seedu.address.model;

import java.util.ArrayList;
import java.util.List;

public class VersionedAppointmentBook extends AppointmentBook {
    private final List<ReadOnlyAppointmentBook> appointmentBookStateList;
    private int currentStatePointer;

    /**
     * Initialize a versioned appointment book with the given initial state.
     */
    public VersionedAppointmentBook(ReadOnlyAppointmentBook initialState) {
        super(initialState);

        appointmentBookStateList = new ArrayList<>();
        appointmentBookStateList.add(new AppointmentBook(initialState));
        currentStatePointer = 0;
    }

    /**
     * Saves a copy of the current {@code AppointmentBook} state at the end of the state list.
     * Undone states are removed from the state list.
     */
    public void commit() {
        removeStatesAfterCurrentPointer();
        appointmentBookStateList.add(new AppointmentBook(this));
        currentStatePointer++;
    }

    private void removeStatesAfterCurrentPointer() {
        appointmentBookStateList.subList(currentStatePointer + 1, appointmentBookStateList.size()).clear();
    }

    /**
     * Restores the appointment book to its previous state.
     */
    public void undo() {
        if (!canUndo()) {
            throw new NoUndoableStateException();
        }
        currentStatePointer--;
        resetData(appointmentBookStateList.get(currentStatePointer));
    }

    /**
     * Restores the appointment book to its previously undone state.
     */
    public void redo() {
        if (!canRedo()) {
            throw new NoRedoableStateException();
        }
        currentStatePointer++;
        resetData(appointmentBookStateList.get(currentStatePointer));
    }

    /**
     * Returns true if {@code undo()} has appointment book states to undo.
     */
    public boolean canUndo() {
        return currentStatePointer > 0;
    }

    /**
     * Returns true if {@code redo()} has appointment book states to redo.
     */
    public boolean canRedo() {
        return currentStatePointer < appointmentBookStateList.size() - 1;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VersionedAppointmentBook)) {
            return false;
        }

        VersionedAppointmentBook versionedAppointmentBook = (VersionedAppointmentBook) other;

        // state check
        return super.equals(versionedAppointmentBook)
                && appointmentBookStateList.equals(versionedAppointmentBook.appointmentBookStateList)
                && currentStatePointer == versionedAppointmentBook.currentStatePointer;
    }

    /**
     * Thrown when trying to {@code undo()} but can't.
     */
    public static class NoUndoableStateException extends RuntimeException {
        private NoUndoableStateException() {
            super("Current state pointer at start of appointmentBookState list, unable to undo.");
        }
    }

    /**
     * Thrown when trying to {@code redo()} but can't.
     */
    public static class NoRedoableStateException extends RuntimeException {
        private NoRedoableStateException() {
            super("Current state pointer at end of appointmentBookState list, unable to redo.");
        }
    }
}

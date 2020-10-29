package seedu.address.model.exceptions;

/**
 * Thrown when trying to {@code undo()} but can't.
 */
public class NoUndoableStateException extends RuntimeException {
    public NoUndoableStateException(String message) {
        super(message);
    }
}

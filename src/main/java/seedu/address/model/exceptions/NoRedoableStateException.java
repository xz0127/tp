package seedu.address.model.exceptions;

/**
 * Thrown when trying to {@code redo()} but can't.
 */
public class NoRedoableStateException extends RuntimeException {
    public NoRedoableStateException(String message) {
        super(message);
    }
}

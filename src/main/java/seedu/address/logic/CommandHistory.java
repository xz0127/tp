package seedu.address.logic;

import static java.util.Objects.requireNonNull;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Represents a history of command inputs entered.
 */
public class CommandHistory {
    private final ObservableList<String> internalHistoryList = FXCollections.observableArrayList();
    private final ObservableList<String> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalHistoryList);

    /**
     * Adds a history to the list.
     * If input string is empty, the input is not added.
     */
    public void add(String commandInput) {
        requireNonNull(commandInput);

        if (commandInput.isEmpty()) {
            return;
        }

        internalHistoryList.add(commandInput);
    }

    /**
     * Returns the command history list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<String> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }
}

package seedu.address.ui;

import java.util.List;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private List<String> commandHistory;
    private int historyPointer;

    @FXML
    private TextField commandTextField;

    /**
     * Creates a {@code CommandBox} with the given {@code CommandExecutor}.
     */
    public CommandBox(List<String> commandHistory, CommandExecutor commandExecutor) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        resetHistory(commandHistory);
    }

    private void resetHistory(List<String> commandHistory) {
        this.commandHistory = commandHistory;
        this.historyPointer = commandHistory.size();
    }

    /**
     * Checks if a previous command input exists.
     */
    private boolean hasPreviousHistory() {
        return historyPointer > 0;
    }

    /**
     * Gets previous command input.
     */
    private void getPreviousHistory() {
        if (hasPreviousHistory()) {
            historyPointer--;
            setInput(commandHistory.get(historyPointer));
        }
    }

    /**
     * Checks if the next command input exists.
     */
    private boolean hasNextHistory() {
        return historyPointer < commandHistory.size() - 1;
    }

    /**
     * Gets next command input.
     */
    private void getNextHistory() {
        if (hasNextHistory()) {
            historyPointer++;
            setInput(commandHistory.get(historyPointer));
        } else {
            historyPointer = commandHistory.size();
            setInput("");
        }
    }

    private void setInput(String input) {
        commandTextField.setText(input);
        commandTextField.positionCaret(input.length());
    }

    @FXML
    private void handleKeyPressed(KeyEvent event) {
        switch (event.getCode()) {
        case UP:
            getPreviousHistory();
            break;
        case DOWN:
            getNextHistory();
            break;
        default:
            break;
        }
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
            commandTextField.setText("");
            resetHistory(commandHistory);
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

}

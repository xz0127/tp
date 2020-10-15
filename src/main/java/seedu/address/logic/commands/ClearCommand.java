package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;

/**
 * Clears the patient book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Appointment book has been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAppointmentBook(new AppointmentBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

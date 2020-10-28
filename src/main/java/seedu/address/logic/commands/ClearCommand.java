package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;
import seedu.address.model.PatientBook;

/**
 * Clears the patient book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All data has been successfully cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAppointmentBook(new AppointmentBook());
        model.setPatientBook(new PatientBook());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

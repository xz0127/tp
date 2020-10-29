package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PATIENTS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;


/**
 * Reverts the {@code model}'s appointment book and patient book to their previously undone state.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";
    public static final String MESSAGE_SUCCESS = "Redo success!";
    public static final String MESSAGE_FAILURE = "No more commands to redo!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.canRedoAppointmentBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }
        if (!model.canRedoPatientBook()) {
            throw new CommandException(MESSAGE_FAILURE);
        }

        model.redoAppointmentBook();
        model.redoPatientBook();
        model.updateFilteredPatientList(PREDICATE_SHOW_ALL_PATIENTS);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}

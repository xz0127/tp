package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Marks an appointment in the list as done.
 */
public class DoneCommand extends Command {
    public static final String COMMAND_WORD = "done";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Mark the appointment "
            + "specified by the date and time as done. \n"
            + "Parameters: APPT_INDEX (must be a positive integer)"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_DONE_SUCCESS = "Marked Appointment as done: %1$s";
    public static final String APPOINTMENT_HAS_BEEN_MARKED = "The appointment has been marked already!";

    private final Index targetIndex;

    /**
     * Creates a DoneCommand to mark the specific appointment as done.
     * @param targetIndex index of the specified appointment.
     */
    public DoneCommand(Index targetIndex) {
        requireAllNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownAppointmentList = model.getFilteredAppointmentList();

        if (targetIndex.getZeroBased() >= lastShownAppointmentList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment toMark = lastShownAppointmentList.get(targetIndex.getZeroBased());

        if (toMark.getIsDoneStatus()) {
            throw new CommandException(APPOINTMENT_HAS_BEEN_MARKED);
        }
        Appointment doneAppointment = toMark.markAsDone();

        model.setAppointment(toMark, doneAppointment);
        model.commitPatientBook();
        model.commitAppointmentBook();
        return new CommandResult(String.format(MESSAGE_MARK_DONE_SUCCESS, toMark));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneCommand // instanceof handles nulls
                && targetIndex.equals(((DoneCommand) other).targetIndex)); // state check
    }

}

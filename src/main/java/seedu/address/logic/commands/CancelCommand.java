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
 * Cancels and deletes an appointment in the appointment list.
 */
public class CancelCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels the appointment "
            + "specified by the date and time and removes it from the appointment book. \n"
            + "Parameters: APPT_INDEX (must be a positive integer)"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MARK_CANCEL_SUCCESS = "Cancelled Appointment: %1$s";

    private final Index targetIndex;

    /**
     * Creates a CancelCommand to delete the specified appointment from the appointment book.
     * @param targetIndex index of the specified appointment.
     */
    public CancelCommand(Index targetIndex) {
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

        Appointment toCancel = lastShownAppointmentList.get(targetIndex.getZeroBased());

        model.deleteAppointment(toCancel);
        // model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        model.commitPatientBook();
        model.commitAppointmentBook();
        return new CommandResult(String.format(MESSAGE_MARK_CANCEL_SUCCESS, toCancel));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CancelCommand // instanceof handles nulls
                && targetIndex.equals(((CancelCommand) other).targetIndex)); // state check
    }

}

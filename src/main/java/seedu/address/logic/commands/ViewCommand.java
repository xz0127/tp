package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_APPOINTMENTS_OVERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.function.Predicate;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Lists all Appointments in the appointment book to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views all appointments [on a date]"
            + "Parameters: "
            + "[" + PREFIX_DATE + "DATE ]"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "2020-09-11"
            + "or \n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Viewed all Appointments";

    private final Predicate<Appointment> predicate;

    /**
     * Creates a ViewCommand to list the appointments on a date.
     */
    public ViewCommand() {
        this.predicate = null;
    }

    /**
     * Creates a ViewCommand to list the appointments on a date.
     * @param predicate date of the appointments.
     */
    public ViewCommand(Predicate<Appointment> predicate) {
        requireNonNull(predicate);

        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredAppointmentList(predicate);
        return new CommandResult(
                String.format(
                        MESSAGE_APPOINTMENTS_OVERVIEW, model.getFilteredAppointmentList().size())
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewCommand
                    && predicate.equals(((ViewCommand) other).predicate));
    }
}

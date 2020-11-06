package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Lists all Appointments in the appointment book to the user.
 */
public class ViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Views all appointments [on a date]\n"
            + "Parameters: "
            + "[" + PREFIX_DATE + "DATE]\n"
            + "Example: " + COMMAND_WORD + " or "
            + COMMAND_WORD + " "
            + PREFIX_DATE + "12-Dec-2021 ";

    public static final String MESSAGE_SUCCESS = "Viewing all Appointments";

    private final Predicate<Appointment> predicate;

    /**
     * Creates a ViewCommand to list the appointments on a date.
     *
     * @param predicate date of the appointments.
     */
    public ViewCommand(Predicate<Appointment> predicate) {
        requireNonNull(predicate);

        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(predicate);

        if (predicate.equals(PREDICATE_SHOW_ALL_APPOINTMENTS)) {
            return new CommandResult(MESSAGE_SUCCESS);
        }

        return new CommandResult(
                String.format(
                        MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, model.getFilteredAppointmentList().size())
        );
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof ViewCommand
                    && predicate.equals(((ViewCommand) other).predicate));
    }
}

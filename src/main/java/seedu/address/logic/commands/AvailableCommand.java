package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;

/**
 * Lists all available time slots on a specific date.
 */
public class AvailableCommand extends Command {

    public static final String COMMAND_WORD = "avail";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all available time slots [on a date]\n"
        + "Parameters: "
        + "[" + PREFIX_DATE + "DATE]\n"
        + "Example: " + COMMAND_WORD + " "
        + PREFIX_DATE + "12-Apr-2021 ";

    public static final String MESSAGE_SUCCESS = "Listing all available time slots: \n";

    private final Predicate<Appointment> predicate;

    private final boolean isToday;

    /**
     * Creates a AvailableCommand to find all available time slots on a date.
     *
     * @param predicate date of the appointments.
     */
    public AvailableCommand(Predicate<Appointment> predicate, boolean isToday) {
        requireNonNull(predicate);

        this.predicate = predicate;
        this.isToday = isToday;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredAppointmentList(predicate);

        List<Appointment> lastShownAppointmentList = model.getFilteredAppointmentList();
        String timeSlotsMessage = model.findAvailableTimeSlots(lastShownAppointmentList, isToday);

        return new CommandResult(MESSAGE_SUCCESS + timeSlotsMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof AvailableCommand
            && (predicate.equals(((AvailableCommand) other).predicate)
            && isToday == ((AvailableCommand) other).isToday));
    }
}

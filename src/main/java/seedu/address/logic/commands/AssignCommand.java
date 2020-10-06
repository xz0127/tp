package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;


/**
 * Assigns an appointment to Nuudle.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an appointment to Nuudle. "
            + "Parameters: "
            + "PATIENT INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_DATE + "Sunday"
            + PREFIX_TIME + "2am";

    public static final String MESSAGE_SUCCESS = "New Appointment added: %1$s";
    public static final String TIME_OVERLAP = "This time slot is occupied";
    public static final String DATE_MISSING = "The date of appointment is missing";
    public static final String TIME_MISSING = "The time of appointment is missing";

    private final Index targetIndex;
    private final Date date;
    private final Time time;

    /**
     * Creates an AssignCommand to add a new {@code Appointment}
     * @param targetIndex index of the patient in the list.
     * @param date date of the assigned appointment.
     * @param time time of the assigned appointment.
     */
    public AssignCommand(Index targetIndex, Date date, Time time) {
        requireNonNull(targetIndex);
        requireNonNull(date);
        requireNonNull(time);

        this.targetIndex = targetIndex;
        this.date = date;
        this.time = time;
    }

    // todo
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignCommand // instanceof handles nulls
                && targetIndex.equals(((AssignCommand) other).targetIndex)); // state check
    }
}

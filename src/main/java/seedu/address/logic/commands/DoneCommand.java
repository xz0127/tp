package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.util.List;
import java.util.Optional;

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
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "12-Dec-2021 "
            + PREFIX_TIME + "4:00PM";

    public static final String MESSAGE_MARK_DONE_SUCCESS = "Marked Appointment as done: %1$s";
    public static final String APPOINTMENT_DOES_NOT_EXISTS = "There is no appointment at this time slot";
    public static final String APPOINTMENT_HAS_BEEN_MARKED = "The appointment has been marked already!";
    public static final String DATE_MISSING = "The date of appointment is missing";
    public static final String TIME_MISSING = "The time of appointment is missing";

    private final DateTimeLoader dateTimeLoader;
    /**
     * Creates a DoneCommand to mark the specific appointment as done.
     * @param dateTimeLoader details of an appointment.
     */
    public DoneCommand(DateTimeLoader dateTimeLoader) {
        requireAllNonNull(dateTimeLoader);
        this.dateTimeLoader = new DateTimeLoader(dateTimeLoader);
    }

    public DateTimeLoader getDateTimeLoader() {
        return this.dateTimeLoader;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownAppointmentList = model.getFilteredAppointmentList();

        Optional<Appointment> appointmentToMark = lastShownAppointmentList.stream()
                .filter(appointment -> appointment.startAtSameTime(dateTimeLoader.getDate().get(),
                        dateTimeLoader.getTime().get()))
                .findAny();
        Appointment toMark = appointmentToMark.orElse(null);
        if (toMark == null) {
            throw new CommandException(APPOINTMENT_DOES_NOT_EXISTS);
        }

        if (toMark.getIsDoneStatus()) {
            throw new CommandException(APPOINTMENT_HAS_BEEN_MARKED);
        }
        Appointment doneAppointment = toMark.markAsDone();

        model.setAppointment(toMark, doneAppointment);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_MARK_DONE_SUCCESS, toMark));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DoneCommand // instanceof handles nulls
                && dateTimeLoader.equals(((DoneCommand) other).getDateTimeLoader())); // state check
    }

}

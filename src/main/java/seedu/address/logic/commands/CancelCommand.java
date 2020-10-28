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
 * Cancels and deletes an appointment in the appointment list.
 */
public class CancelCommand extends Command {
    public static final String COMMAND_WORD = "cancel";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Cancels the appointment "
            + "specified by the date and time and removes it from the appointment book. \n"
            + "Parameters: "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_DATE + "12-Dec-2021 "
            + PREFIX_TIME + "4:00PM";

    public static final String MESSAGE_MARK_CANCEL_SUCCESS = "Cancelled Appointment: %1$s";
    public static final String APPOINTMENT_DOES_NOT_EXISTS = "We can't find any appointment at this time slot";
    public static final String DATE_MISSING = "Hmm it seems that the date of the appointment is missing";
    public static final String TIME_MISSING = "Hmm it seems that the time of the appointment is missing";

    private final DateTimeLoader dateTimeLoader;
    /**
     * Creates a CancelCommand to delete the specified appointment from the appointment book.
     * @param dateTimeLoader details of an appointment.
     */
    public CancelCommand(DateTimeLoader dateTimeLoader) {
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

        Optional<Appointment> appointmentToCancel = lastShownAppointmentList.stream()
                .filter(appointment -> appointment.startAtSameTime(dateTimeLoader.getDate().get(),
                        dateTimeLoader.getTime().get()))
                .findAny();
        Appointment toCancel = appointmentToCancel.orElse(null);
        if (toCancel == null) {
            throw new CommandException(APPOINTMENT_DOES_NOT_EXISTS);
        }

        model.deleteAppointment(toCancel);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        model.commitPatientBook();
        model.commitAppointmentBook();
        return new CommandResult(String.format(MESSAGE_MARK_CANCEL_SUCCESS, toCancel));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CancelCommand // instanceof handles nulls
                && dateTimeLoader.equals(((CancelCommand) other).getDateTimeLoader())); // state check
    }

}

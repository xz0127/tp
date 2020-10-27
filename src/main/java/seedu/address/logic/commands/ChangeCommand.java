package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EXPIRED_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;
import static seedu.address.model.appointment.Appointment.CREATION_OFFSET_MINUTES;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.patient.Patient;

/**
 * Edits the details of an existing appointment in the appointment book.
 */
public class ChangeCommand extends Command {

    public static final String COMMAND_WORD = "change";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Reschedules an existing appointment for a patient "
            + "by the index number used in the displayed appointment list. "
            + "A new appointment will be created with the input values.\n"
            + "The old appointment at the input index will be deleted from the appointment book.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_DURATION + "DURATION] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "12-Dec-2021 "
            + PREFIX_TIME + "4:00PM"
            + PREFIX_DURATION + "30";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the appointment "
            + "book.";

    public static final String ASSIGNMENT_OVERLAP = "This time slot is occupied";
    public static final String DATE_MISSING = "The date of appointment is missing";
    public static final String TIME_MISSING = "The time of appointment is missing";
    public static final String DURATION_MISSING = "The duration of appointment is missing";

    private final Index index;
    private final EditAppointmentDescriptor editAppointmentDescriptor;

    /**
     * Creates a ChangeCommand with an {@code Index} and {@code EditAppointmentDescriptor}.
     * @param index of the appointment in the filtered appointment list to edit.
     * @param editAppointmentDescriptor details to edit the appointment with.
     */
    public ChangeCommand(Index index, EditAppointmentDescriptor editAppointmentDescriptor) {
        requireNonNull(index);
        requireNonNull(editAppointmentDescriptor);

        this.index = index;
        this.editAppointmentDescriptor = new EditAppointmentDescriptor(editAppointmentDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Appointment> lastShownList = model.getFilteredAppointmentList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX);
        }

        Appointment appointmentToEdit = lastShownList.get(index.getZeroBased());
        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);

        if (appointmentToEdit.equals(editedAppointment) && model.hasAppointment(editedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }
        model.deleteAppointment(appointmentToEdit);
        if (model.hasOverlappingAppointment(editedAppointment)) {
            model.addAppointment(appointmentToEdit);
            throw new CommandException(ASSIGNMENT_OVERLAP);
        }
        model.addAppointment(appointmentToEdit);
        model.setAppointment(appointmentToEdit, editedAppointment);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment));
    }

    /**
     * Creates and returns an {@code Appointment} with the details of {@code appointmentToEdit}.
     * edited with {@code editAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppointmentDescriptor editAppointmentDescriptor) throws CommandException {
        assert appointmentToEdit != null;
        assert editAppointmentDescriptor != null;
//        assert editAppointmentDescriptor.duration != null;
//        assert editAppointmentDescriptor.date != null;
//        assert editAppointmentDescriptor.startTime != null;

        Date date;
        Time startTime;
        Duration duration;
        Time endTime;

        if (editAppointmentDescriptor.date != null) {
            date = editAppointmentDescriptor.getDate().get();
        } else {
            date = appointmentToEdit.getDate();
        }

        if (editAppointmentDescriptor.startTime != null) {
            startTime = editAppointmentDescriptor.getStartTime().get();
        } else {
            startTime = appointmentToEdit.getStartTime();
        }

        if (editAppointmentDescriptor.duration != null) {
            duration = editAppointmentDescriptor.getDuration().get();
        } else {
            duration = appointmentToEdit.getDuration();
        }
        endTime = new Time(startTime.getTime().plus(duration));
        Patient patient = appointmentToEdit.getPatient();

        assert date != null;
        assert startTime != null;
        assert endTime != null;
        assert patient != null;

        LocalTime timeWithLeeway = startTime.getTime().plusMinutes(CREATION_OFFSET_MINUTES);
        if (DateTimeUtil.isExpired(date.getDate(), timeWithLeeway)) {
            throw new CommandException(MESSAGE_EXPIRED_DATE_TIME);
        }

        return new Appointment(date, startTime, endTime, patient);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangeCommand)) {
            return false;
        }

        // state check
        ChangeCommand e = (ChangeCommand) other;
        return index.equals(e.index)
                && editAppointmentDescriptor.equals(e.editAppointmentDescriptor);
    }

    /**
     * Stores the details to edit the appointment with. Each field value will replace the
     * corresponding field value of the appointment.
     */
    public static class EditAppointmentDescriptor {
        private Date date;
        private Time startTime;
        private Duration duration;

        public EditAppointmentDescriptor() {
            this.startTime = null;
            this.date = null;
            this.duration = null;
        }

        /**
         * Creates an EditAppointmentDescriptor object to store the new appointment details.
         * @param date Date of the new appointment.
         * @param startTime Starting time of the new appointment.
         * @param duration Duration of the new appointment.
         */
        public EditAppointmentDescriptor(Date date, Time startTime, Duration duration) {
            CollectionUtil.requireAllNonNull(date, startTime, duration);
            this.date = date;
            this.startTime = startTime;
            this.duration = duration;
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAppointmentDescriptor(EditAppointmentDescriptor toCopy) {
            setDate(toCopy.date);
            setStartTime(toCopy.startTime);
            setDuration(toCopy.duration);
        }

        public void setStartTime(Time startTime) {
            this.startTime = startTime;
        }

        public Optional<Time> getStartTime() {
            return Optional.ofNullable(startTime);
        }

        public void setDuration(Duration duration) {
            this.duration = duration;
        }

        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAppointmentDescriptor)) {
                return false;
            }

            // state check
            EditAppointmentDescriptor e = (EditAppointmentDescriptor) other;

            return getDate().equals(e.getDate())
                    && getStartTime().equals(e.getStartTime())
                    && getDuration().equals(e.getDuration());
        }
    }
}

package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_EXPIRED_DATE_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_DURATION;
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
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;
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
            + "Parameters: APPT_INDEX (must be a positive integer) "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_TIME + "TIME] "
            + "[" + PREFIX_DURATION + "DURATION] \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "12-Dec-2021 "
            + PREFIX_TIME + "4:00PM"
            + PREFIX_DURATION + "30";

    public static final String MESSAGE_EDIT_APPOINTMENT_SUCCESS = "Edited Appointment: %1$s";
    public static final String MESSAGE_SAME_APPOINTMENT = "Hmm, it appears there is no change to the details of your "
            + "original appointment with this input.";
    public static final String MESSAGE_DUPLICATE_APPOINTMENT = "This appointment already exists in the appointment "
            + "book for this patient with the same time-slot.";

    public static final String APPOINTMENT_OVERLAP = "Hmm it seems this time slot is occupied. Please choose another "
            + "time-slot to schedule your appointment :)";

    public static final String APPOINTMENT_DONE = "Hmm, it appears the appointment you've selected is already marked"
            + " as done.\nDone appointments cannot be rescheduled.\nPlease select another appointment or create a new"
            + "appointment with a new time-slot :)";

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

        if (appointmentToEdit.getIsDoneStatus()) {
            throw new CommandException(APPOINTMENT_DONE);
        }

        Appointment editedAppointment = createEditedAppointment(appointmentToEdit, editAppointmentDescriptor);

        if (appointmentToEdit.equals(editedAppointment)) {
            throw new CommandException(MESSAGE_SAME_APPOINTMENT);
        }

        if (!appointmentToEdit.startAtSameTime(editedAppointment.getDate(), editedAppointment.getStartTime())
                && model.hasAppointment(editedAppointment)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPOINTMENT);
        }

        try {
            model.setAppointment(appointmentToEdit, editedAppointment);
        } catch (OverlappingAppointmentException e) {
            throw new CommandException(APPOINTMENT_OVERLAP);
        }
        // model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        model.commitPatientBook();
        model.commitAppointmentBook();
        return new CommandResult(String.format(MESSAGE_EDIT_APPOINTMENT_SUCCESS, editedAppointment));
    }

    /**
     * Creates and returns an {@code Appointment} with the details of {@code appointmentToEdit}.
     * edited with {@code editAppointmentDescriptor}.
     */
    private static Appointment createEditedAppointment(Appointment appointmentToEdit,
                                                       EditAppointmentDescriptor editAppointmentDescriptor)
                                                       throws CommandException {
        assert appointmentToEdit != null;
        assert editAppointmentDescriptor != null;

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

        try {
            endTime = new Time(startTime.getTime().plus(duration));
        } catch (IllegalArgumentException e) {
            throw new CommandException(e.getMessage());
        }
        Patient patient = appointmentToEdit.getPatient();

        assert date != null;
        assert startTime != null;
        assert endTime != null;
        assert patient != null;

        LocalTime timeWithLeeway = startTime.getTime().plusMinutes(CREATION_OFFSET_MINUTES);
        if (DateTimeUtil.isExpired(date.getDate(), timeWithLeeway)) {
            throw new CommandException(MESSAGE_EXPIRED_DATE_TIME);
        }
        try {
            return new Appointment(date, startTime, endTime, patient);
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_INVALID_DURATION);
        }
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

        /**
         * Creates an EditAppointmentDescriptor with all variables initialised to null.
         */
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

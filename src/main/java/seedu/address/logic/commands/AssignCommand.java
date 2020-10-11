package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPOINTMENTS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.patient.Patient;

/**
 * Assigns an appointment to an existing patient.
 */
public class AssignCommand extends Command {

    public static final String COMMAND_WORD = "assign";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an appointment to an existing patient. "
            + "Parameters: "
            + "PATIENT INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME \n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_DATE + "12-Dec-2021 "
            + PREFIX_TIME + "4:00PM";

    public static final String MESSAGE_SUCCESS = "New Appointment added: %1$s";
    public static final String ASSIGNMENT_OVERLAP = "This time slot is occupied";
    public static final String DATE_MISSING = "The date of appointment is missing";
    public static final String TIME_MISSING = "The time of appointment is missing";

    private final Index targetIndex;
    private final DateTimeLoader dateTimeLoader;
    /**
     * Creates an AssignCommand to add a new {@code Appointment}
     * @param targetIndex index of the patient in the list.
     * @param dateTimeLoader details of an appointment.
     */
    public AssignCommand(Index targetIndex, DateTimeLoader dateTimeLoader) {
        requireAllNonNull(targetIndex, dateTimeLoader);

        this.targetIndex = targetIndex;
        this.dateTimeLoader = new DateTimeLoader(dateTimeLoader);
    }

    // todo
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownPatientList = model.getFilteredPatientList();

        if (targetIndex.getZeroBased() >= lastShownPatientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patient = lastShownPatientList.get(targetIndex.getZeroBased());
        Appointment appointment = createAppointment(patient, dateTimeLoader);

        if (model.hasAppointment(appointment)) {
            throw new CommandException(ASSIGNMENT_OVERLAP);
        }

        model.addAppointment(appointment);
        model.updateFilteredAppointmentList(PREDICATE_SHOW_ALL_APPOINTMENTS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, appointment));
    }

    /**
     * Creates and returns an {@code Appointment} with merged details of {@code patient}
     * and {@code assignAppointmentBuilder}
     */
    private static Appointment createAppointment(Patient patient, DateTimeLoader dateTimeLoader) {
        assert patient != null;
        assert dateTimeLoader.getDate().isPresent();
        assert dateTimeLoader.getTime().isPresent();

        Date assignedDate = dateTimeLoader.getDate().get();
        Time assignedTime = dateTimeLoader.getTime().get();

        return new Appointment(assignedDate, assignedTime, patient);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignCommand // instanceof handles nulls
                && targetIndex.equals(((AssignCommand) other).targetIndex)); // state check
    }

    /**
     * Stores the details to the appointment to be assigned.
     * Each non=empty field value will replace the corresponding field value of the Appointment.
     */
    public static class DateTimeLoader {
        private Date date;
        private Time time;

        public DateTimeLoader() {}

        /**
         * Copy constructor.
         */
        public DateTimeLoader(DateTimeLoader toCopy) {
            setAppointmentDate(toCopy.date);
            setAppointmentTime(toCopy.time);
        }

        /**
         * Sets {@code date} of this AssignAppointmentBuilder object.
         */
        public void setAppointmentDate(Date date) {
            this.date = date;
        }

        /**
         * Gets {@code date} of this AssignAppointmentBuilder object.
         */
        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        /**
         * Sets {@code time} of this AssignAppointmentBuilder object.
         */
        public void setAppointmentTime(Time time) {
            this.time = time;
        }

        /**
         * Gets {@code time} of this AssignAppointmentBuilder object.
         */
        public Optional<Time> getTime() {
            return Optional.ofNullable(time);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DateTimeLoader)) {
                return false;
            }

            // state check
            DateTimeLoader loader = (DateTimeLoader) other;

            return getDate().equals(loader.getDate())
                    && getTime().equals(loader.getTime());
        }
    }
}

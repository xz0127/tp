package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;

import java.time.Duration;
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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Assigns an appointment to an existing patient.\n"
            + "Parameters: "
            + "PATIENT_INDEX (must be a positive integer) "
            + PREFIX_DATE + "DATE "
            + PREFIX_TIME + "TIME "
            + "[" + PREFIX_DURATION + "DURATION] (minute in unit) \n"
            + "Example: " + COMMAND_WORD + " "
            + "1 "
            + PREFIX_DATE + "12-Dec-2021 "
            + PREFIX_TIME + "4:00PM"
            + PREFIX_DURATION + "30";

    public static final String MESSAGE_SUCCESS = "New Appointment added: %1$s";
    public static final String ASSIGNMENT_OVERLAP = "This time slot is occupied";
    public static final String DATE_MISSING = "The date of appointment is missing";
    public static final String TIME_MISSING = "The time of appointment is missing";

    private final Index targetIndex;
    private final DurationSupporter durationSupporter;

    /**
     * Creates an AssignCommand to add a new {@code Appointment}
     *
     * @param targetIndex    index of the patient in the list.
     * @param durationSupporter details of an appointment.
     */
    public AssignCommand(Index targetIndex, DurationSupporter durationSupporter) {
        requireAllNonNull(targetIndex, durationSupporter);

        this.targetIndex = targetIndex;
        this.durationSupporter = new DurationSupporter(durationSupporter);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Patient> lastShownPatientList = model.getFilteredPatientList();

        if (targetIndex.getZeroBased() >= lastShownPatientList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
        }

        Patient patient = lastShownPatientList.get(targetIndex.getZeroBased());
        Appointment appointment = createAppointment(patient, durationSupporter);

        if (model.hasOverlappingAppointment(appointment)) {
            throw new CommandException(ASSIGNMENT_OVERLAP);
        }

        model.addAppointment(appointment);
        model.commitAppointmentBook();
        model.commitPatientBook();
        return new CommandResult(String.format(MESSAGE_SUCCESS, appointment));
    }

    /**
     * Creates and returns an {@code Appointment} with merged details of {@code patient}
     * and {@code assignAppointmentBuilder}
     */
    private static Appointment createAppointment(Patient patient, DurationSupporter durationSupporter) {
        assert patient != null;
        assert durationSupporter.getDate().isPresent();
        assert durationSupporter.getTime().isPresent();
        assert durationSupporter.getDuration().isPresent();

        Date assignedDate = durationSupporter.getDate().get();
        Time startTime = durationSupporter.getTime().get();
        Duration assignedDuration = durationSupporter.getDuration().get();
        Time endTime = new Time(startTime.getTime().plus(assignedDuration));

        return new Appointment(assignedDate, startTime, endTime, patient);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AssignCommand // instanceof handles nulls
                && targetIndex.equals(((AssignCommand) other).targetIndex)
                && durationSupporter.equals(((AssignCommand) other).durationSupporter)); // state check
    }

    /**
     * An assistant class to help parse the parameters of an {@code AssignCommand}.
     */
    public static class DurationSupporter extends DateTimeLoader {
        private Duration duration = Appointment.DEFAULT_DURATION;

        public DurationSupporter() {}

        /**
         * Copies constructor.
         */
        public DurationSupporter(DurationSupporter toCopy) {
            setAppointmentDate(toCopy.getDate().get());
            setAppointmentTime(toCopy.getTime().get());
            setAppointmentDuration(toCopy.getDuration().get());
        }

        /**
         * Sets {@code AppointmentDuration} of this {@code DurationSupporter}.
         */
        public void setAppointmentDuration(Duration duration) {
            this.duration = duration;
        }

        /**
         * Gets {@code AppointmentDuration} of this {@code DurationSupporter}.
         */
        public Optional<Duration> getDuration() {
            return Optional.ofNullable(duration);
        }

        /**
         * Gets {@code Time} of the start of this {@code DurationSupporter}
         */
        public Optional<Time> getStartTime(Time end) {
            return Optional.of(new Time(end.getTime().minus(duration)));
        }

        /**
         * Gets {@code Time} of the end of this {@code DurationSupporter}
         */
        public Optional<Time> getEndTime(Time start) {
            return Optional.of(new Time(start.getTime().plus(duration)));
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DurationSupporter)) {
                return false;
            }

            // state check
            DurationSupporter supporter = (DurationSupporter) other;

            return getDate().equals(supporter.getDate())
                    && getTime().equals(supporter.getTime())
                    && getDuration().equals(supporter.getDuration());
        }
    }
}

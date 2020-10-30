package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.OVERLAP_TIME;
import static seedu.address.logic.commands.CommandTestUtil.SAME_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DURATION;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_APPOINTMENT;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;
import static seedu.address.testutil.TypicalPatients.getTypicalPatients;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand.DurationSupporter;
import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.testutil.DurationSupporterBuilder;


public class AssignCommandTest {

    private final Model model = new ModelManager(getTypicalPatientBook(), new AppointmentBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        DurationSupporter durationSupporter = new DurationSupporter();
        assertThrows(NullPointerException.class, () -> new AssignCommand(null, durationSupporter));
    }

    @Test
    public void constructor_nullBuilder_throwsNullPointerException() {
        Index index = Index.fromZeroBased(10);
        assertThrows(NullPointerException.class, () -> new AssignCommand(index, null));
    }

    @Test
    public void constructor_nullIndexAndBuilder_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AssignCommand(null, null));
    }

    @Test
    public void constructor_nullDurationAcceptedByModel_assignSuccessful() {
        DurationSupporter durationSupporter = new DurationSupporterBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).build();
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_APPOINTMENT, durationSupporter);
        Date date = durationSupporter.getDate().get();
        Time start = durationSupporter.getTime().get();
        Time end = durationSupporter.getEndTime(start).get();
        Appointment appointment = new Appointment(date, start, end, ALICE);

        String expectedMessage = String.format(AssignCommand.MESSAGE_SUCCESS, appointment);

        Model expectedModel = new ModelManager(model.getPatientBook(),
                model.getAppointmentBook(), new UserPrefs());
        expectedModel.addAppointment(appointment);
        expectedModel.commitPatientBook();
        expectedModel.commitAppointmentBook();
        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appointmentAcceptedByModel_assignSuccessful() {
        DurationSupporter durationSupporter = new DurationSupporterBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).withDuration(VALID_DURATION).build();
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_APPOINTMENT, durationSupporter);
        Date date = durationSupporter.getDate().get();
        Time start = durationSupporter.getTime().get();
        Time end = durationSupporter.getEndTime(start).get();
        Appointment appointment = new Appointment(date, start, end, ALICE);

        String expectedMessage = String.format(AssignCommand.MESSAGE_SUCCESS, appointment);

        Model expectedModel = new ModelManager(model.getPatientBook(),
                model.getAppointmentBook(), new UserPrefs());
        expectedModel.addAppointment(appointment);
        expectedModel.commitPatientBook();
        expectedModel.commitAppointmentBook();
        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appointmentRejectedDueToOverSizedIndex_failure() {
        Index overSizedIndex = Index.fromOneBased(getTypicalPatients().size() + 1);
        DurationSupporter durationSupporter = new DurationSupporterBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).withDuration(VALID_DURATION).build();
        AssignCommand assignCommand = new AssignCommand(overSizedIndex, durationSupporter);

        assertCommandFailure(assignCommand, model, MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_sameAppointmentRejectedDueToOverlap_failure() {
        DurationSupporter durationSupporter = new DurationSupporterBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).withDuration(VALID_DURATION).build();
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_APPOINTMENT, durationSupporter);
        Date date = durationSupporter.getDate().get();
        Time start = durationSupporter.getTime().get();
        Time end = durationSupporter.getEndTime(start).get();
        Appointment appointment = new Appointment(date, start, end, ALICE);

        model.addAppointment(appointment);

        assertCommandFailure(assignCommand, model, AssignCommand.ASSIGNMENT_OVERLAP);
    }

    @Test
    public void execute_differentAppointmentRejectedDueToOverlap_failure() {
        DurationSupporter durationSupporter = new DurationSupporterBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).withDuration(VALID_DURATION).build();
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_APPOINTMENT, durationSupporter);
        Date date = durationSupporter.getDate().get();
        Time start = durationSupporter.getTime().get();
        Time end = durationSupporter.getEndTime(start).get();
        Appointment appointment = new Appointment(date, start, end, ALICE);


        model.addAppointment(appointment);

        assertCommandFailure(assignCommand, model, AssignCommand.ASSIGNMENT_OVERLAP);
    }

    @Test
    public void equals() {
        DurationSupporter durationSupporter = new DurationSupporterBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).withDuration(VALID_DURATION).build();
        DurationSupporter diffSupporter = new DurationSupporterBuilder()
                .withDate(VALID_DATE).withTime(OVERLAP_TIME).withDuration(VALID_DURATION).build();
        AssignCommand command = new AssignCommand(INDEX_FIRST_APPOINTMENT, durationSupporter);

        // same values -> returns true
        DurationSupporter supporterCopy = new DurationSupporterBuilder()
                .withDate(VALID_DATE).withTime(SAME_TIME).withDuration(VALID_DURATION).build();
        AssignCommand commandCopy = new AssignCommand(INDEX_FIRST_APPOINTMENT, supporterCopy);
        assertTrue(command.equals(commandCopy));

        // same object -> returns true
        assertTrue(command.equals(command));

        // null -> returns false
        assertFalse(command.equals(null));

        //different types -> returns false
        assertFalse(command.equals(new ClearCommand()));

        // different index -> returns false
        AssignCommand diffIndexCommand = new AssignCommand(INDEX_SECOND_APPOINTMENT, durationSupporter);
        assertFalse(command.equals(diffIndexCommand));

        // different dateTimeLoader -> returns false
        AssignCommand diffLoaderCommand = new AssignCommand(INDEX_FIRST_APPOINTMENT, diffSupporter);
        assertFalse(command.equals(diffLoaderCommand));
    }
}

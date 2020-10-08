package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX;
import static seedu.address.logic.commands.CommandTestUtil.OVERLAP_TIME;
import static seedu.address.logic.commands.CommandTestUtil.SAME_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIME;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_APPOINTMENT;
import static seedu.address.testutil.TypicalPatients.ALICE;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;
import static seedu.address.testutil.TypicalPatients.getTypicalPatients;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand.DateTimeLoader;
import seedu.address.model.AddressBook;
import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.testutil.AppointmentBookBuilder;
import seedu.address.testutil.AssignLoaderBuilder;


public class AssignCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new AppointmentBook(), new UserPrefs());

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        DateTimeLoader dateTimeLoader = new DateTimeLoader();
        assertThrows(NullPointerException.class, () -> new AssignCommand(null, dateTimeLoader));
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
    public void execute_appointmentAcceptedByModel_assignSuccessful() {
        DateTimeLoader loader = new AssignLoaderBuilder().withDate(VALID_DATE).withTime(VALID_TIME).build();
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_APPOINTMENT, loader);
        Appointment appointment = new Appointment(
                new Date(LocalDate.parse(VALID_DATE)), new Time(LocalTime.parse(VALID_TIME)), (ALICE.getNric())
        );

        String expectedMessage = String.format(AssignCommand.MESSAGE_SUCCESS, appointment);
        AppointmentBook expectedAppointmentBook = new AppointmentBookBuilder().withAppointment(appointment).build();

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                expectedAppointmentBook, new UserPrefs());

        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_appointmentRejectedDueToOverSizedIndex_failure() {
        Index overSizedIndex = Index.fromOneBased(getTypicalPatients().size() + 1);
        DateTimeLoader loader = new AssignLoaderBuilder().withDate(VALID_DATE).withTime(VALID_TIME).build();
        AssignCommand assignCommand = new AssignCommand(overSizedIndex, loader);

        assertCommandFailure(assignCommand, model, MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX);
    }

    @Test
    public void execute_appointmentRejectedDueToOverlap_failure1() {
        DateTimeLoader loader = new AssignLoaderBuilder().withDate(VALID_DATE).withTime(VALID_TIME).build();
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_APPOINTMENT, loader);
        Appointment appointment = new Appointment(
                new Date(LocalDate.parse(VALID_DATE)), new Time(LocalTime.parse(SAME_TIME)), (ALICE.getNric())
        );

        model.addAppointment(appointment);

        assertCommandFailure(assignCommand, model, AssignCommand.ASSIGNMENT_OVERLAP);
    }

    @Test
    public void execute_appointmentRejectedDueToOverlap_failure2() {
        DateTimeLoader loader = new AssignLoaderBuilder().withDate(VALID_DATE).withTime(VALID_TIME).build();
        AssignCommand assignCommand = new AssignCommand(INDEX_FIRST_APPOINTMENT, loader);
        Appointment appointment = new Appointment(
                new Date(LocalDate.parse(VALID_DATE)), new Time(LocalTime.parse(OVERLAP_TIME)), (ALICE.getNric())
        );

        model.addAppointment(appointment);

        assertCommandFailure(assignCommand, model, AssignCommand.ASSIGNMENT_OVERLAP);
    }
}

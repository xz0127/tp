package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NRIC;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIME;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.RemarkUtil.WORDS_ONE_NINETY_NINE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;
import seedu.address.model.PatientBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.DateTimeLoaderBuilder;
import seedu.address.testutil.DurationSupporterBuilder;
import seedu.address.testutil.EditPatientDescriptorBuilder;
import seedu.address.testutil.FindPatientDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_NRIC_AMY = "S1234567J";
    public static final String VALID_NRIC_BOB = "T0034567P";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_REMARK_AMY = "She loves movies";
    public static final String VALID_REMARK_BOB = "Serial entrepreneur";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_DATE = "20 Nov 2050";
    public static final String DIFF_DATE = "03 August 2050";
    public static final String ANOTHER_DATE = "12 May 2050";
    public static final String VALID_TIME = "12pm";
    public static final String SAME_TIME = "Afternoon";
    public static final String OVERLAP_TIME = "12:01 pm";
    public static final String VALID_DURATION = "90";
    public static final long VALID_DURATION_LONG = 90;
    public static final String DIFF_DURATION = "91";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String NRIC_DESC_AMY = " " + PREFIX_NRIC + VALID_NRIC_AMY;
    public static final String NRIC_DESC_BOB = " " + PREFIX_NRIC + VALID_NRIC_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String ASSIGN_DATE_TIME = " " + PREFIX_DATE + VALID_DATE + " " + PREFIX_TIME + VALID_TIME;
    public static final String ASSIGN_TIME = " " + PREFIX_TIME + VALID_TIME;
    public static final String ASSIGN_DATE = " " + PREFIX_DATE + VALID_DATE;
    public static final String ASSIGN_DURATION = " " + PREFIX_DURATION + VALID_DURATION;
    public static final String REMARK_DESC_AMY = PREFIX_REMARK + VALID_REMARK_AMY;
    public static final String REMARK_DESC_BOB = PREFIX_REMARK + VALID_REMARK_BOB;
    public static final String REMARK_DESC_EMPTY = PREFIX_REMARK + " ";
    public static final String CHANGE_DATE_TIME_DURATION =
            " " + PREFIX_DATE + VALID_DATE + " " + PREFIX_TIME + VALID_TIME + " "
            + PREFIX_DURATION + VALID_DURATION;
    public static final String CHANGE_TIME = " " + PREFIX_TIME + VALID_TIME;
    public static final String CHANGE_DATE = " " + PREFIX_DATE + VALID_DATE;
    public static final String CHANGE_DURATION = " " + PREFIX_DURATION + VALID_DURATION;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_NRIC_DESC = " " + PREFIX_NRIC + "q1234567k"; // lower caps not allowed in Nric
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String EMPTY_DATE_DESC = " " + PREFIX_DATE + " "; // date is in the past
    public static final String INVALID_DATE_DESC = " " + PREFIX_DATE + "20201202"; // not a recognised date format
    public static final String INVALID_DATE_DESC_LETTERS = " " + PREFIX_DATE + "abcd"; // not a recognised date format
    public static final String INVALID_DATE_DESC_EXPIRED = " " + PREFIX_DATE + "20/12/2010"; // date is in the past
    public static final String INVALID_TIME_DESC = " " + PREFIX_TIME + "2530"; // not a proper 24h time format
    public static final String INVALID_TIME_DESC_LETTERS = " " + PREFIX_TIME + "abcd"; // not a recognised time format
    public static final String INVALID_TIME_DESC_CLOSED = " " + PREFIX_TIME + "2359"; // not during opening hours
    public static final String INVALID_REMARK_INDEX = " 0 " + PREFIX_REMARK + WORDS_ONE_NINETY_NINE;
    public static final String INVALID_DURATION_NEGATIVE_DESC = " " + PREFIX_DURATION + "-40";
    public static final String INVALID_DURATION_NON_INTEGER_DESC = " " + PREFIX_DURATION + "CS";
    public static final String INVALID_DURATION_EXCEED_CLOSING_TIME_DESC = " " + PREFIX_DURATION + "720";
    public static final String INVALID_DURATION_EXCEED_MAX_DESC = " " + PREFIX_DURATION + "1800";
    public static final String INVALID_DURATION_EMPTY_DESC = " " + PREFIX_DURATION + "";
    public static final String INVALID_DURATION_WHITESPACE_DESC = " " + PREFIX_DURATION + "       ";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPatientDescriptor EDIT_DESC_AMY;
    public static final EditCommand.EditPatientDescriptor EDIT_DESC_BOB;
    public static final FindCommand.FindPatientDescriptor FIND_DESC_AMY;
    public static final FindCommand.FindPatientDescriptor FIND_DESC_BOB;

    public static final DateTimeLoader LOADER;
    public static final DateTimeLoader LOADER_DIFF_TIME;
    public static final DateTimeLoader LOADER_DIFF_DATE;
    public static final AssignCommand.DurationSupporter SUPPORTER;
    public static final AssignCommand.DurationSupporter SUPPORTER_DIFF_DATE;
    public static final AssignCommand.DurationSupporter SUPPORTER_DIFF_TIME;
    public static final AssignCommand.DurationSupporter SUPPORTER_DIFF_DURATION;

    static {
        EDIT_DESC_AMY = new EditPatientDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).withNric(VALID_NRIC_AMY).build();
        EDIT_DESC_BOB = new EditPatientDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withNric(VALID_NRIC_BOB).build();
    }

    static {
        FIND_DESC_AMY = new FindPatientDescriptorBuilder().withName(new String[]{VALID_NAME_AMY})
                .withPhone(new String[]{VALID_PHONE_AMY}).withNric(new String[]{VALID_NRIC_AMY}).build();
        FIND_DESC_BOB = new FindPatientDescriptorBuilder().withName(new String[]{VALID_NAME_BOB})
                .withPhone(new String[]{VALID_PHONE_BOB}).withNric(new String[]{VALID_NRIC_BOB}).build();
    }

    static {
        LOADER = new DateTimeLoaderBuilder().withDate(VALID_DATE).withTime(VALID_TIME).build();
        LOADER_DIFF_TIME = new DateTimeLoaderBuilder().withDate(VALID_DATE).withTime(OVERLAP_TIME).build();
        LOADER_DIFF_DATE = new DateTimeLoaderBuilder().withDate(DIFF_DATE).withTime(OVERLAP_TIME).build();
    }

    static {
        SUPPORTER = new DurationSupporterBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).withDuration(VALID_DURATION).build();
        SUPPORTER_DIFF_DATE = new DurationSupporterBuilder()
                .withDate(DIFF_DATE).withTime(VALID_TIME).withDuration(VALID_DURATION).build();
        SUPPORTER_DIFF_TIME = new DurationSupporterBuilder()
                .withDate(VALID_DATE).withTime(OVERLAP_TIME).withDuration(VALID_DURATION).build();
        SUPPORTER_DIFF_DURATION = new DurationSupporterBuilder()
                .withDate(VALID_DATE).withTime(VALID_TIME).withDuration(DIFF_DURATION).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
                                            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
                                            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the patient book, filtered patient list and selected patient in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        PatientBook expectedPatientBook = new PatientBook(actualModel.getPatientBook());
        List<Patient> expectedFilteredPatientList = new ArrayList<>(actualModel.getFilteredPatientList());

        AppointmentBook expectedAppointmentBook = new AppointmentBook(actualModel.getAppointmentBook());
        List<Appointment> expectedFilteredAppointmentList = new ArrayList<>(actualModel.getFilteredAppointmentList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));

        assertEquals(expectedPatientBook, actualModel.getPatientBook());
        assertEquals(expectedFilteredPatientList, actualModel.getFilteredPatientList());

        assertEquals(expectedAppointmentBook, actualModel.getAppointmentBook());
        assertEquals(expectedFilteredAppointmentList, actualModel.getFilteredAppointmentList());
    }

    /**
     * Updates {@code model}'s filtered patient list to show only the patient at the given {@code targetIndex} in the
     * {@code model}'s patient book.
     */
    public static void showPatientAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPatientList().size());

        Patient patient = model.getFilteredPatientList().get(targetIndex.getZeroBased());
        final String[] splitName = patient.getName().fullName.split("\\s+");
        model.updateFilteredPatientList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPatientList().size());
    }

    /**
     * Updates {@code model}'s filtered appointment list to show only the appointment at the given {@code targetIndex}
     * in the {@code model}'s appointment book.
     */
    public static void showAppointmentAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredAppointmentList().size());

        Appointment appointment = model.getFilteredAppointmentList().get(targetIndex.getZeroBased());
        final Date date = appointment.getDate();
        final Time startTime = appointment.getStartTime();
        model.updateFilteredAppointmentList(appt -> appt.startAtSameTime(date, startTime));

        assertEquals(1, model.getFilteredAppointmentList().size());
    }

    /**
     * Deletes the first appointment in {@code model}'s filtered list from {@code model}'s appointment book.
     */
    public static void deleteFirstAppointment(Model model) {
        Appointment firstAppointment = model.getFilteredAppointmentList().get(0);
        model.deleteAppointment(firstAppointment);
        model.commitAppointmentBook();
        model.commitPatientBook();
    }
}

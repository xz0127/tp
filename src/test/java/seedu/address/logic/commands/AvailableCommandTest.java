package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_AVAILABLE_TIME_SLOTS;
import static seedu.address.commons.core.Messages.MESSAGE_NEXT_AVAILABLE_TIME_SLOT;
import static seedu.address.logic.commands.CommandTestUtil.DIFF_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.DateMatchesPredicate;
import seedu.address.model.appointment.Time;
import seedu.address.model.interval.TimeInterval;
import seedu.address.model.interval.TimeIntervalList;

public class AvailableCommandTest {

    private final Model model = new ModelManager(
        getTypicalPatientBook(),
        getTypicalAppointmentBook(),
        new UserPrefs()
    );

    private Model expectedModel = new ModelManager(
        getTypicalPatientBook(),
        getTypicalAppointmentBook(),
        new UserPrefs()
    );

    /**
     * Passes {@code input} into a {@code DateMatchesPredicate}.
     */
    private DateMatchesPredicate preparePredicate(LocalDate input) {
        return new DateMatchesPredicate(new Date(input));
    }

    @Test
    public void constructor_nullPredicate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AvailableCommand(null, (Boolean) null));
    }

    @Test
    public void execute_multipleSlotsAvailable_availableSuccessful() {
        String expectedMessage = MESSAGE_AVAILABLE_TIME_SLOTS;
        DateMatchesPredicate predicate = preparePredicate(LocalDate.of(2050, 1, 1));
        AvailableCommand command = new AvailableCommand(predicate, false);

        TimeInterval intervalOne = new TimeInterval(new Time(8, 0), new Time(9, 0));
        TimeInterval intervalTwo = new TimeInterval(new Time(10, 0), new Time(20, 0));
        TimeInterval intervalThree = new TimeInterval(new Time(21, 0), new Time(22, 0));
        ArrayList<TimeInterval> intervals = new ArrayList<>(List.of(intervalOne, intervalTwo, intervalThree));
        TimeIntervalList intervalList = new TimeIntervalList(intervals);

        expectedModel.updateFilteredAppointmentList(predicate);
        expectedMessage += intervalList.toString();
        expectedMessage += MESSAGE_NEXT_AVAILABLE_TIME_SLOT;
        expectedMessage += new TimeInterval(new Time(8, 0), new Time(9, 0)).toString();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredAppointmentList(), expectedModel.getFilteredAppointmentList());
    }

    @Test
    public void execute_entireDayAvailable_availableSuccessful() {
        String expectedMessage = MESSAGE_AVAILABLE_TIME_SLOTS;
        DateMatchesPredicate predicate = preparePredicate(LocalDate.of(2088, 3, 3));
        AvailableCommand command = new AvailableCommand(predicate, false);

        TimeInterval interval = new TimeInterval(new Time(Time.OPENING_TIME), new Time(Time.CLOSING_TIME));
        ArrayList<TimeInterval> intervals = new ArrayList<>();
        intervals.add(interval);
        TimeIntervalList intervalList = new TimeIntervalList(intervals);

        expectedModel.updateFilteredAppointmentList(predicate);
        expectedMessage += intervalList.toString();
        expectedMessage += MESSAGE_NEXT_AVAILABLE_TIME_SLOT;
        expectedMessage += new TimeInterval(new Time(8, 0), new Time(9, 0)).toString();
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredAppointmentList(), expectedModel.getFilteredAppointmentList());
    }

    @Test
    public void equals() throws ParseException {
        DateMatchesPredicate firstPredicate =
            new DateMatchesPredicate(ParserUtil.parseDate(VALID_DATE));
        DateMatchesPredicate secondPredicate =
            new DateMatchesPredicate(ParserUtil.parseDate(DIFF_DATE));
        DateMatchesPredicate firstPredicateCopy =
            new DateMatchesPredicate(ParserUtil.parseDate(VALID_DATE));

        AvailableCommand firstAvailableCommand = new AvailableCommand(firstPredicate, false);
        AvailableCommand secondAvailableCommand = new AvailableCommand(secondPredicate, false);

        // same object -> returns true
        assertTrue(firstAvailableCommand.equals(firstAvailableCommand));

        // same values -> returns true
        AvailableCommand firstAvailableCommandCopy = new AvailableCommand(firstPredicateCopy, false);
        assertTrue(firstAvailableCommand.equals(firstAvailableCommandCopy));

        // different types -> returns false
        assertFalse(firstAvailableCommand.equals(1));

        // null -> returns false
        assertFalse(firstAvailableCommand.equals(null));

        // different date -> returns false
        assertFalse(firstAvailableCommand.equals(secondAvailableCommand));

        // different boolean -> returns false
        firstAvailableCommandCopy = new AvailableCommand(firstPredicateCopy, true);
        assertFalse(firstAvailableCommand.equals(firstAvailableCommandCopy));
    }
}

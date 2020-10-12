package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_APPOINTMENTS_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.DIFF_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalPatients.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.DateMatchesPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code ViewCommand}.
 */
public class ViewCommandTest {

    private final Model model = new ModelManager(
            getTypicalAddressBook(),
            getTypicalAppointmentBook(),
            new UserPrefs()
    );
    private Model expectedModel = new ModelManager(
            getTypicalAddressBook(),
            getTypicalAppointmentBook(),
            new UserPrefs()
    );

    @Test
    public void equals() throws ParseException {
        DateMatchesPredicate firstPredicate =
                new DateMatchesPredicate(ParserUtil.parseDate(VALID_DATE));
        DateMatchesPredicate secondPredicate =
                new DateMatchesPredicate(ParserUtil.parseDate(DIFF_DATE));
        DateMatchesPredicate firstPredicateCopy =
                new DateMatchesPredicate(ParserUtil.parseDate(VALID_DATE));

        ViewCommand firstViewCommand = new ViewCommand(firstPredicate);
        ViewCommand secondViewCommand = new ViewCommand(secondPredicate);

        // same object -> returns true
        assertTrue(firstViewCommand.equals(firstViewCommand));

        // same values -> returns true
        ViewCommand firstViewCommandCopy = new ViewCommand(firstPredicateCopy);
        assertTrue(firstViewCommand.equals(firstViewCommandCopy));

        // different types -> returns false
        assertFalse(firstViewCommand.equals(1));

        // null -> returns false
        assertFalse(firstViewCommand.equals(null));

        // different date -> returns false
        assertFalse(firstViewCommand.equals(secondViewCommand));
    }

    @Test
    public void execute_multipleAppointmentsFound() {
        String expectedMessage = String.format(MESSAGE_APPOINTMENTS_OVERVIEW, 2);
        DateMatchesPredicate predicate = preparePredicate(LocalDate.of(2020, 1, 1));
        ViewCommand command = new ViewCommand(predicate);
        expectedModel.updateFilteredAppointmentList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getFilteredAppointmentList(), expectedModel.getFilteredAppointmentList());
    }

    /**
     * Passes {@code input} into a {@code DateMatchesPredicate}.
     */
    private DateMatchesPredicate preparePredicate(LocalDate input) {
        return new DateMatchesPredicate(new Date(input));
    }
}

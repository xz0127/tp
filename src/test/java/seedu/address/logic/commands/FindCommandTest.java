package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_APPOINTMENTS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_PATIENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAppointments.getTypicalAppointmentBook;
import static seedu.address.testutil.TypicalPatients.CARL;
import static seedu.address.testutil.TypicalPatients.ELLE;
import static seedu.address.testutil.TypicalPatients.FIONA;
import static seedu.address.testutil.TypicalPatients.getTypicalPatientBook;

import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand.FindPatientDescriptor;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.patient.Patient;


/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalPatientBook(), getTypicalAppointmentBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(model.getPatientBook(), model.getAppointmentBook(), new UserPrefs());

    @Test
    public void equals() {
        FindPatientDescriptor firstNameDescriptor = new FindPatientDescriptor();
        FindPatientDescriptor firstPhoneDescriptor = new FindPatientDescriptor();
        FindPatientDescriptor firstNricDescriptor = new FindPatientDescriptor();
        FindPatientDescriptor secondNameDescriptor = new FindPatientDescriptor();
        FindPatientDescriptor secondPhoneDescriptor = new FindPatientDescriptor();
        FindPatientDescriptor secondNricDescriptor = new FindPatientDescriptor();

        firstNameDescriptor.setNamePredicate(new String[]{"first"});
        firstNricDescriptor.setNricPredicate(new String[]{"S1234567I"});
        firstPhoneDescriptor.setPhonePredicate(new String[]{"99998888"});

        secondNameDescriptor.setNamePredicate(new String[]{"second"});
        secondNricDescriptor.setNricPredicate(new String[]{"S1234567J"});
        secondPhoneDescriptor.setPhonePredicate(new String[]{"88889999"});

        FindCommand findFirstCommand = new FindCommand(firstNameDescriptor);
        FindCommand findSecondCommand = new FindCommand(secondNameDescriptor);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(firstNameDescriptor);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different patient -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
        assertFalse(new FindCommand(firstNricDescriptor).equals(new FindCommand(secondNricDescriptor)));
        assertFalse(new FindCommand(firstPhoneDescriptor).equals(new FindCommand(secondPhoneDescriptor)));
    }

    @Test
    public void execute_multipleElementsToFind_singlePatientsFound() {
        FindPatientDescriptor descriptor = new FindPatientDescriptor();
        descriptor.setNamePredicate(new String[]{"Carl", "Kurz"});
        descriptor.setNricPredicate(new String[]{CARL.getNric().toString()});
        descriptor.setPhonePredicate(new String[]{CARL.getPhone().toString()});

        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 1)
                + "\n"
                + String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 2);
        FindCommand command = new FindCommand(descriptor);

        Predicate<Patient> predicate = descriptor.getOrPredicate();
        expectedModel.updateFilteredPatientList(predicate);
        expectedModel.updateFilteredAppointmentList(appointment -> predicate.test(appointment.getPatient()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL), model.getFilteredPatientList());
    }

    @Test
    public void execute_multipleElementsToFind_multiplePatientsFound() {
        FindPatientDescriptor descriptor = new FindPatientDescriptor();
        descriptor.setNamePredicate(new String[]{"Carl", "Elle"});
        descriptor.setNricPredicate(new String[]{CARL.getNric().toString(), ELLE.getNric().toString()});
        descriptor.setPhonePredicate(new String[]{CARL.getPhone().toString(), FIONA.getPhone().toString()});

        String expectedMessage = String.format(MESSAGE_PATIENTS_LISTED_OVERVIEW, 3)
                + "\n"
                + String.format(MESSAGE_APPOINTMENTS_LISTED_OVERVIEW, 2);
        FindCommand command = new FindCommand(descriptor);

        Predicate<Patient> predicate = descriptor.getOrPredicate();
        expectedModel.updateFilteredPatientList(predicate);
        expectedModel.updateFilteredAppointmentList(appointment -> predicate.test(appointment.getPatient()));
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(CARL, ELLE, FIONA), model.getFilteredPatientList());
    }
}

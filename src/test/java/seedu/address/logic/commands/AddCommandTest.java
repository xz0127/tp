package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.Model;
import seedu.address.model.PatientBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;
import seedu.address.testutil.PatientBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPatient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_patientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPatientAdded modelStub = new ModelStubAcceptingPatientAdded();
        Patient validPatient = new PatientBuilder().build();

        CommandResult commandResult = new AddCommand(validPatient).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPatient), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPatient), modelStub.patientsAdded);
    }

    @Test
    public void execute_duplicatePatient_throwsCommandException() {
        Patient validPatient = new PatientBuilder().build();
        AddCommand addCommand = new AddCommand(validPatient);
        ModelStub modelStub = new ModelStubWithPatient(validPatient);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PATIENT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Patient alice = new PatientBuilder().withName("Alice").build();
        Patient bob = new PatientBuilder().withName("Bob").build();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different patient -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * A default model stub that have all of the methods failing other than
     * {@code updateFilteredAppointmentList}.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getPatientBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatientBookFilePath(Path patientBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAppointmentBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointmentBookFilePath(Path appointmentBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getArchiveDirPath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setArchiveDirPath(Path archiveDirPath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatientBook(ReadOnlyPatientBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyPatientBook getPatientBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPatient(Patient patient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasOverlappingAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasAppointment(Appointment appointment) {
            throw new AssertionError("This method should not be called.");
        }

        public void deletePatient(Patient target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointment(Appointment target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPatient(Patient target, Patient editedPatient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAppointment(Appointment target, Appointment editedPatient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String findAvailableTimeSlots(List<Appointment> appointmentList, boolean isToday) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateAppointmentsWithPatient(Patient target, Patient editedPatient) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAppointmentsWithPatient(Patient target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Patient> getFilteredPatientList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPatientList(Predicate<Patient> predicate) {
            requireNonNull(predicate);
            FilteredList<Patient> filteredPatients =
                    new FilteredList<>(new PatientBook().getPatientList());

            filteredPatients.setPredicate(predicate);
        }

        @Override
        public void updateFilteredAppointmentList(Predicate<Appointment> predicate) {
            requireNonNull(predicate);
            FilteredList<Appointment> filteredAppointments =
                    new FilteredList<>(new AppointmentBook().getAppointmentList());

            filteredAppointments.setPredicate(predicate);
        }

        @Override
        public void setAppointmentBook(ReadOnlyAppointmentBook appointmentBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAppointmentBook getAppointmentBook() {
            return null;
        }

        @Override
        public ObservableList<Appointment> getFilteredAppointmentList() {
            return null;
        }

        @Override
        public boolean canUndoAppointmentBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoPatientBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAppointmentBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoPatientBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAppointmentBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoPatientBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAppointmentBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoPatientBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAppointmentBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitPatientBook() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single patient.
     */
    private class ModelStubWithPatient extends ModelStub {
        private final Patient patient;

        ModelStubWithPatient(Patient patient) {
            requireNonNull(patient);
            this.patient = patient;
        }

        @Override
        public boolean hasPatient(Patient patient) {
            requireNonNull(patient);
            return this.patient.isSamePatient(patient);
        }
    }

    /**
     * A Model stub that always accept the patient being added.
     */
    private class ModelStubAcceptingPatientAdded extends ModelStub {
        final ArrayList<Patient> patientsAdded = new ArrayList<>();

        @Override
        public boolean hasPatient(Patient patient) {
            requireNonNull(patient);
            return patientsAdded.stream().anyMatch(patient::isSamePatient);
        }

        @Override
        public void addPatient(Patient patient) {
            requireNonNull(patient);
            patientsAdded.add(patient);
        }

        @Override
        public void commitAppointmentBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public void commitPatientBook() {
            // called by {@code AddCommand#execute()}
        }

        @Override
        public ReadOnlyPatientBook getPatientBook() {
            return new PatientBook();
        }
    }

}

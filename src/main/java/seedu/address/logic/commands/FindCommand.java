package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.Model;
import seedu.address.model.patient.NameContainsKeywordsPredicate;
import seedu.address.model.patient.NricMatchesPredicate;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.PhoneMatchesPredicate;

/**
 * Finds and lists all patients in patient book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all patients whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_NO_FIND = "At least one field to edit must be provided";
    private final FindPatientDescriptor descriptor;

    public FindCommand(FindPatientDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        assert descriptor.getNamePredicate().isPresent();
        assert descriptor.getNricPredicate().isPresent();
        assert descriptor.getPhonePredicate().isPresent();

        Predicate<Patient> allPredicates = descriptor.getOrPredicate();
        model.updateFilteredPatientList(allPredicates);
        model.updateFilteredAppointmentList(appointment -> allPredicates.test(appointment.getPatient()));
        return new CommandResult(
                String.format(Messages.MESSAGE_PATIENTS_LISTED_OVERVIEW, model.getFilteredPatientList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && descriptor.equals(((FindCommand) other).descriptor)); // state check
    }

    /**
     * Stores the details to find the patient with. Each non-empty field value will replace the
     * corresponding field value of the patient
     */
    public static class FindPatientDescriptor {
        private NameContainsKeywordsPredicate namePredicate;
        private PhoneMatchesPredicate phonePredicate;
        private NricMatchesPredicate nricPredicate;

        public FindPatientDescriptor() {}

        /**
         * Copy constructor.
         */
        public FindPatientDescriptor(FindPatientDescriptor toCopy) {
            setNamePredicate(toCopy.namePredicate);
            setPhonePredicate(toCopy.phonePredicate);
            setNricPredicate(toCopy.nricPredicate);
        }

        /**
         * Returns true if at least one field needs to be found.
         */
        public boolean isAnyFieldToFind() {
            return CollectionUtil.isAnyNonNull(namePredicate, phonePredicate, nricPredicate);
        }

        public void setNamePredicate(String[] names) {
            List<String> nameList = Arrays.asList(names);
            this.namePredicate = new NameContainsKeywordsPredicate(nameList);
        }

        private void setNamePredicate(NameContainsKeywordsPredicate namePredicate) {
            this.namePredicate = namePredicate;
        }

        public Optional<NameContainsKeywordsPredicate> getNamePredicate() {
            return Optional.ofNullable(namePredicate);
        }

        public void setPhonePredicate(String[] phones) {
            List<String> phoneList = Arrays.asList(phones);
            this.phonePredicate = new PhoneMatchesPredicate(phoneList);
        }

        private void setPhonePredicate(PhoneMatchesPredicate phonePredicate) {
            this.phonePredicate = phonePredicate;
        }

        public Optional<PhoneMatchesPredicate> getPhonePredicate() {
            return Optional.ofNullable(phonePredicate);
        }

        public void setNricPredicate(String[] nrics) {
            List<String> nricList = Arrays.asList(nrics);
            this.nricPredicate = new NricMatchesPredicate(nricList);
        }

        private void setNricPredicate(NricMatchesPredicate nricPredicate) {
            this.nricPredicate = nricPredicate;
        }

        public Optional<NricMatchesPredicate> getNricPredicate() {
            return Optional.ofNullable(nricPredicate);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof FindPatientDescriptor)) {
                return false;
            }

            // state check
            FindPatientDescriptor findPatientDescriptor = (FindPatientDescriptor) other;

            return getNamePredicate().equals(findPatientDescriptor.getNamePredicate())
                    && getPhonePredicate().equals(findPatientDescriptor.getPhonePredicate())
                    && getNricPredicate().equals(findPatientDescriptor.getNricPredicate());
        }

        /**
         * Returns the or statements of all predicates.
         */
        public Predicate<Patient> getOrPredicate() {
            return namePredicate.or(phonePredicate).or(nricPredicate);
        }
    }
}

package seedu.address.testutil;

import seedu.address.logic.commands.FindCommand.FindPatientDescriptor;
import seedu.address.model.patient.Patient;

/**
 * A utility class to help with building FindPatientDescriptor objects.
 */
public class FindPatientDescriptorBuilder {

    private FindPatientDescriptor descriptor;

    public FindPatientDescriptorBuilder() {
        descriptor = new FindPatientDescriptor();
    }

    public FindPatientDescriptorBuilder(FindPatientDescriptor descriptor) {
        this.descriptor = new FindPatientDescriptor(descriptor);
    }

    /**
     * Returns an {@code FindPatientDescriptor} with fields containing {@code patient}'s details
     */
    public FindPatientDescriptorBuilder(Patient patient) {
        descriptor = new FindPatientDescriptor();
        descriptor.setPhonePredicate(new String[]{patient.getPhone().toString()});
        descriptor.setNamePredicate(new String[]{patient.getName().fullName});
        descriptor.setNricPredicate(new String[]{patient.getNric().toString()});
    }

    /**
     * Sets the {@code Name} of the {@code FindPatientDescriptor} that we are building.
     */
    public FindPatientDescriptorBuilder withName(String[] name) {
        descriptor.setNamePredicate(name);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code FindPatientDescriptor} that we are building.
     */
    public FindPatientDescriptorBuilder withPhone(String[] phone) {
        descriptor.setPhonePredicate(phone);
        return this;
    }

    /**
     * Sets the {@code Nric} of the {@code FindPatientDescriptor} that we are building.
     */
    public FindPatientDescriptorBuilder withNric(String[] nric) {
        descriptor.setNricPredicate(nric);
        return this;
    }

    public FindPatientDescriptor build() {
        return descriptor;
    }
}

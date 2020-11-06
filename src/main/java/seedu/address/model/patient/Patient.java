package seedu.address.model.patient;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Patient in the patient book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient implements Comparable<Patient> {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Nric nric;

    // Data fields
    private final Address address;
    private final Remark remark;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Address address, Set<Tag> tags, Nric nric, Remark remark) {
        requireAllNonNull(name, phone, address, tags, nric, remark);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.tags.addAll(tags);
        this.nric = nric;
        this.remark = remark;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Address getAddress() {
        return address;
    }

    public Nric getNric() {
        return nric;
    }

    public Remark getRemark() {
        return remark;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both patients of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getNric().equals(getNric());
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;
        return otherPatient.getName().equals(getName())
                && otherPatient.getPhone().equals(getPhone())
                && otherPatient.getAddress().equals(getAddress())
                && otherPatient.getTags().equals(getTags())
                && otherPatient.getRemark().equals(getRemark())
                && otherPatient.getNric().equals(getNric());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, address, tags, nric);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" (NRIC: ")
                .append(getNric())
                .append(")\nPhone: ")
                .append(getPhone())
                .append("; Address: ")
                .append(getAddress())
                .append("; Remark: ")
                .append(getRemark())
                .append("; Tags: ");
        getTags().forEach(builder::append);
        return builder.append(";").toString();
    }

    @Override
    public int compareTo(Patient other) {
        return getName().compareTo(other.getName());
    }
}

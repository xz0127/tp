package seedu.address.storage.archive;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import seedu.address.model.patient.Patient;

/**
 * Csv-friendly version of {@link Patient}.
 * Contains minimal Patient data for archive purposes.
 */
@JsonPropertyOrder({"name", "phone", "address", "remark"})
class CsvAdaptedPatient {

    private final String name;
    private final String phone;
    private final String address;
    private final String remark;

    /**
     * Constructs a {@code JsonAdaptedPatient} with the given patient details.
     */
    @JsonCreator
    public CsvAdaptedPatient(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("address") String address,
                             @JsonProperty("remark") String remark) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.remark = remark;
    }

    /**
     * Converts a given {@code Patient} into this class for Jackson use.
     */
    public CsvAdaptedPatient(Patient source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        remark = source.getRemark().value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof CsvAdaptedPatient)) {
            return false;
        }

        CsvAdaptedPatient otherPatient = (CsvAdaptedPatient) other;
        return otherPatient.address.equals(address)
                && otherPatient.name.equals(name)
                && otherPatient.phone.equals(phone)
                && otherPatient.remark.equals(remark);
    }
}

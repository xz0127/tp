package seedu.address.testutil;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

/**
 * A class used to test serialization and deserialization for Csv
 */
@JsonPropertyOrder({"name", "number", "minDateTime", "nestedData"})
public class SerializableCsvTestClass {
    public static final String CSV_HEADER_REPRESENTATION = "name,number,minDateTime,data,maxDateTime\n";
    public static final String CSV_DATA_REPRESENTATION = "\"This is a test class\","
            + "1234567890," + "\"-999999999-01-01T00:00:00\"," + "\"This is a nested data\","
            + "\"+999999999-12-31T23:59:59.999999999\"\n";


    private String name;
    private LocalDateTime minDateTime;
    private int number;
    @JsonUnwrapped
    private SerializableCsvNestedClass nestedData;

    public static String getNameTestValue() {
        return "This is a test class";
    }

    public static LocalDateTime getDateTimeTestValue() {
        return LocalDateTime.MIN;
    }

    public static Integer getIntegerTestValue() {
        return 1234567890;
    }

    public static SerializableCsvNestedClass getNestedTestData() {
        return new SerializableCsvNestedClass().setTestValue();
    }

    public void setTestValues() {
        name = getNameTestValue();
        minDateTime = getDateTimeTestValue();
        number = getIntegerTestValue();
        nestedData = getNestedTestData();
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDateTime() {
        return minDateTime;
    }

    public int getInteger() {
        return number;
    }

    public SerializableCsvNestedClass getNestedData() {
        return nestedData;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SerializableCsvTestClass)) {
            return false;
        }

        SerializableCsvTestClass otherObject = (SerializableCsvTestClass) other;
        return otherObject.getDateTime().equals(getDateTime())
                && otherObject.getName().equals(getName())
                && otherObject.getInteger() == getInteger()
                && otherObject.getNestedData().equals(getNestedData());
    }

    public static class SerializableCsvNestedClass {
        private String data;
        private LocalDateTime maxDateTime;

        public SerializableCsvNestedClass setTestValue() {
            data = "This is a nested data";
            maxDateTime = LocalDateTime.MAX;
            return this;
        }

        public String getData() {
            return data;
        }

        public LocalDateTime getTime() {
            return maxDateTime;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof SerializableCsvNestedClass)) {
                return false;
            }

            SerializableCsvNestedClass otherObject = (SerializableCsvNestedClass) other;
            return otherObject.getData().equals(getData())
                    && otherObject.getTime().equals(getTime());
        }
    }
}

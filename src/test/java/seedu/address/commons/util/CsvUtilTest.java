package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.SerializableCsvTestClass.CSV_DATA_REPRESENTATION;
import static seedu.address.testutil.SerializableCsvTestClass.CSV_HEADER_REPRESENTATION;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.SerializableCsvTestClass;
import seedu.address.testutil.TestUtil;

/**
 * Tests Csv Read and Write
 */
public class CsvUtilTest {

    private static final Path SERIALIZATION_FILE = TestUtil.getFilePathInSandboxFolder("csv_serialize.csv");

    @Test
    public void serializeObjectToCsvFile_noExceptionThrown() throws IOException {
        SerializableCsvTestClass serializableTestClass = new SerializableCsvTestClass();
        serializableTestClass.setTestValues();

        // add to back with header
        CsvUtil.serializeObjectToCsvFile(SERIALIZATION_FILE,
                List.of(serializableTestClass, serializableTestClass),
                SerializableCsvTestClass.class, true);
        String expectedFile = CSV_HEADER_REPRESENTATION + CSV_DATA_REPRESENTATION + CSV_DATA_REPRESENTATION;
        assertEquals(expectedFile, FileUtil.readFromFile(SERIALIZATION_FILE));

        // append to back without header
        CsvUtil.serializeObjectToCsvFile(SERIALIZATION_FILE,
                List.of(serializableTestClass, serializableTestClass),
                SerializableCsvTestClass.class, false);
        expectedFile += CSV_DATA_REPRESENTATION + CSV_DATA_REPRESENTATION;

        assertEquals(expectedFile, FileUtil.readFromFile(SERIALIZATION_FILE));
    }

    @Test
    public void deserializeObjectFromCsvFile_noExceptionThrown() throws IOException {
        String fileData = CSV_HEADER_REPRESENTATION + CSV_DATA_REPRESENTATION + CSV_DATA_REPRESENTATION;
        FileUtil.writeToFile(SERIALIZATION_FILE, fileData);

        List<SerializableCsvTestClass> testClasses = CsvUtil
                .deserializeObjectFromCsvFile(SERIALIZATION_FILE, SerializableCsvTestClass.class);

        // 2 testClass objects deserialized
        assertEquals(testClasses.size(), 2);

        SerializableCsvTestClass testObject1 = testClasses.get(0);
        SerializableCsvTestClass testObject2 = testClasses.get(1);

        // object1 and object2 are the same
        assertEquals(testObject1, testObject2);

        // object details are the same
        assertEquals(testObject1.getName(), SerializableCsvTestClass.getNameTestValue());
        assertEquals(testObject1.getInteger(), SerializableCsvTestClass.getIntegerTestValue());
        assertEquals(testObject1.getDateTime(), SerializableCsvTestClass.getDateTimeTestValue());
        assertEquals(testObject1.getNestedData(), SerializableCsvTestClass.getNestedTestData());
    }

    //TODO: @Test csvUtil_readCsvStringToObjectInstance_correctObject()

    //TODO: @Test csvUtil_writeThenReadObjectToCsv_correctObject()
}

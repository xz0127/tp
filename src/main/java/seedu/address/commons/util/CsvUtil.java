package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;

/**
 * Converts a Java object instance to CSV and vice versa
 */
public class CsvUtil {

    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    private static final CsvMapper csvMapper = new CsvMapper();
    private static final ObjectMapper objectMapper = csvMapper
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE)
            .setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
            .registerModule(new JavaTimeModule())
            .registerModule(new SimpleModule("SimpleModule")
                    .addSerializer(Path.class, new ToStringSerializer()));

    static <T> void serializeObjectToCsvFile(Path csvFile, List<T> objectsToSerialize,
                Class<T> classOfObjectToSerialize, boolean isOverwrite) throws IOException {
        if (isOverwrite) {
            FileUtil.writeToFile(csvFile, toCsvString(objectsToSerialize, classOfObjectToSerialize, true));
        } else {
            FileUtil.appendToFile(csvFile, toCsvString(objectsToSerialize, classOfObjectToSerialize, false));
        }
    }

    static <T> List<T> deserializeObjectFromCsvFile(Path csvFile, Class<T> classOfObjectToDeserialize)
            throws IOException {
        return fromCsvString(FileUtil.readFromFile(csvFile), classOfObjectToDeserialize);
    }

    /**
     * Returns the Json object from the given file or {@code Optional.empty()} object if the file is not found.
     * If any values are missing from the file, default values will be used, as long as the file is a valid json file.
     *
     * @param filePath                   cannot be null.
     * @param classOfObjectToDeserialize Json file has to correspond to the structure in the class given here.
     * @throws DataConversionException if the file format is not as expected.
     */
    public static <T> List<T> readCsvFile(
            Path filePath, Class<T> classOfObjectToDeserialize) throws DataConversionException {
        requireNonNull(filePath);

        if (!Files.exists(filePath)) {
            logger.info("Csv file " + filePath + " not found");
            return List.of();
        }

        List<T> csvFile;

        try {
            csvFile = deserializeObjectFromCsvFile(filePath, classOfObjectToDeserialize);
        } catch (IOException e) {
            logger.warning("Error reading from csv file " + filePath + ": " + e);
            throw new DataConversionException(e);
        }

        return csvFile;
    }

    /**
     * Saves the Json object to the specified file.
     * Overwrites existing file if it exists, creates a new file if it doesn't.
     *
     * @param csvData  cannot be null
     * @param filePath cannot be null
     * @throws IOException if there was an error during writing to the file
     */
    public static <T> void saveCsvFile(List<T> csvData, Class<T> classOfObjectToSerialize,
                                       Path filePath, boolean isOverwrite) throws IOException {
        requireNonNull(filePath);
        requireNonNull(csvData);

        serializeObjectToCsvFile(filePath, csvData, classOfObjectToSerialize, isOverwrite);
    }


    /**
     * Converts a given string representation of a JSON data to instance of a class
     *
     * @param <T> The generic type to create an instance of
     * @return The instance of T with the specified values in the JSON string
     */
    public static <T> List<T> fromCsvString(String json, Class<T> instanceClass) throws IOException {
        CsvSchema schema = csvMapper
                .typedSchemaFor(instanceClass)
                .withColumnSeparator(',')
                .withHeader();

        MappingIterator<T> mappingIterator = objectMapper.readerFor(instanceClass).with(schema).readValues(json);
        return mappingIterator.readAll();
    }

    /**
     * Converts a given instance of a class into its JSON data string representation
     *
     * @param instances The T object to be converted into the JSON string
     * @param <T>       The generic type to create an instance of
     * @return JSON data representation of the given class instance, in string
     */
    public static <T> String toCsvString(List<T> instances, Class<T> instanceClass, boolean hasHeaders)
            throws JsonProcessingException {
        CsvSchema schema = csvMapper
                .typedSchemaFor(instanceClass)
                .withColumnSeparator(',');

        if (hasHeaders) {
            schema = schema.withHeader();
        } else {
            schema = schema.withoutHeader();
        }

        return objectMapper.writer(schema).writeValueAsString(instances);
    }

}

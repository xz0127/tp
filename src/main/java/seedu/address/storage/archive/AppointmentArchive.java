package seedu.address.storage.archive;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlyAppointmentBook;

/**
 * Represents an archive storage for {@link seedu.address.model.AppointmentBook}.
 */
public interface AppointmentArchive {

    Path getArchiveDirectoryPath();

    /**
     * Archives and removes past appointments from the {@code appointmentBook}.
     *
     * @param appointmentBook the appointment book.
     * @return the appointment book without past appointments.
     */
    ReadOnlyAppointmentBook archivePastAppointments(ReadOnlyAppointmentBook appointmentBook);


    /**
     * Returns the archived data as a list of {@link CsvAdaptedAppointment}.
     *
     * @param fileName the name of the archive file in the archive storage.
     * @throws DataConversionException if the data in storage is not in the expected format.
     */
    List<CsvAdaptedAppointment> readAppointments(String fileName) throws DataConversionException;

    /**
     * @see #readAppointments(String)
     */
    List<CsvAdaptedAppointment> readAppointments(Path filePath) throws DataConversionException;

    /**
     * Saves the given list of {@code CsvAdaptedAppointment}.
     *
     * @param appointments the appointments to archive.
     * @param fileName     name of the archive data file. Cannot be null.
     */
    void saveAppointments(List<CsvAdaptedAppointment> appointments, String fileName);

    /**
     * @see #saveAppointments(List, String)
     */
    void saveAppointments(List<CsvAdaptedAppointment> appointments, Path directoryPath)
            throws IOException;

}

package seedu.address.storage;

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
     * Archive and remove expired appointments from the {@code appointmentBook}.
     *
     * @param appointmentBook the appointment book.
     * @return the appointment book without expired appointments.
     */
    ReadOnlyAppointmentBook archiveAppointmentBook(ReadOnlyAppointmentBook appointmentBook);


    /**
     * Returns AppointmentBook data as a {@link ReadOnlyAppointmentBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     * Archives past appointment.
     *
     * @throws DataConversionException if the data in storage is not in the expected format.
     */
    List<CsvAdaptedAppointment> readAppointments(String fileName) throws DataConversionException;

    /**
     * @see #readAppointments(String)  ()
     */
    List<CsvAdaptedAppointment> readAppointments(Path filePath) throws DataConversionException;

    /**
     * Archive the given list of {@code CsvAdaptedAppointment}.
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

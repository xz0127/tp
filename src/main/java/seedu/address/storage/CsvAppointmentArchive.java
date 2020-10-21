package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.CsvUtil;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.appointment.Appointment;

/**
 * A class to access archived appointment data stored as a csv file on the hard disk.
 */
public class CsvAppointmentArchive implements AppointmentArchive {
    private static final Logger logger = LogsCenter.getLogger(CsvAppointmentArchive.class);
    // Statistics
    private static int numberOfArchivedAppointments = 0;
    private static int numberOfExpiredAppointments = 0;

    private final Path directoryPath;

    public CsvAppointmentArchive(Path directoryPath) {
        this.directoryPath = directoryPath;
    }

    public Path getArchiveDirectoryPath() {
        return directoryPath;
    }

    @Override
    public ReadOnlyAppointmentBook archiveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) {
        requireNonNull(appointmentBook);
        List<Appointment> appointments = appointmentBook.getAppointmentList();

        LocalDate date = null;
        List<CsvAdaptedAppointment> pastAppointments = new ArrayList<>();
        List<Appointment> upcomingAppointments = new ArrayList<>();

        for (int i = 0; i < appointments.size(); i++) {
            Appointment appointment = appointments.get(i);
            LocalDate appointmentDate = appointment.getDate().getDate();

            if (date == null || date != appointmentDate) {
                if (DateTimeUtil.isExpiredByDay(appointmentDate)) {
                    // change of day
                    if (date != null && (date.getYear() != appointmentDate.getYear()
                            || date.getMonth() != appointmentDate.getMonth())) {
                        saveAppointments(pastAppointments, getFileName(date));
                    }
                    pastAppointments.clear();
                    date = appointmentDate;
                } else {
                    // no more expired appointments
                    while (i < appointments.size()) {
                        upcomingAppointments.add(appointments.get(i));
                        i++;
                    }
                    break;
                }
            }
            pastAppointments.add(new CsvAdaptedAppointment(appointment));
            numberOfArchivedAppointments++;
            numberOfExpiredAppointments += appointment.getIsDoneStatus() ? 0 : 1;
        }

        if (pastAppointments.isEmpty()) {
            return appointmentBook;
        }

        saveAppointments(pastAppointments, getFileName(date));

        AppointmentBook book = new AppointmentBook();
        book.setAppointments(upcomingAppointments);
        return book;
    }

    @Override
    public void saveAppointments(List<CsvAdaptedAppointment> appointments, String fileName) {
        try {
            saveAppointments(appointments, directoryPath.resolve(fileName));
        } catch (IOException ioe) {
            logger.warning("Failed to archive past appointment: " + ioe);
        }
    }

    /**
     * Same as {@link #saveAppointments(List, String)}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws IOException if there is an error writing to file.
     */
    public void saveAppointments(List<CsvAdaptedAppointment> appointments, Path filePath)
            throws IOException {
        requireNonNull(appointments);
        requireNonNull(directoryPath);

        boolean isNewFile = !FileUtil.isFileExists(filePath);
        FileUtil.createIfMissing(filePath);
        CsvUtil.saveCsvFile(appointments, CsvAdaptedAppointment.class, filePath, isNewFile);
    }

    @Override
    public List<CsvAdaptedAppointment> readAppointments(String fileName) throws DataConversionException {
        requireNonNull(fileName);
        return readAppointments(directoryPath.resolve(fileName));
    }

    /**
     * Similar to {@link #readAppointments(String)}.
     *
     * @param filePath location of the archived data. Cannot be null.
     * @throws DataConversionException if the file is not in the correct format.
     */
    public List<CsvAdaptedAppointment> readAppointments(Path filePath) throws DataConversionException {
        requireNonNull(filePath);

        List<CsvAdaptedAppointment> csvAdaptedAppointments =
                CsvUtil.readCsvFile(filePath, CsvAdaptedAppointment.class);

        return csvAdaptedAppointments;
    }

    /**
     * Create filename from {@code LocalDate}.
     * The filename has details on year and month.
     *
     * @param date the reference date
     * @return the filename with the year and month.
     */
    public String getFileName(LocalDate date) {
        requireNonNull(date);
        return date.getYear() + "_" + date.getMonth().toString() + ".csv";
    }

    public static String getArchiveStatistics() {
        return String.format("%d %s archived, %d expired", numberOfArchivedAppointments,
                numberOfArchivedAppointments > 1 ? "appointments" : "appointment", numberOfExpiredAppointments);
    }

}

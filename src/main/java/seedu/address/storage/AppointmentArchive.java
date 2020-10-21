package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.CsvUtil;
import seedu.address.commons.util.DateTimeUtil;
import seedu.address.commons.util.FileUtil;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.appointment.Appointment;

public class AppointmentArchive {
    private static final Logger logger = LogsCenter.getLogger(AppointmentArchive.class);
    // Statistics
    private static int numberOfArchivedAppointments = 0;
    private static int numberOfExpiredAppointments = 0;

    private final Path directoryPath;

    public AppointmentArchive(Path directoryPath) {
        this.directoryPath = directoryPath;
    }

    public Path getArchiveDirectoryPath() {
        return directoryPath;
    }

    /**
     * Archive and remove expired appointments from the {@code appointmentBook}.
     *
     * @param appointmentBook the appointment book.
     * @return the appointment book without expired appointments.
     */
    public ReadOnlyAppointmentBook archiveAppointmentBook(ReadOnlyAppointmentBook appointmentBook) {
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
                        archiveAppointments(pastAppointments, getFileName(date));
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

        archiveAppointments(pastAppointments, getFileName(date));

        AppointmentBook book = new AppointmentBook();
        book.setAppointments(upcomingAppointments);
        return book;
    }

    /**
     * Archive the given appointments.
     *
     * @param appointments the appointments to archive.
     * @param fileName     name of the archive data file. Cannot be null.
     */
    public void archiveAppointments(List<CsvAdaptedAppointment> appointments, String fileName) {
        try {
            archiveAppointments(appointments, directoryPath, fileName);
        } catch (IOException ioe) {
            logger.warning("Failed to archive past appointment: " + ioe);
        }
    }

    /**
     * Same as {@link #archiveAppointments(List, String)}.
     *
     * @param directoryPath location of the data. Cannot be null.
     * @throws IOException if there is an error writing to file.
     */
    public void archiveAppointments(List<CsvAdaptedAppointment> appointments, Path directoryPath, String fileName)
            throws IOException {
        requireNonNull(appointments);
        requireNonNull(directoryPath);

        Path filePath = directoryPath.resolve(fileName);
        boolean isNewFile = !FileUtil.isFileExists(filePath);
        FileUtil.createIfMissing(filePath);
        CsvUtil.saveCsvFile(appointments, CsvAdaptedAppointment.class, filePath, isNewFile);
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
        return date.getYear() + "_" + date.getMonth().toString();
    }

    public static String getArchiveStatistics() {
        return String.format("%d %s archived, %d expired", numberOfArchivedAppointments,
                numberOfArchivedAppointments > 1 ? "appointments" : "appointment", numberOfExpiredAppointments);
    }

}

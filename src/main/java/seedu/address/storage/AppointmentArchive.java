package seedu.address.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
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
        ListIterator<Appointment> iter = appointments.listIterator();

        LocalDate date = null;
        List<CsvAdaptedAppointment> pastAppointments = new ArrayList<>();

        while (iter.hasNext()) {
            Appointment appointment = iter.next();
            LocalDate appointmentDate = appointment.getDate().getDate();

            if (date == null || date != appointmentDate) {
                if (DateTimeUtil.isExpiredByDay(appointmentDate)) {
                    // change of day
                    date = appointmentDate;
                    // todo: check if should archive in new file
                } else {
                    // no more expired appointments
                    break;
                }
            }
            pastAppointments.add(new CsvAdaptedAppointment(appointment));
            iter.remove();
        }

        try {
            archiveAppointments(pastAppointments, directoryPath, getFileName(date));
        } catch (IOException ioe) {
            logger.warning("Failed to archive past appointment: " + ioe);
        }

        AppointmentBook book = new AppointmentBook();
        book.setAppointments(appointments);
        return appointmentBook;
    }

    /**
     * Archive the expired appointments.
     *
     * @param appointments  the appointments to archive.
     * @param directoryPath location of the data. Cannot be null.
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
        return date.getYear() + date.getMonth().toString();
    }

}

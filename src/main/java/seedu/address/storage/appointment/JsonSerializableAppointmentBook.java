package seedu.address.storage.appointment;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AppointmentBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.storage.StorageStatsManager;

/**
 * An Immutable AppointmentBook that is serializable to JSON format.
 */
@JsonRootName(value = "appointmentbook")
class JsonSerializableAppointmentBook {

    public static final String MESSAGE_OVERLAPPING_APPOINTMENT =
            "Appointment list contains overlapping appointment(s).";

    private static final Logger logger = LogsCenter.getLogger(JsonSerializableAppointmentBook.class);

    private final List<JsonAdaptedAppointment> appointments = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAppointmentBook} with the given appointments.
     */
    @JsonCreator
    public JsonSerializableAppointmentBook(@JsonProperty("appointments") List<JsonAdaptedAppointment> appointments) {
        this.appointments.addAll(appointments);
    }

    /**
     * Converts a given {@code ReadOnlyAppointmentBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAppointmentBook}.
     */
    public JsonSerializableAppointmentBook(ReadOnlyAppointmentBook source) {
        appointments.addAll(source.getAppointmentList()
                .stream()
                .map(JsonAdaptedAppointment::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this appointment book into the model's {@code AppointmentBook} object.
     */
    public AppointmentBook toModelType(StorageStatsManager statsManager) {
        AppointmentBook appointmentBook = new AppointmentBook();
        int nDataViolations = 0;

        for (JsonAdaptedAppointment jsonAdaptedAppointment : appointments) {
            Appointment appointment;
            try {
                appointment = jsonAdaptedAppointment.toModelType();
            } catch (IllegalValueException ive) {
                logger.info("Data constraints violated: " + ive.getMessage());
                nDataViolations++;
                continue;
            }

            if (appointmentBook.hasOverlapsWith(appointment)) {
                logger.info(MESSAGE_OVERLAPPING_APPOINTMENT);
                nDataViolations++;
                continue;
            }

            // add to appointment book
            appointmentBook.addAppointment(appointment);
        }
        if (nDataViolations > 0) {
            logger.warning("Failed to read " + nDataViolations + " appointment data!");
            statsManager.setRemovedAppointmentCount(nDataViolations);
        }
        return appointmentBook;
    }

}

package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PATIENT_DISPLAYED_INDEX = "The patient index provided is invalid";
    public static final String MESSAGE_PATIENTS_LISTED_OVERVIEW = "%1$d patients" + " listed!";
    public static final String MESSAGE_APPOINTMENTS_LISTED_OVERVIEW = "%1$d appointments" + " listed!";
    public static final String MESSAGE_EXPIRED_DATE = "Cannot view appointments from the past.\n"
            + "Past appointments are archived and can be accessed from the archive folder.";
    public static final String MESSAGE_EXPIRED_DATE_TIME = "The appointment date and time should be an upcoming one.";
    public static final String MESSAGE_INVALID_APPOINTMENT_DISPLAYED_INDEX = "The appointment index provided is "
            + "invalid";
    public static final String MESSAGE_EXPIRED_TIME_SLOTS = "Cannot obtain past available time slots.\n"
        + "Past appointments are archived and can be accessed from the archive folder.";
    public static final String MESSAGE_AVAILABLE_TIME_SLOTS = "Listing all available time slots: \n";
    public static final String MESSAGE_NEXT_AVAILABLE_TIME_SLOT = "Next available time slot: \n";
}

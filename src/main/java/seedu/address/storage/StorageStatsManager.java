package seedu.address.storage;

public class StorageStatsManager {

    private int numArchivedAppointments;
    private int numExpiredAppointments;
    private int numRemovedAppointments;
    private int numRemovedPatients;

    public String getMessage() {
        return getLoadStatusMessage() + getArchiveStatusMessage();
    }

    public String getArchiveStatusMessage() {
        String message = "";
        if (numArchivedAppointments > 0) {
            message += String.format("%d %s archived", numArchivedAppointments,
                    numArchivedAppointments > 1 ? "appointments" : "appointment");
        }
        if (numExpiredAppointments > 0) {
            message += String.format(", of which %d %s not done", numExpiredAppointments,
                    numExpiredAppointments > 1 ? "are" : "is");
        }
        return message.isBlank() ? message : message + ".";
    }

    public String getLoadStatusMessage() {
        String message = "";
        if (numRemovedPatients > 0) {
            message += String.format("Failed to load %d %s.\n", numRemovedPatients,
                    numRemovedPatients > 1 ? "patients" : "patient");
        }
        if (numRemovedAppointments > 0) {
            message += String.format("Failed to load %d %s.\n", numRemovedAppointments,
                    numRemovedAppointments > 1 ? "appointments" : "appointment");
        }
        return message;
    }

    public void setArchiveStats(int numOfArchivedAppointments, int numOfExpiredAppointments) {
        assert numOfArchivedAppointments >= 0;
        assert numOfExpiredAppointments >= 0;
        assert numOfArchivedAppointments >= numOfExpiredAppointments;

        this.numArchivedAppointments = numOfArchivedAppointments;
        this.numExpiredAppointments = numOfExpiredAppointments;
    }

    public void setRemovedAppointmentCount(int numRemovedAppointments) {
        assert numRemovedAppointments >= 0;
        this.numRemovedAppointments = numRemovedAppointments;
    }

    public void setRemovedPatientCount(int numRemovedPatients) {
        assert numRemovedPatients >= 0;
        this.numRemovedPatients = numRemovedPatients;
    }
}

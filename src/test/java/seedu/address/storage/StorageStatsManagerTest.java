package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class StorageStatsManagerTest {

    @Test
    public void getArchiveStatusMessage_allOk() {
        StorageStatsManager statsManager = new StorageStatsManager();
        assertEquals("", statsManager.getArchiveStatusMessage());

        statsManager.setArchiveStats(1, 0);
        assertEquals("1 appointment archived.", statsManager.getArchiveStatusMessage());

        statsManager.setArchiveStats(1, 1);
        assertEquals("1 appointment archived, of which 1 is not done.", statsManager.getArchiveStatusMessage());

        statsManager.setArchiveStats(2, 1);
        assertEquals("2 appointments archived, of which 1 is not done.",
                statsManager.getArchiveStatusMessage());

        statsManager.setArchiveStats(10, 10);
        assertEquals("10 appointments archived, of which 10 are not done.",
                statsManager.getArchiveStatusMessage());
    }

    @Test
    public void getLoadStatusMessage_allOk() {
        StorageStatsManager statsManager = new StorageStatsManager();
        assertEquals("", statsManager.getLoadStatusMessage());

        statsManager.setRemovedPatientCount(1);
        statsManager.setRemovedAppointmentCount(0);
        assertEquals("Failed to load 1 patient.\n", statsManager.getLoadStatusMessage());

        statsManager.setRemovedPatientCount(0);
        statsManager.setRemovedAppointmentCount(1);
        assertEquals("Failed to load 1 appointment.\n", statsManager.getLoadStatusMessage());

        statsManager.setRemovedPatientCount(1);
        statsManager.setRemovedAppointmentCount(1);
        assertEquals("Failed to load 1 patient.\nFailed to load 1 appointment.\n",
                statsManager.getLoadStatusMessage());

        statsManager.setRemovedPatientCount(10);
        statsManager.setRemovedAppointmentCount(10);
        assertEquals("Failed to load 10 patients.\nFailed to load 10 appointments.\n",
                statsManager.getLoadStatusMessage());
    }

    @Test
    public void getMessage_allOk() {
        StorageStatsManager statsManager = new StorageStatsManager();
        assertEquals("", statsManager.getMessage());

        statsManager.setRemovedPatientCount(1);
        statsManager.setRemovedAppointmentCount(0);
        statsManager.setArchiveStats(0, 0);
        assertEquals("Failed to load 1 patient.\n", statsManager.getMessage());

        statsManager.setRemovedPatientCount(0);
        statsManager.setRemovedAppointmentCount(1);
        statsManager.setArchiveStats(0, 0);
        assertEquals("Failed to load 1 appointment.\n", statsManager.getMessage());

        statsManager.setRemovedPatientCount(0);
        statsManager.setRemovedAppointmentCount(1);
        statsManager.setArchiveStats(1, 0);
        assertEquals("Failed to load 1 appointment.\n1 appointment archived.", statsManager.getMessage());

        statsManager.setRemovedPatientCount(10);
        statsManager.setRemovedAppointmentCount(10);
        statsManager.setArchiveStats(10, 10);
        assertEquals("Failed to load 10 patients.\nFailed to load 10 appointments.\n"
                        + "10 appointments archived, of which 10 are not done.",
                statsManager.getMessage());
    }
}

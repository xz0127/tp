package seedu.address.model.appointment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.ALICE_APPOINTMENT;
import static seedu.address.testutil.TypicalAppointments.BENSON_APPOINTMENT;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.appointment.exceptions.AppointmentNotFoundException;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;
import seedu.address.testutil.AppointmentBuilder;

public class UniqueAppointmentListTest {

    private final UniqueAppointmentList uniqueAppointmentList = new UniqueAppointmentList();

    @Test
    public void hasOverlaps_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.hasOverlaps(null));
    }

    @Test
    public void hasOverlaps_appointmentNotInList_returnsFalse() {
        assertFalse(uniqueAppointmentList.hasOverlaps(ALICE_APPOINTMENT));
    }

    @Test
    public void hasOverlaps_appointmentInListWithOverlaps_returnsTrue() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        Appointment editedAliceAppt = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(LocalDate.of(2050, 1, 1))
                .withTime(LocalTime.of(9, 30))
                .build();
        assertTrue(uniqueAppointmentList.hasOverlaps(editedAliceAppt));
    }

    @Test
    public void hasOverlaps_appointmentInList_returnsFalse() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        assertFalse(uniqueAppointmentList.hasOverlaps(BENSON_APPOINTMENT));
    }

    @Test
    public void hasOverlaps_appointmentInList_returnsTrue() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);

        assertTrue(uniqueAppointmentList.hasOverlaps(ALICE_APPOINTMENT));
    }

    @Test
    public void hasCompleteOverlaps_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.hasCompleteOverlaps(null));
    }

    @Test
    public void hasCompleteOverlaps_appointmentNotInList_returnsFalse() {
        assertFalse(uniqueAppointmentList.hasCompleteOverlaps(ALICE_APPOINTMENT));
    }
    @Test
    public void hasCompleteOverlaps_appointmentInList_returnsTrue() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        assertTrue(uniqueAppointmentList.hasCompleteOverlaps(ALICE_APPOINTMENT));
    }

    @Test
    public void hasCompleteOverlaps_appointmentInListWithPartialOverlaps_returnsFalse() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        Appointment editedAliceAppt = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(LocalDate.of(2050, 1, 1))
                .withTime(LocalTime.of(9, 30))
                .build();
        assertFalse(uniqueAppointmentList.hasCompleteOverlaps(editedAliceAppt));
    }

    @Test
    public void add_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.add(null));
    }

    @Test
    public void add_duplicateAppointment_throwsOverlappingAppointmentException() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        assertThrows(OverlappingAppointmentException.class, () -> uniqueAppointmentList.add(ALICE_APPOINTMENT));
    }

    @Test
    public void setAppointment_nullTargetAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.setAppointment(null, ALICE_APPOINTMENT));
    }

    @Test
    public void setAppointment_nullEditedAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.setAppointment(ALICE_APPOINTMENT, null));
    }

    @Test
    public void setAppointment_targetAppointmentNotInList_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, () -> uniqueAppointmentList
                .setAppointment(ALICE_APPOINTMENT, ALICE_APPOINTMENT));
    }

    @Test
    public void setAppointment_editedAppointmentIsSameAppointment_success() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        uniqueAppointmentList.setAppointment(ALICE_APPOINTMENT, ALICE_APPOINTMENT);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(ALICE_APPOINTMENT);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasSameDateTime_success() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        Appointment editedAliceAppt = new AppointmentBuilder(ALICE_APPOINTMENT)
                .withDate(LocalDate.of(2050, 1, 1))
                .withTime(LocalTime.of(9, 0))
                .build();
        uniqueAppointmentList.setAppointment(ALICE_APPOINTMENT, editedAliceAppt);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(editedAliceAppt);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasDifferentDateTime_success() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        uniqueAppointmentList.setAppointment(ALICE_APPOINTMENT, BENSON_APPOINTMENT);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(BENSON_APPOINTMENT);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointment_editedAppointmentHasOverlappingDateTime_throwsOverlappingAppointmentException() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        Appointment editedBensonAppt = new AppointmentBuilder(BENSON_APPOINTMENT)
                .withDate(LocalDate.of(2050, 1, 1))
                .withTime(LocalTime.of(9, 0))
                .build();
        uniqueAppointmentList.add(BENSON_APPOINTMENT);
        assertThrows(OverlappingAppointmentException.class, () -> uniqueAppointmentList
                .setAppointment(BENSON_APPOINTMENT, editedBensonAppt));
    }

    @Test
    public void remove_nullAppointment_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.remove(null));
    }

    @Test
    public void remove_appointmentDoesNotExist_throwsAppointmentNotFoundException() {
        assertThrows(AppointmentNotFoundException.class, () -> uniqueAppointmentList.remove(ALICE_APPOINTMENT));
    }

    @Test
    public void remove_existingAppointment_removesAppointment() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        uniqueAppointmentList.remove(ALICE_APPOINTMENT);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setPatients_nullUniqueAppointmentList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList
                .setAppointments((UniqueAppointmentList) null));
    }

    @Test
    public void setPatients_uniquePatientList_replacesOwnListWithProvidedUniquePatientList() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(BENSON_APPOINTMENT);
        uniqueAppointmentList.setAppointments(expectedUniqueAppointmentList);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueAppointmentList.setAppointments((List<Appointment>) null));
    }

    @Test
    public void setAppointments_list_replacesOwnListWithProvidedList() {
        uniqueAppointmentList.add(ALICE_APPOINTMENT);
        List<Appointment> appointmentList = Collections.singletonList(BENSON_APPOINTMENT);
        uniqueAppointmentList.setAppointments(appointmentList);
        UniqueAppointmentList expectedUniqueAppointmentList = new UniqueAppointmentList();
        expectedUniqueAppointmentList.add(BENSON_APPOINTMENT);
        assertEquals(expectedUniqueAppointmentList, uniqueAppointmentList);
    }

    @Test
    public void setAppointments_listWithOverlappingAppointment_throwsOverlappingAppointmentException() {
        List<Appointment> listWithDuplicateAppointments = Arrays.asList(ALICE_APPOINTMENT, ALICE_APPOINTMENT);
        assertThrows(OverlappingAppointmentException.class, () -> uniqueAppointmentList
                .setAppointments(listWithDuplicateAppointments));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> uniqueAppointmentList
                .asUnmodifiableObservableList().remove(0));
    }
}

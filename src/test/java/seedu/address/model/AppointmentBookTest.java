package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalAppointments.APPOINTMENT_ONE;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.exceptions.OverlappingAppointmentException;
import seedu.address.testutil.AppointmentBuilder;

public class AppointmentBookTest {

    private final AppointmentBook appointmentBook = new AppointmentBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), appointmentBook.getAppointmentList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> appointmentBook.resetData(null));
    }


//  Wait for assign command
//    @Test
//    public void resetData_withValidReadOnlyAddressBook_replacesData() {
//        AppointmentBook newData = getTypicalAppointmentBook();
//        appointmentBook.resetData(newData);
//        assertEquals(newData, appointmentBook);
//    }

    @Test
    public void resetData_withOverlappingAppointments_throwsOverlappingAppointmentException() {
        // Two persons with the same identity fields
        Appointment editedAppointmentOne = new AppointmentBuilder(APPOINTMENT_ONE).withStartTime(LocalTime.of(9, 30))
                .build();
        List<Appointment> newAppointments = Arrays.asList(APPOINTMENT_ONE, editedAppointmentOne);
        AppointmentBookStub newData = new AppointmentBookStub(newAppointments);

        assertThrows(OverlappingAppointmentException.class, () -> appointmentBook.resetData(newData));
    }

//    @Test
//    public void hasPerson_nullPerson_throwsNullPointerException() {
//        assertThrows(NullPointerException.class, () -> appointmentBook.hasPerson(null));
//    }
//
//    @Test
//    public void hasPerson_personNotInAddressBook_returnsFalse() {
//        assertFalse(addressBook.hasPerson(ALICE));
//    }
//
//    @Test
//    public void hasPerson_personInAddressBook_returnsTrue() {
//        addressBook.addPerson(ALICE);
//        assertTrue(addressBook.hasPerson(ALICE));
//    }
//
//    @Test
//    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
//        addressBook.addPerson(ALICE);
//        Person editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
//                .build();
//        assertTrue(addressBook.hasPerson(editedAlice));
//    }

    @Test
    public void getAppointmentList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> appointmentBook.getAppointmentList().remove(0));
    }

    /**
     * A stub ReadOnlyAppointmentBook whose appointments list can violate interface constraints.
     */
    private static class AppointmentBookStub implements ReadOnlyAppointmentBook {
        private final ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        AppointmentBookStub(Collection<Appointment> persons) {
            this.appointments.setAll(persons);
        }

        @Override
        public ObservableList<Appointment> getAppointmentList() {
            return appointments;
        }
    }

}

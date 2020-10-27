package seedu.address.model.util;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AppointmentBook;
import seedu.address.model.PatientBook;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.Date;
import seedu.address.model.appointment.Time;
import seedu.address.model.patient.Address;
import seedu.address.model.patient.Name;
import seedu.address.model.patient.Nric;
import seedu.address.model.patient.Patient;
import seedu.address.model.patient.Phone;
import seedu.address.model.patient.Remark;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code PatientBook} and
 * {@code AppointmentBook} with sample data.
 */
public class SampleDataUtil {
    public static Patient[] getSamplePatients() {
        return new Patient[] {
            new Patient(new Name("Alex Yeoh"), new Phone("87438807"),
                new Address("Blk 30 Geylang Street 29, #06-40"),
                getTagSet("asthma"), new Nric("S9234567Q"), new Remark("Friendly guy")),
            new Patient(new Name("Bernice Yu"), new Phone("99272758"),
                new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                getTagSet("asthma", "hypertension"), new Nric("S9345678P"), new Remark("")),
            new Patient(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                getTagSet("asthma"), new Nric("S9456789L"), new Remark("next door store owner")),
            new Patient(new Name("David Li"), new Phone("91031282"),
                new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                getTagSet(), new Nric("T1234567V"), new Remark("")),
            new Patient(new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Address("Blk 47 Tampines Street 20, #17-35"),
                getTagSet("epilepsy"), new Nric("T0034567Q"), new Remark("Tough guy")),
            new Patient(new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Address("Blk 45 Aljunied Street 85, #11-31"),
                getTagSet("colleagues"), new Nric("T0934567Q"), new Remark("Honest boy"))
        };
    }

    public static ReadOnlyPatientBook getSamplePatientBook() {
        PatientBook samplePb = new PatientBook();
        for (Patient samplePatient : getSamplePatients()) {
            samplePb.addPatient(samplePatient);
        }
        return samplePb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    public static Appointment[] getSampleAppointments() {
        Patient[] patients = getSamplePatients();
        // Expired appointments will be deleted on model initialization
        return new Appointment[]{
            new Appointment(new Date(LocalDate.now()), new Time(15, 0), patients[2]),
            new Appointment(new Date(LocalDate.now().plusDays(1)), new Time(10, 30), patients[0]),
            new Appointment(new Date(LocalDate.now()), new Time(16, 0), patients[4]),
            new Appointment(new Date(LocalDate.now().plusDays(2)), new Time(20, 0), patients[0]),
            new Appointment(new Date(2025, 12, 1), new Time(13, 30), patients[3]),
            new Appointment(new Date(2021, 11, 5), new Time(9, 0), patients[1]),
            new Appointment(new Date(2022, 5, 2), new Time(9, 0), patients[3]),
            new Appointment(new Date(LocalDate.now().plusDays(1)), new Time(11, 30), patients[5]),
        };
    }

    public static ReadOnlyAppointmentBook getSampleAppointmentBook() {
        AppointmentBook sampleAb = new AppointmentBook();
        for (Appointment sampleAppointment : getSampleAppointments()) {
            sampleAb.addAppointment(sampleAppointment);
        }
        return sampleAb;
    }

}

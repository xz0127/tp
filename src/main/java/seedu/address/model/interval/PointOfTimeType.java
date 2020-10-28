package seedu.address.model.interval;

// The order is important here: if multiple events happen at the same point of time,
// this is the order in which you want to deal with them

/**
 * Represents 4 types of point of time. AppointmentEnd refers to the point of time when an appointment ends.
 * AppointmentStart refers to the point of time when an appointment starts. TimeIntervalEnd refers to the end time
 * of the operation of the clinic. TimeIntervalStart refers to the start time of the operation of the clinic.
 * A normal working day may consist of multiple working sessions with breaks in between.
 */
public enum PointOfTimeType { TimeIntervalEnd, AppointmentEnd, AppointmentStart, TimeIntervalStart }

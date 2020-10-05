package seedu.address.model.appointment;

import java.util.Arrays;

/**
 * Represents the natural days that are recognised by Nuudle.
 */
public enum NaturalDay {
    // Days of Week
    MONDAY("MON", "MONDAY"),
    TUESDAY("TUE", "TUESDAY", "TUES"),
    WEDNESDAY("WED", "WEDNESDAY"),
    THURSDAY("THU", "THURSDAY", "THURS"),
    FRIDAY("FRI", "FRIDAY"),
    SATURDAY("SAT", "SATURDAY"),
    SUNDAY("SUN", "SUNDAY"),

    // Other date language
    TODAY("TDY", "TODAY"),
    TOMORROW("TMR", "TOMORROW"),
    YESTERDAY("YESTERDAY"),

    // Time grammar
    MIDNIGHT("MIDNIGHT"),
    NOON("NOON", "AFTERNOON", "AFTER-NOON", "LUNCH"),
    MORNING("MORNING", "SUNRISE", "BREAKFAST"),
    EVENING("EVENING", "EVE", "DINNER"),
    NIGHT("NIGHT", "BEDTIME", "BED-TIME");

    /**
     * The string of words that is linked to the NaturalDay
     **/
    private final String[] words;

    NaturalDay(String... words) {
        this.words = words;
    }

    /**
     * Check if the word given indicate the respective {@code NaturalDay}
     *
     * @param wordToCheck the string to check
     * @return true if the word implies the corresponding {@code NaturalDay}, false otherwise
     */
    public boolean contains(String wordToCheck) {
        return Arrays.stream(words)
                .anyMatch(word -> word.equalsIgnoreCase(wordToCheck));
    }

    /**
     * Parse the string input for any natural date or time grammar that is used.
     *
     * @param dateString the date or time input string with a possible natural day reference.
     * @return the {@code NaturalDay} that corresponds to the date or time grammar, or null if no match is found.
     */
    public static NaturalDay parse(String dateString) {
        dateString = dateString.strip();
        NaturalDay[] days = NaturalDay.values();
        for (int i = 0; i < days.length; i++) {
            if (days[i].contains(dateString)) {
                return days[i];
            }
        }
        return null;
    }
}

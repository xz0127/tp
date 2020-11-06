package seedu.address.commons.core;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.logging.Level;

import org.junit.jupiter.api.Test;

public class ConfigTest {

    @Test
    public void toString_defaultObject_stringReturned() {
        String defaultConfigAsString = "Current log level : INFO\n"
                + "Preference file Location : preferences.json";

        assertEquals(defaultConfigAsString, new Config().toString());
    }

    @Test
    public void equalsMethod() {
        Config defaultConfig = new Config();
        assertNotNull(defaultConfig);

        // same values -> returns true
        Config copy = new Config();
        copy.setLogLevel(defaultConfig.getLogLevel());
        copy.setUserPrefsFilePath(defaultConfig.getUserPrefsFilePath());
        assertTrue(defaultConfig.equals(copy));

        // same object -> returns true
        assertTrue(defaultConfig.equals(defaultConfig));

        // null -> returns false
        assertFalse(defaultConfig.equals(null));

        // different type -> returns false
        assertFalse(defaultConfig.equals(5));

        // different content: level -> returns false
        Config editedConfig = new Config();
        editedConfig.setLogLevel(Level.ALL);
        assertFalse(defaultConfig.equals(editedConfig));

        // different content: path -> returns false
        editedConfig = new Config();
        editedConfig.setUserPrefsFilePath(Path.of("temp"));
        assertFalse(defaultConfig.equals(editedConfig));
    }


}

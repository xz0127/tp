package seedu.address.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.NuudleParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAppointmentBook;
import seedu.address.model.ReadOnlyPatientBook;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.patient.Patient;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final NuudleParser nuudleParser;
    private final CommandHistory commandHistory;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        nuudleParser = new NuudleParser();
        commandHistory = new CommandHistory();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {

        //Logging, safe to ignore
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        commandHistory.add(commandText);

        CommandResult commandResult;
        //Parse user input from String to a Command
        Command command = nuudleParser.parseCommand(commandText);
        //Executes the Command and stores the result
        commandResult = command.execute(model);

        try {
            // We can deduce that the previous line of code modifies model in some way
            // since it's being stored here.
            saveData();
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public void saveData() throws IOException {
        storage.savePatientBook(model.getPatientBook());
        storage.saveAppointmentBook(model.getAppointmentBook());
    }

    @Override
    public ReadOnlyPatientBook getPatientBook() {
        return model.getPatientBook();
    }

    @Override
    public ObservableList<Patient> getFilteredPatientList() {
        return model.getFilteredPatientList();
    }

    @Override
    public Path getPatientBookFilePath() {
        return model.getPatientBookFilePath();
    }

    @Override
    public ReadOnlyAppointmentBook getAppointmentBook() {
        return model.getAppointmentBook();
    }

    @Override
    public ObservableList<Appointment> getFilteredAppointmentList() {
        return model.getFilteredAppointmentList();
    }

    @Override
    public Path getAppointmentBookFilePath() {
        return model.getAppointmentBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public String getStorageStatus() {
        return storage.getStatusMessage();
    }

    @Override
    public ObservableList<String> getCommandHistory() {
        return commandHistory.asUnmodifiableObservableList();
    }
}

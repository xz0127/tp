package seedu.address.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AppointmentStatistics;


/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PatientListPanel patientListPanel;
    private AppointmentListPanel appointmentListPanel;
    private ResultDisplay resultDisplay;
    private StatisticsDisplay statisticsDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private Label dateTime;

    @FXML
    private SplitPane splitView;

    @FXML
    private StackPane patientListPanelPlaceholder;

    @FXML
    private StackPane appointmentListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statisticsDisplayPlaceholder;

    @FXML
    private StackPane patientStatusbarPlaceholder;

    @FXML
    private StackPane appointmentStatusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        patientListPanel = new PatientListPanel(logic.getFilteredPatientList());
        patientListPanelPlaceholder.getChildren().add(patientListPanel.getRoot());

        appointmentListPanel = new AppointmentListPanel(logic.getFilteredAppointmentList());
        appointmentListPanelPlaceholder.getChildren().add(appointmentListPanel.getRoot());

        statisticsDisplay = new StatisticsDisplay(logic.getAppointmentBook().getAppointmentBookStatistics());
        statisticsDisplayPlaceholder.getChildren().add(statisticsDisplay.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        resultDisplay.setFeedbackToUser(logic.getStorageStatus());

        StatusBarFooter patientStatusBarFooter = new StatusBarFooter(logic.getPatientBookFilePath());
        patientStatusbarPlaceholder.getChildren().add(patientStatusBarFooter.getRoot());

        StatusBarFooter appointmentStatusBarFooter = new StatusBarFooter(logic.getAppointmentBookFilePath());
        appointmentStatusbarPlaceholder.getChildren().add(appointmentStatusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(logic.getCommandHistory(), this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        initClock();

        setSplitViewPosition(logic.getGuiSettings());
    }

    // @@author JinHao-L-reused
    // Inspired by CS2103T-T12-1
    // Reused from https://stackoverflow.com/q/42383857 with minor modifications.
    private void initClock() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM uuuu h:mm a");
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, event -> {
            dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(15)));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }
    // @@author

    private void setSplitViewPosition(GuiSettings guiSettings) {
        splitView.setDividerPosition(0, guiSettings.getSplitViewRatio());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY(), splitView.getDividerPositions()[0]);
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    public PatientListPanel getPatientListPanel() {
        return patientListPanel;
    }

    public AppointmentListPanel getAppointmentListPanel() {
        return appointmentListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            AppointmentStatistics stats = logic.getAppointmentBook().getAppointmentBookStatistics();
            assert (stats != null);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser() + "\n");
            statisticsDisplay.setStatistics(stats.toString());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage() + "\n");
            throw e;
        }
    }
}

---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<img src="images/ArchitectureDiagram.png" width="450" />

The ***Architecture Diagram*** given above explains the high-level design of the App. Given below is a quick overview of each component.

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![Class Diagram of the Logic Component](images/LogicClassDiagram.png)

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### UI component

![Structure of the UI Component](images/UiClassDiagram.png)

**API** :
[`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class.

The `UI` component uses JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* Executes user commands using the `Logic` component.
* Listens for changes to `Model` data so that the UI can be updated with the modified data.

### Logic component

![Structure of the Logic Component](images/LogicClassDiagram.png)

**API** :
[`Logic.java`](https://github.com/AY2021S1-CS2103T-T12-4/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

1. `LogicManager` implements the `Logic` interface and uses the `NuudleParser` class to parse the user command with the `execute` method.
1. This results in a `Command` object which is then executed by the `LogicManager`.
1. The command execution can affect the `Model` (e.g. adding or deleting a patient).
1. The result of the command execution is encapsulated as a `CommandResult` object which is passed back to the `Ui`.
1. In addition, the `CommandResult` object can also instruct the `Ui` to perform certain actions, such as displaying help to the user.

Given below is the Sequence Diagram for interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

### Model component

![Structure of the Model Component](images/ModelClassDiagram.png)

**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

The `Model`,

* stores a `UserPref` object that represents the user’s preferences.
* stores the patient book data.
* exposes an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* does not depend on any of the other three components.


<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `PatientBook`, which `Person` references. This allows `PatientBook` to only require one `Tag` object per unique `Tag`, instead of each `Person` needing their own `Tag` object.<br>
![BetterModelClassDiagram](images/BetterModelClassDiagram.png)

</div>


### Storage component

![Structure of the Storage Component](images/StorageClassDiagram.png)

**API** : [`Storage.java`](https://github.com/AY2021S1-CS2103T-T12-4/tp/tree/master/src/main/java/seedu/address/storage/Storage.java)

The `Storage` component,
* can save `UserPref` objects in json format and read it back.
* can save the patient book data in json format and read it back.
* can save the appointment data in json format and read it back.
* can save the appointment data in csv format for archiving.

### Common classes

Classes used by multiple components are in the `seedu.patientbook.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedPatientBook`. It extends `PatientBook` with an undo/redo history, stored internally as an `patientBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedPatientBook#commit()` — Saves the current patient book state in its history.
* `VersionedPatientBook#undo()` — Restores the previous patient book state from its history.
* `VersionedPatientBook#redo()` — Restores a previously undone patient book state from its history.

These operations are exposed in the `Model` interface as `Model#commitPatientBook()`, `Model#undoPatientBook()` and `Model#redoPatientBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedPatientBook` will be initialized with the initial patient book state, and the `currentStatePointer` pointing to that single patient book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th patient in the patient book. The `delete` command calls `Model#commitPatientBook()`, causing the modified state of the patient book after the `delete 5` command executes to be saved in the `PatientBookStateList`, and the `currentStatePointer` is shifted to the newly inserted patient book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new patient. The `add` command also calls `Model#commitPatientBook()`, causing another modified patient book state to be saved into the `patientBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitPatientBook()`, so the patient book state will not be saved into the `patientBookStateList`.

</div>

Step 4. The user now decides that adding the patient was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoPatientBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous patient book state, and restores the patient book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial PatientBook state, then there are no previous PatientBook states to restore. The `undo` command uses `Model#canUndoPatientBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoPatientBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the patient book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `patientBookStateList.size() - 1`, pointing to the latest patient book state, then there are no undone PatientBook states to restore. The `redo` command uses `Model#canRedoPatientBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the patient book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all patient book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

![CommitActivityDiagram](images/CommitActivityDiagram.png)

#### Design consideration:

##### Aspect: How undo & redo executes

* **Alternative 1 (current choice):** Saves the entire patient book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the patient being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope
**Target user profile story**:

Namise is a hard working nurse working at a popular dental clinic situated in town and gets appointment calls on an hourly basis. Swarmed with incoming calls, Namise has to make new appointments for new and existing patients while keeping track of the doctor’s schedule at the same time 😞. With the need to juggle multiple tasks at once, Namise is also prone to making careless mistakes in his work due to fatigue.

Being a tech-savvy nurse armed with a commendable experience in unix, Namise prefers to scribble down appointment schedules on paper while on call with his patients to maximise efficiency. This task is further exacerbated with the need to transfer these notes into an excel table manually later in the day.
 
**Target user profile summary**:
*   Nurse working in a highly popular, small scale dental clinic
*   Responsible for scheduling a large number of appointments daily
*   Add new patients to the clinic records
*   Do not entertain walk-ins and only operate on an appointment-basis
*   Required to multi-task (create appointment arrangements with patients over the phone)
*   Prone to carelessness due to the sheer number of appointments to handle
*   Tech-savvy
*   Prefers typing & wants to get things done quickly
*   Tired of transferring appointment details from paper notes to excel
*   Prefers desktop apps over other types
*   Types fast
*   Prefers typing to mouse interactions
*   Reasonably comfortable using CLI apps

**Value proposition**: 

Help nurses **handle and schedule dental appointments for patients** faster than a typical mouse/GUI driven app or excel scheduling


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​          | I want to …​                      | So that I can…​                                                      |
| -------- | --------------------| -------------------------------------| ------------------------------------------------------------------------|
| `* *`    | new user            | see an overview of the commands that are available to me | familiarise myself with using the app command       |
| `* * *`  | nurse               | view the entire patient list         | assign them to their appointments                                       |
| `* * *`  | administrative nurse| add new patient into the system      | assign an appointment to them                                           |
| `* * *`  | administrative nurse| delete the patient information       | manage the patient list easily when it is needed                        |
| `* * *`  | administrative nurse| search for patient by IC             | locate a patient easily                                                 |
| `* * *`  | nurse               | create patient appointment           | assign patient to a time slot for their appointment                     |
| `* * *`  | nurse               | delete an appointment                | cancel an appointment                                                   |
| `* * *`  | nurse               | view the entire appointment list for the certain day| update my dentists of the itinerary for the day          |
| `* * *`  | nurse               | mark the appointment as complete if the patient completes his/her appointment| have an accurate representation of the remaining appointments|
| `* *`    | nurse               | add diagnosis to a completed appointment| have a record of the patient’s visit                                 |
| `* *`    | administrative nurse| search for patient by phone number   | locate a patient easily                                                 |
| `* *`    | administrative nurse| search for patient by name           | locate a patient easily                                                 |
| `* *`    | administrative nurse| change the patient’s appointment if they call to postpone their appointment| update the appointment easily     |
| `* *`    | administrative nurse| edit patient’s details               | keep the information of patients up-to-date                             |
| `* *`    | nurse               | get the available time slots for a day| inform my patient of the available times for that day                  |
| `*`      | nurse               | get the next available time slot     | inform my patient of the next available timing should their original preferred choice be filled|
| `* *`    | nurse handling multiple appointments| archive records of past appointments| review them in the future                                |
| `*`      | nurse               | record the patient's existing medical condition | confirm if the medicine prescribed are suitable              |
| `*`      | nurse               | check the patient’s drug allergy     | confirm if the medicines prescribed are not in the list                 |
| `*`      | nurse               | get the number of appointments for the day | know how many appointments to expect today                        |
| `*`      | nurse               | get the number of appointments for the week | know how busy the clinic is for the week                         |


### Use cases

(For all use cases below, the **System** is the `Nuudle` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC01 - Add a patient**

**MSS**

1.  User requests to add a patient to the list.
2.  Nuudle adds the patient.

**Extensions**

* 1a. The given keywords are invalid.

    * 1a1. Nuudle shows an error message.
    
      Use case ends.

&nbsp;

**Use case: UC02 - Delete a patient**

**MSS**

1.  User requests to list patients.
2.  Nuudle shows a list of patients.
3.  User requests to delete a specific patient in the list.
4.  Nuudle deletes the patient.

    Use case ends.

**Extensions**

* 1a. The given keywords are invalid.

    * 1a1. Nuudle shows an error message.
    
      Use case ends.

* 2a. The list is empty.

  Use case ends.
  
* 3a. The given index is invalid.

    * 3a1. Nuudle shows an error message.
    
      Use case resumes at step 2.

&nbsp;

**Use case: UC03 - Edit a patient**

**MSS**

1.  User requests to find a specific patient.
2.  Nuudle shows the list of patients with the given name.
3.  User requests to edit the patient information.
4.  Nuudle changes the patient information.

    Use case ends.

**Extensions**

* 1a. The given keywords are invalid.

    * 1a1. Nuudle shows an error message.

      Use case ends.

* 3a. The given information for editing is invalid (including empty input).

    * 3a1. Nuudle shows an error message.

      Use case resumes at step 2.

&nbsp;

**Use case: UC04 - View patient record**

**MSS**

1. User requests to find a patient by name.
2. Nuudle shows the list of patients with the requested name.
3. User request to view patient record of a specific patient in the list.
4. Nuudle shows the list of records for that patient.

   Use case ends.

**Extensions**

* 1a. The given keywords are invalid.

    * 1a1. Nuudle shows an error message.

      Use case ends.

* 2a. The list is empty.

  Use case ends.

&nbsp;

**Use case: UC05 - Add an appointment**

**MSS**

1.  User requests to find an available time slot.
2.  Nuudle shows the available time slots.
3.  User requests to add an appointment to a specific time slot.
4.  Nuudle adds the appointment to the list of appointment records.

    Use case ends.
    
**Extensions**

* 1a. The given keywords are invalid.

    * 1a1. Nuudle shows an error message.

      Use case ends.
      
* 3a. The given time slot is invalid (including empty input).

    * 3a1. Nuudle shows an error message.

      Use case resumes at step 2.

&nbsp;
 
**Use case: UC06 - Delete an appointment**

**MSS**

1. User requests to list appointments.
2. Nuudle shows the list of appointments.
3. User requests to delete a specific appointment in the list.
4. Nuudle deletes the appointment.

   Use case ends.

**Extensions**

* 1a. The given index is invalid.

    * 1a1. Nuudle shows an error message.

      Use case ends.

* 2a. The list is empty.

  Use case ends.

&nbsp;

**Use case: UC07 - Change an appointment**

**MSS**

1.  User requests to list all appointments.
2.  Nuudle shows a list of appointments.
3.  User requests to find an available time slot.
4.  Nuudle shows the available time slots.
5.  User requests to change a specific appointment to another time.
6.  Nuudle changes the appointment.

    Use case ends.

**Extensions**

* 1a. The given keywords are invalid.

    * 1a1. Nuudle shows an error message.

      Use case ends.
      
* 2a. User has no current appointments.

    * 2a1. Nuudle shows an error message.

      Use case ends.
      
* 5a. The given time slot is invalid (including empty input).

    * 5a1. Nuudle shows an error message.

      Use case resumes at step 4.
    
* 5b. The given keywords are invalid.
      
     * 5b1. Nuudle shows an error message.
      
       Use case ends.

&nbsp;

**Use case: UC08 - Mark an appointment as complete**

**MSS**

1. User requests to list appointments.
2. Nuudle shows the list of appointments.
3. User requests to mark an appointment as done.
4. Nuudle marks the appointment as done.

   Use case ends.

**Extensions**

* 3a. The given index is invalid.

    * 3a1. Nuudle shows an error message.

      Use case resumes at step 2.

**Use case: UC09 - View appointments for today**

**MSS**

1.  User requests for the appointments scheduled for today.
2.  Nuudle shows a list of appointments scheduled for today.

    Use case ends.

**Extensions**

* 1a. The given keywords are invalid.

    * 1a1. Nuudle shows an error message.
    
      Use case ends.

* 1b. The given date is invalid.

    * 1b1. Nuudle shows an error message.

      Use case ends.

* 2a. The list is empty.

  Use case ends.

&nbsp;

**Use case: UC10 - Create an appointment for a new patient**

**MSS**

1. User requests to create a <u>new patient (UC01)</u>.
2. Nuudle creates the new patient.
3. User requests for an available time slot on a preferred day.
4. Nuudle shows the available time slots.
5. User requests to add an appointment for the new patient.
6. Nuudle creates the appointment.

   Use case ends.

**Extensions**

* 3a. The given date is invalid.

    * 3a1. Nuudle shows an error message.

      Use case resumes at step 2.

* 4a. No more time slot is available for that day.

    * 4a1. Nuudle shows the next available time slot on the nearest day.
    
        * 4a1a. User uses the suggested time slot.

          Use case resumes at step 5.

        * 4a1b. User does not use the suggested time slot.
        
          Use case resumes at step 3.
        
### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2.  Should be able to hold up to 500 patients without a noticeable sluggishness in performance for typical usage.
3.  Should be able to hold up to 1000 upcoming appointments without a noticeable sluggishness in performance for typical usage.
4.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Patient records**: The past records of the patient's visit to the clinic. May contain doctor's diagnosis (if any).

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a patient

1. Deleting a patient while all patients are being shown

   1. Prerequisites: List all patients using the `list` command. Multiple patients in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No patient is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_

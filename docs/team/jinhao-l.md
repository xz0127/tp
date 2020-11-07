---
layout: page
title: Lim Jin Hao's Project Portfolio Page
---

## Project: Nuudle

Nuudle is a **desktop app that helps nurses manage patient records and schedule appointments** in an accurate and efficient manner.
It is optimised for use via a Command Line Interface (CLI), and has a Graphical User Interface (GUI) built with JavaFX.

It is written in Java, and has about 20 kLoC, of which I contributed about 5 kLoC.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=jinhao-l)

* **New Feature**: Added the ability to automatically archive past appointments. ([\#111](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/111))
  * **What it does**: automatically archive past appointments into the archive folder as a Comma-Separated Values (CSV) file upon starting the app. The data are saved according to their months.
  * **Justification**: This feature improves the product significantly as it removes past appointments, reducing the amount of data shown to the user. The past appointments will be neatly stored in a CSV file for future reference.
  * **Highlights**: This enhancement requires understanding on how Jackson serialised objects to CSV format. The implementation was challenging as certain data type, like Sets, cannot be serialised into a linear data form, like CSV format.
  * **Credits**: The [Jackson CSV library](https://github.com/FasterXML/jackson-dataformats-text/tree/master/csv) was used to serialise Java objects to CSV format for saving.

* **New Feature**: Added a history feature that allows the user to navigate to previous commands using up/down arrow keys. ([\#128](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/128))
  * **Justification**: This feature improves the product significantly because a user may want to repeat similar commands without typing in the whole command. This feature pairs well with the undo feature as it allows user to undo and subsequently modify the previously executed command.

* **Enhancements to existing features**:
  * Improved the existing storage feature to allow reading and saving of appointment data from a JSON file. ([\#94](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/94))
  * Added parser logic for date and time, with support for natural datetime language. ([\#86](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/86))
  * Updated the GUI to support appointment list view, live clock view and realtime update to appointment list. ([\#87](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/87), [\#127](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/127), [\#175](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/175))
  <div style="page-break-after: always;"></div>
  * Improved the way the app loads corrupted data from the storage. ([\#123](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/123))
    * **What it does**: Loads all uncorrupted data from the JSON file and displays a status message if there are any corrupted appointment or patient data.
    * **Justification**: Previously, if any data is corrupted in the JSON file, the whole JSON file will be invalidated, and the app starts up with an empty appointment/patient book. This is not appropriate as it removes all user's data without warning even when some of the data are not corrupted.
    * **Highlights**: This enhancement requires detection of corrupted data by checking for missing fields, invalid data type in fields (such as unsupported date, time) and unexpected data value (such as null paths).
  * Added commonly-used commands in help box (can be accessed through `help` command). ([\#131](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/131))
  * Wrote relevant tests for the new implementation and enhancements to existing features.

* **Project management**:
  * Maintained the issue tracker and managed milestones
  * Managed releases `v1.1` - `v1.4` (4 releases) on GitHub

* **Documentation**:
  * README:
    * Updated README to stay updated to current Nuudle project details ([\#40](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/40))
  * User Guide:
    * Added details for the `assign`, `cancel` and data archiving feature. ([\#42](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/42))
    * Authored details on the Nuudle's supported date time format. ([\#49](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/49))
    * Authored details on Nuudle's file backup feature. ([\#123](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/123))
  * Developer Guide:
    * Authored the non-functional requirements and glossary segment. ([\#50](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/50))
    * Updated and maintained the Storage design section, and the corresponding Storage UML class diagram. ([\#110](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/110), [\#139](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/139))
    * Added implementation details on the data archiving feature, with the appropriate UML diagrams. ([\#118](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/118))
    * Added more manual testing instructions to the appendix section for manually testing data archiving and data corruption handling feature. ([\#131](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/131))

* **Community**:
  * PRs reviewed (with non-trivial review comments): ([\#92](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/92), [\#95](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/95), [\#96](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/96))
  * Contributed to forum discussions
  (examples:
  [1](https://github.com/nus-cs2103-AY2021S1/forum/issues/99#issuecomment-683256251),
  [2](https://github.com/nus-cs2103-AY2021S1/forum/issues/94#issuecomment-692407535),
  [3](https://github.com/nus-cs2103-AY2021S1/forum/issues/318#issuecomment-708233836))

* **Tools**:
  * Integrated a new Github plugin (PlantUML GitHub Action) to the team repo ([\#102](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/102))
    * Credits for workflow to: [qwoprocks](https://github.com/nus-cs2103-AY2021S1/forum/issues/309#issue-718884781)


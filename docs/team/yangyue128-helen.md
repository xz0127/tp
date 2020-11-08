---
layout: page
title: Yang Yue's Project Portfolio Page
---

## Project: Nuudle

Nuudle is a **desktop app that helps nurses manage patient records and schedule appointments** in an accurate and efficient manner.
It is optimised for use via a Command Line Interface (CLI), and has a Graphical User Interface (GUI) built with JavaFX.

It is written in Java, and has about 20 kLoC, of which I contributed about 2.5 kLoC.

Given below are my contributions to the project.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2021s1.github.io/tp-dashboard/#breakdown=true&search=yangyue128-helen)

* **New Model**: Created the appointment book model which is similar to the patient book model to support implementing features related to appointment. ([\#79](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/79))
  * **Justification**: This model of appointment book is essential, since all the operations on the appointment book list need to call methods in appointment book model. It is a bridge between logic component (commands) and actual model component (appointment list).

* **New Feature**: Added a feature allowing users to mark a specific appointment as done. ([\#92](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/92))
  * **What it does**: Allows the user to mark an appointment as done, and turn the status bar in appointment card into done status.
  * **Justification**: This feature improves the product significantly since it will be easy for nurse to identify upcoming appointments from an overwhelming list of appointments. The color of the status bar indicates the status of the appointment.
  
* **New Feature**: Added the ability to undo/redo previous commands. ([\#125](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/125))
  * **What it does**: Allows the user to undo all previous commands one at a time. Preceding undo commands can be reversed by using the redo command.
  * **Justification**: This feature improves the product significantly since it will be troublesome if users accidentally execute commands like `delete 1` and `cancel 1` and all the information is unrecoverable. Now a user can make mistakes in commands and the app should provide a convenient way to rectify them.
  * **Highlights**: This enhancement affects existing commands and commands to be added in future. It required an in-depth analysis of design alternatives. The implementation too was challenging as it required changes to existing commands.
  * **Credits**: Undo/redo implementation in ab4. ([\#ab4](https://github.com/se-edu/addressbook-level4))
<div style="page-break-after: always;"></div>

* **Enhancements to existing features**:
  * Updated the GUI to support viewing the overview statistic for current day and current week. Provided a realtime update of the statics if some related commands are excecuted.([\#103](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/103))
  * Added relevant tests for the new implementation and untested model. ([\#173](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/173))

* **Documentation**:
  * User Guide:
    * Added documentation for the features `view` and `done` ([\#53](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/53))
    * Added documentation for the features `undo` and `redo` ([\#140](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/140))
  * Developer Guide:
    * Added user stories segment. ([\#56](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/56))
    * Updated and maintained the UI design section, and the corresponding UI UML class diagram. ([\#113](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/113))
    * Added implementation details on the mark as done feature, with the appropriate UML diagrams. ([\#113](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/113))
    * Updated the implementation details on the undo and redo feature, with feasible UML diagrams. ([\#184](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/184))

* **Community**:
  * PRs reviewed : ([\#91](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/91), [\#114](https://github.com/AY2021S1-CS2103T-T12-4/tp/pull/114))
  * Bugs reported in other teams project: ([\#6](https://github.com/YangYue128-helen/ped/issues/6), [\#2](https://github.com/YangYue128-helen/ped/issues/2), [\#5](https://github.com/YangYue128-helen/ped/issues/5))

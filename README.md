# RP6-JAVA-CLIENT

The purpose of this project is to create a Java Client to control remotely the RP6 Robot made by Arexx

Of course, this supposes that you have the WIFI-Module installed on the robot.

## Requirements

To control the RP6, you will need to have installed 2 Programs on the RP6. You can find them on the [RP6 website][rp6_link].

On the base, you need to install the example program **I2C-Master-Slave**.

On the wifi module, you need to install the example program **WIFI-REMOTE-CONTROL-2**

The installation can be done by using the software **RP6-LOADER** from the CD containing drivers for the robot.

## Functionalities

Actually, with this project, you are able to :

- **control the robot remotely with the keyboard**
- **control remotely the robot with the mouse**
- **read all sensor values and monitor them**
- **load trajectory based on a specific type of file (autopilot)**
- **create a trajectory with a trajectory editor included**
- **presence of a log with the different actions done**
- **console mode**

## Project Structure

The project contains multiple packages :

- **model** : Package containing object classes related to the robot (trajectory, sensorsData...)
- **view** : Package containing classes related to the UI
- **controller** : Package containing classes which manipulates the model classes.
- **enums** : Package containing all project's enums which describes different type of states for the robot
- **console** : Package containing classes related to the console mode of the Java client

## Object structure

The different classes in the project have a defined role which is the following :
- **RobotSensorsData** : Class which holds the different sensors value available for the robot
- **DriveCommand** : Class which holds the different value related to a command which can be sent to a robot in the autopilot mode (_direction,speed,duration_)
- **RobotTrajectory** : Class which holds the different values related to a trajectory executed by a robot (_List of **DriveCommand**, ONCE or LOOP execution_...)
- **BlinkerUI** : Class which manages "blink" animation in the UI (Connecting state, low battery warning...)
- **RobotClientUI** : Class which is the main UI of the software (JFrame)
- **PopupCommandEditor** : A JDialog popup to edit a command when you edit a drive command in the trajectory editor
- **RobotIO** : A static class to read, write, check the .traj file which are used in the autopilot mode. **A .traj file is translated as a RobotTrajectory object by the client**.
- **RobotClient** : The class which makes the communication between the robot and the client possible (open connection,send string commands,etc...)
- **Launcher** : The class which holds the 'main' and decides if the software starts in console or graphical mode
- **RobotClientConsole** : The class which holds the way the console mode works
- **ConsoleState** : The class which defines the current state of the console mode (WAIT for user input,etc...)


## License
 
 This project is licensed under GNU 3.0
 
 
 [rp6_link]: <http://www.arexx.com/rp6/html/en/software.htm>

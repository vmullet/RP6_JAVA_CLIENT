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

## Command mode

**NOTE : The command mode is the mode when the robot is able to interpret the commands that you will send to it. To enter this mode, when the robot connects, the client sends "\ncmd".(In the client, it is automatic.)**

## Commands syntax for remote control

The command syntax interpreted by the **C program** installed on the wifi module is very simple:

The first parameter is the direction :
- **f** : FORWARD
- **b** : BACKWARD
- **l** : LEFT
- **r** : RIGHT

The second parameter is the speed which is an integer number between 1 and 160

**NOTE : The direction and the speed must be separated with a break line "\n"**

_Example_ : **"f\n110"**

## Traj File Format

The autopilot is based on a specific file format that I created and which is called .traj file.
This is the standard structure of a traj file :
A traj file **always** starts with **"--BEGIN--"** and ends with **"--END--"**.
The second line of the file defines the trajectory mode which can be: **"ONCE"** or **"LOOP"** _(with two stars before and after the word)_
All other lines defines the drive commands which compose the trajectory ; a drive command is follows this regular expression:
**[f|b|l|r][{][0-9]{1,3}[}][-][>][0-9]+**

**Example** : f{85}->15 means (_"Move forward at speed 85 during 15 seconds"_)

## Trajectory Editor

The Java client includes a graphical trajectory editor to create a traj file.

## Console Mode

The console mode is useful if you want to use the client on a non graphical OS such as a Linux distribution for example.
To launch the console, you have to export the client as a runnable jar and use the following command :
_**java -jar my_client.jar --console**_

For more information about the commands, you can use the command ".help" once the console mode is started or you can check the file **RobotClientConsole.java** in the source code.

## License
 
 This project is licensed under GNU 3.0
 
 [rp6_link]: <http://www.arexx.com/rp6/html/en/software.htm>

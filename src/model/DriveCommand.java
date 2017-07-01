package model;

import enums.RobotDirection;

/**
 * Class which allow to store informations related to a command sent to the robot
 * @author Valentin
 *
 */
public class DriveCommand {

	/**
	 * Attribut which stores the direction of this command (forward,left...)
	 */
	private RobotDirection _robotDirection;
	
	/**
	 * Attribute which stores the speed of this command
	 */
	private int _robotSpeed;
	
	/**
	 * Attribute which stores the duration of this command in seconds
	 */
	private int _commandDuration;

	/**
	 * Default Constructor
	 */
	public DriveCommand() {
		_robotDirection = RobotDirection.NONE;
		_robotSpeed = 0;
		_commandDuration = 0;
	}

	/**
	 * Constructor with 3 parameters
	 * @param dir_letter A dir letter to translate into a RobotDirection Object (f->FORWARD for example...)
	 * @param p_speed The speed for this command
	 * @param p_duration The duration for this command
	 */
	public DriveCommand(String dir_letter, int p_speed, int p_duration) {
		_robotDirection = getDirectionFromCommand(dir_letter);
		_robotSpeed = p_speed;
		_commandDuration = p_duration;

	}

	/**
	 * Constructor with 3 parameters
	 * @param p_direction The RobotDirection for this command
	 * @param p_speed The speed for this command
	 * @param p_duration The duration for this command
	 */
	public DriveCommand(RobotDirection p_direction, int p_speed, int p_duration) {
		_robotDirection = p_direction;
		_robotSpeed = p_speed;
		_commandDuration = p_duration;
	}

	/**
	 * Method to get the {@link DriveCommand#_robotDirection}
	 * @return The value of the attribute
	 */
	public RobotDirection get_robotDirection() {
		return _robotDirection;
	}

	/**
	 * Setter for the {@link DriveCommand#_robotDirection}
	 * @param p_robotDirection The value to affect to the attribute
	 */
	public void set_robotDirection(RobotDirection p_robotDirection) {
		this._robotDirection = p_robotDirection;
	}

	/**
	 * Method to get the {@link DriveCommand#_robotSpeed}
	 * @return The new value affected to the attribute
	 */
	public int get_robotSpeed() {
		return _robotSpeed;
	}

	/**
	 * Setter for the {@link DriveCommand#_robotSpeed}
	 * @param p_robotSpeed The new value affected to the attribute
	 */
	public void set_robotSpeed(int p_robotSpeed) {
		this._robotSpeed = p_robotSpeed;
	}

	/**
	 * Method to get the {@link DriveCommand#_commandDuration}
	 * @return The value of the attribute
	 */
	public int get_commandDuration() {
		return _commandDuration;
	}

	/**
	 * Setter for the {@link DriveCommand#_commandDuration}
	 * @param p_commandDuration The new value affected to the attribute
	 */
	public void set_commandDuration(int p_commandDuration) {
		this._commandDuration = p_commandDuration;
	}

	/**
	 * Method to translate a drive command to a command for the autopilot mode
	 * @return The string command based on the drive command
	 */
	public String toStringCommandForAutoPilot() {
		if (_robotDirection == RobotDirection.LEFT
				|| _robotDirection == RobotDirection.RIGHT)
			return "f" + "\n" + _robotSpeed;
		
		return getCommandFromDirection(_robotDirection) + "\n" + _robotSpeed;
	}
	
	/**
	 * Metrhod to translate a DriveCommand to a line of a traj file
	 * @return	The translated DriveCommand into a traj file syntax
	 */
	public String toStringTraj() {
		return getCommandFromDirection(_robotDirection) + "{" + _robotSpeed + "}->" + _commandDuration;
	}
	
	/**
	 * Method to modify the toString method of this object (used in the UI trajectory editor)
	 */
	@Override
	public String toString() {
		return _robotDirection.toString() + "(" + _robotSpeed + ";" + _commandDuration + ")";
	}

	/**
	 * Method to translate a RobotDirection into a String letter/command
	 * @param p_direction The RobotDirection to translate
	 * @return Return the String command / dir letter
	 */
	public static String getCommandFromDirection(RobotDirection p_direction) {
		String direction = "";

		switch (p_direction) {

		case FORWARD:
			direction = "f";
			break;

		case BACKWARD:
			direction = "b";
			break;

		case LEFT:
			direction = "l";
			break;

		case RIGHT:
			direction = "r";
			break;

		case NONE:
			direction = "";
			break;

		default:
			break;

		}
		return direction;
	}

	/**
	 * LMethod to translate a string command / dir letter into a RobotDirection object
	 * @param command The string command / dir letter to translate
	 * @return Return the RobotDirection
	 */
	private static RobotDirection getDirectionFromCommand(String command) {
		RobotDirection direction = RobotDirection.NONE;
		switch (command) {

		case "f":
			direction = RobotDirection.FORWARD;
			break;

		case "b":
			direction = RobotDirection.BACKWARD;
			break;

		case "l":
			direction = RobotDirection.LEFT;
			break;

		case "r":
			direction = RobotDirection.RIGHT;
			break;

		case "":
			direction = RobotDirection.NONE;
			break;

		default:
			break;

		}
		return direction;
	}

}

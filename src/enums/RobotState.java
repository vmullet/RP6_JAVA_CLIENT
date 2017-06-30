package enums;

/**
 * Enum to get the actual state of the robot (auto or manual and what directions)
 * @author Valentin
 *
 */
public enum RobotState {

	NONE, /* The robot is doing nothing */
	READY_TO_START,
	AUTO_MOVING_FORWARD, 
	AUTO_MOVING_BACKWARD, 
	AUTO_TURNING_LEFT, 
	AUTO_TURNING_RIGHT, 
	MANUAL_MOVING_FORWARD, 
	MANUAL_MOVING_BACKWARD, 
	MANUAL_TURNING_LEFT, 
	MANUAL_TURNING_RIGHT;

}

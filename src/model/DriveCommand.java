package model;

public class DriveCommand {

	private RobotDirection _robotDirection;
	private int _robotSpeed;
	private int _commandDuration;
	
	public DriveCommand() {
		_robotDirection = RobotDirection.NONE;
		_robotSpeed = 0;
		_commandDuration = 0;
	}
	
	public DriveCommand(String dir_letter,int p_speed,int p_duration) {
		_robotDirection = getDirectionFromCommand(dir_letter);
		_robotSpeed = p_speed;
		_commandDuration = p_duration;
		
	}
	
	public DriveCommand(RobotDirection p_direction,int p_speed,int p_duration) {
		_robotDirection = p_direction;
		_robotSpeed = p_speed;
		_commandDuration = p_duration;
	}

	public RobotDirection get_robotDirection() {
		return _robotDirection;
	}

	public void set_robotDirection(RobotDirection _robotDirection) {
		this._robotDirection = _robotDirection;
	}

	public int get_robotSpeed() {
		return _robotSpeed;
	}

	public void set_robotSpeed(int _robotSpeed) {
		this._robotSpeed = _robotSpeed;
	}

	public int get_commandDuration() {
		return _commandDuration;
	}

	public void set_commandDuration(int _commandDuration) {
		this._commandDuration = _commandDuration;
	}
	
	public String toStringCommand() {
		return getCommandFromDirection(_robotDirection)
				+ "\n"
				+ _robotSpeed;
	}
	
	
	public static String getCommandFromDirection(RobotDirection p_direction) {
		String direction = "";
		
		switch(p_direction) {
		
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
	
	private RobotDirection getDirectionFromCommand(String command) {
		RobotDirection direction = RobotDirection.NONE;
		switch(command) {
		
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

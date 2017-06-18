package model;

public class DriveCommand {

	private RobotDirection _robotDirection;
	private int[] _robotSpeed;
	private int _commandDuration;
	
	public DriveCommand(RobotDirection p_direction,int[] p_speed,int p_duration) {
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

	public int[] get_robotSpeed() {
		return _robotSpeed;
	}

	public void set_robotSpeed(int[] _robotSpeed) {
		this._robotSpeed = _robotSpeed;
	}

	public int get_commandDuration() {
		return _commandDuration;
	}

	public void set_commandDuration(int _commandDuration) {
		this._commandDuration = _commandDuration;
	}
	
	
	
}

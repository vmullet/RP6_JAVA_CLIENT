package model;

import java.util.ArrayList;

import controller.RobotClient;

public class RobotTrajectory {

	private RobotClient _myClient;
	private ArrayList<DriveCommand> _driveCommands;
	private TrajectoryMode _trajMode;

	/* Attribute to manage through outter classes */
	private Thread drive_thread;
	private boolean _stop = true;
	private int _currentCommandIndex = -1;

	public RobotTrajectory() {
		_driveCommands = new ArrayList<DriveCommand>();
		_trajMode = TrajectoryMode.NONE;
		_stop = true;
	}

	public RobotTrajectory(RobotClient p_client, ArrayList<DriveCommand> p_driveCommands, TrajectoryMode p_mode) {
		_myClient = p_client;
		_driveCommands = p_driveCommands;
		_trajMode = p_mode;
	}

	public RobotClient get_myClient() {
		return _myClient;
	}

	public void set_myClient(RobotClient _myClient) {
		this._myClient = _myClient;
	}

	public TrajectoryMode get_trajMode() {
		return _trajMode;
	}

	public void set_trajMode(TrajectoryMode _trajMode) {
		this._trajMode = _trajMode;
	}
	
	public int getCommandsListSize() {
		return _driveCommands.size();
	}
	
	public DriveCommand getCommandAt(int index) {
		return _driveCommands.get(index);
	}
	
	
	public int get_currentCommandIndex() {
		return _currentCommandIndex;
	}

	public boolean is_running() {
		return !_stop;
	}

	public int getTotalDuration() {
		int duration = 0;
		for (int i = 0 ; i< _driveCommands.size() ; i++) {
			duration += _driveCommands.get(i).get_commandDuration();
		}
		return duration;
	}
	
	public void startAutoPilot() {

		drive_thread = new Thread() {

			@Override
			public void run() {
				
				switch (_trajMode) {
				case ONCE:
					_myClient.set_robotState(RobotState.READY_TO_START);
					executeCommands();
					//_myClient.get_myUI().unSelectCurrentSegment();
					break;

				case LOOP:
					_myClient.set_robotState(RobotState.READY_TO_START);
					executeCommands();
					break;

				case NONE:
					System.out.println("Le mode de trajectoire n'est pas défini");
					break;

				default:
					break;
				}

			}
		};

		drive_thread.start();

	}

	public void stopAutoPilot() {
		_stop = true;
	}
	
	public void forceStopAutoPilot() {
		if (!_stop)
		drive_thread.interrupt();
	}

	public void addDriveCommand(DriveCommand toAdd) {
		_driveCommands.add(toAdd);
	}
	
	public void editDriveCommand(int index,DriveCommand dc) {
		_driveCommands.get(index).set_robotDirection(dc.get_robotDirection());
		_driveCommands.get(index).set_robotSpeed(dc.get_robotSpeed());
		_driveCommands.get(index).set_commandDuration(dc.get_commandDuration());
	}

	private void executeCommands() {
		_currentCommandIndex = 0;
		_stop = false;
		while(!_stop) {

			String full_command = _driveCommands.get(_currentCommandIndex).toStringCommand();

			//_myClient.send(full_command);

			_myClient.set_robotState(getStateFromDirection(_driveCommands.get(_currentCommandIndex).get_robotDirection()));

			try {
				Thread.sleep(_driveCommands.get(_currentCommandIndex).get_commandDuration() * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			nextCommand();
			System.out.println(_currentCommandIndex);
			if (_currentCommandIndex == _driveCommands.size() - 1 && _trajMode == TrajectoryMode.ONCE)
				_stop = true;
				
			
		}
	}
	
	private void nextCommand() {
		if (_currentCommandIndex + 1 >= _driveCommands.size())
			_currentCommandIndex = 0;
		else
			_currentCommandIndex++;
	}
	

	private RobotState getStateFromDirection(RobotDirection p_direction) {
		RobotState state = RobotState.NONE;

		switch (p_direction) {

		case FORWARD:
			state = RobotState.AUTO_MOVING_FORWARD;
			break;

		case BACKWARD:
			state = RobotState.AUTO_MOVING_BACKWARD;
			break;

		case LEFT:
			state = RobotState.AUTO_TURNING_LEFT;
			break;

		case RIGHT:
			state = RobotState.AUTO_TURNING_RIGHT;
			break;

		case NONE:
			state = RobotState.NONE;
			break;

		default:
			break;

		}

		return state;

	}

}

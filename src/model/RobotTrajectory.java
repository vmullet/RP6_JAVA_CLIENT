package model;

import java.util.ArrayList;

import controller.RobotClient;

public class RobotTrajectory {

	private RobotClient _myClient;
	private ArrayList<DriveCommand> _driveCommands;
	private TrajectoryMode _trajMode;

	private Thread drive_thread;

	public RobotTrajectory() {
		_driveCommands = new ArrayList<DriveCommand>();
		_trajMode = TrajectoryMode.NONE;
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

	public void startAutoPilot() {

		drive_thread = new Thread() {

			@Override
			public void run() {

				switch (_trajMode) {
				case ONCE:
					executeCommands();
					_myClient.get_myUI().unSelectCurrentSegment();
					break;

				case INFINITE:
					_myClient.set_robotState(RobotState.READY_TO_START);
					while (_myClient.get_robotState() != RobotState.NONE) {
						executeCommands();
					}
					_myClient.get_myUI().unSelectCurrentSegment();
					break;

				case NONE:
					break;

				default:
					break;
				}

			}
		};

		drive_thread.start();

	}

	public void stopAutopilot() {
		_myClient.set_robotState(RobotState.NONE);
	}

	public void addDriveCommand(DriveCommand toAdd) {
		_driveCommands.add(toAdd);
	}

	private void executeCommands() {
		for (int cntCommand = 0; cntCommand < _driveCommands.size(); cntCommand++) {

			String full_command = _driveCommands.get(cntCommand).toStringCommand();

			//_myClient.send(full_command);

			_myClient.set_robotState(getStateFromDirection(_driveCommands.get(cntCommand).get_robotDirection()));
			
			_myClient.get_myUI().selectSegment(cntCommand);

			try {
				Thread.sleep(_driveCommands.get(cntCommand).get_commandDuration() * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
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

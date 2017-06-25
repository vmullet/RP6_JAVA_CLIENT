package model;

import java.util.ArrayList;

import controller.RobotClient;
import enums.RobotDirection;
import enums.RobotOrientation;
import enums.RobotState;
import enums.TrajectoryMode;

public class RobotTrajectory {

	private RobotClient _myClient;
	private ArrayList<DriveCommand> _driveCommands;
	private TrajectoryMode _trajMode;

	/* Attribute to manage through outter classes */
	private Thread drive_thread;
	private boolean _stop = true;
	private int _currentCommandIndex = -1;
	private RobotOrientation _currentOrientation;

	public RobotTrajectory() {
		_driveCommands = new ArrayList<DriveCommand>();
		_trajMode = TrajectoryMode.NONE;
		_stop = true;
		_currentCommandIndex = -1;
		_currentOrientation = RobotOrientation.NONE;
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
	

	public RobotOrientation get_currentOrientation() {
		return _currentOrientation;
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
	
	public RobotOrientation estimOrientationAt(int commandIndex) {
		RobotOrientation ro = RobotOrientation.FACE_FRONT;
		
		for (int i = 0 ; i < commandIndex ; i++) {
			RobotDirection rd = _driveCommands.get(i).get_robotDirection();
			boolean found = false;
			
			if (ro == RobotOrientation.FACE_FRONT && !found) {
				switch(rd) {
				case FORWARD:
					ro = RobotOrientation.FACE_FRONT;
					found = true;
					break;
				case BACKWARD:
					ro = RobotOrientation.FACE_FRONT;
					found = true;
					break;
				case LEFT:
					ro = RobotOrientation.SIDE_LEFT;
					found = true;
					break;
				case RIGHT:
					ro = RobotOrientation.SIDE_RIGHT;
					found = true;
					break;
				case NONE:
					break;
				}
			}
			if (ro == RobotOrientation.FACE_REAR && !found) {
				switch(rd) {
				case FORWARD:
					ro = RobotOrientation.FACE_REAR;
					found = true;
					break;
				case BACKWARD:
					ro = RobotOrientation.FACE_REAR;
					found = true;
					break;
				case LEFT:
					ro = RobotOrientation.SIDE_RIGHT;
					found = true;
					break;
				case RIGHT:
					ro = RobotOrientation.SIDE_LEFT;
					found = true;
					break;
				case NONE:
					break;
				}
			}
			if (ro == RobotOrientation.SIDE_LEFT && !found) {
				switch(rd) {
				case FORWARD:
					ro = RobotOrientation.SIDE_LEFT;
					found = true;
					break;
				case BACKWARD:
					ro = RobotOrientation.SIDE_LEFT;
					found = true;
					break;
				case LEFT:
					ro = RobotOrientation.FACE_REAR;
					found = true;
					break;
				case RIGHT:
					ro = RobotOrientation.FACE_FRONT;
					found = true;
					break;
				case NONE:
					break;
				}
			}
			if (ro == RobotOrientation.SIDE_RIGHT && !found) {
				switch(rd) {
				case FORWARD:
					ro = RobotOrientation.SIDE_RIGHT;
					found = true;
					break;
				case BACKWARD:
					ro = RobotOrientation.SIDE_RIGHT;
					found = true;
					break;
				case LEFT:
					ro = RobotOrientation.FACE_FRONT;
					found = true;
					break;
				case RIGHT:
					ro = RobotOrientation.FACE_REAR;
					found = true;
					break;
				case NONE:
					break;
				}
			}
			System.out.println("after " + rd.toString() + "->" + ro.toString() + " ");
		}
		return ro;
	}
	
	public RobotDirection getDirectionBaseOnOrientation(RobotOrientation ro,RobotDirection rd) {
		RobotDirection to_return = rd;
		if (ro == RobotOrientation.SIDE_LEFT) {
			switch(rd) {
			case FORWARD:
				to_return =  RobotDirection.LEFT;
				break;
			case BACKWARD:
				to_return =  RobotDirection.RIGHT;
				break;
			case LEFT:
				to_return =  RobotDirection.BACKWARD;
				break;
			case RIGHT:
				to_return =  RobotDirection.FORWARD;
				break;
			case NONE:
				break;
			}
		}
		if (ro == RobotOrientation.SIDE_RIGHT) {
			switch(rd) {
			case FORWARD:
				to_return =  RobotDirection.RIGHT;
				break;
			case BACKWARD:
				to_return =  RobotDirection.LEFT;
				break;
			case LEFT:
				to_return =  RobotDirection.FORWARD;
				break;
			case RIGHT:
				to_return =  RobotDirection.BACKWARD;
				break;
			case NONE:
				break;
			}
		}
		if (ro == RobotOrientation.FACE_REAR) {
			switch(rd) {
			case FORWARD:
				to_return =  RobotDirection.BACKWARD;
				break;
			case BACKWARD:
				to_return =  RobotDirection.FORWARD;
				break;
			case LEFT:
				to_return =  RobotDirection.RIGHT;
				break;
			case RIGHT:
				to_return =  RobotDirection.LEFT;
				break;
			case NONE:
				break;
			}
		}
		
		return to_return;
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

			if (_driveCommands.get(_currentCommandIndex).get_robotDirection() == RobotDirection.LEFT) {
				_myClient.send("l");
				_myClient.send("100");
				try {
					Thread.sleep(1100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (_driveCommands.get(_currentCommandIndex).get_robotDirection() == RobotDirection.RIGHT) {
				_myClient.send("r");
				_myClient.send("100");
				try {
					Thread.sleep(1350);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			_myClient.send(full_command);

			_myClient.set_robotState(getStateFromDirection(_driveCommands.get(_currentCommandIndex).get_robotDirection()));

			try {
				Thread.sleep(_driveCommands.get(_currentCommandIndex).get_commandDuration() * 1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			nextCommand();
			System.out.println(_currentCommandIndex);
			if (_currentCommandIndex == 0 && _trajMode == TrajectoryMode.ONCE)
				_stop = true;
				
			
		}
		_myClient.send("stp");
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

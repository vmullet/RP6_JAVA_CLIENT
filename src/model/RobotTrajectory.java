package model;

import java.util.ArrayList;
import java.util.HashMap;

import controller.RobotClient;
import enums.RobotDirection;
import enums.RobotOrientation;
import enums.RobotState;
import enums.TrajectoryMode;

/**
 * Class which allows to store a trajectory which can be executed automatically by the robot
 * @author Valentin
 *
 */
public class RobotTrajectory {

	/**
	 * Attribute which stores the client attached ot this trajectory
	 */
	private RobotClient _myClient;
	
	/**
	 * Attribut which stores the drive commands contained inside this trajectory
	 */
	private ArrayList<DriveCommand> _driveCommands;
	
	/**
	 * Attribute which stores the trajectory mode (1 time or infinite)
	 */
	private TrajectoryMode _trajMode;
	
	/**
	 * Attribute which stores the orientation of the robot at every commands
	 */
	private HashMap<Integer, RobotOrientation> _orientationMap;

	/* Attribute to manage through outter classes */
	
	private Thread drive_thread;
	private boolean _stop = true;
	private int _currentCommandIndex = -1;
	private RobotOrientation _currentOrientation;

	/**
	 * Default constructor
	 */
	public RobotTrajectory() {
		_driveCommands = new ArrayList<DriveCommand>();
		_trajMode = TrajectoryMode.NONE;
		_stop = true;
		_currentCommandIndex = -1;
		_currentOrientation = RobotOrientation.NONE;
		_orientationMap = new HashMap<Integer,RobotOrientation>();
	}

	/**
	 * Constructor with main parameters
	 * @param p_client The client attached to this trajectory
	 * @param p_driveCommands The drive commands inside this trajectory
	 * @param p_mode The mode of this trajecory
	 */
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
	

	/**
	 * Method to add a driveCommand to the list
	 * @param toAdd DriveCommand to add
	 */
	public void addDriveCommand(DriveCommand toAdd) {
		_driveCommands.add(toAdd);
		_orientationMap.put(_driveCommands.size() - 1, estimOrientationAt(_driveCommands.size() - 1));
	}
	
	/**
	 * Method to replace a driveCommand at index by another
	 * @param index Index of the actual command to replace
	 * @param dc DriveCommand which will replace the previous one
	 */
	public void editDriveCommand(int index,DriveCommand dc) {
		_driveCommands.get(index).set_robotDirection(dc.get_robotDirection());
		_driveCommands.get(index).set_robotSpeed(dc.get_robotSpeed());
		_driveCommands.get(index).set_commandDuration(dc.get_commandDuration());
		recompileHashMap();
	}
	
	/**
	 * Method to update the orientationHashMap after edit of the driveCommand list
	 */
	private void recompileHashMap() {
		_orientationMap.clear();
		for (int i = 0 ; i < _driveCommands.size() ; i++) {
			RobotOrientation robotOrientation = estimOrientationAt(i);
			_orientationMap.put(i, robotOrientation);
			
		}
	}

	/**
	 * Method to get the number of driveCommands into the list
	 * @return
	 */
	public int getCommandsListSize() {
		return _driveCommands.size();
	}
	
	/**
	 * Method to get the driveCommand at the given index
	 * @param index Index where the driveCommand is located
	 * @return The driveCommand located at this index
	 */
	public DriveCommand getCommandAt(int index) {
		return _driveCommands.get(index);
	}
	
	
	public int get_currentCommandIndex() {
		return _currentCommandIndex;
	}
	

	public RobotOrientation get_currentOrientation() {
		return _currentOrientation;
	}
	
	/**
	 * Method to get the orientation at the given index in the hashMap
	 * @param index Index where the orientation is located
	 * @return The orientation at the given index
	 */
	public RobotOrientation getOrientationAt(int index) {
		return _orientationMap.get(index);
	}

	/**
	 * Method to know if the autopilot is running
	 * @return The state of the autopilot (running or not)
	 */
	public boolean is_running() {
		return !_stop;
	}

	/**
	 * Method to get the total duration of one loop of the robot trajectory
	 * @return The total duration of one loop of this robot trajectory
	 */
	public int getTotalDuration() {
		int duration = 0;
		for (int i = 0 ; i< _driveCommands.size() ; i++) {
			duration += _driveCommands.get(i).get_commandDuration();
		}
		return duration;
	}
	
	public void resetTrajectory() {
		
	}
	
	/**
	 * Method to estimate the orientation of the robot after many commands
	 * @param commandIndex The commandIndex where we want the orientation of the robot
	 * @return The orientation of the robot at this commandIndex
	 */
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
			
		}
		return ro;
	}
	
	/**
	 * Method to get the 2D direction based on the orientation and the 3D direction( for the UI editor)
	 * @param ro Actual robotOrientation
	 * @param rd The direction wanted
	 * @return The 2D direction based on the two parameters
	 */
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
	
	/**
	 * Method to start the autopilot mode and execute the trajectory in the correct trajectory mode
	 */
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

	/**
	 * Method to stop the autopilot
	 */
	public void stopAutoPilot() {
		_stop = true;
	}
	
	/**
	 * Method to force the autopilot to stop by interrupting the thread
	 */
	public void forceStopAutoPilot() {
		if (!_stop)
		drive_thread.interrupt();
	}
	
	/**
	 * Method to turn the robot of 90 degrees on the left
	 */
	private void turnLeft() {
		_myClient.send("l");
		_myClient.send("100");
		try {
			Thread.sleep(1765);
			_myClient.send("stp");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Method to turn the robot of 90 degrees on the right
	 */
	private void tunrRight() {
		_myClient.send("r");
		_myClient.send("100");
		try {
			Thread.sleep(1750);
			_myClient.send("stp");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method to execute the commands of the drive commands list
	 */
	private void executeCommands() {
		
		_currentCommandIndex = 0;
		_stop = false;
		while(!_stop) {

			String full_command = _driveCommands.get(_currentCommandIndex).toStringCommandForAutoPilot();

			if (_driveCommands.get(_currentCommandIndex).get_robotDirection() == RobotDirection.LEFT) {
				turnLeft();
			}
			
			if (_driveCommands.get(_currentCommandIndex).get_robotDirection() == RobotDirection.RIGHT) {
				tunrRight();
			}
			
			_myClient.send(full_command);
			
			try{
					Thread.sleep(_driveCommands.get(_currentCommandIndex).get_commandDuration() * 1000);
			}catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();	
			}
			
			nextCommand();
			if (_currentCommandIndex == 0 && _trajMode == TrajectoryMode.ONCE)
				_stop = true;

		}
		_myClient.send("0");
		_myClient.send("stp");
	}
	
	/**
	 * Method to go to next command and adjusting if needed
	 */
	private void nextCommand() {
		if (_currentCommandIndex + 1 >= _driveCommands.size())
			_currentCommandIndex = 0;
		else
			_currentCommandIndex++;
	}
	
	/**
	 * Method to get the state of the robot based on its direction
	 * @param p_direction The direction of the robot to translate
	 * @return The robot state based on this direction
	 */
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

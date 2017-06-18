package model;

import java.util.ArrayList;

public class RobotTrajectory {

	private RobotClient _myClient;
	private ArrayList<DriveCommand> _driveCommands;
	private TrajectoryMode _trajMode;
	
	private Thread drive_thread;
	
	
	public RobotTrajectory(RobotClient p_client,ArrayList<DriveCommand> p_driveCommands,TrajectoryMode p_mode) {
		_myClient = p_client;
		_driveCommands = p_driveCommands;
		_trajMode = p_mode;
	}
	
	public void startAutoPilot() {
		
		drive_thread = new Thread() {
			
			@Override
			public void run() {
				
				switch(_trajMode) {
				case ONCE:
					executeCommands();
					break;
					
				case INFINITE:
					_myClient.set_robotState(RobotState.READY_TO_START);
					while(_myClient.get_robotState()!=RobotState.NONE) {
						executeCommands();
					}
					
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
	
	private void executeCommands() {
		for (int cntCommand = 0 ; cntCommand < _driveCommands.size() ; cntCommand++) {
			
			String full_command = getCommandFromDirection(_driveCommands.get(cntCommand).get_robotDirection())
					+ "\n"
					+ _driveCommands.get(cntCommand).get_robotSpeed()[0]
					+ "\n";
			
			_myClient.send(full_command);
			
			_myClient.set_robotState(getStateFromDirection(_driveCommands.get(cntCommand).get_robotDirection()));
			
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
		
		switch(p_direction) {
		
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
	
	private String getCommandFromDirection(RobotDirection p_direction) {
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
	
}

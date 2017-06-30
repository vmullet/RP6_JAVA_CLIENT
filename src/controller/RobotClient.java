package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import enums.RobotState;
import model.RobotSensorsData;

/**
 * Class wich allows to connect to the robot by using a socket connection
 * (Get,Send data,connect,disconnect...)
 * @author Valentin
 *
 */
public class RobotClient {

	private String _robotIP;
	private int _robotPort;
	private Socket _robotSocket = null;
	private BufferedReader _input = null;	// Allow to receive the transmitted data
	private PrintWriter _output = null;		// Allow to send commands to the robot

	private RobotState _robotState;			// Actual state of the robot
	
	private RobotSensorsData _data;			// To store the data sent by the robot

	private boolean _is_connecting;
	private boolean _is_connected = false;

	public RobotClient() {
		_robotState = RobotState.NONE;
		_data = new RobotSensorsData();
	}

	public String get_robotIP() {
		return _robotIP;
	}
	
	public int get_robotPort() {
		return _robotPort;
	}

	public RobotState get_robotState() {
		return _robotState;
	}

	public void set_robotState(RobotState p_robotState) {
		this._robotState = p_robotState;
	}

	public boolean is_connected() {
		return _is_connected;
	}

	public boolean is_connecting() {
		return _is_connecting;
	}

	public void set_is_connecting(boolean p_is_connecting) {
		this._is_connecting = p_is_connecting;
	}
	
	public RobotSensorsData get_data() {
		return _data;
	}

	/**
	 * Open a socket connection with the robot to get the transmitted data
	 * @param p_robotIP	Address IP to connect to the robot
	 * @param p_robotPort	Port to open the connection
	 */
	public void openConnection(String p_robotIP,int p_robotPort) {

		_robotIP = p_robotIP;
		_robotPort = p_robotPort;
		_is_connecting = true;
			Thread t1 = new Thread() {
				public void run() {
					try {	
						
							_robotSocket = new Socket(_robotIP, _robotPort);	// Open the socket connection
							_input = new BufferedReader(new InputStreamReader(_robotSocket.getInputStream())); // Get the transmitted data of the robot
							_output = new PrintWriter(_robotSocket.getOutputStream(),true);	// Send command to the robot
							Thread.sleep(1000);
						
							send("");	// wake up the program installed on the rp6
							send("cmd");
						
							_is_connecting = false;
							_is_connected = true;
						
							String message = "";
						
							while ((message = _input.readLine()) != null) {
								_data.parseString(message);
							}	
							
						}catch (Exception e) {
						// TODO Auto-generated catch block
						_is_connecting = false;
						_is_connected = false;
						//e.printStackTrace();
					}

				}

			};
			t1.start();
			
		
			
	}

	/**
	 * Close the connection open by the openConnection method
	 * and reset the BufferedReader and printWriter
	 */
	public void closeConnection() {
			if (_robotSocket != null) {
						send("quit");
						try {
							_input = null;
							_output = null;
							_robotSocket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
						
	}

	/**
	 * Send a command to the robot
	 * @param p_message The command to send to the robot 
	 * (automatically ended by a break line)
	 */
	public void send(String p_message) {
		_output.println(p_message);
		_output.flush();

	}


}
package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import enums.RobotState;
import model.RobotSensorsData;

public class RobotClient {

	private String _robotIP;
	private int _robotPort;
	private Socket _robotSocket = null;
	private BufferedReader _input = null;
	private PrintWriter _output = null;

	private RobotState _robotState;
	
	private RobotSensorsData _data;

	private boolean _is_connecting;
	private boolean _is_connected = false;

	public RobotClient() {
		_robotState = RobotState.NONE;
		_data = new RobotSensorsData();
	}

	public String get_robotIP() {
		return _robotIP;
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

	public void openConnection(String p_robotIP,int p_robotPort) {

		_robotIP = p_robotIP;
		_robotPort = p_robotPort;
		_is_connecting = true;
			Thread t1 = new Thread() {
				public void run() {
					try {	
						_robotSocket = new Socket(_robotIP, _robotPort);
						_input = new BufferedReader(new InputStreamReader(_robotSocket.getInputStream()));
						_output = new PrintWriter(_robotSocket.getOutputStream(),true);
						Thread.sleep(1000);
						
						send("");
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
						e.printStackTrace();
					}

				}

			};
			t1.start();
			
		
			
	}

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

	public void send(String p_message) {
		_output.println(p_message);
		_output.flush();

	}


}
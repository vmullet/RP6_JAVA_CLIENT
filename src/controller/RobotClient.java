package controller;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import model.RobotState;
import view.RobotClientUI_V2;

public class RobotClient {
    
	private String _robotIP;
    private Socket _robotSocket = null;
    private BufferedReader _input = null;
    private PrintWriter _output = null;
    
    private RobotState _robotState;
    
    private RobotClientUI_V2 _myUI;
    
    final int CONN_PORT = 2001;

    public RobotClient() {	
    	_robotState = RobotState.NONE;
    }
    

    public String get_robotIP() {
		return _robotIP;
	}

	public RobotState get_robotState() {
		return _robotState;
	}


	public void set_robotState(RobotState _robotState) {
		this._robotState = _robotState;
	}


	public void openConnection(String p_robotIP){
		
    	_robotIP = p_robotIP;
        try {
            _robotSocket = new Socket(p_robotIP, CONN_PORT);
            _input = new BufferedReader(new InputStreamReader(
                    _robotSocket.getInputStream()));
            _output = new PrintWriter(_robotSocket.getOutputStream());
            
            while(!_robotSocket.isConnected()) {}
            
            Thread t1 = new Thread() {
            	public void run() {
            			try {
            				String message = "";
							while((message = _input.readLine())!=null) {
								System.out.println(_input.readLine());
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
            		
            	}
            	
            };
            t1.start();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + _robotIP);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for " + "the connection to: "
                    + _robotIP);
        }
    }

    public void closeConnection() {
			try {
				if (_robotSocket!=null)
				_robotSocket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }
    
    public void send(String s){
        _output.println(s);
        _output.flush();
       
    }
    
    
    public void buildUI() {
    	_myUI = new RobotClientUI_V2();
    	prepareUI();
    }
    
    private void prepareUI() {
    	
    }

    public void showUI(boolean show) {
    	_myUI.setVisible(show);
    }
    
    
    
      
}
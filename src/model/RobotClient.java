package model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class RobotClient {
    
	private String _robotIP;
    private BufferedReader _buffIn;
    private Socket _robotSocket = null;
    private PrintWriter _output = null;
    private BufferedReader _input = null;
    
    private RobotState _robotState;

    
    public RobotClient() {	
    	_robotState = RobotState.NONE;
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
            _robotSocket = new Socket(p_robotIP, 2001);
            _output = new PrintWriter(_robotSocket.getOutputStream());
            _input = new BufferedReader(new InputStreamReader(
                    _robotSocket.getInputStream()));
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
    
    
    public String readTrajFile(String filePath) {
    	
    	String content = "";
    	TrajectoryMode mode = TrajectoryMode.NONE;
    	int cnt = 0;
    	
    	try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
		{

			String currentLine="";
			while ((currentLine=br.readLine()) != null) {
				
			content += currentLine+"\n" ; //Ajout des retours à la ligne
			if (cnt==0) {
				if (currentLine.equals("--ONCE--"))
					mode = TrajectoryMode.ONCE;
				else if (currentLine.equals("--INFINITE--"))
					mode = TrajectoryMode.INFINITE;
			}
			cnt++;		
			}
					
		}
		catch (IOException e) {
			e.printStackTrace();
		}
    	
    	return content;
    }
      
}
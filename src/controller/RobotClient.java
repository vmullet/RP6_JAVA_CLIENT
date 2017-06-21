package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import model.DriveCommand;
import model.RobotDirection;
import model.RobotState;
import model.RobotTrajectory;
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
	

	public RobotClientUI_V2 get_myUI() {
		return _myUI;
	}


	public void openConnection(String p_robotIP) {

		_robotIP = p_robotIP;
		System.out.println(_robotIP);
		
		
			Thread t1 = new Thread() {
				public void run() {
					try {
						_myUI.startBlinkConnectImg();
						Thread.sleep(2000);
						_robotSocket = new Socket(p_robotIP, CONN_PORT);
						_input = new BufferedReader(new InputStreamReader(_robotSocket.getInputStream()));
						_output = new PrintWriter(_robotSocket.getOutputStream());
						while(!_robotSocket.isConnected()) {}
						
						
						_myUI.setConnectImg(true);
						
						String message = "";
						while ((message = _input.readLine()) != null) {
							System.out.println(_input.readLine());
						}
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						_myUI.stopBlinkConnectImg();
						_myUI.setConnectImg(false);
						JOptionPane.showMessageDialog(_myUI, "Connection Time out");
						e.printStackTrace();
					}

				}

			};
			t1.start();
		
			
	}

	public void closeConnection() {
		try {
			if (_robotSocket != null)
				_robotSocket.close();
			_myUI.setConnectImg(false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send(String s) {
		_output.println(s);
		_output.flush();

	}

	public void buildUI() {
		_myUI = new RobotClientUI_V2();
		
		_myUI.buildGrid(11);
		_myUI.setMouseListeners(RobotDirection.FORWARD, getMouseListenerByDirection(RobotDirection.FORWARD));
		_myUI.setMouseListeners(RobotDirection.BACKWARD, getMouseListenerByDirection(RobotDirection.BACKWARD));
		_myUI.setMouseListeners(RobotDirection.LEFT, getMouseListenerByDirection(RobotDirection.LEFT));
		_myUI.setMouseListeners(RobotDirection.RIGHT, getMouseListenerByDirection(RobotDirection.RIGHT));

		_myUI.addKeyListener(getKeyListener());
		
		_myUI.setBtnConnectListener(getConnectButtonListener());
		
		_myUI.setBrowseFileListener(getBrowseFileListener());
		
		_myUI.writeToLogArea("Démarrage de l'application");
	}

	private MouseListener getMouseListenerByDirection(RobotDirection direction) {
		MouseListener listener = new MouseListener() {

			int speed = 1;
			boolean pressed = false;

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

				Thread t1 = new Thread() {
					@Override
					public void run() {
						pressed = true;
						String command = DriveCommand.getCommandFromDirection(direction) + "\n";
						// send(command);

						while (pressed) {
							try {
								System.out.println(command + speed);
								Thread.sleep(800);
								if (speed < 10)
									speed++;

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				};

				t1.start();

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				// send("stop\n");
				System.out.println("stop");
				pressed = false;
				speed = 1;
				_myUI.requestFocusInWindow();
			}

		};
		return listener;
	}

	private KeyListener getKeyListener() {
		KeyListener listener = new KeyListener() {

			int speed = 1;
			boolean pressed = false;
			boolean launched = false;

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

				if (!launched) {

					launched = true;
					Thread t1 = new Thread() {
						@Override
						public void run() {
							pressed = true;
							String command = "";
							switch (arg0.getKeyCode()) {
							case KeyEvent.VK_UP:
								command = "f\n";
								break;
							case KeyEvent.VK_DOWN:
								command = "b\n";
								break;
							case KeyEvent.VK_LEFT:
								command = "l\n";
								break;
							case KeyEvent.VK_RIGHT:
								command = "r\n";
								break;
							}
							// send(command);

							while (pressed) {
								try {
									Thread.sleep(1000);
									if (speed < 10)
										speed++;

								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}
					};

					t1.start();

				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				// send("stop\n");
				System.out.println("stop");
				pressed = false;
				launched = false;
				speed = 1;
				_myUI.requestFocusInWindow();
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}

		};
		return listener;
	}
	
	private ActionListener getBrowseFileListener() {
		RobotClient rc = this;
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choisissez un fichier .traj");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returned = fc.showOpenDialog(_myUI.get_btnBrowseTrajFile());
				if (returned == JFileChooser.APPROVE_OPTION) {
					String selectedPath = fc.getSelectedFile().getAbsolutePath();
					RobotTrajectory rt = RobotIO.readTrajFile(selectedPath);
					if (rt == null)
						JOptionPane.showMessageDialog(_myUI, "Le fichier .traj n'est pas valide");
					else {
						rt.set_myClient(rc);
						drawTrajOnGrid(rt);
						rt.startAutoPilot();
					}
					
				}
			}
			
		};
		
		return listener;
	}
	
	private ActionListener getConnectButtonListener() {
		ActionListener listener = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				openConnection(_myUI.get_txtAdrIp());
			}
			
		};
		return listener;
	}
	
	public void drawTrajOnGrid(RobotTrajectory rt) {
		int arrival = _myUI.get_gridBaseStartPoint(); // Point de départ
		int[] indexList = null;
		for (int i = 0 ; i < rt.getCommandsListSize() ; i++) {
			
			DriveCommand dc = rt.getCommandAt(i);
			indexList = _myUI.selectGridNeighBoorButtons(2, dc.get_robotDirection(), arrival, dc.get_robotSpeed() + "");
			_myUI.addSegmentToMap(i,indexList); // Ajout des points du segment à la hashmap (l'index du segment est l'index de la commande)
			arrival = indexList[indexList.length - 1]; // Dernier point du segment dessiné (point de départ du prochain)
		}
	}

	public void showUI(boolean show) {
		
		_myUI.setVisible(show);
		_myUI.showBatteryState(100);
		
		

	}

}
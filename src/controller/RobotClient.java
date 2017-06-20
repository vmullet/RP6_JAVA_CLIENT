package controller;

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

import model.DriveCommand;
import model.RobotDirection;
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

	public void openConnection(String p_robotIP) {

		_robotIP = p_robotIP;
		try {
			_robotSocket = new Socket(p_robotIP, CONN_PORT);
			_input = new BufferedReader(new InputStreamReader(_robotSocket.getInputStream()));
			_output = new PrintWriter(_robotSocket.getOutputStream());

			while (!_robotSocket.isConnected()) {
			}

			Thread t1 = new Thread() {
				public void run() {
					try {
						String message = "";
						while ((message = _input.readLine()) != null) {
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
			System.err.println("Couldn't get I/O for " + "the connection to: " + _robotIP);
		}
	}

	public void closeConnection() {
		try {
			if (_robotSocket != null)
				_robotSocket.close();
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

		_myUI.setMouseListeners(RobotDirection.FORWARD, getMouseListenerByDirection(RobotDirection.FORWARD));
		_myUI.setMouseListeners(RobotDirection.BACKWARD, getMouseListenerByDirection(RobotDirection.BACKWARD));
		_myUI.setMouseListeners(RobotDirection.LEFT, getMouseListenerByDirection(RobotDirection.LEFT));
		_myUI.setMouseListeners(RobotDirection.RIGHT, getMouseListenerByDirection(RobotDirection.RIGHT));

		_myUI.addKeyListener(getKeyListener());

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
									System.out.println(command + speed);
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

	public void showUI(boolean show) {
		_myUI.setVisible(show);
		_myUI.showBatteryState(20);

	}

}
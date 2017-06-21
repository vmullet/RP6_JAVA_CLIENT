package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import model.RobotDirection;

import javax.swing.JTabbedPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class RobotClientUI_V2 extends JFrame {

	private JPanel contentPane;

	private JButton _btnStartAutoPilot;
	private JButton _btnStopAutoPilot;

	private JButton _btnConnect;
	private JButton _btnDisconnect;

	private JButton _btnMoveUpward;
	private JButton _btnMoveBackward;
	private JButton _btnTurnLeft;
	private JButton _btnTurnRight;

	private JButton _btnStatusUpLeft;
	private JButton _btnStatusUpRight;
	private JButton _btnStatusMiddle;
	private JButton _btnStatusBackLeft;
	private JButton _btnStatusBackRight;

	private JButton _btnStatusAutoUpLeft;
	private JButton _btnStatusAutoUpRight;
	private JButton _btnStatusAutoMiddle;
	private JButton _btnStatusAutoBackLeft;
	private JButton _btnStatusAutoBackRight;

	private JButton _btnBrowseTrajFile;

	private JLabel _imgConnectionState;
	private JLabel _imgBatteryState;
	private JLabel _lblBatteryPercent;
	private JLabel _lblBatteryWarning;

	private JTextField _txtAdrIp;
	private JTextField _txtFilePath;
	private JTextArea _txtLogArea;

	private JPanel _gridPanel;
	private JButton[] _gridTrajPreview;
	private int _gridSize;
	private int _baseStartPoint;
	private HashMap<Integer,int[]> _segmentMap;
	private int _selectedSegmentIndex;
	
	
	private Color _defaultButtonColor;

	private boolean _stopConnectBlink = true;
	private boolean _stopBatteryBlink = true;

	static String UP_ARROW_PATH = "resources\\up-arrow.png";
	static String DOWN_ARROW_PATH = "resources\\down-arrow.png";
	static String LEFT_ARROW_PATH = "resources\\left-arrow.png";
	static String RIGHT_ARROW_PATH = "resources\\right-arrow.png";

	static String BATTERY_100_PATH = "resources\\battery-100.png";
	static String BATTERY_75_PATH = "resources\\battery-75.png";
	static String BATTERY_50_PATH = "resources\\battery-50.png";
	static String BATTERY_25_PATH = "resources\\battery-25.png";
	static String BATTERY_00_PATH = "resources\\battery-00.png";

	static String WARNING_PATH = "resources\\warning.png";

	static String TRAFFIC_LIGHT_BLACK = "resources\\traffic-light-black.png";
	static String TRAFFIC_LIGHT_RED = "resources\\traffic-light-red.png";
	static String TRAFFIC_LIGHT_YELLOW = "resources\\traffic-light-yellow.png";
	static String TRAFFIC_LIGHT_GREEN = "resources\\traffic-light-green.png";

	/**
	 * Create the frame.
	 */
	public RobotClientUI_V2() {
		setTitle("RP6 Robot Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1090, 914);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(13, 145, 999, 503);
		contentPane.add(tabbedPane);

		JPanel tabManualControl = new JPanel();
		tabbedPane.addTab("Contrôle Manuel", null, tabManualControl, null);
		tabbedPane.setEnabledAt(0, false);
		tabManualControl.setLayout(null);

		JLabel lbl_robot_status = new JLabel("Statut du robot :");
		lbl_robot_status.setBounds(50, 13, 109, 16);
		tabManualControl.add(lbl_robot_status);

		JLabel lbl_robot_control = new JLabel("Contr\u00F4le du robot :");
		lbl_robot_control.setBounds(528, 13, 116, 16);
		tabManualControl.add(lbl_robot_control);

		JPanel control_robot_panel = new JPanel();
		control_robot_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		control_robot_panel.setLayout(null);
		control_robot_panel.setBounds(570, 53, 394, 406);
		tabManualControl.add(control_robot_panel);

		_btnMoveUpward = new JButton("");
		_btnMoveUpward.setBounds(148, 13, 100, 100);
		control_robot_panel.add(_btnMoveUpward);

		_btnMoveBackward = new JButton("");
		_btnMoveBackward.setBounds(148, 293, 100, 100);
		control_robot_panel.add(_btnMoveBackward);

		_btnTurnLeft = new JButton("");
		_btnTurnLeft.setBounds(12, 144, 100, 100);
		control_robot_panel.add(_btnTurnLeft);

		_btnTurnRight = new JButton("");
		_btnTurnRight.setBounds(282, 144, 100, 100);
		control_robot_panel.add(_btnTurnRight);

		JPanel status_robot_panel = new JPanel();
		status_robot_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		status_robot_panel.setLayout(null);
		status_robot_panel.setBounds(53, 53, 376, 318);
		tabManualControl.add(status_robot_panel);

		_btnStatusUpLeft = new JButton("");
		_btnStatusUpLeft.setBackground(UIManager.getColor("Button.background"));
		_btnStatusUpLeft.setBounds(12, 13, 56, 85);
		status_robot_panel.add(_btnStatusUpLeft);

		_btnStatusBackLeft = new JButton("");
		_btnStatusBackLeft.setBounds(12, 220, 56, 85);
		status_robot_panel.add(_btnStatusBackLeft);

		_btnStatusMiddle = new JButton("");
		_btnStatusMiddle.setBounds(157, 122, 56, 85);
		status_robot_panel.add(_btnStatusMiddle);

		_btnStatusUpRight = new JButton("");
		_btnStatusUpRight.setBounds(308, 13, 56, 85);
		status_robot_panel.add(_btnStatusUpRight);

		_btnStatusBackRight = new JButton("");
		_btnStatusBackRight.setBounds(308, 220, 56, 85);
		status_robot_panel.add(_btnStatusBackRight);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(478, 41, 13, 419);
		tabManualControl.add(separator);

		JPanel tabAutoControl = new JPanel();
		tabbedPane.addTab("Contr\u00F4le Automatique", null, tabAutoControl, null);
		tabAutoControl.setLayout(null);

		JPanel panel_choose_file = new JPanel();
		panel_choose_file.setBounds(0, 1, 994, 47);
		tabAutoControl.add(panel_choose_file);
		panel_choose_file.setLayout(null);

		JLabel lbl_select_fichier = new JLabel("S\u00E9lectionner un fichier...");
		lbl_select_fichier.setBounds(12, 0, 144, 47);
		panel_choose_file.add(lbl_select_fichier);

		_txtFilePath = new JTextField();
		_txtFilePath.setEditable(false);
		_txtFilePath.setBounds(163, 6, 232, 34);
		panel_choose_file.add(_txtFilePath);

		_btnBrowseTrajFile = new JButton("...");
		_btnBrowseTrajFile.setBounds(408, 6, 80, 34);
		panel_choose_file.add(_btnBrowseTrajFile);

		_gridPanel = new JPanel();
		_gridPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		_gridPanel.setBounds(10, 79, 484, 381);
		tabAutoControl.add(_gridPanel);
		

		JLabel lbl_preview_traj = new JLabel("Aper\u00E7u de la trajectoire :");
		lbl_preview_traj.setBounds(10, 61, 153, 16);
		tabAutoControl.add(lbl_preview_traj);

		_btnStartAutoPilot = new JButton("START AUTO PILOT");
		_btnStartAutoPilot.setBounds(823, 92, 159, 164);
		tabAutoControl.add(_btnStartAutoPilot);

		_btnStopAutoPilot = new JButton("STOP AUTO PILOT");
		_btnStopAutoPilot.setBounds(823, 282, 159, 164);
		tabAutoControl.add(_btnStopAutoPilot);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setLayout(null);
		panel.setBounds(507, 92, 304, 354);
		tabAutoControl.add(panel);

		_btnStatusAutoUpLeft = new JButton("");
		_btnStatusAutoUpLeft.setBackground(UIManager.getColor("Button.background"));
		_btnStatusAutoUpLeft.setBounds(52, 36, 56, 85);
		panel.add(_btnStatusAutoUpLeft);

		_btnStatusAutoBackLeft = new JButton("");
		_btnStatusAutoBackLeft.setBounds(52, 234, 56, 85);
		panel.add(_btnStatusAutoBackLeft);

		_btnStatusAutoMiddle = new JButton("");
		_btnStatusAutoMiddle.setBounds(107, 136, 85, 85);
		panel.add(_btnStatusAutoMiddle);

		_btnStatusAutoUpRight = new JButton("");
		_btnStatusAutoUpRight.setBounds(192, 36, 56, 85);
		panel.add(_btnStatusAutoUpRight);

		_btnStatusAutoBackRight = new JButton("");
		_btnStatusAutoBackRight.setBounds(192, 234, 56, 85);
		panel.add(_btnStatusAutoBackRight);

		
		_defaultButtonColor = _btnStatusAutoBackLeft.getBackground();
		

		tabbedPane.setEnabledAt(1, true);

		JPanel tabMonitoring = new JPanel();
		tabbedPane.addTab("Monitoring", null, tabMonitoring, null);
		tabMonitoring.setLayout(null);

		MaskFormatter mf = null;
		try {
			mf = new MaskFormatter("###.###.###.###");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		_txtAdrIp = new JTextField();
		_txtAdrIp.setFont(new Font("Tahoma", Font.BOLD, 16));
		_txtAdrIp.setColumns(10);
		_txtAdrIp.setBounds(92, 20, 139, 41);
		contentPane.add(_txtAdrIp);

		_btnConnect = new JButton("Connexion");
		_btnConnect.setBounds(248, 21, 160, 41);
		contentPane.add(_btnConnect);

		_btnDisconnect = new JButton("D\u00E9connexion");
		_btnDisconnect.setBounds(435, 20, 150, 41);
		contentPane.add(_btnDisconnect);

		JLabel lbl_etat_connexion = new JLabel("Etat de la connexion :");
		lbl_etat_connexion.setBounds(781, 28, 129, 27);
		contentPane.add(lbl_etat_connexion);

		_imgConnectionState = new JLabel("");
		_imgConnectionState.setBounds(904, 13, 72, 96);
		contentPane.add(_imgConnectionState);

		JLabel lbl_log = new JLabel("Log :");
		lbl_log.setBounds(23, 661, 56, 27);
		contentPane.add(lbl_log);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(27, 689, 985, 10);
		contentPane.add(separator_1);

		_txtLogArea = new JTextArea();
		_txtLogArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		_txtLogArea.setBackground(Color.WHITE);
		_txtLogArea.setBounds(24, 712, 988, 142);
		contentPane.add(_txtLogArea);

		JLabel label = new JLabel("Adresse IP :");
		label.setBounds(13, 20, 72, 35);
		contentPane.add(label);

		JLabel lbl_batterie = new JLabel("Etat de la batterie :");
		lbl_batterie.setBounds(13, 99, 129, 16);
		contentPane.add(lbl_batterie);

		_imgBatteryState = new JLabel("");
		_imgBatteryState.setBounds(136, 85, 108, 47);
		contentPane.add(_imgBatteryState);

		_lblBatteryPercent = new JLabel("");
		_lblBatteryPercent.setBounds(247, 75, 56, 57);
		contentPane.add(_lblBatteryPercent);

		_lblBatteryWarning = new JLabel("");
		_lblBatteryWarning.setBounds(305, 75, 71, 57);
		contentPane.add(_lblBatteryWarning);
		_btnDisconnect.setVisible(false);

		_segmentMap = new HashMap<Integer,int[]>();
		_selectedSegmentIndex = -1;
		
		setIcons();
		setFocusable(true);
		requestFocusInWindow();
	}

	public void setMouseListeners(RobotDirection direction, MouseListener listener) {
		switch (direction) {
		case FORWARD:
			_btnMoveUpward.addMouseListener(listener);
			break;
		case BACKWARD:
			_btnMoveBackward.addMouseListener(listener);
			break;
		case LEFT:
			_btnTurnLeft.addMouseListener(listener);
			break;
		case RIGHT:
			_btnTurnRight.addMouseListener(listener);
			break;
		case NONE:
			break;
		default:
			break;
		}
	}
	
	public void setBrowseFileListener(ActionListener listener) {
		_btnBrowseTrajFile.addActionListener(listener);
	}
	
	public void setBtnConnectListener(ActionListener listener) {
		_btnConnect.addActionListener(listener);
	}

	private void setIcons() {
		_btnMoveUpward.setIcon(new ImageIcon(UP_ARROW_PATH));
		_btnMoveBackward.setIcon(new ImageIcon(DOWN_ARROW_PATH));
		_btnTurnLeft.setIcon(new ImageIcon(LEFT_ARROW_PATH));
		_btnTurnRight.setIcon(new ImageIcon(RIGHT_ARROW_PATH));

		_imgConnectionState.setIcon(new ImageIcon(TRAFFIC_LIGHT_RED));
	}
	

	public JButton get_btnBrowseTrajFile() {
		return _btnBrowseTrajFile;
	}

	
	public int get_gridBaseStartPoint() {
		return _baseStartPoint;
	}
	
	
	
	public String get_txtAdrIp() {
		return _txtAdrIp.getText();
	}

	private void setStartPointButton(int size) {
		_baseStartPoint = (size/2 + 1) * (size - 1);
		_gridTrajPreview[_baseStartPoint].setBackground(new Color(255,0,0));
		setGridButtonText(_baseStartPoint,"R");
	}
	
	public void buildGrid(int size) {
		_gridSize = size;
		_gridTrajPreview = new JButton[size * size];
		_gridPanel.setLayout(new GridLayout(size, size, 0, 0));
		for (int i = 0 ; i < size * size ; i++) {
			
			JButton _gridBtn = new JButton("");
			_gridPanel.add(_gridBtn);
			_gridTrajPreview[i] = _gridBtn;
			
		}
		setStartPointButton(size);
	}

	public void startBlinkConnectImg() {
		_stopConnectBlink = false;
		ImageIcon ic1 = new ImageIcon(TRAFFIC_LIGHT_YELLOW);
		ImageIcon ic2 = new ImageIcon(TRAFFIC_LIGHT_BLACK);
		Thread t1 = new Thread() {
			@Override
			public void run() {
				while (!_stopConnectBlink) {
					_imgConnectionState.setIcon(ic1);
					try {
						Thread.sleep(500);
						_imgConnectionState.setIcon(ic2);
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t1.start();
	}

	public void stopBlinkConnectImg() {
		_stopConnectBlink = true;
	}
	
	public void setConnectImg(boolean connected) {
		ImageIcon ic1 = new ImageIcon(TRAFFIC_LIGHT_RED);
		ImageIcon ic2 = new ImageIcon(TRAFFIC_LIGHT_GREEN);
		if (connected)
			_imgConnectionState.setIcon(ic2);
		else
			_imgConnectionState.setIcon(ic1);
	}

	public void startBlinkBattery() {
		_stopBatteryBlink = false;
		ImageIcon ic1 = new ImageIcon(BATTERY_25_PATH);
		ImageIcon ic2 = new ImageIcon(BATTERY_00_PATH);
		Thread t1 = new Thread() {
			@Override
			public void run() {
				while (!_stopBatteryBlink) {
					_imgBatteryState.setIcon(ic1);
					try {
						Thread.sleep(500);
						_imgBatteryState.setIcon(ic2);
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
		t1.start();

	}

	public void stopBlinkBattery() {
		_stopBatteryBlink = true;
	}

	public void showBatteryState(int batteryValue) {
		ImageIcon ic1 = null;
		_lblBatteryWarning.setIcon(null);
		if (batteryValue <= 100 && batteryValue > 75)
			ic1 = new ImageIcon(BATTERY_100_PATH);
		else if (batteryValue <= 75 && batteryValue > 50)
			ic1 = new ImageIcon(BATTERY_75_PATH);
		else if (batteryValue <= 50 && batteryValue > 25)
			ic1 = new ImageIcon(BATTERY_50_PATH);
		else if (batteryValue <= 25 && batteryValue > 0) {
			ic1 = new ImageIcon(BATTERY_25_PATH);
			_lblBatteryWarning.setIcon(new ImageIcon(WARNING_PATH));
			if (_stopBatteryBlink)
				startBlinkBattery();
		}

		_lblBatteryPercent.setText("(" + batteryValue + "%)");
		_imgBatteryState.setIcon(ic1);
	}

	public void writeToLogArea(String message) {
		_txtLogArea.setText(_txtLogArea.getText() + "[" + new Date() + "] : " + message + "\n");
	}
	
	public void setGridButtonText(int index,String text) {
		_gridTrajPreview[index].setForeground(Color.WHITE);
		_gridTrajPreview[index].setFont(new Font("Arial", Font.PLAIN, 12));
		_gridTrajPreview[index].setText(text);
	}
	
	private void selectButton(int index,Color color) {
		_gridTrajPreview[index].setBackground(color);
	}
	
	private void unSelectTrajButton(int index,Color color) {
		if (index != _baseStartPoint)
			_gridTrajPreview[index].setBackground(color);
		else
			_gridTrajPreview[index].setBackground(Color.RED);
	}
	
	private void unSelectButton(int index) {
		_gridTrajPreview[index].setBackground(_defaultButtonColor);
	}
	
	public int[] selectGridNeighBoorButtons(int nbButtons,RobotDirection direction,int actual,String text) {
		int lastIndex = -1;
		int[] indexList = new int[nbButtons];
		
		switch(direction) {
			case FORWARD:
				for (int i = 0 ; i < nbButtons; i++) {
					lastIndex = actual - _gridSize * (i+1);
					if (lastIndex != _baseStartPoint) {
						selectButton(lastIndex,Color.BLACK);
						setGridButtonText(lastIndex,text);
					}
					indexList[i] = lastIndex;
					
				}
				break;
			
			case BACKWARD:
				for (int i = 0 ; i < nbButtons; i++) {
					lastIndex = actual + _gridSize * (i+1);
					if (lastIndex != _baseStartPoint) {
						selectButton(lastIndex,Color.BLACK);
						setGridButtonText(lastIndex,text);
					}
					indexList[i] = lastIndex;
					
				}
				break;
				
			case LEFT:
				for (int i = 0 ; i < nbButtons; i++) {
					lastIndex = actual - (i+1);
					if (lastIndex != _baseStartPoint) {
						selectButton(lastIndex,Color.BLACK);
						setGridButtonText(lastIndex,text);
					}
					indexList[i] = lastIndex;
					
				}
				break;
				
			case RIGHT:
				for (int i = 0 ; i < nbButtons; i++) {
					lastIndex = actual + (i+1);
					if (lastIndex != _baseStartPoint) {
						selectButton(lastIndex,Color.BLACK);
						setGridButtonText(lastIndex,text);
					}
					indexList[i] = lastIndex;
					
				}
				break;
				
			case NONE:
				break;
				
			default:
				break;
			
		}
		return indexList;
	}
	
	public void addSegmentToMap(int commandIndex,int[] indexList) {
		_segmentMap.put(commandIndex,indexList);
	}
	
	public void selectSegment(int index) {
		if (_selectedSegmentIndex != -1)
			unSelectSegment(_selectedSegmentIndex);
		
		_selectedSegmentIndex = index;
		int[] btnIndexes = _segmentMap.get(index);
		for (int i = 0 ; i < btnIndexes.length ; i++) {
			selectButton(btnIndexes[i],Color.GREEN);
		}
		
	}
	
	public void unSelectSegment(int index) {
		
		int[] btnIndexes = _segmentMap.get(index);
		for (int i = 0 ; i < btnIndexes.length ; i++) {
			unSelectTrajButton(btnIndexes[i],Color.BLACK);
		}
		
	}
	
	public void unSelectCurrentSegment() {
		
		int[] btnIndexes = _segmentMap.get(_selectedSegmentIndex);
		for (int i = 0 ; i < btnIndexes.length ; i++) {
			unSelectTrajButton(btnIndexes[i],Color.BLACK);
		}
		
	}
	

	public void resetGrid() {
		for (int i = 0 ; i < _gridTrajPreview.length ; i++) {
			unSelectButton(i);
			_gridTrajPreview[i].setText("");
		}
	}
	
	
	
}

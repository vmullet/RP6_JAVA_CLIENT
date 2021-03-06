package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.BlinkerUI;
import model.DriveCommand;
import model.RobotTrajectory;
import controller.RobotClient;
import controller.RobotIO;
import enums.RobotDirection;
import enums.RobotOrientation;
import enums.TrajectoryMode;

import javax.swing.JTabbedPane;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.Date;
import java.util.HashMap;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.URL;

import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JList;

@SuppressWarnings("serial")
/**
 * Class which contains the UI for the graphical mode of the client
 * @author Valentin
 *
 */
public class RobotClientUI extends JFrame {

	private JPanel _contentPane;
	private JTabbedPane _tabPane;

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
	private JLabel _imgRP6;
	private JLabel _lblBatteryPercent;
	private JLabel _lblBatteryWarning;

	private JTextField _txtAdrIp;
	private JTextField _txtFilePath;
	private JTextArea _txtLogArea;
	


	static URL IMG_UP_ARROW_PATH;
	static URL IMG_DOWN_ARROW_PATH;
	static URL IMG_LEFT_ARROW_PATH;
	static URL IMG_RIGHT_ARROW_PATH;

	static URL IMG_BATTERY_100_PATH;
	static URL IMG_BATTERY_75_PATH;
	static URL IMG_BATTERY_50_PATH;
	static URL IMG_BATTERY_25_PATH;
	static URL IMG_BATTERY_00_PATH;

	static URL IMG_WARNING_PATH;

	static URL IMG_TRAFFIC_LIGHT_BLACK;
	static URL IMG_TRAFFIC_LIGHT_RED;
	static URL IMG_TRAFFIC_LIGHT_YELLOW;
	static URL IMG_TRAFFIC_LIGHT_GREEN;

	static URL IMG_RP6_PATH;

	private JTextField _txt_distance_left;
	private JTextField _txt_speed_left;
	private JTextField _txt_desired_speed_left;
	private JTextField _txt_power_left;
	private JTextField _txt_curr_motor_left;
	private JTextField _txt_light_sensors_left;
	private JTextField _txt_distance_right;
	private JTextField _txt_speed_right;
	private JTextField _txt_desired_speed_right;
	private JTextField _txt_power_right;
	private JTextField _txt_curr_motor_right;
	private JTextField _txt_light_sensors_right;

	private JLabel lbl_vitesse_robot;
	private JTextField _txt_manual_speed;
	private JLabel lbl_distance_robot_meter;
	private JTextField _txt_manual_distance;
	private JTextField _txt_speed_command;
	
	private RobotClient _myClient;

	/* - - - Trajectory and grid variables - - - */
	private JPanel _previewGridPanel;
	private JButton[] _previewBtns;
	private int _previewGridSize;
	private int _previewBaseStartPoint;
	private HashMap<Integer, int[]> _previewSegmentMap;
	private int _previewSelectedSegmentIndex;
	private RobotTrajectory _previewLoadedTrajectory;
	private Color _previewDefaultButtonColor;
	/* - - - END - - - */

	/* - - - Editor Variables - - - */
	private JPanel _editorGridTraj;
	private JButton[] _editorBtns;
	private int _editorGridSize;
	private int _editorBaseStartPoint;
	private int _editorLastPoint = -1;
	private HashMap<Integer, int[]> _editorSegmentMap;
	private int _editorSelectedSegmentIndex;
	/* - - - Editor UI Variables - - - */
	private JComboBox<RobotDirection> _cb_direction_command;
	private JComboBox<TrajectoryMode> _cb_trajectory_mode;
	private JTextField _txt_duration_command;
	private JList<DriveCommand> _list_command;
	private DefaultListModel<DriveCommand> _list_command_model;
	private JLabel _lblCrerUneCommande;
	private RobotTrajectory _editorTrajectory;
	private JTextField _txtRobotPort;
	/* - - -  END - - - */
	
	
	
	/**
	 * Create the frame.
	 */
	public RobotClientUI() {

		setTitle("RP6 Robot Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1090, 914);
		_contentPane = new JPanel();
		_contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(_contentPane);
		_contentPane.setLayout(null);

		_tabPane = new JTabbedPane(JTabbedPane.TOP);
		_tabPane.setBounds(13, 145, 999, 503);
		_contentPane.add(_tabPane);

		JPanel tabManualControl = new JPanel();
		_tabPane.addTab("Contr?le Manuel", null, tabManualControl, null);
		_tabPane.setEnabledAt(0, false);
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

		lbl_vitesse_robot = new JLabel("Vitesse du robot");
		lbl_vitesse_robot.setBounds(82, 384, 116, 16);
		tabManualControl.add(lbl_vitesse_robot);

		_txt_manual_speed = new JTextField();
		_txt_manual_speed.setForeground(Color.BLUE);
		_txt_manual_speed.setEditable(false);
		_txt_manual_speed.setBounds(217, 384, 116, 22);
		tabManualControl.add(_txt_manual_speed);
		_txt_manual_speed.setColumns(10);

		lbl_distance_robot_meter = new JLabel("Distance parcourue (m)");
		lbl_distance_robot_meter.setBounds(54, 440, 144, 16);
		tabManualControl.add(lbl_distance_robot_meter);

		_txt_manual_distance = new JTextField();
		_txt_manual_distance.setForeground(Color.BLUE);
		_txt_manual_distance.setEditable(false);
		_txt_manual_distance.setBounds(217, 437, 116, 22);
		tabManualControl.add(_txt_manual_distance);
		_txt_manual_distance.setColumns(10);

		JPanel tabAutoControl = new JPanel();
		_tabPane.addTab("Contr\u00F4le Automatique", null, tabAutoControl, null);
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

		_previewGridPanel = new JPanel();
		_previewGridPanel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		_previewGridPanel.setBounds(10, 79, 528, 381);
		tabAutoControl.add(_previewGridPanel);

		JLabel lbl_preview_traj = new JLabel("Aper\u00E7u de la trajectoire :");
		lbl_preview_traj.setBounds(10, 61, 153, 16);
		tabAutoControl.add(lbl_preview_traj);

		_btnStartAutoPilot = new JButton("START AUTO PILOT");
		_btnStartAutoPilot.setEnabled(false);
		_btnStartAutoPilot.setBounds(835, 92, 147, 164);
		tabAutoControl.add(_btnStartAutoPilot);

		_btnStopAutoPilot = new JButton("STOP AUTO PILOT");
		_btnStopAutoPilot.setEnabled(false);
		_btnStopAutoPilot.setBounds(835, 282, 147, 164);
		tabAutoControl.add(_btnStopAutoPilot);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setLayout(null);
		panel.setBounds(550, 92, 273, 354);
		tabAutoControl.add(panel);

		_btnStatusAutoUpLeft = new JButton("");
		_btnStatusAutoUpLeft.setBackground(UIManager.getColor("Button.background"));
		_btnStatusAutoUpLeft.setBounds(38, 36, 56, 85);
		panel.add(_btnStatusAutoUpLeft);

		_btnStatusAutoBackLeft = new JButton("");
		_btnStatusAutoBackLeft.setBounds(38, 234, 56, 85);
		panel.add(_btnStatusAutoBackLeft);

		_btnStatusAutoMiddle = new JButton("");
		_btnStatusAutoMiddle.setBounds(93, 136, 85, 85);
		panel.add(_btnStatusAutoMiddle);

		_btnStatusAutoUpRight = new JButton("");
		_btnStatusAutoUpRight.setBounds(178, 36, 56, 85);
		panel.add(_btnStatusAutoUpRight);

		_btnStatusAutoBackRight = new JButton("");
		_btnStatusAutoBackRight.setBounds(178, 234, 56, 85);
		panel.add(_btnStatusAutoBackRight);

		_previewDefaultButtonColor = _btnStatusAutoBackLeft.getBackground();

		JPanel tabMonitoring = new JPanel();
		_tabPane.addTab("Monitoring", null, tabMonitoring, null);
		tabMonitoring.setLayout(null);

		JLabel lbl_distance_left = new JLabel("Distance Left");
		lbl_distance_left.setBounds(18, 53, 83, 16);
		tabMonitoring.add(lbl_distance_left);

		_txt_distance_left = new JTextField();
		_txt_distance_left.setForeground(Color.BLUE);
		_txt_distance_left.setEditable(false);
		_txt_distance_left.setBounds(113, 50, 116, 22);
		tabMonitoring.add(_txt_distance_left);
		_txt_distance_left.setColumns(10);

		_imgRP6 = new JLabel("");
		_imgRP6.setBounds(510, 13, 472, 434);
		tabMonitoring.add(_imgRP6);

		JLabel lbl_speed_left = new JLabel("Speed Left");
		lbl_speed_left.setBounds(34, 123, 67, 16);
		tabMonitoring.add(lbl_speed_left);

		_txt_speed_left = new JTextField();
		_txt_speed_left.setForeground(Color.BLUE);
		_txt_speed_left.setEditable(false);
		_txt_speed_left.setColumns(10);
		_txt_speed_left.setBounds(113, 120, 116, 22);
		tabMonitoring.add(_txt_speed_left);

		JLabel lbl_desired_speed_left = new JLabel("Des. Speed Left");
		lbl_desired_speed_left.setBounds(12, 185, 90, 33);
		tabMonitoring.add(lbl_desired_speed_left);

		_txt_desired_speed_left = new JTextField();
		_txt_desired_speed_left.setForeground(Color.BLUE);
		_txt_desired_speed_left.setEditable(false);
		_txt_desired_speed_left.setColumns(10);
		_txt_desired_speed_left.setBounds(113, 190, 116, 22);
		tabMonitoring.add(_txt_desired_speed_left);

		JLabel lbl_power_left = new JLabel("Power Left");
		lbl_power_left.setBounds(28, 263, 73, 16);
		tabMonitoring.add(lbl_power_left);

		_txt_power_left = new JTextField();
		_txt_power_left.setForeground(Color.BLUE);
		_txt_power_left.setEditable(false);
		_txt_power_left.setColumns(10);
		_txt_power_left.setBounds(113, 260, 116, 22);
		tabMonitoring.add(_txt_power_left);

		JLabel lbl_motor_left = new JLabel("Cur.Motor Left");
		lbl_motor_left.setBounds(12, 333, 89, 16);
		tabMonitoring.add(lbl_motor_left);

		_txt_curr_motor_left = new JTextField();
		_txt_curr_motor_left.setForeground(Color.BLUE);
		_txt_curr_motor_left.setEditable(false);
		_txt_curr_motor_left.setColumns(10);
		_txt_curr_motor_left.setBounds(113, 330, 116, 22);
		tabMonitoring.add(_txt_curr_motor_left);

		JLabel lbl_light_sensors_left = new JLabel("LS Left");
		lbl_light_sensors_left.setBounds(33, 406, 56, 16);
		tabMonitoring.add(lbl_light_sensors_left);

		_txt_light_sensors_left = new JTextField();
		_txt_light_sensors_left.setForeground(Color.BLUE);
		_txt_light_sensors_left.setEditable(false);
		_txt_light_sensors_left.setColumns(10);
		_txt_light_sensors_left.setBounds(113, 403, 116, 22);
		tabMonitoring.add(_txt_light_sensors_left);

		JLabel lbl_distance_right = new JLabel("Distance Right");
		lbl_distance_right.setBounds(268, 53, 83, 16);
		tabMonitoring.add(lbl_distance_right);

		_txt_distance_right = new JTextField();
		_txt_distance_right.setForeground(Color.BLUE);
		_txt_distance_right.setEditable(false);
		_txt_distance_right.setColumns(10);
		_txt_distance_right.setBounds(363, 50, 116, 22);
		tabMonitoring.add(_txt_distance_right);

		JLabel lblSpeedRight = new JLabel("Speed Right");
		lblSpeedRight.setBounds(268, 123, 79, 16);
		tabMonitoring.add(lblSpeedRight);

		_txt_speed_right = new JTextField();
		_txt_speed_right.setForeground(Color.BLUE);
		_txt_speed_right.setEditable(false);
		_txt_speed_right.setColumns(10);
		_txt_speed_right.setBounds(363, 120, 116, 22);
		tabMonitoring.add(_txt_speed_right);

		JLabel lbl_desired_speed_right = new JLabel("Des. Speed Right");
		lbl_desired_speed_right.setBounds(253, 190, 98, 22);
		tabMonitoring.add(lbl_desired_speed_right);

		_txt_desired_speed_right = new JTextField();
		_txt_desired_speed_right.setForeground(Color.BLUE);
		_txt_desired_speed_right.setEditable(false);
		_txt_desired_speed_right.setColumns(10);
		_txt_desired_speed_right.setBounds(363, 190, 116, 22);
		tabMonitoring.add(_txt_desired_speed_right);

		JLabel lbl_power_right = new JLabel("Power Right");
		lbl_power_right.setBounds(275, 263, 73, 16);
		tabMonitoring.add(lbl_power_right);

		_txt_power_right = new JTextField();
		_txt_power_right.setForeground(Color.BLUE);
		_txt_power_right.setEditable(false);
		_txt_power_right.setColumns(10);
		_txt_power_right.setBounds(363, 260, 116, 22);
		tabMonitoring.add(_txt_power_right);

		JLabel lbl_motor_right = new JLabel("Curr.Motor Right");
		lbl_motor_right.setBounds(253, 333, 98, 16);
		tabMonitoring.add(lbl_motor_right);

		_txt_curr_motor_right = new JTextField();
		_txt_curr_motor_right.setForeground(Color.BLUE);
		_txt_curr_motor_right.setEditable(false);
		_txt_curr_motor_right.setColumns(10);
		_txt_curr_motor_right.setBounds(363, 330, 116, 22);
		tabMonitoring.add(_txt_curr_motor_right);

		JLabel lbl_light_sensors_right = new JLabel("LS Right");
		lbl_light_sensors_right.setBounds(286, 406, 66, 16);
		tabMonitoring.add(lbl_light_sensors_right);

		_txt_light_sensors_right = new JTextField();
		_txt_light_sensors_right.setForeground(Color.BLUE);
		_txt_light_sensors_right.setEditable(false);
		_txt_light_sensors_right.setColumns(10);
		_txt_light_sensors_right.setBounds(363, 403, 116, 22);
		tabMonitoring.add(_txt_light_sensors_right);

		_txtAdrIp = new JTextField();
		_txtAdrIp.setFont(new Font("Tahoma", Font.BOLD, 16));
		_txtAdrIp.setColumns(10);
		_txtAdrIp.setBounds(92, 20, 139, 41);
		_contentPane.add(_txtAdrIp);

		_btnConnect = new JButton("Connexion");
		_btnConnect.setBounds(391, 21, 160, 41);
		_contentPane.add(_btnConnect);

		_btnDisconnect = new JButton("D\u00E9connexion");
		_btnDisconnect.setBounds(571, 21, 150, 41);
		_contentPane.add(_btnDisconnect);

		JLabel lbl_etat_connexion = new JLabel("Etat de la connexion :");
		lbl_etat_connexion.setBounds(781, 28, 129, 27);
		_contentPane.add(lbl_etat_connexion);

		_imgConnectionState = new JLabel("");
		_imgConnectionState.setBounds(904, 13, 72, 96);
		_contentPane.add(_imgConnectionState);

		JLabel lbl_log = new JLabel("Log :");
		lbl_log.setBounds(23, 661, 56, 27);
		_contentPane.add(lbl_log);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(27, 689, 985, 10);
		_contentPane.add(separator_1);

		_txtLogArea = new JTextArea();
		_txtLogArea.setForeground(Color.WHITE);
		_txtLogArea.setEditable(false);
		_txtLogArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		_txtLogArea.setBackground(Color.BLACK);
		_txtLogArea.setBounds(24, 712, 988, 142);
		_contentPane.add(_txtLogArea);
		writeToLogArea("D?marrage de l'application");

		JLabel lblIP = new JLabel("Adresse IP :");
		lblIP.setBounds(13, 20, 72, 35);
		_contentPane.add(lblIP);

		JLabel lbl_batterie = new JLabel("Etat de la batterie :");
		lbl_batterie.setBounds(13, 99, 129, 16);
		_contentPane.add(lbl_batterie);

		_imgBatteryState = new JLabel("");
		_imgBatteryState.setBounds(136, 85, 108, 47);
		_contentPane.add(_imgBatteryState);

		_lblBatteryPercent = new JLabel("");
		_lblBatteryPercent.setBounds(247, 75, 56, 57);
		_contentPane.add(_lblBatteryPercent);

		_lblBatteryWarning = new JLabel("");
		_lblBatteryWarning.setBounds(305, 75, 71, 57);
		_contentPane.add(_lblBatteryWarning);

		JPanel tabEditor = new JPanel();
		_tabPane.addTab("Editeur de trajectoire", null, tabEditor, null);
		tabEditor.setLayout(null);

		_editorGridTraj = new JPanel();
		_editorGridTraj.setBounds(12, 13, 549, 447);
		tabEditor.add(_editorGridTraj);
		_editorGridTraj.setLayout(new GridLayout(1, 0, 0, 0));

		JButton btnNewButton = new JButton("RESET");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				_list_command_model.clear();
				_txt_speed_command.setText("");
				_txt_duration_command.setText("");
				_cb_direction_command.setSelectedItem(null);
				_cb_trajectory_mode.setSelectedIndex(-1);
				resetEditorGrid();
			}
		});
		btnNewButton.setBounds(728, 395, 254, 65);
		tabEditor.add(btnNewButton);

		JLabel lbl_command_direction = new JLabel("Direction");
		lbl_command_direction.setBounds(790, 93, 56, 16);
		tabEditor.add(lbl_command_direction);

		_cb_direction_command = new JComboBox<RobotDirection>();
		_cb_direction_command.setBounds(871, 90, 111, 22);
		tabEditor.add(_cb_direction_command);

		JLabel lbl_speed_command = new JLabel("Vitesse (entre 1 et 160)");
		lbl_speed_command.setBounds(710, 140, 136, 16);
		tabEditor.add(lbl_speed_command);

		JLabel lbl_command_duration = new JLabel("Dur\u00E9e de la commande");
		lbl_command_duration.setBounds(718, 193, 144, 16);
		tabEditor.add(lbl_command_duration);

		_txt_speed_command = new JTextField();
		_txt_speed_command.setBounds(866, 137, 116, 22);
		tabEditor.add(_txt_speed_command);
		_txt_speed_command.setColumns(10);

		_txt_duration_command = new JTextField();
		_txt_duration_command.setColumns(10);
		_txt_duration_command.setBounds(866, 190, 116, 22);
		tabEditor.add(_txt_duration_command);

		JButton _btnSaveTrajFile = new JButton("Sauvegarder Trajectoire");
		_btnSaveTrajFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (_cb_trajectory_mode.getSelectedIndex() != -1) {
					if (_editorTrajectory.getCommandsListSize() > 0) {
						JFileChooser fc = new JFileChooser();
						fc.setDialogTitle("Choississez un chemin de destination");
						fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
						fc.setDialogType(JFileChooser.SAVE_DIALOG);
						fc.setFileFilter(new FileNameExtensionFilter("Fichier traj","traj"));
						
						int returned = fc.showSaveDialog(_btnSaveTrajFile);
						if (returned == JFileChooser.APPROVE_OPTION) {
							String selectedPath = fc.getSelectedFile().getAbsolutePath();
							String writtenPath = RobotIO.writeTrajFile(_editorTrajectory, selectedPath);
							if (writtenPath != null)
								JOptionPane.showMessageDialog(null, "Le fichier " + writtenPath + " a ?t? cr?? avec succ?s");
							else
								JOptionPane.showMessageDialog(null, "Probl?me lors de la cr?ation du fichier");

						}
					}
					else {
						JOptionPane.showMessageDialog(null, "Veuillez ajouter au moins une commande");
					}
					
				}
				else {
					JOptionPane.showMessageDialog(null, "Veuillez s?lectionner un mode trajectoire valide");
				}
				
			}
		});
		_btnSaveTrajFile.setBounds(731, 317, 254, 65);
		tabEditor.add(_btnSaveTrajFile);

		JButton btnAjouterCommande = new JButton("Ajouter Commande");
		btnAjouterCommande.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (PopupCommandEditor.checkValues(_cb_direction_command, _txt_speed_command, _txt_duration_command)) {
					DriveCommand dc = new DriveCommand((RobotDirection) _cb_direction_command.getSelectedItem(),
							Integer.parseInt(_txt_speed_command.getText()),
							Integer.parseInt(_txt_duration_command.getText()));
					
					
					_editorTrajectory.addDriveCommand(dc);
					
					_list_command_model.addElement(dc);
					_list_command.setModel(_list_command_model);
					
					RobotOrientation ro = _editorTrajectory.getOrientationAt(_editorTrajectory.getCommandsListSize() - 1);
					
					RobotDirection rd = _editorTrajectory.getDirectionBaseOnOrientation(ro, (RobotDirection)_cb_direction_command.getSelectedItem());
					
					int[] _selectedButtons = selectEditorGridNeighBoorButtons(2,rd,_editorLastPoint,_txt_speed_command.getText());
					_editorSegmentMap.put(_editorTrajectory.getCommandsListSize() - 1 , _selectedButtons);
					_editorLastPoint = _selectedButtons[1];
				}
				

			}
		});
		btnAjouterCommande.setBounds(728, 239, 254, 65);
		tabEditor.add(btnAjouterCommande);

		_list_command = new JList<DriveCommand>();
		_list_command.setForeground(Color.WHITE);
		_list_command.setBackground(Color.BLACK);
		_list_command.setBounds(573, 13, 125, 447);
		_list_command.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// TODO Auto-generated method stub
				int index = _list_command.getSelectedIndex();
				if (index != -1) {
					selectEditorSegment(_list_command.getSelectedIndex());
					_editorSelectedSegmentIndex = _list_command.getSelectedIndex();
				}
				
			}
		});
		
		_list_command.addMouseListener(new MouseAdapter() {
			
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) {
					RobotClientUI parent = (RobotClientUI) SwingUtilities.getRoot(evt.getComponent());
					int selected = _list_command.getSelectedIndex();
					PopupCommandEditor popup = new PopupCommandEditor(parent, selected);
					popup.showUI(true);

				}

			}
		});
		
		tabEditor.add(_list_command);

		_lblCrerUneCommande = new JLabel("Cr\u00E9er une commande :");
		_lblCrerUneCommande.setFont(new Font("Tahoma", Font.BOLD, 13));
		_lblCrerUneCommande.setForeground(Color.BLACK);
		_lblCrerUneCommande.setBackground(Color.BLACK);
		_lblCrerUneCommande.setHorizontalAlignment(SwingConstants.CENTER);
		_lblCrerUneCommande.setBounds(706, 53, 276, 16);
		tabEditor.add(_lblCrerUneCommande);
		
		_cb_trajectory_mode = new JComboBox<TrajectoryMode>();
		_cb_trajectory_mode.setBounds(884, 11, 98, 22);
		_cb_trajectory_mode.addItem(TrajectoryMode.ONCE);
		_cb_trajectory_mode.addItem(TrajectoryMode.LOOP);
		
		_cb_trajectory_mode.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_editorTrajectory.set_trajMode((TrajectoryMode)_cb_trajectory_mode.getSelectedItem());
			}
		});
		tabEditor.add(_cb_trajectory_mode);
		
		JLabel lblTrajectoryMode = new JLabel("Mode Trajectoire");
		lblTrajectoryMode.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTrajectoryMode.setBounds(728, 13, 144, 16);
		tabEditor.add(lblTrajectoryMode);
		
		this.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				if (_myClient.is_connected())
				_myClient.send("quit");
			}
			
			
		});

		_previewSegmentMap = new HashMap<Integer, int[]>();
		_previewSelectedSegmentIndex = -1;
		
		_myClient = new RobotClient();
		
		_editorTrajectory = new RobotTrajectory();
		_editorTrajectory.set_trajMode(TrajectoryMode.ONCE);
		_list_command_model = new DefaultListModel<DriveCommand>();
		_editorSegmentMap = new HashMap<Integer,int[]>();
		_editorSelectedSegmentIndex = -1;

		_btnDisconnect.setEnabled(false);
		
		JLabel lbl_port = new JLabel("Port :");
		lbl_port.setBounds(247, 25, 37, 30);
		_contentPane.add(lbl_port);
		
		_txtRobotPort = new JTextField();
		_txtRobotPort.setFont(new Font("Tahoma", Font.BOLD, 16));
		_txtRobotPort.setBounds(296, 20, 83, 41);
		_contentPane.add(_txtRobotPort);
		_txtRobotPort.setColumns(10);
		//_tabPane.setEnabledAt(1, false);
		//_tabPane.setEnabledAt(2, false);

		declareResources();
		buildUI();

		setIcons();
		setFocusable(true);
		requestFocusInWindow();
	}

	/**
	 * Method to affect the image path to constants variables
	 */
	private void declareResources() {
		IMG_UP_ARROW_PATH = getClass().getResource("/up-arrow.png");
		IMG_DOWN_ARROW_PATH = getClass().getResource("/down-arrow.png");
		IMG_LEFT_ARROW_PATH = getClass().getResource("/left-arrow.png");
		IMG_RIGHT_ARROW_PATH = getClass().getResource("/right-arrow.png");

		IMG_BATTERY_100_PATH = getClass().getResource("/battery-100.png");
		IMG_BATTERY_75_PATH = getClass().getResource("/battery-75.png");
		IMG_BATTERY_50_PATH = getClass().getResource("/battery-50.png");
		IMG_BATTERY_25_PATH = getClass().getResource("/battery-25.png");
		IMG_BATTERY_00_PATH = getClass().getResource("/battery-00.png");

		IMG_WARNING_PATH = getClass().getResource("/warning.png");

		IMG_TRAFFIC_LIGHT_BLACK = getClass().getResource("/traffic-light-black.png");
		IMG_TRAFFIC_LIGHT_RED = getClass().getResource("/traffic-light-red.png");
		IMG_TRAFFIC_LIGHT_YELLOW = getClass().getResource("/traffic-light-yellow.png");
		IMG_TRAFFIC_LIGHT_GREEN = getClass().getResource("/traffic-light-green.png");

		IMG_RP6_PATH = getClass().getResource("/rp6.jpg");
	}

	/**
	 * Method to set the different listeners for the keybaord,mouse,UI fields
	 * fill comboBoxes, draw the different grids for the trajectory
	 */
	private void buildUI() {

		buildPreviewGrid(11);
		buildEditorGrid(11);
		setMouseListeners(RobotDirection.FORWARD);
		setMouseListeners(RobotDirection.BACKWARD);
		setMouseListeners(RobotDirection.LEFT);
		setMouseListeners(RobotDirection.RIGHT);

		_cb_direction_command.addItem(RobotDirection.FORWARD);
		_cb_direction_command.addItem(RobotDirection.BACKWARD);
		_cb_direction_command.addItem(RobotDirection.LEFT);
		_cb_direction_command.addItem(RobotDirection.RIGHT);

		addKeyListener();

		setBrowseFileListener();
		setBtnConnectListener();
		setBtnDisconnectListener();

		setBtnStartAutoPilotListener();
		setBtnStopAutoPilotListener();

	}


	/**
	 * Method to display the different default image in the interface
	 * when the client is started
	 */
	private void setIcons() {
		_btnMoveUpward.setIcon(new ImageIcon(IMG_UP_ARROW_PATH));
		_btnMoveBackward.setIcon(new ImageIcon(IMG_DOWN_ARROW_PATH));
		_btnTurnLeft.setIcon(new ImageIcon(IMG_LEFT_ARROW_PATH));
		_btnTurnRight.setIcon(new ImageIcon(IMG_RIGHT_ARROW_PATH));

		_imgConnectionState.setIcon(new ImageIcon(IMG_TRAFFIC_LIGHT_RED));
		_imgRP6.setIcon(new ImageIcon(IMG_RP6_PATH));
	}
	

	/**
	 * Method to display the sensor values and update them while the client is connected to the robot
	 */
	private void displayRobotData() {
		Thread t1 = new Thread() {
			@Override
			public void run() {

				BlinkerUI bk = new BlinkerUI(_imgBatteryState,
						new ImageIcon[] { new ImageIcon(IMG_BATTERY_25_PATH), new ImageIcon(IMG_BATTERY_00_PATH) });

				while (_myClient.is_connected()) {

					_txt_distance_left.setText(_myClient.get_data().get_distanceLeft() + "");
					_txt_distance_right.setText(_myClient.get_data().get_distanceRight() + "");
					_txt_speed_left.setText(_myClient.get_data().get_speedLeft() + "");
					_txt_speed_right.setText(_myClient.get_data().get_speedRight() + "");
					_txt_desired_speed_left.setText(_myClient.get_data().get_desiredSpeedLeft() + "");
					_txt_desired_speed_right.setText(_myClient.get_data().get_desiredSpeedRight() + "");
					_txt_power_left.setText(_myClient.get_data().get_powerLeft() + "");
					_txt_power_right.setText(_myClient.get_data().get_powerRight() + "");
					_txt_curr_motor_left.setText(_myClient.get_data().get_motorCurrentLeft() + "");
					_txt_curr_motor_right.setText(_myClient.get_data().get_motorCurrentRight() + "");
					_txt_light_sensors_left.setText(_myClient.get_data().get_lightSensorLeft() + "");
					_txt_light_sensors_right.setText(_myClient.get_data().get_lightSensorRight() + "");

					_txt_manual_speed.setText(_myClient.get_data().getSpeedPerSecond() + " cm/s");

					_txt_manual_distance.setText(_myClient.get_data().getTotalDistanceMeter() + "");

					if (_myClient.get_data().getBatteryPercentage() <= 25) {
						bk.startBlink();
					} else {
						if (bk.is_running())
							bk.stopBlink();
					}
					showBatteryState(_myClient.get_data().getBatteryPercentage());

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				resetRobotData();
				if (bk.is_running())
					bk.stopBlink();
				_imgBatteryState.setIcon(null);
				_lblBatteryPercent.setText("");
			}
		};
		t1.start();
	}

	/**
	 * Method to reset the different fields containing the sensor values
	 * (when the robot is disconnected for example)
	 */
	private void resetRobotData() {
		_txt_distance_left.setText("");
		_txt_distance_right.setText("");
		_txt_speed_left.setText("");
		_txt_speed_right.setText("");
		_txt_desired_speed_left.setText("");
		_txt_desired_speed_right.setText("");
		_txt_power_left.setText("");
		_txt_power_right.setText("");
		_txt_curr_motor_left.setText("");
		_txt_curr_motor_right.setText("");
		_txt_light_sensors_left.setText("");
		_txt_light_sensors_right.setText("");
		_txt_manual_speed.setText("");
		_txt_manual_distance.setText("");
	}

	/**
	 * Method to update the battery level icon depending of its value
	 * @param batteryValue The actual battery value in percentage
	 */
	private void showBatteryState(int batteryValue) {
		ImageIcon ic1 = null;
		_lblBatteryWarning.setIcon(null);
		if (batteryValue <= 100 && batteryValue > 75)
			ic1 = new ImageIcon(IMG_BATTERY_100_PATH);
		else if (batteryValue <= 75 && batteryValue > 50)
			ic1 = new ImageIcon(IMG_BATTERY_75_PATH);
		else if (batteryValue <= 50 && batteryValue > 25)
			ic1 = new ImageIcon(IMG_BATTERY_50_PATH);
		else if (batteryValue <= 25 && batteryValue > 0) {
			ic1 = new ImageIcon(IMG_BATTERY_25_PATH);
			_lblBatteryWarning.setIcon(new ImageIcon(IMG_WARNING_PATH));
		}

		_lblBatteryPercent.setText("(" + batteryValue + "%)");
		_imgBatteryState.setIcon(ic1);
	}

	/**
	 * Method to write text inside the log area
	 * Message structure: [DATE] : MESSAGE_TO_WRITE \n
	 * @param message The MESSAGE_TO_WRITE
	 */
	private void writeToLogArea(String message) {
		_txtLogArea.setText(_txtLogArea.getText() + "[" + new Date() + "] : " + message + "\n");
	}

	

	/**
	 * Method to build the preview grid in the Autocontrol tab
	 * @param size The size of the grid to build (ex: 5 = a square grid of 5x5) (a odd number is better !!!) 
	 */
	private void buildPreviewGrid(int size) {
		_previewGridSize = size;
		_previewBtns = new JButton[size * size];
		_previewGridPanel.setLayout(new GridLayout(size, size, 0, 0));
		for (int i = 0; i < size * size; i++) {

			JButton _gridBtn = new JButton("");
			_previewGridPanel.add(_gridBtn);
			_previewBtns[i] = _gridBtn;

		}
		setStartPointButtonPreview(size);
	}


	/**
	 * Method to determine the middle point of the grid
	 * and set it as the start point
	 * @param size The size of the grid built (a odd number is better !!!)
	 */
	private void setStartPointButtonPreview(int size) {
		_previewBaseStartPoint = (size / 2 + 1) * (size - 1);
		_previewBtns[_previewBaseStartPoint].setBackground(new Color(255, 0, 0));
		setPreviewGridButtonText(_previewBaseStartPoint, "R");
	}
	

	/**
	 * Method to build the editor grid in the create trajectory tab
	 * @param size The size of the grid to build (ex: 5 = a square grid of 5x5) (a odd number is better !!!) 
	 */
	private void buildEditorGrid(int size) {
		_editorGridSize = size;
		_editorBtns = new JButton[size * size];
		_editorGridTraj.setLayout(new GridLayout(size, size, 0, 0));
		for (int i = 0; i < size * size; i++) {

			JButton _gridBtn = new JButton("");
			_editorGridTraj.add(_gridBtn);
			_editorBtns[i] = _gridBtn;

		}
		setStartPointButtonEditor(size);
	}


	/**
	 * Method to determine the middle point of the grid
	 * and set is as the start point
	 * @param size The size of the grid built
	 */
	private void setStartPointButtonEditor(int size) {
		_editorBaseStartPoint = (size / 2 + 1) * (size - 1);
		_editorBtns[_previewBaseStartPoint].setBackground(new Color(255, 0, 0));
		setEditorGridButtonText(_editorBaseStartPoint, "R");
	}
	

	/**
	 * Method to set Text of a preview grid button
	 * @param index The index of the button in the button array
	 * @param text The text value to affect
	 */
	private void setPreviewGridButtonText(int index, String text) {
		_previewBtns[index].setForeground(Color.WHITE);
		_previewBtns[index].setFont(new Font("Arial", Font.PLAIN, 10));
		_previewBtns[index].setText(text);
	}

	/**
	 * Method to set Text of an editor grid button
	 * @param index The index of the button in the button array
	 * @param text The text value to affect
	 */
	private void setEditorGridButtonText(int index, String text) {
		_editorBtns[index].setForeground(Color.WHITE);
		_editorBtns[index].setFont(new Font("Arial", Font.PLAIN, 12));
		_editorBtns[index].setText(text);
	}


	/**
	 * Method to draw a trajectory on the preview grid based on a
	 * RobotTrajectory object
	 * @param rt The robotTrajectory object to draw
	 */
	private void drawTrajOnPreviewGrid(RobotTrajectory rt) {
		int arrival = _previewBaseStartPoint; // Point de d?part
		int[] indexList = null;
		for (int i = 0; i < rt.getCommandsListSize(); i++) {

			DriveCommand dc = rt.getCommandAt(i);
			RobotDirection rd = dc.get_robotDirection();
			
			RobotOrientation ro = rt.getOrientationAt(i);
			
			rd = rt.getDirectionBaseOnOrientation(ro, rd);
			
			indexList = selectPreviewGridNeighBoorButtons(2, rd, arrival, dc.get_robotSpeed() + "");
			_previewSegmentMap.put(i, indexList); // Ajout des points du segment ? la
											// hashmap (l'index du segment est
											// l'index de la commande)
			arrival = indexList[indexList.length - 1]; // Dernier point du
														// segment dessin?
														// (point de d?part du
														// prochain)
		}
	}
	
	/* - - - SELECT PREVIEW GRID BUTTONS - - -  */
	
	/**
	 * Method to select a button of the preview grid
	 * by affecting a color to it
	 * @param index The index of the button in the button array
	 * @param color The color to affect
	 */
	private void selectPreviewButton(int index, Color color) {
		_previewBtns[index].setBackground(color);
	}

	/**
	 * Method to unselect a button of the preview grid 
	 * by setting its color to the default color
	 * @param index The index of the button to unselect in the button array
	 */
	private void unSelectPreviewButton(int index) {
		_previewBtns[index].setBackground(_previewDefaultButtonColor);
	}

	/**
	 * Method to select a button of the trajectory inside
	 * the preview grid (The current trajectory executed)
	 * @param index The index of the button to select 
	 */
	private void selectPreviewTrajButton(int index) {
		if (index != _previewBaseStartPoint)
			_previewBtns[index].setBackground(Color.GREEN);
	}

	/**
	 * Method to unselect a button of the trajectory inside
	 * the preview grid by setting its color to black or red if it is the start point
	 * @param index The index of the button to unselect
	 */
	private void unSelectPreviewTrajButton(int index) {
		if (index != _previewBaseStartPoint)
			_previewBtns[index].setBackground(Color.BLACK);
		else
			_previewBtns[index].setBackground(Color.RED);
	}
	

	/**
	 * Method to get X neightboor buttons of a button in a specific direction
	 * and affect their values to a specific text
	 * @param nbButtons The number of neightboor buttons ( = segment length)
	 * @param direction The direction of these buttons (ex : the 2 buttons on the left of THIS button)
	 * @param actual The button index from who the direction is based
	 * @param text The text value to affect to these neightboor buttons
	 * @return An array of indexes of these neightboor buttons (array of size = nbButtons parameter)
	 */
	private int[] selectPreviewGridNeighBoorButtons(int nbButtons, RobotDirection direction, int actual, String text) {
		int lastIndex = -1;
		int[] indexList = new int[nbButtons];

		switch (direction) {
		case FORWARD:
			for (int i = 0; i < nbButtons; i++) {
				lastIndex = actual - _previewGridSize * (i + 1);
				if (lastIndex != _previewBaseStartPoint) {
					selectPreviewButton(lastIndex, Color.BLACK);
					setPreviewGridButtonText(lastIndex, text);
				}
				indexList[i] = lastIndex;

			}
			break;

		case BACKWARD:
			for (int i = 0; i < nbButtons; i++) {
				lastIndex = actual + _previewGridSize * (i + 1);
				if (lastIndex != _previewBaseStartPoint) {
					selectPreviewButton(lastIndex, Color.BLACK);
					setPreviewGridButtonText(lastIndex, text);
				}
				indexList[i] = lastIndex;

			}
			break;

		case LEFT:
			for (int i = 0; i < nbButtons; i++) {
				lastIndex = actual - (i + 1);
				if (lastIndex != _previewBaseStartPoint) {
					selectPreviewButton(lastIndex, Color.BLACK);
					setPreviewGridButtonText(lastIndex, text);
				}
				indexList[i] = lastIndex;

			}
			break;

		case RIGHT:
			for (int i = 0; i < nbButtons; i++) {
				lastIndex = actual + (i + 1);
				if (lastIndex != _previewBaseStartPoint) {
					selectPreviewButton(lastIndex, Color.BLACK);
					setPreviewGridButtonText(lastIndex, text);
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

	/**
	 * Method to select a segment (line of buttons) of the preview grid which is a graphical representation of a
	 * driveCommand object
	 * @param index The index of the segment to select
	 */
	private void selectPreviewSegment(int index) {
		if (_previewSelectedSegmentIndex == -1) {
			_previewSelectedSegmentIndex = index;
			int[] btnIndexes = _previewSegmentMap.get(index);
			for (int i = 0; i < btnIndexes.length; i++) {
				selectPreviewTrajButton(btnIndexes[i]); // Select the button which compose the segment
			}
		}
		else {
			if (_previewSelectedSegmentIndex != index) {
				unSelectPreviewSegment(_previewSelectedSegmentIndex);
				_previewSelectedSegmentIndex = index;
				int[] btnIndexes = _previewSegmentMap.get(index);
				for (int i = 0; i < btnIndexes.length; i++) {
					selectPreviewTrajButton(btnIndexes[i]); // Select the button which compose the segment
				}
			}
		}
			
	}

	/**
	 * Method to unselect a segment of the preview grid
	 * @param index The index of the segment to unselect
	 */
	private void unSelectPreviewSegment(int index) {

		if (index != -1) {
			int[] btnIndexes = _previewSegmentMap.get(index);
			for (int i = 0; i < btnIndexes.length; i++) {
				unSelectPreviewTrajButton(btnIndexes[i]); // Unselect the button which compose the segment
			}
		}
		

	}
	
	/**
	 * Method to unselect the currently selected segment
	 */
	private void unSelectCurrentPreviewSegment() {

		int[] btnIndexes = _previewSegmentMap.get(_previewSelectedSegmentIndex);
		for (int i = 0; i < btnIndexes.length; i++) {
			unSelectPreviewTrajButton(btnIndexes[i]);	// Unselect the button which compose the segment
		}

	}
	

	/**
	 * Method to reset the preview grid
	 * and the linked variables
	 */
	private void resetPreviewGrid() {
		for (int i = 0; i < _previewBtns.length; i++) {
			unSelectPreviewButton(i);
			_previewBtns[i].setText("");
			_editorSelectedSegmentIndex = -1;
			setStartPointButtonPreview(_previewGridSize);
		}
	}
	
	/* - - - END - - - */
	
	/**
	 * Method to get X neightboor buttons of a button in a specific direction
	 * and affect their values to a specific text inside the editor grid
	 * @param nbButtons The number of neightboor buttons ( = segment length)
	 * @param direction The direction of these buttons (ex : the 2 buttons on the left of THIS button)
	 * @param actual The button index from who the direction is based
	 * @param text The text value to affect to these neightboor buttons
	 * @return An array of indexes of these neightboor buttons (array of size = nbButtons parameter)
	 */
	private int[] selectEditorGridNeighBoorButtons(int nbButtons, RobotDirection direction, int actual, String text) {
		int lastIndex = -1;
		if (actual == -1)
			actual = _editorBaseStartPoint;
		int[] indexList = new int[nbButtons];

		switch (direction) {
		case FORWARD:
			for (int i = 0; i < nbButtons; i++) {
				lastIndex = actual - _editorGridSize * (i + 1);
				if (lastIndex != _editorBaseStartPoint) {
					selectEditorButton(lastIndex, Color.BLACK);
					setEditorGridButtonText(lastIndex, text);
				}
				indexList[i] = lastIndex;

			}
			break;

		case BACKWARD:
			for (int i = 0; i < nbButtons; i++) {
				lastIndex = actual + _editorGridSize * (i + 1);
				if (lastIndex != _editorBaseStartPoint) {
					selectEditorButton(lastIndex, Color.BLACK);
					setEditorGridButtonText(lastIndex, text);
				}
				indexList[i] = lastIndex;

			}
			break;

		case LEFT:
			for (int i = 0; i < nbButtons; i++) {
				lastIndex = actual - (i + 1);
				if (lastIndex != _editorBaseStartPoint) {
					selectEditorButton(lastIndex, Color.BLACK);
					setEditorGridButtonText(lastIndex, text);
				}
				indexList[i] = lastIndex;

			}
			break;

		case RIGHT:
			for (int i = 0; i < nbButtons; i++) {
				lastIndex = actual + (i + 1);
				if (lastIndex != _editorBaseStartPoint) {
					selectEditorButton(lastIndex, Color.BLACK);
					setEditorGridButtonText(lastIndex, text);
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
	
	/* - - - SELECT EDITOR BUTTONS METHODS - - -  */
	
	/**
	 * Method to select a button of the editor grid and affect it
	 * a specific color
	 * @param index The index of the button to select
	 * @param color The color to affect to this button
	 */
	private void selectEditorButton(int index, Color color) {
		_editorBtns[index].setBackground(color);
	}

	/**
	 * Method to unselect a button of the editor grid
	 * @param index the index of the button to unselect
	 */
	private void unSelectEditorButton(int index) {
		_editorBtns[index].setBackground(_previewDefaultButtonColor);
	}
	
	/**
	 * Method to select a segment (line of buttons) of the editor grid
	 * @param index The index of the segment to select
	 */
	private void selectEditorSegment(int index) {
		if (_editorSelectedSegmentIndex != -1)
			unSelectEditorSegment(_editorSelectedSegmentIndex);

		_editorSelectedSegmentIndex = index;
		int[] btnIndexes = _editorSegmentMap.get(index);
		for (int i = 0; i < btnIndexes.length; i++) {
			selectEditorTrajButton(btnIndexes[i]);
		}

	}

	/**
	 * Method to unselect a segment of the editor grid
	 * @param index The index of the segment to unselect
	 */
	private void unSelectEditorSegment(int index) {

		int[] btnIndexes = _editorSegmentMap.get(index);
		for (int i = 0; i < btnIndexes.length; i++) {
			unSelectEditorTrajButton(btnIndexes[i]);
		}

	}

	/**
	 * Method to unselect the currently selected segment inside the editor grid
	 */
	private void unSelectCurrentEditorSegment() {

		int[] btnIndexes = _editorSegmentMap.get(_editorSelectedSegmentIndex);
		for (int i = 0; i < btnIndexes.length; i++) {
			unSelectEditorTrajButton(btnIndexes[i]);
		}

	}
	
	/**
	 * Method to select a button of the trajectory (from BLACK to GREEN)
	 * @param index The index of the traj button to select
	 */
	private void selectEditorTrajButton(int index) {
		if (index != _editorBaseStartPoint)
			_editorBtns[index].setBackground(Color.GREEN);
	}

	/**
	 * Method to unselect a selected button of the trajectory (from GREEN to BLACK)
	 * @param index The index of the traj button to unselect
	 */
	private void unSelectEditorTrajButton(int index) {
		if (index != _editorBaseStartPoint)
			_editorBtns[index].setBackground(Color.BLACK);
		else
			_editorBtns[index].setBackground(Color.RED);
	}
	

	/**
	 * Method to reset the editor grid and
	 * its variables
	 */
	private void resetEditorGrid() {
		for (int i = 0; i < _previewBtns.length; i++) {
			unSelectEditorButton(i);
			_editorBtns[i].setText("");
			_list_command.clearSelection();
			setStartPointButtonEditor(_editorGridSize);
		}
	}
	
	/**
	 * Method to edit a driveCommand and replace its values by
	 * those of another driveCommand (used by the popupCommandEditor)
	 * @param index The index of the driveCommand to edit inside the robotTrajectory
	 * @param dc The driveCommand containing new values to affect to the one which will be edited
	 */
	public void replaceDriveCommand(int index,DriveCommand dc) {
		_editorTrajectory.editDriveCommand(index, dc);
		_editorSegmentMap.clear();
		resetEditorGrid();
		drawTrajOnEditorGrid(_editorTrajectory);
		_list_command.clearSelection();
		_cb_direction_command.setSelectedIndex(-1);
		_txt_speed_command.setText("");
		_txt_duration_command.setText("");
	}
	
	/**
	 * Method to draw a trajectory (multiple segments) on the grid editor based on
	 * a robotTrajectory object
	 * @param rt The robotTrajectory to represent graphically
	 */
	private void drawTrajOnEditorGrid(RobotTrajectory rt) {
		int arrival = _editorBaseStartPoint; // Point de d?part
		int[] indexList = null;
		for (int i = 0; i < rt.getCommandsListSize(); i++) {

			RobotDirection rd = rt.getCommandAt(i).get_robotDirection();
			
			RobotOrientation ro = rt.getOrientationAt(i);
			
			rd = rt.getDirectionBaseOnOrientation(ro, rd);
			
			indexList = selectEditorGridNeighBoorButtons(2, rd, arrival, rt.getCommandAt(i).get_robotSpeed() + "");
			
			_editorSegmentMap.put(i, indexList); // Ajout des points du segment ? la
											// hashmap (l'index du segment est
											// l'index de la commande)
			arrival = indexList[indexList.length - 1]; // Dernier point du
														// segment dessin?
														// (point de d?part du
														// prochain)
		}
	}
	
	/* - - - END - - - */


	/**
	 * Method to affect the listener to the buttons UP,DOWN,LEFT,RIGHT in the manual
	 * control tab
	 * @param direction The direction associated to the different buttons
	 */
	private void setMouseListeners(RobotDirection direction) {
		switch (direction) {
		case FORWARD:
			_btnMoveUpward.addMouseListener(getMouseListenerByDirection(RobotDirection.FORWARD));
			break;
		case BACKWARD:
			_btnMoveBackward.addMouseListener(getMouseListenerByDirection(RobotDirection.BACKWARD));
			break;
		case LEFT:
			_btnTurnLeft.addMouseListener(getMouseListenerByDirection(RobotDirection.LEFT));
			break;
		case RIGHT:
			_btnTurnRight.addMouseListener(getMouseListenerByDirection(RobotDirection.RIGHT));
			break;
		case NONE:
			break;
		default:
			break;
		}
	}

	/**
	 * Method to generate the good MouseListener for a specific direction
	 * @param direction The direction for the MouseListener needed
	 * @return The correct MouseListener for this direction
	 */
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
						_myClient.send(DriveCommand.getCommandFromDirection(direction));

						while (pressed) {
							try {
								_myClient.send(speed + "");
								Thread.sleep(50);
								if (speed <= 158)
									speed += 2;

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						_myClient.send("stp");
						speed = 0;

					}

				};

				t1.start();

			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				pressed = false;
				speed = 0;
				requestFocusInWindow();
			}

		};
		return listener;
	}

	/**
	 * Method to attach the keyListener for the 4 keyboards keys (UP_ARROW,
	 * DOWN_ARROW,LEFT_ARROW,RIGHT_ARROW)
	 */
	private void addKeyListener() {

		this.addKeyListener(new KeyListener() {

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
							_myClient.send(command);

							while (pressed) {
								try {
									Thread.sleep(50);
									if (speed <= 158)
										speed += 2;

									_myClient.send(speed + "");
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							_myClient.send("stp");
							speed = 0;
						}
					};

					t1.start();

				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				pressed = false;
				launched = false;
				System.out.println("stop");
				speed = 0;
				requestFocusInWindow();
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});

	}

	/**
	 * Method to attach the action when there's a click on the browse button
	 * for a traj file
	 */
	private void setBrowseFileListener() {
		_btnBrowseTrajFile.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choisissez un fichier .traj");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returned = fc.showOpenDialog(_btnBrowseTrajFile);
				if (returned == JFileChooser.APPROVE_OPTION) {
					String selectedPath = fc.getSelectedFile().getAbsolutePath();
					_txtFilePath.setText(selectedPath);
					_previewLoadedTrajectory = RobotIO.readTrajFile(selectedPath);
					if (_previewLoadedTrajectory == null)
						JOptionPane.showMessageDialog(null, "Le fichier .traj n'est pas valide");
					else {
						_previewLoadedTrajectory.set_myClient(_myClient);
						resetPreviewGrid();
						_previewSegmentMap.clear();
						drawTrajOnPreviewGrid(_previewLoadedTrajectory);
						_btnStartAutoPilot.setEnabled(true);
						writeToLogArea("Fichier traj charg? avec succ?s (" + selectedPath + ")");
					}

				}
				requestFocusInWindow();
			}

		});
	}

	/**
	 * Method to attach the action when there's a click on the connect
	 * button
	 */
	private void setBtnConnectListener() {
		_btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (_txtAdrIp.getText() != "" && _txtRobotPort.getText() != "") {
					_btnConnect.setEnabled(false);
					_txtAdrIp.setEnabled(false);
					_txtRobotPort.setEnabled(false);
					BlinkerUI bk = new BlinkerUI(_imgConnectionState, new ImageIcon[] {
							new ImageIcon(IMG_TRAFFIC_LIGHT_YELLOW), new ImageIcon(IMG_TRAFFIC_LIGHT_BLACK) });
					bk.startBlink();
					writeToLogArea("Connexion en cours vers " + _txtAdrIp.getText() + ":" + _txtRobotPort.getText());
					_myClient.openConnection(_txtAdrIp.getText(),Integer.parseInt(_txtRobotPort.getText()));
					Thread t1 = new Thread() {
						@Override
						public void run() {
							while (_myClient.is_connecting()) {
								try {
									Thread.sleep(500);

								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}

							try {
								bk.stopBlink();
								Thread.sleep(200);
								if (_myClient.is_connected()) {
									_btnDisconnect.setEnabled(true);
									_imgConnectionState.setIcon(new ImageIcon(IMG_TRAFFIC_LIGHT_GREEN));
									_tabPane.setEnabledAt(0, true);
									_tabPane.setEnabledAt(1, true);
									_tabPane.setEnabledAt(2, true);
									writeToLogArea("Connect? au RP6 (" + _txtAdrIp.getText() + ")");
									displayRobotData();
								} else {
									_btnConnect.setEnabled(true);
									_txtAdrIp.setEnabled(true);
									_txtRobotPort.setEnabled(true);
									writeToLogArea("Erreur de connexion (TimeOut)");
								}

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					};
					t1.start();
					requestFocusInWindow();
				}
				else {
					JOptionPane.showMessageDialog(null, "Veuillez saisir une adresse IP et un port valide");
				}
				
			}

		});
	}

	/**
	 * Method to attach the action when there's a click on the disconnect
	 * button
	 */
	private void setBtnDisconnectListener() {
		_btnDisconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_myClient.closeConnection();
				Thread t1 = new Thread() {
					@Override
					public void run() {
						while (_myClient.is_connected()) {
							try {
								Thread.sleep(200);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}

						_btnConnect.setEnabled(true);
						_btnDisconnect.setEnabled(false);
						_txtAdrIp.setEnabled(true);
						_txtRobotPort.setEnabled(true);
						_tabPane.setEnabledAt(0, false);
						_tabPane.setEnabledAt(1, false);
						_tabPane.setEnabledAt(2, false);
						_imgConnectionState.setIcon(new ImageIcon(IMG_TRAFFIC_LIGHT_RED));
						resetRobotData();
						writeToLogArea("D?connexion effectu?e avec succ?s");
						requestFocusInWindow();
					}
				};

				t1.start();
			}

		});
	}

	/**
	 * Method to attach the action when there's a click on the start auto pilot
	 * button
	 */
	private void setBtnStartAutoPilotListener() {
		_btnStartAutoPilot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_btnStartAutoPilot.setEnabled(false);
				_btnStopAutoPilot.setEnabled(true);
				_previewLoadedTrajectory.startAutoPilot();
				writeToLogArea("D?marrage de l'autopilote");
				Thread t1 = new Thread() {
					@Override
					public void run() {
						
						while (_previewLoadedTrajectory.is_running()) {
							selectPreviewSegment(_previewLoadedTrajectory.get_currentCommandIndex());
							writeRp6File();
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
							
					}
				};

				t1.start();
			}

		});
	}

	/**
	 * Method to attach the action when there's a click on the stop auto pilot
	 * button
	 */
	private void setBtnStopAutoPilotListener() {
		_btnStopAutoPilot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_previewLoadedTrajectory.stopAutoPilot();
				_btnStartAutoPilot.setEnabled(true);
				_btnStopAutoPilot.setEnabled(false);
				writeToLogArea("Arr?t de l'autopilote");
				requestFocusInWindow();
			}

		});
	}
	
	private void writeRp6File() {
		try {
			PrintWriter p = new PrintWriter(new File("robot_status.rp6"));
			p.write("GRID_SIZE=" + _previewGridSize + "\n");
			String traj_indexes = "";
			for (int[] value : _previewSegmentMap.values()) {
				for (int i = 0 ; i< value.length ; i++) {
					traj_indexes += value[i];
					if (i != value.length - 1)
						traj_indexes += ";";
				}
					 
			}
			p.write("TRAJ_INDEXES=" + traj_indexes + "\n");
			String curr_indexes = "";
			int[] curr_indexesi  = _previewSegmentMap.get(_previewSelectedSegmentIndex);
			for (int i = 0 ; i< curr_indexesi.length ; i++) {
				curr_indexes += curr_indexesi[i];
				if (i != curr_indexesi.length -1)
					curr_indexes += ";";
			}
				
			p.write("CURR_INDEXES=" + curr_indexes + "\n");
			p.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

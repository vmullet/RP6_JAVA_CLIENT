package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import model.DriveCommand;
import model.RobotDirection;
import model.RobotTrajectory;
import controller.RobotClient;
import controller.RobotIO;

import javax.swing.JTabbedPane;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.EtchedBorder;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JList;

@SuppressWarnings("serial")
public class RobotClientUI_V2 extends JFrame {

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

	private RobotClient _myClient;

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

	/* - - - Editor Variables - - - */
	private JPanel _editorGridTraj;
	private JButton[] _editorBtns;
	private int _editorGridSize;
	private int _editorBaseStartPoint;
	private HashMap<Integer, int[]> _editorSegmentMap;
	private int _editorSelectedSegmentIndex;
	/* - - - Editor UI Variables - - - */
	private JComboBox<RobotDirection> _cb_direction_command;
	private JTextField _txt_duration_command;
	private JList<DriveCommand> _list_command;
	private DefaultListModel<DriveCommand> _list_command_model;
	private JLabel _lblCrerUneCommande;
	private RobotTrajectory _createdTrajectory;
	/* - - -  END - - - */
	
	
	/**
	 * Create the frame.
	 */
	public RobotClientUI_V2() {

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
		_tabPane.addTab("Contrôle Manuel", null, tabManualControl, null);
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
		_previewGridPanel.setBounds(10, 79, 484, 381);
		tabAutoControl.add(_previewGridPanel);

		JLabel lbl_preview_traj = new JLabel("Aper\u00E7u de la trajectoire :");
		lbl_preview_traj.setBounds(10, 61, 153, 16);
		tabAutoControl.add(lbl_preview_traj);

		_btnStartAutoPilot = new JButton("START AUTO PILOT");
		_btnStartAutoPilot.setEnabled(false);
		_btnStartAutoPilot.setBounds(823, 92, 159, 164);
		tabAutoControl.add(_btnStartAutoPilot);

		_btnStopAutoPilot = new JButton("STOP AUTO PILOT");
		_btnStopAutoPilot.setEnabled(false);
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
		_btnConnect.setBounds(248, 21, 160, 41);
		_contentPane.add(_btnConnect);

		_btnDisconnect = new JButton("D\u00E9connexion");
		_btnDisconnect.setBounds(435, 20, 150, 41);
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
		writeToLogArea("Démarrage de l'application");

		JLabel label = new JLabel("Adresse IP :");
		label.setBounds(13, 20, 72, 35);
		_contentPane.add(label);

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
			}
		});
		btnNewButton.setBounds(728, 395, 254, 65);
		tabEditor.add(btnNewButton);

		JLabel lbl_command_direction = new JLabel("Direction");
		lbl_command_direction.setBounds(790, 50, 56, 16);
		tabEditor.add(lbl_command_direction);

		_cb_direction_command = new JComboBox<RobotDirection>();
		_cb_direction_command.setBounds(866, 47, 111, 22);
		tabEditor.add(_cb_direction_command);

		JLabel lbl_speed_command = new JLabel("Vitesse (entre 1 et 150)");
		lbl_speed_command.setBounds(718, 122, 136, 16);
		tabEditor.add(lbl_speed_command);

		JLabel lbl_command_duration = new JLabel("Dur\u00E9e de la commande");
		lbl_command_duration.setBounds(718, 193, 144, 16);
		tabEditor.add(lbl_command_duration);

		_txt_speed_command = new JTextField();
		_txt_speed_command.setBounds(866, 119, 116, 22);
		tabEditor.add(_txt_speed_command);
		_txt_speed_command.setColumns(10);

		_txt_duration_command = new JTextField();
		_txt_duration_command.setColumns(10);
		_txt_duration_command.setBounds(866, 190, 116, 22);
		tabEditor.add(_txt_duration_command);

		JButton _btnSaveTrajFile = new JButton("Sauvegarder Trajectoire");
		_btnSaveTrajFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choississez un chemin de destination");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returned = fc.showSaveDialog(_btnSaveTrajFile);
				if (returned == JFileChooser.APPROVE_OPTION) {
					String selectedPath = fc.getSelectedFile().getAbsolutePath();
					boolean written = RobotIO.writeTrajFile(_createdTrajectory, selectedPath);
					if (written)
						JOptionPane.showMessageDialog(null, "Le fichier " + selectedPath + " a été créé avec succès");
					else
						JOptionPane.showMessageDialog(null, "Problème lors de la création du fichier");

				}
			}
		});
		_btnSaveTrajFile.setBounds(731, 317, 254, 65);
		tabEditor.add(_btnSaveTrajFile);

		JButton btnAjouterCommande = new JButton("Ajouter Commande");
		btnAjouterCommande.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DriveCommand dc = new DriveCommand((RobotDirection) _cb_direction_command.getSelectedItem(),
						Integer.parseInt(_txt_speed_command.getText()),
						Integer.parseInt(_txt_duration_command.getText()));
				_createdTrajectory.addDriveCommand(dc);
				_list_command_model.addElement(dc);
				_list_command.setModel(_list_command_model);

			}
		});
		btnAjouterCommande.setBounds(728, 239, 254, 65);
		tabEditor.add(btnAjouterCommande);

		_list_command = new JList<DriveCommand>();
		_list_command.setForeground(Color.WHITE);
		_list_command.setBackground(Color.BLACK);
		_list_command.setBounds(573, 13, 125, 447);
		tabEditor.add(_list_command);

		_lblCrerUneCommande = new JLabel("Cr\u00E9er une commande :");
		_lblCrerUneCommande.setFont(new Font("Tahoma", Font.BOLD, 13));
		_lblCrerUneCommande.setForeground(Color.BLACK);
		_lblCrerUneCommande.setBackground(Color.BLACK);
		_lblCrerUneCommande.setHorizontalAlignment(SwingConstants.CENTER);
		_lblCrerUneCommande.setBounds(667, 13, 306, 16);
		tabEditor.add(_lblCrerUneCommande);

		_previewSegmentMap = new HashMap<Integer, int[]>();
		_previewSelectedSegmentIndex = -1;
		_myClient = new RobotClient();
		_createdTrajectory = new RobotTrajectory();
		_list_command_model = new DefaultListModel<DriveCommand>();

		_btnDisconnect.setEnabled(false);
		_tabPane.setEnabledAt(1, false);
		_tabPane.setEnabledAt(2, false);

		declareResources();
		buildUI();

		setIcons();
		setFocusable(true);
		requestFocusInWindow();
	}

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

	private void buildUI() {

		buildGrid(11);
		buildEditor(_editorGridTraj, 11);
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

	public void setMouseListeners(RobotDirection direction) {
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
								Thread.sleep(100);
								if (speed <= 158)
									speed += 2;

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
				_myClient.send("stp");
				pressed = false;
				speed = 1;
				requestFocusInWindow();
			}

		};
		return listener;
	}

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
									Thread.sleep(100);
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
						drawTrajOnGrid(_previewLoadedTrajectory);
						_btnStartAutoPilot.setEnabled(true);
						writeToLogArea("Fichier traj chargé avec succès (" + selectedPath + ")");
					}

				}
				requestFocusInWindow();
			}

		});
	}

	private void setBtnConnectListener() {
		_btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_btnConnect.setEnabled(false);
				_txtAdrIp.setEnabled(false);
				BlinkerUI bk = new BlinkerUI(_imgConnectionState, new ImageIcon[] {
						new ImageIcon(IMG_TRAFFIC_LIGHT_YELLOW), new ImageIcon(IMG_TRAFFIC_LIGHT_BLACK) });
				bk.startBlink();
				writeToLogArea("Connexion en cours vers " + _txtAdrIp.getText() + ":" + RobotClient.getCONN_PORT());
				_myClient.openConnection(_txtAdrIp.getText());
				Thread t1 = new Thread() {
					@Override
					public void run() {
						while (_myClient.get_is_connecting()) {
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
							if (_myClient.get_is_connected()) {
								_btnDisconnect.setEnabled(true);
								_imgConnectionState.setIcon(new ImageIcon(IMG_TRAFFIC_LIGHT_GREEN));
								_tabPane.setEnabledAt(0, true);
								_tabPane.setEnabledAt(1, true);
								_tabPane.setEnabledAt(2, true);
								writeToLogArea("Connecté au RP6 (" + _txtAdrIp.getText() + ")");
								displayRobotData();
							} else {
								_btnConnect.setEnabled(true);
								_txtAdrIp.setEnabled(true);
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

		});
	}

	private void setBtnDisconnectListener() {
		_btnDisconnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_myClient.closeConnection();
				Thread t1 = new Thread() {
					@Override
					public void run() {
						while (!_myClient.get_is_connected()) {
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
						_tabPane.setEnabledAt(0, false);
						_tabPane.setEnabledAt(1, false);
						_tabPane.setEnabledAt(2, false);
						_imgConnectionState.setIcon(new ImageIcon(IMG_TRAFFIC_LIGHT_RED));
						resetRobotData();
						writeToLogArea("Déconnexion effectuée avec succès");
						requestFocusInWindow();
					}
				};

				t1.start();
			}

		});
	}

	private void setBtnStartAutoPilotListener() {
		_btnStartAutoPilot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_previewLoadedTrajectory.startAutoPilot();
				Thread t1 = new Thread() {
					@Override
					public void run() {
						while (_previewLoadedTrajectory.is_running())
							selectPreviewSegment(_previewLoadedTrajectory.get_currentCommandIndex());
					}
				};

				t1.start();
			}

		});
	}

	private void setBtnStopAutoPilotListener() {
		_btnStopAutoPilot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				_previewLoadedTrajectory.stopAutoPilot();
			}

		});
	}

	private void setIcons() {
		_btnMoveUpward.setIcon(new ImageIcon(IMG_UP_ARROW_PATH));
		_btnMoveBackward.setIcon(new ImageIcon(IMG_DOWN_ARROW_PATH));
		_btnTurnLeft.setIcon(new ImageIcon(IMG_LEFT_ARROW_PATH));
		_btnTurnRight.setIcon(new ImageIcon(IMG_RIGHT_ARROW_PATH));

		_imgConnectionState.setIcon(new ImageIcon(IMG_TRAFFIC_LIGHT_RED));
		_imgRP6.setIcon(new ImageIcon(IMG_RP6_PATH));
	}

	private void setStartPointButton(int size) {
		_previewBaseStartPoint = (size / 2 + 1) * (size - 1);
		_previewBtns[_previewBaseStartPoint].setBackground(new Color(255, 0, 0));
		setPreviewGridButtonText(_previewBaseStartPoint, "R");
	}

	private void setStartPointButtonEditor(int size) {
		_previewBaseStartPoint = (size / 2 + 1) * (size - 1);
		_editorBtns[_previewBaseStartPoint].setBackground(new Color(255, 0, 0));
		setEditorGridEditorButtonText(_previewBaseStartPoint, "R");
	}

	private void buildGrid(int size) {
		_previewGridSize = size;
		_previewBtns = new JButton[size * size];
		_previewGridPanel.setLayout(new GridLayout(size, size, 0, 0));
		for (int i = 0; i < size * size; i++) {

			JButton _gridBtn = new JButton("");
			_previewGridPanel.add(_gridBtn);
			_previewBtns[i] = _gridBtn;

		}
		setStartPointButton(size);
	}

	private void buildEditor(JPanel _grid, int size) {
		_previewGridSize = size;
		_editorBtns = new JButton[size * size];
		_grid.setLayout(new GridLayout(size, size, 0, 0));
		for (int i = 0; i < size * size; i++) {

			JButton _gridBtn = new JButton("");
			_grid.add(_gridBtn);
			_editorBtns[i] = _gridBtn;

		}
		setStartPointButtonEditor(size);
	}

	private void displayRobotData() {
		Thread t1 = new Thread() {
			@Override
			public void run() {

				BlinkerUI bk = new BlinkerUI(_lblBatteryWarning,
						new ImageIcon[] { new ImageIcon(IMG_BATTERY_25_PATH), new ImageIcon(IMG_BATTERY_00_PATH) });

				while (_myClient.get_is_connected()) {

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
			}
		};
		t1.start();
	}

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

	private void writeToLogArea(String message) {
		_txtLogArea.setText(_txtLogArea.getText() + "[" + new Date() + "] : " + message + "\n");
	}

	private void setPreviewGridButtonText(int index, String text) {
		_previewBtns[index].setForeground(Color.WHITE);
		_previewBtns[index].setFont(new Font("Arial", Font.PLAIN, 12));
		_previewBtns[index].setText(text);
	}

	private void setEditorGridEditorButtonText(int index, String text) {
		_editorBtns[index].setForeground(Color.WHITE);
		_editorBtns[index].setFont(new Font("Arial", Font.PLAIN, 12));
		_editorBtns[index].setText(text);
	}

	public void drawTrajOnGrid(RobotTrajectory rt) {
		int arrival = _previewBaseStartPoint; // Point de départ
		int[] indexList = null;
		for (int i = 0; i < rt.getCommandsListSize(); i++) {

			DriveCommand dc = rt.getCommandAt(i);
			indexList = selectPreviewGridNeighBoorButtons(2, dc.get_robotDirection(), arrival, dc.get_robotSpeed() + "");
			_previewSegmentMap.put(i, indexList); // Ajout des points du segment à la
											// hashmap (l'index du segment est
											// l'index de la commande)
			arrival = indexList[indexList.length - 1]; // Dernier point du
														// segment dessiné
														// (point de départ du
														// prochain)
		}
	}

	/* - - - SELECT PREVIEW GRID BUTTONS - - -  */
	
	private void selectPreviewButton(int index, Color color) {
		_previewBtns[index].setBackground(color);
	}

	private void unSelectPreviewButton(int index) {
		_previewBtns[index].setBackground(_previewDefaultButtonColor);
	}

	private void selectPreviewTrajButton(int index) {
		if (index != _previewBaseStartPoint)
			_previewBtns[index].setBackground(Color.GREEN);
	}

	private void unSelectPreviewTrajButton(int index) {
		if (index != _previewBaseStartPoint)
			_previewBtns[index].setBackground(Color.BLACK);
		else
			_previewBtns[index].setBackground(Color.RED);
	}
	

	
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

	private void selectPreviewSegment(int index) {
		if (_previewSelectedSegmentIndex != -1)
			unSelectPreviewSegment(_previewSelectedSegmentIndex);

		_previewSelectedSegmentIndex = index;
		int[] btnIndexes = _previewSegmentMap.get(index);
		for (int i = 0; i < btnIndexes.length; i++) {
			selectPreviewTrajButton(btnIndexes[i]);
		}

	}

	private void unSelectPreviewSegment(int index) {

		int[] btnIndexes = _previewSegmentMap.get(index);
		for (int i = 0; i < btnIndexes.length; i++) {
			unSelectPreviewTrajButton(btnIndexes[i]);
		}

	}

	private void unSelectCurrentPreviewSegment() {

		int[] btnIndexes = _previewSegmentMap.get(_previewSelectedSegmentIndex);
		for (int i = 0; i < btnIndexes.length; i++) {
			unSelectPreviewTrajButton(btnIndexes[i]);
		}

	}
	

	private void resetPreviewGrid() {
		for (int i = 0; i < _previewBtns.length; i++) {
			unSelectPreviewButton(i);
			_previewBtns[i].setText("");
		}
	}
	
	/* - - - END - - - */
	
	
	/* - - - SELECT EDITOR BUTTONS METHODS - - -  */
	
	private void selectEditorButton(int index, Color color) {
		_editorBtns[index].setBackground(color);
	}

	private void unSelectEditorButton(int index) {
		_editorBtns[index].setBackground(_previewDefaultButtonColor);
	}

	private void selectEditorTrajButton(int index) {
		if (index != _previewBaseStartPoint)
			_editorBtns[index].setBackground(Color.GREEN);
	}

	private void unSelectEditorTrajButton(int index) {
		if (index != _previewBaseStartPoint)
			_editorBtns[index].setBackground(Color.BLACK);
		else
			_editorBtns[index].setBackground(Color.RED);
	}
	

	private void selectEditorSegment(int index) {
		if (_editorSelectedSegmentIndex != -1)
			unSelectEditorSegment(_editorSelectedSegmentIndex);

		_editorSelectedSegmentIndex = index;
		int[] btnIndexes = _editorSegmentMap.get(index);
		for (int i = 0; i < btnIndexes.length; i++) {
			selectEditorTrajButton(btnIndexes[i]);
		}

	}
	
	private void unSelectEditorSegment(int index) {

		int[] btnIndexes = _editorSegmentMap.get(index);
		for (int i = 0; i < btnIndexes.length; i++) {
			unSelectEditorTrajButton(btnIndexes[i]);
		}

	}
	
	private void unSelectCurrentEditorSegment() {

		int[] btnIndexes = _editorSegmentMap.get(_editorSelectedSegmentIndex);
		for (int i = 0; i < btnIndexes.length; i++) {
			unSelectEditorTrajButton(btnIndexes[i]);
		}

	}

	private void resetEditorGrid() {
		for (int i = 0; i < _previewBtns.length; i++) {
			unSelectEditorButton(i);
			_editorBtns[i].setText("");
		}
	}
	
	/* - - - END - - - */

	
}

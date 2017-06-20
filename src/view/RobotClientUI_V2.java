package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import java.text.ParseException;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
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
	
	private JTextField _txtFilePath;
	private JTextArea _txtLogArea;
	
	private JButton[] _gridTrajPreview;

	/**
	 * Create the frame.
	 */
	public RobotClientUI_V2() {
		setTitle("RP6 Robot Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1090, 868);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(13, 103, 999, 503);
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
		
		
		JPanel panel_preview_grid = new JPanel();
		panel_preview_grid.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_preview_grid.setBounds(10, 92, 484, 354);
		tabAutoControl.add(panel_preview_grid);
		panel_preview_grid.setLayout(new GridLayout(10, 10, 0, 0));
		
		JLabel lbl_preview_traj = new JLabel("Aper\u00E7u de la trajectoire :");
		lbl_preview_traj.setBounds(12, 63, 153, 16);
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
		
		_gridTrajPreview = new JButton[100];
		
		for (int i = 0 ; i<100;i++) {
			JButton _gridBtn = new JButton("");
			panel_preview_grid.add(_gridBtn);
			_gridTrajPreview[i] = _gridBtn;
		}
		
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
		
		JFormattedTextField formattedTextField = new JFormattedTextField(mf);
		formattedTextField.setFont(new Font("Tahoma", Font.BOLD, 16));
		formattedTextField.setColumns(10);
		formattedTextField.setBounds(92, 20, 139, 41);
		contentPane.add(formattedTextField);
		
		_btnConnect = new JButton("Connexion");
		_btnConnect.setBounds(248, 21, 160, 41);
		contentPane.add(_btnConnect);
		
		_btnDisconnect = new JButton("D\u00E9connexion");
		_btnDisconnect.setBounds(435, 20, 150, 41);
		contentPane.add(_btnDisconnect);
		
		JLabel lbl_etat_connexion = new JLabel("Etat de la connexion :");
		lbl_etat_connexion.setBounds(810, 25, 129, 27);
		contentPane.add(lbl_etat_connexion);
		
		JLabel lbl_img_etat_connexion = new JLabel("");
		lbl_img_etat_connexion.setBounds(956, 14, 56, 47);
		contentPane.add(lbl_img_etat_connexion);
		
		JLabel lbl_log = new JLabel("Log :");
		lbl_log.setBounds(23, 607, 56, 27);
		contentPane.add(lbl_log);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(27, 637, 985, 10);
		contentPane.add(separator_1);
		
		_txtLogArea = new JTextArea();
		_txtLogArea.setText("[Sun Jun 18 11:04:55 CEST 2017] : D\u00E9marrage de l'application\n");
		_txtLogArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
		_txtLogArea.setBackground(Color.WHITE);
		_txtLogArea.setBounds(27, 666, 988, 142);
		contentPane.add(_txtLogArea);
		
		JLabel label = new JLabel("Adresse IP :");
		label.setBounds(13, 20, 72, 35);
		contentPane.add(label);
		
		
		
		JLabel lbl_batterie = new JLabel("Etat de la batterie :");
		lbl_batterie.setBounds(810, 76, 129, 16);
		contentPane.add(lbl_batterie);
		
		JLabel lbl_img_batterie = new JLabel("");
		lbl_img_batterie.setBounds(956, 63, 56, 47);
		contentPane.add(lbl_img_batterie);
		_btnDisconnect.setVisible(false);
		
		setFocusable(true);
	    requestFocusInWindow();
	}
}

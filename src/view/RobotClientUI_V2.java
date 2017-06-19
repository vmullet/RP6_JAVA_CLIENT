package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import model.RobotClient;

import javax.swing.JTabbedPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;
import java.awt.SystemColor;
import java.text.ParseException;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JTable;
import java.awt.GridLayout;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EtchedBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class RobotClientUI_V2 extends JFrame {

	private JPanel contentPane;
	private JTextField txt_chemin_fichier;
	private RobotClient _client;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RobotClientUI_V2 frame = new RobotClientUI_V2();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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
		
		JButton button_1 = new JButton("");
		button_1.setBounds(148, 13, 100, 100);
		control_robot_panel.add(button_1);
		
		JButton button_2 = new JButton("");
		button_2.setBounds(12, 144, 100, 100);
		control_robot_panel.add(button_2);
		
		JButton button_3 = new JButton("");
		button_3.setBounds(148, 293, 100, 100);
		control_robot_panel.add(button_3);
		
		JButton button_4 = new JButton("");
		button_4.setBounds(282, 144, 100, 100);
		control_robot_panel.add(button_4);
		
		JPanel status_robot_panel = new JPanel();
		status_robot_panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		status_robot_panel.setLayout(null);
		status_robot_panel.setBounds(53, 53, 376, 318);
		tabManualControl.add(status_robot_panel);
		
		JButton button_5 = new JButton("");
		button_5.setBackground(UIManager.getColor("Button.background"));
		button_5.setBounds(12, 13, 56, 85);
		status_robot_panel.add(button_5);
		
		JButton button_6 = new JButton("");
		button_6.setBounds(12, 220, 56, 85);
		status_robot_panel.add(button_6);
		
		JButton button_7 = new JButton("");
		button_7.setBounds(157, 122, 56, 85);
		status_robot_panel.add(button_7);
		
		JButton button_8 = new JButton("");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		button_8.setBounds(308, 13, 56, 85);
		status_robot_panel.add(button_8);
		
		JButton button_9 = new JButton("");
		button_9.setBounds(308, 220, 56, 85);
		status_robot_panel.add(button_9);
		
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
		
		
		txt_chemin_fichier = new JTextField();
		txt_chemin_fichier.setEditable(false);
		txt_chemin_fichier.setBounds(163, 6, 232, 34);
		panel_choose_file.add(txt_chemin_fichier);
		
		JButton btn_select_fichier = new JButton("...");
		btn_select_fichier.setBounds(408, 6, 80, 34);
		btn_select_fichier.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				JFileChooser fc = new JFileChooser();
				fc.setDialogTitle("Choose a Trajectory File...");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				//fc.addChoosableFileFilter(new FileNameExtensionFilter("*.traj", "traj"));
				fc.setFileFilter(new FileNameExtensionFilter("*.traj", "traj"));
				if (fc.showOpenDialog(txt_chemin_fichier)==JFileChooser.APPROVE_OPTION) {
					txt_chemin_fichier.setText(fc.getSelectedFile().getAbsolutePath());
					/*System.out.print(_client.readTrajFile(fc.getSelectedFile().getAbsolutePath()));*/
				}
			}
			
		});
		panel_choose_file.add(btn_select_fichier);
		
		
		JPanel panel_preview_grid = new JPanel();
		panel_preview_grid.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel_preview_grid.setBounds(10, 92, 484, 354);
		tabAutoControl.add(panel_preview_grid);
		panel_preview_grid.setLayout(new GridLayout(10, 10, 0, 0));
		
		JLabel lbl_preview_traj = new JLabel("Aper\u00E7u de la trajectoire :");
		lbl_preview_traj.setBounds(12, 63, 153, 16);
		tabAutoControl.add(lbl_preview_traj);
		
		JButton btn_start_traj = new JButton("START AUTO PILOT");
		btn_start_traj.setBounds(823, 92, 159, 164);
		tabAutoControl.add(btn_start_traj);
		
		JButton btn_stop_traj = new JButton("STOP AUTO PILOT");
		btn_stop_traj.setBounds(823, 282, 159, 164);
		tabAutoControl.add(btn_stop_traj);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setLayout(null);
		panel.setBounds(507, 92, 304, 354);
		tabAutoControl.add(panel);
		
		JButton button = new JButton("");
		button.setBackground(UIManager.getColor("Button.background"));
		button.setBounds(52, 36, 56, 85);
		panel.add(button);
		
		JButton button_10 = new JButton("");
		button_10.setBounds(52, 234, 56, 85);
		panel.add(button_10);
		
		JButton button_11 = new JButton("");
		button_11.setBounds(107, 136, 85, 85);
		panel.add(button_11);
		
		JButton button_12 = new JButton("");
		button_12.setBounds(192, 36, 56, 85);
		panel.add(button_12);
		
		JButton button_13 = new JButton("");
		button_13.setBounds(192, 234, 56, 85);
		panel.add(button_13);
		
		for (int i = 0 ; i<100;i++) {
			JButton btnNewButton_4 = new JButton("");
			panel_preview_grid.add(btnNewButton_4);
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
		
		JButton btn_connexion = new JButton("Connexion");
		btn_connexion.setBounds(248, 21, 160, 41);
		contentPane.add(btn_connexion);
		
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
		
		JTextArea txt_log = new JTextArea();
		txt_log.setText("[Sun Jun 18 11:04:55 CEST 2017] : D\u00E9marrage de l'application\n");
		txt_log.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txt_log.setBackground(Color.WHITE);
		txt_log.setBounds(27, 666, 988, 142);
		contentPane.add(txt_log);
		
		JLabel label = new JLabel("Adresse IP :");
		label.setBounds(13, 20, 72, 35);
		contentPane.add(label);
		
		JButton btnDeconnexion = new JButton("D\u00E9connexion");
		btnDeconnexion.setBounds(435, 20, 150, 41);
		contentPane.add(btnDeconnexion);
		
		JLabel lbl_batterie = new JLabel("Etat de la batterie :");
		lbl_batterie.setBounds(810, 76, 129, 16);
		contentPane.add(lbl_batterie);
		
		JLabel lbl_img_batterie = new JLabel("");
		lbl_img_batterie.setBounds(956, 63, 56, 47);
		contentPane.add(lbl_img_batterie);
		btnDeconnexion.setVisible(false);
		
		_client = new RobotClient();
		setFocusable(true);
	    requestFocusInWindow();
	}
}

package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import controller.RobotClient;

import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import java.awt.Color;
import javax.swing.UIManager;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.ParseException;
import java.util.Date;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTabbedPane;

@SuppressWarnings("serial")
public class RobotClientUI extends JFrame {

	private RobotClient _client;
	private JPanel contentPane;
	private JTextField txt_adr_ip;
	private JTextArea txt_area_log;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RobotClientUI frame = new RobotClientUI();
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
	public RobotClientUI() {
		setTitle("RP6 Robot Java Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 766, 639);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		txt_area_log = new JTextArea();
		txt_area_log.setFont(new Font("Monospaced", Font.PLAIN, 13));
		txt_area_log.setBackground(Color.WHITE);
		txt_area_log.setBounds(96, 522, 336, 133);
		contentPane.add(txt_area_log);
		writeLog("Démarrage de l'application");

		JLabel lbl_log = new JLabel("Log :");
		lbl_log.setBounds(12, 404, 56, 27);
		contentPane.add(lbl_log);

		JSeparator log_separator = new JSeparator();
		log_separator.setBounds(12, 430, 727, 10);
		contentPane.add(log_separator);

		JSeparator status_ctrl_separator = new JSeparator();
		status_ctrl_separator.setOrientation(SwingConstants.VERTICAL);
		status_ctrl_separator.setBounds(328, 71, 13, 331);
		contentPane.add(status_ctrl_separator);

		JLabel lbl_adr_ip = new JLabel("Adresse IP :");
		lbl_adr_ip.setBounds(12, 19, 72, 27);
		contentPane.add(lbl_adr_ip);

		MaskFormatter mf = null;
		try {
			mf = new MaskFormatter("###.###.###.###");

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		txt_adr_ip = new JFormattedTextField(mf);
		txt_adr_ip.setBounds(96, 21, 116, 27);
		contentPane.add(txt_adr_ip);
		txt_adr_ip.setColumns(10);

		JButton btn_connexion = new JButton("Connexion");
		btn_connexion.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				writeLog("Connexion en cours vers " + txt_adr_ip.getText() + "...");
				_client.openConnection("192.168.1.9");
				_client.send("cmd\r\n");
			}

		});
		btn_connexion.setBounds(229, 13, 97, 38);
		contentPane.add(btn_connexion);

		JLabel lbl_etat = new JLabel("Etat de la connexion :");
		lbl_etat.setBounds(540, 24, 129, 27);
		contentPane.add(lbl_etat);

		JPanel status_panel = new JPanel();
		status_panel.setBounds(12, 84, 304, 318);
		contentPane.add(status_panel);
		status_panel.setLayout(null);

		JButton btn_left_front = new JButton("");
		btn_left_front.setBackground(UIManager.getColor("Button.background"));
		btn_left_front.setBounds(52, 25, 56, 85);
		status_panel.add(btn_left_front);

		JButton btn_left_rear = new JButton("");
		btn_left_rear.setBounds(52, 193, 56, 85);
		status_panel.add(btn_left_rear);

		JButton btn_body_status = new JButton("");
		btn_body_status.setBounds(107, 109, 85, 85);
		status_panel.add(btn_body_status);

		JButton btn_right_front = new JButton("");
		btn_right_front.setBounds(192, 25, 56, 85);
		status_panel.add(btn_right_front);

		JButton btn_right_rear = new JButton("");
		btn_right_rear.setBounds(192, 193, 56, 85);
		status_panel.add(btn_right_rear);

		JLabel lbl_img_etat = new JLabel("");
		lbl_img_etat.setIcon(new ImageIcon("C:\\Users\\Valentin\\Documents\\RP6-pictures\\red-circle-md1.png"));
		lbl_img_etat.setBounds(681, 13, 48, 55);
		contentPane.add(lbl_img_etat);

		JLabel lbl_statut_robot = new JLabel("Statut du robot :");
		lbl_statut_robot.setBounds(12, 69, 109, 16);
		contentPane.add(lbl_statut_robot);

		JLabel lbl_controle_robot = new JLabel("Contr\u00F4le du robot :");
		lbl_controle_robot.setBounds(345, 69, 116, 16);
		contentPane.add(lbl_controle_robot);

		JPanel panel = new JPanel();
		panel.setBounds(345, 84, 394, 318);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton btn_up_direction = new JButton("");
		btn_up_direction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestFocusInWindow();
				_client.send("cmd");
				_client.send("f\n");
				_client.send("5");
			}
		});
		btn_up_direction
				.setIcon(new ImageIcon("C:\\Users\\Valentin\\Documents\\RP6-pictures\\up-arrow-png-271601.png"));
		btn_up_direction.setBounds(148, 13, 100, 100);
		panel.add(btn_up_direction);

		JButton btn_left_direction = new JButton("");
		btn_left_direction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestFocusInWindow();
			}
		});
		btn_left_direction.setIcon(
				new ImageIcon("C:\\Users\\Valentin\\Documents\\RP6-pictures\\up-arrow-png-271601 - Copie (2).png"));
		btn_left_direction.setBounds(36, 108, 100, 100);
		panel.add(btn_left_direction);

		JButton btn_down_direction = new JButton("");
		btn_down_direction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestFocusInWindow();
			}
		});
		btn_down_direction.setIcon(
				new ImageIcon("C:\\Users\\Valentin\\Documents\\RP6-pictures\\up-arrow-png-271601 - Copie (3).png"));
		btn_down_direction.setBounds(148, 205, 100, 100);
		panel.add(btn_down_direction);

		JButton btn_right_direction = new JButton("");
		btn_right_direction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				requestFocusInWindow();
			}
		});
		btn_right_direction.setIcon(
				new ImageIcon("C:\\Users\\Valentin\\Documents\\RP6-pictures\\up-arrow-png-271601 - Copie.png"));
		btn_right_direction.setBounds(260, 108, 100, 100);
		panel.add(btn_right_direction);

		JScrollPane scrollPane = new JScrollPane(txt_area_log);
		scrollPane.setBounds(22, 444, 707, 135);
		contentPane.add(scrollPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(384, 41, 89, 27);
		contentPane.add(tabbedPane);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("New tab", null, panel_1, null);

		setFocusable(true);
		requestFocusInWindow();
		addKeyListeners();
		_client = new RobotClient();
	}

	public void addKeyListeners() {
		this.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					writeLog("Haut");
					break;
				case KeyEvent.VK_DOWN:
					writeLog("Bas");
					break;
				case KeyEvent.VK_LEFT:
					writeLog("Gauche");
					break;
				case KeyEvent.VK_RIGHT:
					writeLog("Droite");
					break;
				}

			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				switch (arg0.getKeyCode()) {
				case KeyEvent.VK_UP:
					writeLog("Haut relaché");
					break;
				case KeyEvent.VK_DOWN:
					writeLog("Bas relaché");
					break;
				case KeyEvent.VK_LEFT:
					writeLog("Gauche relaché");
					break;
				case KeyEvent.VK_RIGHT:
					writeLog("Droite relaché");
					break;
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	public void writeLog(String message) {
		txt_area_log.setText(txt_area_log.getText() + "[" + new Date() + "] : " + message + "\n");
	}
}

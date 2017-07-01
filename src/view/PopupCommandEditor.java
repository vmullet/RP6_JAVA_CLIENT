package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import enums.RobotDirection;
import model.DriveCommand;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
/**
 * Class which contains the JDialog Popup to edit a driveCommand inside the Trajectory Editor
 * @author Valentin
 *
 */
public class PopupCommandEditor extends JDialog {

	/**
	 * The JPanel containing all fields
	 */
	private final JPanel contentPanel = new JPanel();
	/**
	 * The ComboBox concerning the direction to edit
	 */
	private JComboBox<RobotDirection> _cb_robot_direction;
	/**
	 * The textField concerning the speed to edit
	 */
	private JTextField _txt_speed_robot;
	/**
	 * The textField concerning the duration to edit
	 */
	private JTextField _txt_duration_command;


	/**
	 * Create the dialog.
	 */
	public PopupCommandEditor(RobotClientUI parent, int commandIndex) {
		setTitle("Edit Command n\u00B0 " + commandIndex);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Vitesse ( entre 1 et 160)");
			lblNewLabel.setBounds(44, 132, 146, 16);
			contentPanel.add(lblNewLabel);
		}
		
		_txt_speed_robot = new JTextField();
		_txt_speed_robot.setBounds(210, 129, 116, 22);
		contentPanel.add(_txt_speed_robot);
		_txt_speed_robot.setColumns(10);
		
		_txt_duration_command = new JTextField();
		_txt_duration_command.setBounds(210, 183, 116, 22);
		contentPanel.add(_txt_duration_command);
		_txt_duration_command.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Dur\u00E9e de la commande");
		lblNewLabel_1.setBounds(52, 186, 146, 16);
		contentPanel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Command Editor");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_2.setBounds(12, 13, 408, 31);
		contentPanel.add(lblNewLabel_2);
		
		JLabel lblRobotDirection = new JLabel("Direction");
		lblRobotDirection.setBounds(134, 83, 56, 16);
		contentPanel.add(lblRobotDirection);
		
		_cb_robot_direction = new JComboBox<RobotDirection>();
		_cb_robot_direction.setBounds(210, 80, 116, 22);
		_cb_robot_direction.addItem(RobotDirection.FORWARD);
		_cb_robot_direction.addItem(RobotDirection.BACKWARD);
		_cb_robot_direction.addItem(RobotDirection.LEFT);
		_cb_robot_direction.addItem(RobotDirection.RIGHT);
		
		contentPanel.add(_cb_robot_direction);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (checkValues(_cb_robot_direction,_txt_speed_robot,_txt_duration_command)) {
							DriveCommand driveCommand = new DriveCommand((RobotDirection)_cb_robot_direction.getSelectedItem(),
									Integer.parseInt(_txt_speed_robot.getText()),Integer.parseInt(_txt_duration_command.getText()));
							parent.replaceDriveCommand(commandIndex, driveCommand);
							setVisible(false);
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		
	}
	
	/**
	 * Method to check if all fields values are correct
	 * @param combo	The comboBox to check
	 * @param speed	The textField to check
	 * @param duration The textField to check
	 * @return	TRUE or FALSE if the values are correct
	 */
	public static boolean checkValues(JComboBox<RobotDirection> combo,JTextField speed,JTextField duration) {
		boolean ok = false;
		if (combo.getSelectedIndex() != -1 && !speed.getText().equals("") && !duration.getText().equals("")) {
			try {
				if (Integer.parseInt(speed.getText()) >= 1 
						&& Integer.parseInt(speed.getText()) <= 160)
				{
					Integer.parseInt(duration.getText());
					ok = true;
				}
				else {
					ok = false;
					JOptionPane.showMessageDialog(null, "La vitesse doit être comprise entre 1 et 160");
				}
						
			}
			catch(NumberFormatException e) {
				ok = false;
				JOptionPane.showMessageDialog(null, "Valeurs numériques invalides");
			}
			
		}
		else
			JOptionPane.showMessageDialog(null, "Valeurs invalides. Avez-vous saisi tous les champs ?");
		
		return ok;
	}
	
	/**
	 * Method to make the popup visible or not
	 * @param show The boolean to make the popup visible or not
	 */
	public void showUI(boolean show) {
		
		setVisible(show);
	}
}

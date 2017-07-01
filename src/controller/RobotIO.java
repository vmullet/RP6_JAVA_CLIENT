package controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import enums.TrajectoryMode;
import model.DriveCommand;
import model.RobotTrajectory;

/**
 * Static class to read, write and check the validity
 * of trajectory files (.traj files)
 * @author Valentin
 *
 */
public final class RobotIO {

	/**
	 * Attribute to hold the last/current read file as an ArrayList of String (1 line = 1 element)
	 */
	private static ArrayList<String> _readFile = new ArrayList<String>();

	/**
	 * Static Method to get the {@link RobotIO#_readFile}
	 * @return The value of the attribute _readFile
	 */
	public static ArrayList<String> getLastReadFile() {
		return _readFile;
	}

	/**
	 * Static Method to read a .traj file
	 * @param p_filepath The path of the file to tread
	 * @return	The RobotTrajectory object based on this file
	 */
	public static RobotTrajectory readTrajFile(String p_filepath) {

		if (isTrajFileValid(p_filepath)) {	// If the file is valid
			RobotTrajectory trajectory = new RobotTrajectory();
			if (_readFile.get(1).equals("**ONCE**"))		// If the file starts by "ONCE"
				trajectory.set_trajMode(TrajectoryMode.ONCE);
			else if (_readFile.get(1).equals("**LOOP**"))	// OR by "LOOP"
				trajectory.set_trajMode(TrajectoryMode.LOOP);

			for (int i = 2; i < _readFile.size() - 1; i++) {
				DriveCommand dc = getDriveCommandFromString(_readFile.get(i));
				trajectory.addDriveCommand(dc);
			}

			return trajectory;
		}
		return null;
	}

	/**
	 * Static Method to write a traj file
	 * @param p_trajectory The RobotTrajectory to translate into a traj file
	 * @param p_filepath The path where the generated traj file will be written
	 * @return Return the path where the file will be written
	 */
	public static String writeTrajFile(RobotTrajectory p_trajectory, String p_filepath) {

		try {
			if (!p_filepath.endsWith(".traj")) {
				p_filepath += ".traj";
			}
			PrintWriter pw = new PrintWriter(new File(p_filepath));
			pw.write("--BEGIN--\n");
			pw.write("**" + p_trajectory.get_trajMode().toString() + "**\n");
			for (int i = 0; i < p_trajectory.getCommandsListSize(); i++) {
				pw.write(p_trajectory.getCommandAt(i).toStringTraj() + "\n");
			}
			pw.write("--END--");
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return p_filepath;
	}

	/**
	 * Static Method to check if the syntax of the traj file is correct/valid
	 * @param p_filepath The path of the file to check
	 * @return Return TRUE or FALSE if the file is valid or not
	 */
	public static boolean isTrajFileValid(String p_filepath) {

		readFileContent(p_filepath);
		if (_readFile.get(0).equals("--BEGIN--") && _readFile.get(_readFile.size() - 1).equals("--END--")
				&& (_readFile.get(1).equals("**ONCE**") || _readFile.get(1).equals("**LOOP**"))) {
			for (int i = 2; i < _readFile.size() - 1; i++) {
				if (!_readFile.get(i).matches("[f|b|l|r][{][0-9]{1,3}[}][-][>][0-9]+"))
					return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Method to read a file and stores its content into the _readFile attribute
	 * @param p_filepath The path of the file to read
	 */
	private static void readFileContent(String p_filepath) {

		_readFile = new ArrayList<String>();
		try (BufferedReader br = new BufferedReader(new FileReader(p_filepath))) {

			String currentLine = "";
			while ((currentLine = br.readLine()) != null) {
				System.out.println(currentLine);
				_readFile.add(currentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Static Method to translate a string command into a DriveCommand object
	 * @param instruction The command instruction to translate
	 * @return The DriveCommand based on the string instruction parameter
	 */
	private static DriveCommand getDriveCommandFromString(String instruction) {

		Pattern p = Pattern.compile("([f|b|l|r])[{]([0-9]{1,3})[}][-][>]([0-9]+)"); // Check if it is valid with a regular expression
		Matcher m = p.matcher(instruction);
		boolean b = m.matches();
		DriveCommand dc = null;
		if (b) {
			dc = new DriveCommand(m.group(1), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
		}
		

		return dc;
	}

}

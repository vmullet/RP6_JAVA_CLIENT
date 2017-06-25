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

import model.DriveCommand;
import model.RobotTrajectory;
import model.TrajectoryMode;

public final class RobotIO {

	private static ArrayList<String> _readFile = new ArrayList<String>();

	public static ArrayList<String> getLastReadFile() {
		return _readFile;
	}

	public static RobotTrajectory readTrajFile(String p_filepath) {

		if (isTrajFileValid(p_filepath)) {
			RobotTrajectory trajectory = new RobotTrajectory();
			if (_readFile.get(1).equals("**ONCE**"))
				trajectory.set_trajMode(TrajectoryMode.ONCE);
			else if (_readFile.get(1).equals("**LOOP**"))
				trajectory.set_trajMode(TrajectoryMode.LOOP);

			for (int i = 2; i < _readFile.size() - 1; i++) {
				DriveCommand dc = getDriveCommandFromString(_readFile.get(i));
				trajectory.addDriveCommand(dc);
			}

			return trajectory;
		}
		return null;
	}

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

	private static DriveCommand getDriveCommandFromString(String instruction) {

		Pattern p = Pattern.compile("([f|b|l|r])[{]([0-9]{1,3})[}][-][>]([0-9]+)");
		Matcher m = p.matcher(instruction);
		boolean b = m.matches();
		DriveCommand dc = null;
		if (b) {
			dc = new DriveCommand(m.group(1), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));
		}
		

		return dc;
	}

}

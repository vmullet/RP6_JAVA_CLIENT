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
				trajectory.set_trajMode(TrajectoryMode.INFINITE);

			for (int i = 2; i < _readFile.size() - 1; i++) {
				DriveCommand dc = getDriveCommandFromString(_readFile.get(i));
				trajectory.addDriveCommand(dc);
			}

			return trajectory;
		}
		return null;
	}

	public static boolean writeTrajFile(String[] p_content, String p_filepath) {

		boolean written = false;

		try {
			PrintWriter pw = new PrintWriter(new File(p_filepath));
			for (int i = 0; i < p_content.length; i++) {
				pw.write(p_content[i]);
			}
			pw.close();
			written = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			written = false;
			e.printStackTrace();
		}

		return written;
	}

	public static boolean isTrajFileValid(String p_filepath) {

		readFileContent(p_filepath);
		if (_readFile.get(0).equals("--BEGIN--") && _readFile.get(_readFile.size() - 1).equals("--END--")
				&& (_readFile.get(1).equals("**ONCE**") || _readFile.get(1).equals("**LOOP**"))) {
			for (int i = 2; i < _readFile.size() - 1; i++) {
				if (!_readFile.get(i).matches("[f|b|l|r][{][0-9]{1,1}[}][-][>][0-9]+"))
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
				_readFile.add(currentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static DriveCommand getDriveCommandFromString(String instruction) {

		Pattern p = Pattern.compile("([f|b|l|r])[{]([0-9]{1,1})[}][-][>]([0-9]+)");
		Matcher m = p.matcher(instruction);
		boolean b = m.matches();
		DriveCommand dc = new DriveCommand(m.group(1), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)));

		return dc;
	}

}

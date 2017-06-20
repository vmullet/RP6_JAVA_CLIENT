package controller;

public class Launcher {

	public static void main(String[] args) {
		RobotClient rc = new RobotClient();
		rc.buildUI();
		rc.showUI(true);
	}
}

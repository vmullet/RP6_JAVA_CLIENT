package controller;

import view.RobotClientUI_V2;

public class Launcher {

	public static void main(String[] args) {
		if (args.length == 0) {
			RobotClientUI_V2 rc = new RobotClientUI_V2();
			rc.setVisible(true);
		}
		else {
			/* *********************** CONSOLE MODE ***************************** */
			System.out.println(args.length);
			
		}
		
	}
}

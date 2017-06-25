package controller;

import view.RobotClientUI;

public class Launcher {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
	public static void main(String[] args) {
		
		if (args.length == 0) {
			RobotClientUI rc = new RobotClientUI();
			rc.setVisible(true);
		}
		else {
			/* *********************** CONSOLE MODE ***************************** */
			String ascii = "\r\n" + 
					" ________  ________  ________          ________  ___       ___  _______   ________   _________        ___      ___  _____      ________     \r\n" + 
					"|\\   __  \\|\\   __  \\|\\   ____\\        |\\   ____\\|\\  \\     |\\  \\|\\  ___ \\ |\\   ___  \\|\\___   ___\\     |\\  \\    /  /|/ __  \\    |\\   __  \\    \r\n" + 
					"\\ \\  \\|\\  \\ \\  \\|\\  \\ \\  \\___|        \\ \\  \\___|\\ \\  \\    \\ \\  \\ \\   __/|\\ \\  \\\\ \\  \\|___ \\  \\_|     \\ \\  \\  /  / /\\/_|\\  \\   \\ \\  \\|\\  \\   \r\n" + 
					" \\ \\   _  _\\ \\   ____\\ \\  \\____        \\ \\  \\    \\ \\  \\    \\ \\  \\ \\  \\_|/_\\ \\  \\\\ \\  \\   \\ \\  \\       \\ \\  \\/  / /\\|/ \\ \\  \\   \\ \\  \\\\\\  \\  \r\n" + 
					"  \\ \\  \\\\  \\\\ \\  \\___|\\ \\  ___  \\       \\ \\  \\____\\ \\  \\____\\ \\  \\ \\  \\_|\\ \\ \\  \\\\ \\  \\   \\ \\  \\       \\ \\    / /      \\ \\  \\ __\\ \\  \\\\\\  \\ \r\n" + 
					"   \\ \\__\\\\ _\\\\ \\__\\    \\ \\_______\\       \\ \\_______\\ \\_______\\ \\__\\ \\_______\\ \\__\\\\ \\__\\   \\ \\__\\       \\ \\__/ /        \\ \\__\\\\__\\ \\_______\\\r\n" + 
					"    \\|__|\\|__|\\|__|     \\|_______|        \\|_______|\\|_______|\\|__|\\|_______|\\|__| \\|__|    \\|__|        \\|__|/          \\|__\\|__|\\|_______|\r\n" + 
					"                                                                                                                                            \r\n" + 
					"                                                                                                                                            \r\n" + 
					"                                                                                                                                            \r\n" + 
					"";
			
			System.out.println(ANSI_YELLOW + ascii + ANSI_RESET);
			
			
		}
		
	}
}

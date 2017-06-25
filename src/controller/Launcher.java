package controller;

import console.RobotClientConsole;
import view.RobotClientUI;

public class Launcher {

	public static void main(String[] args) {
		
		if (args.length == 0) {
			RobotClientUI rc = new RobotClientUI();
			rc.setVisible(true);
		}
		else {
			/* *********************** CONSOLE MODE ***************************** */
			if (args[0].equals("--console")) {
				RobotClientConsole console = new RobotClientConsole();
				console.startConsole();
			}
			else {
				System.out.println("Commande invalide. Pour entrer en mode console vous devez utiliser --console");
			}
			
			
		}
		
	}
}

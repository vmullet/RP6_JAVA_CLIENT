package controller;

import console.RobotClientConsole;
import view.RobotClientUI;

/**
 * Main class
 * soit le mode graphique ou le mode console
 * @author Valentin
 *
 */
public class Launcher {

	public static void main(String[] args) {
		
		if (args.length == 0) {		// If there's no argument at the execution
			RobotClientUI rc = new RobotClientUI();	// Graphical mode
			rc.setVisible(true);
		}
		else {
			/* *********************** CONSOLE MODE ***************************** */
			if (args[0].equals("--console")) {	// If "--console" is passed at a first argument
				RobotClientConsole console = new RobotClientConsole();	// Console mode
				console.startConsole();
			}
			else {
				System.out.println("Commande invalide. Pour entrer en mode console vous devez utiliser --console");
			}
			
			
		}
		
	}
}

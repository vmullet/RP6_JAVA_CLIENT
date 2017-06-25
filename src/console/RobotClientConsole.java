package console;

import java.util.Date;
import java.util.Scanner;

public class RobotClientConsole {

	private ConsoleState _consoleState;
	private String _userInput;
	Scanner _scanner;
	boolean _stop = false;
	
	private String ascii_title = "\r\n" + 
			" /$$$$$$$  /$$$$$$$   /$$$$$$         /$$$$$$  /$$ /$$                       /$$           /$$    /$$   /$$        /$$$$$$ \r\n" + 
			"| $$__  $$| $$__  $$ /$$__  $$       /$$__  $$| $$|__/                      | $$          | $$   | $$ /$$$$       /$$$_  $$\r\n" + 
			"| $$  \\ $$| $$  \\ $$| $$  \\__/      | $$  \\__/| $$ /$$  /$$$$$$  /$$$$$$$  /$$$$$$        | $$   | $$|_  $$      | $$$$\\ $$\r\n" + 
			"| $$$$$$$/| $$$$$$$/| $$$$$$$       | $$      | $$| $$ /$$__  $$| $$__  $$|_  $$_/        |  $$ / $$/  | $$      | $$ $$ $$\r\n" + 
			"| $$__  $$| $$____/ | $$__  $$      | $$      | $$| $$| $$$$$$$$| $$  \\ $$  | $$           \\  $$ $$/   | $$      | $$\\ $$$$\r\n" + 
			"| $$  \\ $$| $$      | $$  \\ $$      | $$    $$| $$| $$| $$_____/| $$  | $$  | $$ /$$        \\  $$$/    | $$      | $$ \\ $$$\r\n" + 
			"| $$  | $$| $$      |  $$$$$$/      |  $$$$$$/| $$| $$|  $$$$$$$| $$  | $$  |  $$$$/         \\  $/    /$$$$$$ /$$|  $$$$$$/\r\n" + 
			"|__/  |__/|__/       \\______/        \\______/ |__/|__/ \\_______/|__/  |__/   \\___/            \\_/    |______/|__/ \\______/ \r\n" + 
			"                                                                                                                           \r\n" + 
			"";
	
	//#region Colors constants
	
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
	
	//#endregion
	
	public RobotClientConsole() {
		_consoleState = ConsoleState.NONE;
		_userInput = "";
		_scanner = new Scanner(System.in);
		_stop = true;
	}
	
	public void startConsole() {
		_stop = false;
		System.out.println(ANSI_YELLOW + ascii_title + ANSI_RESET);
		writeWithBreak("************************************************** CONSOLE MODE ********************************************************",2);
		writeWithBreak("RP6 Client V1.0 Démarré [" + new Date() + "]",2);
		writeWithBreak("Pour obtenir la liste complète des commandes, vous pouvez utiliser la commande .help", 2);
		while(!_stop) {
			waitUser();
		}
		
	}
	
	public void displayHelp() {
		writeWithBreak("", 1);
		writeWithBreak("LISTE DES COMMANDES :", 2);
		writeWithBreak("1: connect XXX.XXX.XXX.XXX	|	Permet de se connecter au RP6 où XXX.XXX.XXX.XXX",2);
		writeWithBreak("------------------------------- COMMANDES FONCTIONNANT UNIQUEMENT SI L'ON EST CONNECTE  ---------------------------------------",2);
		writeWithBreak("2: sensors -XXX			|	Permet d'afficher les valeurs des différents capteurs où X est l'identifiant d'un capteur",1);
		writeWithBreak("				-all  : Affiche tous les capteurs",1);
		writeWithBreak("				-lspd : Capteur de vitesse gauche",1);
		writeWithBreak("				-rspd : Capteur de vitesse droit",1);
		writeWithBreak("				-other : Capteur de vitesse gauche",1);
	}
	
	private void write(String text) {
		System.out.print(text);
	}
	
	private void writeWithBreak(String text,int nbbreak) {
		System.out.print(text);
		for (int i = 0 ; i < nbbreak;i++) {
			System.out.println("");
		}
	}
	
	private void waitUser() {
		write("> ");
		_userInput = _scanner.next();
		if (_userInput.equals(".help")) {
			displayHelp();
		}
		else {
			
		}
			
	}
	
}

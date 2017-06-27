package console;

import java.util.Date;
import java.util.Scanner;

import controller.RobotClient;

public class RobotClientConsole {

	private ConsoleState _consoleState;
	private String _userInput;
	private Scanner _scanner;
	private RobotClient _client;
	private boolean _stop = false;

	private String ascii_title = "\r\n"
			+ " /$$$$$$$  /$$$$$$$   /$$$$$$         /$$$$$$  /$$ /$$                       /$$           /$$    /$$   /$$        /$$$$$$ \r\n"
			+ "| $$__  $$| $$__  $$ /$$__  $$       /$$__  $$| $$|__/                      | $$          | $$   | $$ /$$$$       /$$$_  $$\r\n"
			+ "| $$  \\ $$| $$  \\ $$| $$  \\__/      | $$  \\__/| $$ /$$  /$$$$$$  /$$$$$$$  /$$$$$$        | $$   | $$|_  $$      | $$$$\\ $$\r\n"
			+ "| $$$$$$$/| $$$$$$$/| $$$$$$$       | $$      | $$| $$ /$$__  $$| $$__  $$|_  $$_/        |  $$ / $$/  | $$      | $$ $$ $$\r\n"
			+ "| $$__  $$| $$____/ | $$__  $$      | $$      | $$| $$| $$$$$$$$| $$  \\ $$  | $$           \\  $$ $$/   | $$      | $$\\ $$$$\r\n"
			+ "| $$  \\ $$| $$      | $$  \\ $$      | $$    $$| $$| $$| $$_____/| $$  | $$  | $$ /$$        \\  $$$/    | $$      | $$ \\ $$$\r\n"
			+ "| $$  | $$| $$      |  $$$$$$/      |  $$$$$$/| $$| $$|  $$$$$$$| $$  | $$  |  $$$$/         \\  $/    /$$$$$$ /$$|  $$$$$$/\r\n"
			+ "|__/  |__/|__/       \\______/        \\______/ |__/|__/ \\_______/|__/  |__/   \\___/            \\_/    |______/|__/ \\______/ \r\n"
			+ "                                                                                                                           \r\n"
			+ "";

	// #region Colors constants

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

	// #endregion

	public RobotClientConsole() {
		_consoleState = ConsoleState.NONE;
		_userInput = "";
		_scanner = new Scanner(System.in);
		_client = new RobotClient();
		_stop = true;
	}

	public void startConsole() {
		_stop = false;
		System.out.println(ANSI_YELLOW + ascii_title + ANSI_RESET);
		writeWithBreak(
				"************************************************** CONSOLE MODE ********************************************************",
				2);
		writeWithBreak("RP6 Client V1.0 Démarré [" + new Date() + "]", 2);
		writeWithBreak("Pour obtenir la liste complète des commandes, vous pouvez utiliser la commande .help", 2);
		while (!_stop) {
			startStateMachine();
		}

	}

	private void startStateMachine() {
		waitUser();
		switch (_userInput) {

		case "connect":
			displayConnectForm();
			break;

		case "disconnect":
			if (_client.is_connected())
				_client.closeConnection();
			else {
				writeWithBreak("Vous n'êtes connecté à aucun robot RP6", 1);
			}
			break;

		case ".help":
			displayHelp();
			break;

		case "quit":
			_stop = true;
			if (_client.is_connected())
				_client.closeConnection();
			writeWithBreak("Fermeture de RP6 Client...", 1);
			writeWithBreak("RP6 Client fermé", 1);
			break;

		default:
			writeWithBreak("Commande introuvable. Veuillez utiliser la commande .help si vous avez besoin d'aide", 1);
			break;

		}

	}

	public void displayHelp() {
		writeWithBreak("", 1);
		writeWithBreak("LISTE DES COMMANDES :", 2);
		writeWithBreak("1: connect		|	Permet de se connecter au RP6", 2);
		writeWithBreak("2: disconnect		|	Permet de se déconnecter du RP6", 2);
		writeWithBreak(
				"------------------------------- COMMANDES FONCTIONNANT UNIQUEMENT SI L'ON EST CONNECTE  ---------------------------------------",
				2);
		writeWithBreak("3: Contrôle du robot", 2);
		writeWithBreak("[f|b|l|r][0-9]+		|	Syntaxe d'une commande pour piloter le robot", 1);
		writeWithBreak("f,b,l,r			|	Commandes directionnelles (f = forward , b = backward, l = left, r = right",
				1);
		writeWithBreak("[0-9]+			|	Vitesse désirée pour la commande (entier entre 1 et 160 : 160 -> 40cm/s)",
				2);
		writeWithBreak(
				"4: sensors -XXX		|	Permet d'afficher les valeurs des différents capteurs où X est l'identifiant d'un capteur",
				1);
		writeWithBreak("			-all  : Affiche tous les capteurs", 1);
		writeWithBreak("			-bp  :  Affiche le pourcentage restant de batterie", 1);
		writeWithBreak("			-lspd : Capteur de vitesse gauche", 1);
		writeWithBreak("			-rspd : Capteur de vitesse droit", 1);
		writeWithBreak("			-dsl : Vitesse désirée côté gauche", 1);
		writeWithBreak("			-dsr : Vitesse désirée côté droit", 1);
		writeWithBreak("			-dsl : Capteur de vitesse droit", 1);
		writeWithBreak("			-pl :  Force exercée côté gauche", 1);
		writeWithBreak("			-pr :  Force exercée côté droit", 1);
		writeWithBreak("			-mcl : Puissance moteur côté gauche", 1);
		writeWithBreak("			-mcr : Puissance moteur côté droit", 1);
		writeWithBreak("			-dl :  Distance parcourue côté gauche", 1);
		writeWithBreak("			-dr :  Distance parcourue côté droit", 1);
		writeWithBreak("			-lsl : Capteur de lumière côté gauche", 1);
		writeWithBreak("			-lsr : Capteur de lumière côté droit", 1);
		writeWithBreak("			-td :  Distance totale parcourue", 1);
		writeWithBreak("			-spd : Vitesse actuelle du robot en cm/s", 2);
		writeWithBreak(
				"----------------------------------------------------------------------------------------------------------------------------",
				1);
	}

	private void write(String text) {
		System.out.print(text);
	}

	private void writeWithBreak(String text, int nbbreak) {
		System.out.print(text);
		for (int i = 0; i < nbbreak; i++) {
			System.out.println("");
		}
	}

	private void waitUser() {
		write("> ");
		_userInput = _scanner.next();
	}
	
	private void displayConnectForm() {
		String adr_ip = "";
		int port = -1;
		write("Veuillez saisir l'adresse IP du RP6:");
		adr_ip = _scanner.next();
		write("Veuillez saisir votre port de connexion:");
		port = _scanner.nextInt();
		_client.openConnection(adr_ip, port);
		while(_client.is_connecting()) {
			
		}
		if (_client.is_connected()) {
			writeWithBreak("Connecté au Robot RP6 à l'adresse " + adr_ip + ":" + port, 1);
		}
		else {
			writeWithBreak("Echec lors de la connexion. Veuillez vérifier les informations saisies ou que le RP6 est bien connecté à votre réseau", 1);
		}
		
	}

}

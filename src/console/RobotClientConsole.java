package console;

import java.util.Date;
import java.util.Scanner;

import controller.RobotClient;
import controller.RobotIO;
import model.RobotTrajectory;

/**
 * Class which defines the console mode of the RP6 Client
 * @author Valentin
 *
 */
public class RobotClientConsole {

	/**
	 * Attribute to control the actual state of the console mode
	 */
	private ConsoleState _consoleState;
	
	/**
	 * Attribute to store the actual input of the user
	 */
	private String _userInput;
	
	/**
	 * Attribute to store the scanner which will wait/read the user input
	 */
	private Scanner _scanner;
	
	/**
	 * Attribute to hold the robot client to interact with the robot
	 */
	private RobotClient _client;
	
	/**
	 * Attribute to hold the trajectory which can be loaded by the appropriate command
	 */
	private RobotTrajectory _loadedTrajectory;
	
	/**
	 * Attribute to hold the Ascii title displayed at start
	 */
	private static String ascii_title = "\r\n"
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

	/**
	 * Static attribute to end an ANSI Tag for the color / background property
	 */
	public static final String ANSI_RESET = "\u001B[0m";
	
	/**
	 * Static attribute for the black color in ANSI
	 */
	public static final String ANSI_BLACK = "\u001B[30m";
	
	/**
	 * Static attribute for the red color in ANSI
	 */
	public static final String ANSI_RED = "\u001B[31m";
	
	/**
	 * Static attribute for the green color in ANSI
	 */
	public static final String ANSI_GREEN = "\u001B[32m";
	
	/**
	 * Static attribute for the yellow color in ANSI
	 */
	public static final String ANSI_YELLOW = "\u001B[33m";
	
	/**
	 * Static attribute for the blue color in ANSI
	 */
	public static final String ANSI_BLUE = "\u001B[34m";
	
	/**
	 * Static attribute for the purple color in ANSI
	 */
	public static final String ANSI_PURPLE = "\u001B[35m";
	
	/**
	 * Static attribute for the cyan color in ANSI
	 */
	public static final String ANSI_CYAN = "\u001B[36m";
	
	/**
	 * Static attribute for the white color in ANSI
	 */
	public static final String ANSI_WHITE = "\u001B[37m";

	/**
	 * Static attribute for the black background color in ANSI
	 */
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	
	/**
	 * Static attribute for the red background color in ANSI
	 */
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	
	/**
	 * Static attribute for the green background color in ANSI
	 */
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	
	/**
	 * Static attribute for the yellow background color in ANSI
	 */
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	
	/**
	 * Static attribute for the blue background color in ANSI
	 */
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	
	/**
	 * Static attribute for the purple background color in ANSI
	 */
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	
	/**
	 * Static attribute for the cyan background color in ANSI
	 */
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	
	/**
	 * Static attribute for the white background color in ANSI
	 */
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// #endregion

	/**
	 * Default constructor
	 */
	public RobotClientConsole() {
		_consoleState = ConsoleState.NONE;
		_userInput = "";
		_scanner = new Scanner(System.in);;
		_client = new RobotClient();
		_loadedTrajectory = null;
	}
	
	/**
	 * Method to display text on the console without break line (shorter than System.out)
	 * @param text Text to be displayed on the console without automatic break
	 */
	private void write(String text) {
		System.out.print(text);
	}

	/**
	 * Method to display text on the console with break lines
	 * @param text Text to be displayed on the console without automatic break line
	 * @param nbbreak Number of breaks after this line
	 */
	private void writeWithBreak(String text, int nbbreak) {
		System.out.print(text);
		for (int i = 0; i < nbbreak; i++) {
			System.out.println("");
		}
	}

	/**
	 * Method to wait for the user input in DEFAULT mode
	 */
	private void waitUser() {
		write("> ");
		_userInput = _scanner.nextLine();
	}
	
	/**
	 * Method to wait for the user input in COMMAND mode
	 */
	private void waitDrive() {
		write("[ "+ _client.get_robotIP() + ":" + _client.get_robotPort() +" ]>>> ");
		_userInput = _scanner.nextLine();
	}

	/**
	 * Method to start the console and a loop based on a console state
	 */
	public void startConsole() {
		
		writeWithBreak(ANSI_YELLOW + ascii_title + ANSI_RESET,1);	// Title ASCII
		writeWithBreak(
				"************************************************** CONSOLE MODE ********************************************************",
				2);
		writeWithBreak("RP6 Client V1.0 Démarré [" + new Date() + "]", 2);
		writeWithBreak("Pour obtenir la liste complète des commandes, vous pouvez utiliser la commande .help", 2);
		
		_consoleState = ConsoleState.DEFAULT; // Initiate Console State
		
		while (_consoleState != ConsoleState.NONE) {
			
			switch(_consoleState) {
			case NONE:
				break;
			case DEFAULT:
				startDefaultMode();
				break;
			case COMMAND:
				startCommandMode();
				break;
			case WAIT:
				// Useful empty state when waiting for Threads
				break;
				
			};
			
		}

	}

	/**
	 * Method to start the default mode and which will loop in DEFAULT State
	 */
	private void startDefaultMode() {
		waitUser();
		switch (_userInput) {

		case "connect":
			if (_client.is_connected()) {
				writeWithBreak("Vous êtes déjà connecté à l'adresse " + _client.get_robotIP(), 1);
			}
			else {
				displayConnectForm();
			}
			break;

		case "disconnect":
			if (_client.is_connected()) {
				_client.closeConnection();
			}	
			else {
				writeWithBreak("Vous n'êtes connecté à aucun robot RP6", 1);
			}
			break;
			
		case "command":
			if (_client.is_connected()) {
				writeWithBreak("Bienvenue dans le mode de pilotage manuel du RP6", 1);
				writeWithBreak("Veuillez saisir une commande compatible avec le mode 'command'. Vous pouvez utiliser la commande .help si besoin", 1);
				_consoleState = ConsoleState.COMMAND; // The command will start on next loop
			}
			else {
				writeWithBreak("Veuillez d'abord vous connecter au RP6 via la commande 'connect'", 1);
			}
			break;

		case ".help":
			displayHelp();
			break;

		case "quit":
			_consoleState = ConsoleState.NONE;
			if (_client.is_connected()) {
				_client.send("quit");
				_client.closeConnection();
			}
				
			writeWithBreak("Fermeture de RP6 Client...", 1);
			writeWithBreak("RP6 Client fermé", 1);
			break;

		default:
			writeWithBreak("Commande introuvable. Veuillez utiliser la commande .help si vous avez besoin d'aide", 1);
			break;

		}

	}
	
	/**
	 * Method to start the command Mode and which will loop in COMMAND State
	 */
	private void startCommandMode() {
		waitDrive();
		String speed = "";
		switch(_userInput) {
		
		case "f":
			writeWithBreak("Veuillez saisir une vitesse entre 1 et 160",1);
			speed = _scanner.nextLine();
			_client.send("f\n" + speed);
			break;
			
		case "b":
			writeWithBreak("Veuillez saisir une vitesse entre 1 et 160",1);
			speed = _scanner.nextLine();
			_client.send("b\n" + speed);
			break;
			
		case "l":
			writeWithBreak("Veuillez saisir une vitesse entre 1 et 160",1);
			speed = _scanner.nextLine();
			_client.send("l\n" + speed);
			break;
			
		case "r":
			writeWithBreak("Veuillez saisir une vitesse entre 1 et 160",1);
			speed = _scanner.nextLine();
			_client.send("r\n" + speed);
			break;
		
		case "stop":
			_client.send("stp");
			break;
			
		case "sensors":
			displaySensorsForm();
			break;
			
		case "exec-traj":
			writeWithBreak("Veuillez indiquer l'emplacement du fichier .traj à exécuter", 1);
			String traj_file = _scanner.nextLine();
			_loadedTrajectory = RobotIO.readTrajFile(traj_file);
			if (_loadedTrajectory != null) {
				_loadedTrajectory.startAutoPilot();
			}
			else {
				writeWithBreak("Fichier .traj invalide. Veuillez vérifier que le fichier est correct.", 1);
			}
			
			break;
			
		case "cancel-traj":
			if (_loadedTrajectory != null && _loadedTrajectory.is_running()) {
				_loadedTrajectory.stopAutoPilot();
				writeWithBreak("Demande d'arrêt de l'autopilote prise en compte. Veuillez patienter quelques secondes", 1);
			}
			else {
				writeWithBreak("Aucun fichier .traj en cours d'exécution", 1);
			}
				
			break;
			
		case ".help":
			displayHelp();
			break;
		
		case "quit":
			_client.send("stp");
			writeWithBreak("Vous avez quitté le mode de pilotage", 1);
			_consoleState = ConsoleState.DEFAULT;
			break;
			
		default:
			writeWithBreak("Commande introuvable. Veuillez utiliser la commande .help si vous avez besoin d'aide", 1);
			break;
		}
	}
	
	
	/**
	 * Method to display sensors value in the console
	 */
	private void displaySensorsForm() {
		writeWithBreak("Veuillez indiquer l'identifiant du/des capteurs souhaités", 1);
		waitDrive();
		switch(_userInput) {
			case "all":
				writeWithBreak("Niveau de batterie : " + _client.get_data().get_batteryPower(),1);
				writeWithBreak("Capteur de vitesse gauche : " + _client.get_data().get_speedLeft(),1);
				writeWithBreak("Capteur de vitesse droit : " + _client.get_data().get_speedRight(),1);
				writeWithBreak("Vitesse désirée côté gauche : " + _client.get_data().get_desiredSpeedLeft(),1);
				writeWithBreak("Vitesse désirée côté droit : " + _client.get_data().get_desiredSpeedRight(),1);
				writeWithBreak("Force exercée côté gauche : " + _client.get_data().get_powerLeft(),1);
				writeWithBreak("Force exercée côté droit : " + _client.get_data().get_powerRight(),1);
				writeWithBreak("Puissance moteur côté gauche : " + _client.get_data().get_motorCurrentLeft(),1);
				writeWithBreak("Puissance moteur côté droit : " + _client.get_data().get_motorCurrentRight(),1);
				writeWithBreak("Distance parcourue côté gauche : " + _client.get_data().get_distanceLeft(),1);
				writeWithBreak("Distance parcourue côté droit : " + _client.get_data().get_distanceRight(),1);
				writeWithBreak("Capteur de lumière côté gauchee : " + _client.get_data().get_lightSensorLeft(),1);
				writeWithBreak("Capteur de lumière côté droit : " + _client.get_data().get_lightSensorRight(),1);
				writeWithBreak("Distance totale parcourue (en mètres) : " + _client.get_data().getTotalDistanceMeter(),1);
				writeWithBreak("Vitesse du robot (en cm/s) : " + _client.get_data().getSpeedPerSecond(),1);
				break;
			case "bp":
				writeWithBreak("Niveau de batterie : " + _client.get_data().get_batteryPower(),1);
				break;
			case "lspd":
				writeWithBreak("Capteur de vitesse gauche : " + _client.get_data().get_speedLeft(),1);
				break;
			case "rspd":
				writeWithBreak("Capteur de vitesse droit : " + _client.get_data().get_speedRight(),1);
				break;
			case "dsl":
				writeWithBreak("Vitesse désirée côté gauche : " + _client.get_data().get_desiredSpeedLeft(),1);
				break;
			case "dsr":
				writeWithBreak("Vitesse désirée côté droit : " + _client.get_data().get_desiredSpeedRight(),1);
				break;
			case "pl":
				writeWithBreak("Force exercée côté gauche : " + _client.get_data().get_powerLeft(),1);
				break;
			case "pr":
				writeWithBreak("Force exercée côté droit : " + _client.get_data().get_powerRight(),1);
				break;
			case "mcl":
				writeWithBreak("Puissance moteur côté gauche : " + _client.get_data().get_motorCurrentLeft(),1);
				break;
			case "mcr":
				writeWithBreak("Puissance moteur côté droit : " + _client.get_data().get_motorCurrentRight(),1);
				break;
			case "dl":
				writeWithBreak("Distance parcourue côté gauche : " + _client.get_data().get_distanceLeft(),1);
				break;
			case "dr":
				writeWithBreak("Distance parcourue côté droit : " + _client.get_data().get_distanceRight(),1);
				break;
			case "lsl":
				writeWithBreak("Capteur de lumière côté gauchee : " + _client.get_data().get_lightSensorLeft(),1);
				break;
			case "lsr":
				writeWithBreak("Capteur de lumière côté droit : " + _client.get_data().get_lightSensorRight(),1);
				break;
			case "td":
				writeWithBreak("Distance totale parcourue (en mètres) : " + _client.get_data().getTotalDistanceMeter(),1);
				break;
			case "spd":
				writeWithBreak("Vitesse du robot (en cm/s) : " + _client.get_data().getSpeedPerSecond(),1);
				break;
		}
	}
	
	
	/**
	 * Method to display the help / console manual
	 */
	private void displayHelp() {
		writeWithBreak("", 1);
		writeWithBreak("LISTE DES COMMANDES :", 2);
		writeWithBreak("1: connect		|	Permet de se connecter au RP6", 2);
		writeWithBreak("2: disconnect		|	Permet de se déconnecter du RP6", 2);
		writeWithBreak("3: command		|	Permet d'entrer en mode 'command' pour contrôler le RP6", 2);
		writeWithBreak("4: quit			|	Permet de fermer le client", 2);
		
		writeWithBreak(
				"------------------------------- COMMANDES FONCTIONNANT UNIQUEMENT SI L'ON EST EN MODE COMMAND  ---------------------------------------",
				2);
		writeWithBreak("5: Contrôle du robot", 2);
		writeWithBreak("[f|b|l|r][0-9]+		|	Syntaxe d'une commande pour piloter le robot", 1);
		writeWithBreak("f,b,l,r			|	Commandes directionnelles (f = forward , b = backward, l = left, r = right",
				1);
		writeWithBreak("[0-9]+			|	Vitesse désirée pour la commande (entier entre 1 et 160 : 160 -> 40cm/s)",
				2);
		writeWithBreak("stop			|	Permet de stopper le robot",
				2);
		writeWithBreak("quit			|	Permet de quitter le mode 'command'",2);
		writeWithBreak(
				"sensors			|	Permet d'afficher les valeurs des différents capteurs",
				1);
		writeWithBreak("			-all  : Affiche tous les capteurs", 1);
		writeWithBreak("			-bp  :  Affiche le pourcentage restant de batterie", 1);
		writeWithBreak("			-lspd : Capteur de vitesse gauche", 1);
		writeWithBreak("			-rspd : Capteur de vitesse droit", 1);
		writeWithBreak("			-dsl : Vitesse désirée côté gauche", 1);
		writeWithBreak("			-dsr : Vitesse désirée côté droit", 1);
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

	
	/**
	 * Method to display the connect process when user enters the "connect" command
	 */
	private void displayConnectForm() {
		String adr_ip = "";
		int port = -1;
		write("Veuillez saisir l'adresse IP du RP6: ");
		adr_ip = _scanner.next();
		write("Veuillez saisir votre port de connexion: ");
		port = _scanner.nextInt();
		_client.openConnection(adr_ip, port);
		write("Connexion en cours à " + adr_ip + ":" + port + "\n");
		_consoleState = ConsoleState.WAIT; // Empty state to wait until the thread has determined if the connection was possible or not
		Thread t1 = new Thread() {
			@Override
			public void run() {
				while(_client.is_connecting()) {
					write(".");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		
				if (_client.is_connected()) {
					writeWithBreak("\nConnecté au Robot RP6 à l'adresse " + _client.get_robotIP(), 1);
				}
				else {
					writeWithBreak("\nEchec lors de la connexion. Veuillez vérifier les informations saisies ou que le RP6 est bien connecté à votre réseau", 1);
				}
				_consoleState = ConsoleState.DEFAULT; // Return to DEFAULT state
			}
		};
		t1.start();
		
	}
	

}

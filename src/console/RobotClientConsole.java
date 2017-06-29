package console;

import java.util.Date;
import java.util.Scanner;

import controller.RobotClient;
import controller.RobotIO;
import model.RobotTrajectory;

public class RobotClientConsole {

	private ConsoleState _consoleState;
	private String _userInput;
	private Scanner _scanner;
	private RobotClient _client;
	private RobotTrajectory _loadedTrajectory;
	private boolean _stop = false;

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
		_scanner = null;
		_client = new RobotClient();
		_loadedTrajectory = null;
		_stop = true;
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
		_scanner = new Scanner(System.in);
		write("> ");
		_userInput = _scanner.nextLine();
	}
	
	private void waitDrive() {
		write(">>> ");
		_userInput = _scanner.nextLine();
	}

	public void startConsole() {
		_stop = false;
		System.out.println(ANSI_YELLOW + ascii_title + ANSI_RESET);
		writeWithBreak(
				"************************************************** CONSOLE MODE ********************************************************",
				2);
		writeWithBreak("RP6 Client V1.0 D�marr� [" + new Date() + "]", 2);
		writeWithBreak("Pour obtenir la liste compl�te des commandes, vous pouvez utiliser la commande .help", 2);
		_consoleState = ConsoleState.RUNNING;
		while (!_stop) {
			
			switch(_consoleState) {
			case NONE:
				break;
			case RUNNING:
				startStateMachine();
				break;
			case DRIVING:
				startDriveMachine();
				break;
			case WAIT:
				break;
				
			};
			
		}

	}

	private void startStateMachine() {
		waitUser();
		switch (_userInput) {

		case "connect":
			if (_client.is_connected()) {
				writeWithBreak("Vous �tes d�j� connect� � l'adresse " + _client.get_robotIP(), 1);
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
				writeWithBreak("Vous n'�tes connect� � aucun robot RP6", 1);
			}
			break;
			
		case "command":
			if (_client.is_connected()) {
				writeWithBreak("Bienvenue dans le mode de pilotage manuel du RP6", 1);
				writeWithBreak("Veuillez saisir une commande compatible avec le mode 'command'. Vous pouvez utiliser la commande .help si besoin", 1);
				_consoleState = ConsoleState.DRIVING;
			}
			else {
				writeWithBreak("Veuillez d'abord vous connecter au RP6 via la commande 'connect'", 1);
			}
			break;

		case ".help":
			displayHelp();
			break;

		case "quit":
			_stop = true;
			if (_client.is_connected()) {
				_client.send("quit");
				_client.closeConnection();
			}
				
			writeWithBreak("Fermeture de RP6 Client...", 1);
			writeWithBreak("RP6 Client ferm�", 1);
			break;

		default:
			writeWithBreak("Commande introuvable. Veuillez utiliser la commande .help si vous avez besoin d'aide", 1);
			break;

		}

	}
	
	private void startDriveMachine() {
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
			writeWithBreak("Veuillez indiquer l'emplacement du fichier .traj � ex�cuter", 1);
			String traj_file = _scanner.nextLine();
			_loadedTrajectory = RobotIO.readTrajFile(traj_file);
			if (_loadedTrajectory != null) {
				_loadedTrajectory.startAutoPilot();
			}
			else {
				writeWithBreak("Fichier .traj valide. Veuillez v�rifier que le fichier est correct.", 1);
			}
			
			break;
			
		case "cancel-traj":
			if (_loadedTrajectory != null && _loadedTrajectory.is_running()) {
				_loadedTrajectory.stopAutoPilot();
				writeWithBreak("Demande d'arr�t de l'autopilote prise en compte. Veuillez patienter quelques secondes", 1);
			}
			else {
				writeWithBreak("Aucun fichier .traj en cours d'ex�cution", 1);
			}
				
			break;
			
		case ".help":
			displayHelp();
			break;
		
		case "quit":
			_client.send("stp");
			writeWithBreak("Vous avez quitt� le mode de pilotage", 1);
			_consoleState = ConsoleState.RUNNING;
			break;
			
		default:
			writeWithBreak("Commande introuvable. Veuillez utiliser la commande .help si vous avez besoin d'aide", 1);
			break;
		}
	}
	
	
	
	private void displaySensorsForm() {
		writeWithBreak("Veuillez indiquer l'identifiant du/des capteurs souhait�s", 1);
		waitDrive();
		switch(_userInput) {
			case "all":
				writeWithBreak("Niveau de batterie : " + _client.get_data().get_batteryPower(),1);
				writeWithBreak("Capteur de vitesse gauche : " + _client.get_data().get_speedLeft(),1);
				writeWithBreak("Capteur de vitesse droit : " + _client.get_data().get_speedRight(),1);
				writeWithBreak("Vitesse d�sir�e c�t� gauche : " + _client.get_data().get_desiredSpeedLeft(),1);
				writeWithBreak("Vitesse d�sir�e c�t� droit : " + _client.get_data().get_desiredSpeedRight(),1);
				writeWithBreak("Force exerc�e c�t� gauche : " + _client.get_data().get_powerLeft(),1);
				writeWithBreak("Force exerc�e c�t� droit : " + _client.get_data().get_powerRight(),1);
				writeWithBreak("Puissance moteur c�t� gauche : " + _client.get_data().get_motorCurrentLeft(),1);
				writeWithBreak("Puissance moteur c�t� droit : " + _client.get_data().get_motorCurrentRight(),1);
				writeWithBreak("Distance parcourue c�t� gauche : " + _client.get_data().get_distanceLeft(),1);
				writeWithBreak("Distance parcourue c�t� droit : " + _client.get_data().get_distanceRight(),1);
				writeWithBreak("Capteur de lumi�re c�t� gauchee : " + _client.get_data().get_lightSensorLeft(),1);
				writeWithBreak("Capteur de lumi�re c�t� droit : " + _client.get_data().get_lightSensorRight(),1);
				writeWithBreak("Distance totale parcourue (en m�tres) : " + _client.get_data().getTotalDistanceMeter(),1);
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
				writeWithBreak("Vitesse d�sir�e c�t� gauche : " + _client.get_data().get_desiredSpeedLeft(),1);
				break;
			case "dsr":
				writeWithBreak("Vitesse d�sir�e c�t� droit : " + _client.get_data().get_desiredSpeedRight(),1);
				break;
			case "pl":
				writeWithBreak("Force exerc�e c�t� gauche : " + _client.get_data().get_powerLeft(),1);
				break;
			case "pr":
				writeWithBreak("Force exerc�e c�t� droit : " + _client.get_data().get_powerRight(),1);
				break;
			case "mcl":
				writeWithBreak("Puissance moteur c�t� gauche : " + _client.get_data().get_motorCurrentLeft(),1);
				break;
			case "mcr":
				writeWithBreak("Puissance moteur c�t� droit : " + _client.get_data().get_motorCurrentRight(),1);
				break;
			case "dl":
				writeWithBreak("Distance parcourue c�t� gauche : " + _client.get_data().get_distanceLeft(),1);
				break;
			case "dr":
				writeWithBreak("Distance parcourue c�t� droit : " + _client.get_data().get_distanceRight(),1);
				break;
			case "lsl":
				writeWithBreak("Capteur de lumi�re c�t� gauchee : " + _client.get_data().get_lightSensorLeft(),1);
				break;
			case "lsr":
				writeWithBreak("Capteur de lumi�re c�t� droit : " + _client.get_data().get_lightSensorRight(),1);
				break;
			case "td":
				writeWithBreak("Distance totale parcourue (en m�tres) : " + _client.get_data().getTotalDistanceMeter(),1);
				break;
			case "spd":
				writeWithBreak("Vitesse du robot (en cm/s) : " + _client.get_data().getSpeedPerSecond(),1);
				break;
		}
	}
	
	
	private void displayHelp() {
		writeWithBreak("", 1);
		writeWithBreak("LISTE DES COMMANDES :", 2);
		writeWithBreak("1: connect		|	Permet de se connecter au RP6", 2);
		writeWithBreak("2: disconnect		|	Permet de se d�connecter du RP6", 2);
		writeWithBreak("3: command		|	Permet d'entrer en mode 'command' pour contr�ler le RP6", 2);
		writeWithBreak("4: quit			|	Permet de fermer le client", 2);
		
		writeWithBreak(
				"------------------------------- COMMANDES FONCTIONNANT UNIQUEMENT SI L'ON EST EN MODE COMMAND  ---------------------------------------",
				2);
		writeWithBreak("4: Contr�le du robot", 2);
		writeWithBreak("[f|b|l|r][0-9]+		|	Syntaxe d'une commande pour piloter le robot", 1);
		writeWithBreak("f,b,l,r			|	Commandes directionnelles (f = forward , b = backward, l = left, r = right",
				1);
		writeWithBreak("[0-9]+			|	Vitesse d�sir�e pour la commande (entier entre 1 et 160 : 160 -> 40cm/s)",
				2);
		writeWithBreak("stop			|	Permet de stopper le robot",
				2);
		writeWithBreak("quit			|	Permet de quitter le mode 'command'",2);
		writeWithBreak(
				"sensors			|	Permet d'afficher les valeurs des diff�rents capteurs",
				1);
		writeWithBreak("			-all  : Affiche tous les capteurs", 1);
		writeWithBreak("			-bp  :  Affiche le pourcentage restant de batterie", 1);
		writeWithBreak("			-lspd : Capteur de vitesse gauche", 1);
		writeWithBreak("			-rspd : Capteur de vitesse droit", 1);
		writeWithBreak("			-dsl : Vitesse d�sir�e c�t� gauche", 1);
		writeWithBreak("			-dsr : Vitesse d�sir�e c�t� droit", 1);
		writeWithBreak("			-pl :  Force exerc�e c�t� gauche", 1);
		writeWithBreak("			-pr :  Force exerc�e c�t� droit", 1);
		writeWithBreak("			-mcl : Puissance moteur c�t� gauche", 1);
		writeWithBreak("			-mcr : Puissance moteur c�t� droit", 1);
		writeWithBreak("			-dl :  Distance parcourue c�t� gauche", 1);
		writeWithBreak("			-dr :  Distance parcourue c�t� droit", 1);
		writeWithBreak("			-lsl : Capteur de lumi�re c�t� gauche", 1);
		writeWithBreak("			-lsr : Capteur de lumi�re c�t� droit", 1);
		writeWithBreak("			-td :  Distance totale parcourue", 1);
		writeWithBreak("			-spd : Vitesse actuelle du robot en cm/s", 2);
		writeWithBreak(
				"----------------------------------------------------------------------------------------------------------------------------",
				1);
	}

	
	
	private void displayConnectForm() {
		String adr_ip = "";
		int port = -1;
		write("Veuillez saisir l'adresse IP du RP6: ");
		adr_ip = _scanner.next();
		write("Veuillez saisir votre port de connexion: ");
		port = _scanner.nextInt();
		_client.openConnection(adr_ip, port);
		write("Connexion en cours � " + adr_ip + ":" + port + "\n");
		_consoleState = ConsoleState.WAIT;
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
					writeWithBreak("\nConnect� au Robot RP6 � l'adresse " + _client.get_robotIP(), 1);
				}
				else {
					writeWithBreak("\nEchec lors de la connexion. Veuillez v�rifier les informations saisies ou que le RP6 est bien connect� � votre r�seau", 1);
				}
				_consoleState = ConsoleState.RUNNING;
			}
		};
		t1.start();
		
	}
	

}

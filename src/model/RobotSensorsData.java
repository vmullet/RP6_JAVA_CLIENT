package model;

/**
 * Class used to store and update the data sent by the robot
 * everytime new ones are received
 * from the RP6 robot
 * @author Valentin
 *
 */
public class RobotSensorsData {

	/**
	 * Attribute to hold the actual battery power
	 */
	private int _batteryPower;
	
	/**
	 * Attribute to hold the desired speed left
	 */
	private int _desiredSpeedLeft;
	
	/**
	 * Attribute hold the desired speed right
	 */
	private int _desiredSpeedRight;
	
	/**
	 * Attribute to hold the power applied on left side
	 */
	private int _powerLeft;
	
	/**
	 * Attribute to hold the power applied on right side
	 */
	private int _powerRight;
	
	/**
	 * Attribute to calculate the speed on left side
	 */
	private int _speedLeft;
	
	/**
	 * Attribute to calculate the speed on right side
	 */
	private int _speedRight;
	
	/**
	 * Attribute to hold the current value of the motor on the left side
	 */
	private int _motorCurrentLeft;
	
	/**
	 * Attribute to hold the current value of the motor on the right side
	 */
	private int _motorCurrentRight;
	
	/**
	 * Attribute to hold the distance travelled on left side
	 */
	private float _distanceLeft;
	
	/**
	 * Attribute ot hold the distance travelled on right side
	 */
	private float _distanceRight;
	
	/**
	 * Attribute to hold the light sensor value on left side
	 */
	private int _lightSensorLeft;
	
	/**
	 * Attribute to hold the light sensor value on right side
	 */
	private int _lightSensorRight;
	
	/**
	 * Attribute to hold the total distance travelled
	 */
	private float _totalDistance;
	
	/**
	 * Attribute to hold the previous distance sent by the robot (to calculate the delta)
	 */
	private float _previousDistance = 0;	// Previous distance sent by captors (used to calculate the delta)
	
	/**
	 * Maximum value when the battery power is at 100% (mostly 7.2V)
	 */
	final int FULL_BATTERY_POWER = 830;
	
	/**
	 * Default constructor
	 */
	public RobotSensorsData() {
		_totalDistance = 0.0f;
		_previousDistance = 0.0f;
		_distanceLeft = 0.0f;
		_distanceRight = 0.0f;
		
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_batteryPower}
	 * @return Value of the attribute
	 */
	public int get_batteryPower() {
		return _batteryPower;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_batteryPower}
	 * @param _batteryPower The new value to affect to the attribute
	 */
	public void set_batteryPower(int _batteryPower) {
		this._batteryPower = _batteryPower;
	}
	
	/**
	 * Method to return percentage the {@link RobotSensorsData#_batteryPower}
	 * @return The attribute in percentage
	 */
	public int getBatteryPercentage() {
		return (_batteryPower - 560) * 100 / (FULL_BATTERY_POWER - 560);
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_desiredSpeedLeft}
	 * @return Value of the attribute
	 */
	public int get_desiredSpeedLeft() {
		return _desiredSpeedLeft;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_desiredSpeedLeft}
	 * @param _desiredSpeedLeft The new value to affect to the attribute
	 */
	public void set_desiredSpeedLeft(int _desiredSpeedLeft) {
		this._desiredSpeedLeft = _desiredSpeedLeft;
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_desiredSpeedRight}
	 * @return Value of the attribute
	 */
	public int get_desiredSpeedRight() {
		return _desiredSpeedRight;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_desiredSpeedRight}
	 * @param _desiredSpeedRight The new value to affect to the attribute
	 */
	public void set_desiredSpeedRight(int _desiredSpeedRight) {
		this._desiredSpeedRight = _desiredSpeedRight;
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_powerLeft}
	 * @return Value of the attribute
	 */
	public int get_powerLeft() {
		return _powerLeft;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_powerLeft}
	 * @param _powerLeft The new value to affect to the attribute
	 */
	public void set_powerLeft(int _powerLeft) {
		this._powerLeft = _powerLeft;
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_powerRight}
	 * @return Value of the attribute
	 */
	public int get_powerRight() {
		return _powerRight;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_powerRight}
	 * @param _powerRight The new value to affect to the attribute
	 */
	public void set_powerRight(int _powerRight) {
		this._powerRight = _powerRight;
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_speedLeft}
	 * @return Value of the attribute
	 */
	public int get_speedLeft() {
		return _speedLeft;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_speedLeft}
	 * @param _speedLeft The new value to affect to the attribute
	 */
	public void set_speedLeft(int _speedLeft) {
		this._speedLeft = _speedLeft;
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_speedRight}
	 * @return Value of the attribute
	 */
	public int get_speedRight() {
		return _speedRight;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_speedRight}
	 * @param _speedRight	The new value to affect to the attribute
	 */
	public void set_speedRight(int _speedRight) {
		this._speedRight = _speedRight;
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_motorCurrentLeft}
	 * @return Value of the attribute
	 */
	public int get_motorCurrentLeft() {
		return _motorCurrentLeft;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_motorCurrentLeft}
	 * @param _motorCurrentLeft The new value to affect to the attribute
	 */
	public void set_motorCurrentLeft(int _motorCurrentLeft) {
		this._motorCurrentLeft = _motorCurrentLeft;
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_motorCurrentRight}
	 * @return Value of the attribute
	 */
	public int get_motorCurrentRight() {
		return _motorCurrentRight;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_motorCurrentRight}
	 * @param _motorCurrentRight The new value to affect to the attribute
	 */
	public void set_motorCurrentRight(int _motorCurrentRight) {
		this._motorCurrentRight = _motorCurrentRight;
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_distanceLeft}
	 * @return Value of the attribute
	 */
	public float get_distanceLeft() {
		return _distanceLeft;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_distanceLeft}
	 * @param _distanceLeft The new value to the attribute
	 */
	public void set_distanceLeft(float _distanceLeft) {
		this._distanceLeft = _distanceLeft;
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_distanceRight}
	 * @return Value of the attribute
	 */
	public float get_distanceRight() {
		return _distanceRight;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_distanceRight}
	 * @param _distanceRight The new value to the attribute
	 */
	public void set_distanceRight(float _distanceRight) {
		this._distanceRight = _distanceRight;
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_lightSensorLeft}
	 * @return Value of the attribute
	 */
	public int get_lightSensorLeft() {
		return _lightSensorLeft;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_lightSensorLeft}
	 * @param _lightSensorLeft 	The new value to the attribute
	 */
	public void set_lightSensorLeft(int _lightSensorLeft) {
		this._lightSensorLeft = _lightSensorLeft;
	}
	
	/**
	 * Getter for the {@link RobotSensorsData#_batteryPower}
	 * @return Value of the attribute
	 */
	public int get_lightSensorRight() {
		return _lightSensorRight;
	}
	
	/**
	 * Setter for the {@link RobotSensorsData#_lightSensorRight}
	 * @param _lightSensorRight The new value to the attribute
	 */
	public void set_lightSensorRight(int _lightSensorRight) {
		this._lightSensorRight = _lightSensorRight;
	}
	
	/**
	 *	Method to get the speed of the robot in cm/s
	 * @return Value of the speed in cm/s
	 */
	public float getSpeedPerSecond() {
		return _speedLeft * 0.25f;
	}
	
	/**
	 * Method to get the total distance travelled by the robot in meters
	 * @return The total distance travelled in meters
	 */
	public float getTotalDistanceMeter() {
		return _totalDistance * 0.25f / 1000f;
	}
	
	/**
	 * Function to parse the data sent by the robot
	 * @param full_data	data sent by the robot as a long string message
	 */
	public void parseString(String full_data) {
		String[] parsed = full_data.split("\\|"); // Parse based on the pipe | character
		
		if (parsed.length == 15 && parsed[0].equals("BEGIN") && parsed[14].equals("END")) // If the message is complete and valid
		for (int i = 1 ; i <= 12 ; i++) {
			
			String value = parsed[i].replaceAll(".*:","");	// Replace every character before ":" by nothing
			switch(i) {
			case 1:
				_batteryPower = Integer.parseInt(value,10);
				break;
			case 2:
				_desiredSpeedLeft = Integer.parseInt(value,10);
				break;
			case 3:
				_desiredSpeedRight = Integer.parseInt(value,10);
				break;
			case 4:
				_powerLeft = Integer.parseInt(value,10);
				break;
			case 5:
				_powerRight = Integer.parseInt(value,10);
				break;
			case 6:
				_speedLeft = Integer.parseInt(value,10);
				break;
			case 7:
				_speedRight = Integer.parseInt(value,10);
				break;
			case 8:
				_motorCurrentLeft = Integer.parseInt(value,10);
				break;
			case 9:
				_motorCurrentRight = Integer.parseInt(value,10);
				break;
			case 10:
				_lightSensorLeft = Integer.parseInt(value,10);
				break;
			case 11:
				_lightSensorRight = Integer.parseInt(value,10);
				break;
			case 12:	// Calculate the total distance
				value = value.replaceAll("[^\\d.]","");
				float delta = Math.abs(Float.parseFloat(value) - _previousDistance); // Absolute difference betwwen the previous distance and the actual
				_distanceLeft = Integer.parseInt(value,10);
				_totalDistance += delta;											// Increment the total distance with the delta value
				_previousDistance = _distanceLeft;
				break;
			case 13:
				value = value.replaceAll("[^\\d.]","");
				_distanceRight = Integer.parseInt(value,10);
				
				break;
				
			}
		}
	}
	
	
}

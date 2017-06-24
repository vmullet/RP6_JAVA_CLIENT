package model;

public class RobotSensorsData {

	private int _batteryPower;
	private int _desiredSpeedLeft;
	private int _desiredSpeedRight;
	private int _powerLeft;
	private int _powerRight;
	private int _speedLeft;
	private int _speedRight;
	private int _motorCurrentLeft;
	private int _motorCurrentRight;
	
	private float _distanceLeft;
	private float _distanceRight;
	
	private int _lightSensorLeft;
	private int _lightSensorRight;
	
	final int FULL_BATTERY_POWER = 720;
	
	public int get_batteryPower() {
		return _batteryPower;
	}
	public void set_batteryPower(int _batteryPower) {
		this._batteryPower = _batteryPower;
	}
	
	public int getBatteryPercentage() {
		return (_batteryPower - 560) * 100 / (850 - 560);
	}
	
	public int get_desiredSpeedLeft() {
		return _desiredSpeedLeft;
	}
	public void set_desiredSpeedLeft(int _desiredSpeedLeft) {
		this._desiredSpeedLeft = _desiredSpeedLeft;
	}
	public int get_desiredSpeedRight() {
		return _desiredSpeedRight;
	}
	public void set_desiredSpeedRight(int _desiredSpeedRight) {
		this._desiredSpeedRight = _desiredSpeedRight;
	}
	public int get_powerLeft() {
		return _powerLeft;
	}
	public void set_powerLeft(int _powerLeft) {
		this._powerLeft = _powerLeft;
	}
	public int get_powerRight() {
		return _powerRight;
	}
	public void set_powerRight(int _powerRight) {
		this._powerRight = _powerRight;
	}
	public int get_speedLeft() {
		return _speedLeft;
	}
	public void set_speedLeft(int _speedLeft) {
		this._speedLeft = _speedLeft;
	}
	public int get_speedRight() {
		return _speedRight;
	}
	public void set_speedRight(int _speedRight) {
		this._speedRight = _speedRight;
	}
	public int get_motorCurrentLeft() {
		return _motorCurrentLeft;
	}
	public void set_motorCurrentLeft(int _motorCurrentLeft) {
		this._motorCurrentLeft = _motorCurrentLeft;
	}
	public int get_motorCurrentRight() {
		return _motorCurrentRight;
	}
	public void set_motorCurrentRight(int _motorCurrentRight) {
		this._motorCurrentRight = _motorCurrentRight;
	}
	public float get_distanceLeft() {
		return _distanceLeft;
	}
	public void set_distanceLeft(float _distanceLeft) {
		this._distanceLeft = _distanceLeft;
	}
	public float get_distanceRight() {
		return _distanceRight;
	}
	public void set_distanceRight(float _distanceRight) {
		this._distanceRight = _distanceRight;
	}
	public int get_lightSensorLeft() {
		return _lightSensorLeft;
	}
	public void set_lightSensorLeft(int _lightSensorLeft) {
		this._lightSensorLeft = _lightSensorLeft;
	}
	public int get_lightSensorRight() {
		return _lightSensorRight;
	}
	public void set_lightSensorRight(int _lightSensorRight) {
		this._lightSensorRight = _lightSensorRight;
	}
	
	public float getSpeedPerSecond() {
		return _speedLeft * 0.25f;
	}
	
	public float getTotalDistanceMeter() {
		if (_distanceLeft == 1.0f)
			_distanceLeft = 0.0f;
		return _distanceLeft * 0.25f;
	}
	
	public void parseString(String full_data) {
		String[] parsed = full_data.split(" \\|");
		for (int i = 0 ; i < 15 ; i++) {
			
			String value = parsed[i].split(":")[1];
			switch(i) {
			case 0:
				_batteryPower = Integer.parseInt(value,10);
				break;
			case 1:
				_desiredSpeedLeft = Integer.parseInt(value,10);
				break;
			case 2:
				_desiredSpeedRight = Integer.parseInt(value,10);
				break;
			case 3:
				_powerLeft = Integer.parseInt(value,10);
				break;
			case 4:
				_powerRight = Integer.parseInt(value,10);
				break;
			case 5:
				_speedLeft = Integer.parseInt(value,10);
				break;
			case 6:
				_speedRight = Integer.parseInt(value,10);
				break;
			case 7:
				_motorCurrentLeft = Integer.parseInt(value,10);
				break;
			case 8:
				_motorCurrentRight = Integer.parseInt(value,10);
				break;
			case 9:
				_lightSensorLeft = Integer.parseInt(value,10);
				break;
			case 10:
				_lightSensorRight = Integer.parseInt(value,10);
				break;
			case 13:
				_distanceLeft = Integer.parseInt(value,10);
				break;
			case 14:
				_distanceRight = Integer.parseInt(value,10);
				break;
				
			}
		}
	}
	
	
}
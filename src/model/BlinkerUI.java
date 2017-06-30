package model;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Class which will control the different animations in the UI
 * @author Valentin
 *
 */
public class BlinkerUI extends Thread {

	/**
	 * Attribute to store the JLabel concerned by the animation
	 */
	private JLabel _component;
	
	/**
	 * Attribute to store the diferent image icon which will alternate during the animation
	 */
	private ImageIcon[] _icons;
	
	/**
	 * Attribute to control the start and stop part of the animation
	 */
	private boolean _stop = true;
	
	/**
	 * Constructor with the 2 main parameters
	 * @param p_component	The JLabel where the animation is applied
	 * @param p_icons An array of ImageIcon which will be set to the JLabel
	 */
	public BlinkerUI(JLabel p_component,ImageIcon[] p_icons) {
		_component = p_component;
		_icons = p_icons;
		_stop = true;
	}
	
	/**
	 * Method to know if the animation is running
	 * @return	Return the state of the animation
	 */
	public boolean is_running() {
		return !_stop;
	}

	/**
	 * The run method which contains the thread logic
	 */
	@Override
	public void run() {
		
		while(!_stop) {
			for (int i = 0 ; i < _icons.length ; i++) {
				_component.setIcon(_icons[i]);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * Method to start the animation and initiate the boolean flag
	 */
	public void startBlink() {
		_stop = false;
		start();
	}
	
	/**
	 * Method to stop the animation
	 */
	public void stopBlink() {
		_stop = true;
	}
}

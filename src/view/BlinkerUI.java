package view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class BlinkerUI extends Thread {

	private JLabel _component;
	private ImageIcon[] _icons;
	private boolean _stop = true;
	private boolean _is_running = false;
	
	public BlinkerUI(JLabel p_component,ImageIcon[] p_icons) {
		_component = p_component;
		_icons = p_icons;
		_stop = true;
		_is_running = false;
	}
	

	public boolean is_running() {
		return _is_running;
	}

	@Override
	public void run() {
		_is_running = true;
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
		_is_running = false;
	}
	
	public void startBlink() {
		_stop = false;
		start();
	}
	
	public void stopBlink() {
		_stop = true;
	}
}

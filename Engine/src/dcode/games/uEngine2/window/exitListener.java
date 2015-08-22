package dcode.games.uEngine2.window;

import dcode.games.uEngine2.StData;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by dusakus on 13.04.15.
 */
public class exitListener implements WindowListener {
	@Override
	public void windowOpened(WindowEvent e) {

	}

	@Override
	public void windowClosing(WindowEvent e) {
		StData.gameIsRunning = false;
	}

	@Override
	public void windowClosed(WindowEvent e) {

	}

	@Override
	public void windowIconified(WindowEvent e) {
		if(StData.setup.frezeOnFocusLost)
		StData.gameFreeze = true;
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		if(StData.setup.frezeOnFocusLost)
			StData.gameFreeze = false;
	}

	@Override
	public void windowActivated(WindowEvent e) {
		if(StData.setup.frezeOnFocusLost)
			StData.gameFreeze = false;
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		if(StData.setup.frezeOnFocusLost)
			StData.gameFreeze = true;
	}
}

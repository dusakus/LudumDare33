/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.input;

import dcode.games.uEngine2.PInputHandler;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author dusakus
 */
public class KeyWrapper implements KeyListener {

	boolean isSHIFTHeld = false;
	boolean isCONTROLLHeld = false;
	boolean isALTTHeld = false;
	private PInputHandler PIH;
	private boolean[] keyTable;

	public KeyWrapper() {
		this.keyTable = new boolean[2048];
		for (int i = 0; i < keyTable.length; i++) {
			keyTable[i] = false;

		}
	}

	public void setInputHandler(PInputHandler pih) {
		PIH = pih;
		PIH.KW = this;
	}

	public boolean isIsALTTHeld() {
		return isALTTHeld;
	}

	public boolean isIsCONTROLLHeld() {
		return isCONTROLLHeld;
	}

	public boolean isIsSHIFTHeld() {
		return isSHIFTHeld;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		if (PIH != null) {
			PIH.keyTyped(e.getKeyChar());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (PIH != null) {
			PIH.keyPressed(e.getKeyCode());
			PIH.keyPressed(e.getKeyChar() + "");
			switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					PIH.keyPressed_ENTER();
					break;
				case KeyEvent.VK_ESCAPE:
					PIH.keyPressed_ESC();
					break;
				case KeyEvent.VK_BACK_SPACE:
					PIH.keyPressed_BACKSPACE();
					break;
				case KeyEvent.VK_SPACE:
					PIH.keyPressed_SPACE();
					break;
				case KeyEvent.VK_TAB:
					PIH.keyPressed_TAB();
					break;
				case KeyEvent.VK_UP:
					PIH.keyPressed_UP();
					break;
				case KeyEvent.VK_DOWN:
					PIH.keyPressed_DOWN();
					break;
				case KeyEvent.VK_LEFT:
					PIH.keyPressed_LEFT();
					break;
				case KeyEvent.VK_RIGHT:
					PIH.keyPressed_RIGHT();
					break;
				case KeyEvent.VK_W:
					PIH.keyPressed_W();
					break;
				case KeyEvent.VK_S:
					PIH.keyPressed_S();
					break;
				case KeyEvent.VK_A:
					PIH.keyPressed_A();
					break;
				case KeyEvent.VK_D:
					PIH.keyPressed_D();
					break;
				case KeyEvent.VK_Z:
					PIH.keyPressed_Z();
					break;
				case KeyEvent.VK_X:
					PIH.keyPressed_X();
					break;
				case KeyEvent.VK_CONTROL:
					isCONTROLLHeld = true;
					break;
				case KeyEvent.VK_SHIFT:
					isSHIFTHeld = true;
					break;
				case KeyEvent.VK_ALT:
					isALTTHeld = true;
					break;
			}
		}
		if (e.getKeyCode() >= 0 && e.getKeyCode() <= 2047) {
			keyTable[e.getKeyCode()] = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (PIH != null) {
			PIH.keyReleased(e.getKeyCode());
			PIH.keyReleased(e.getKeyChar() + "");
			switch (e.getKeyCode()) {
				case KeyEvent.VK_CONTROL:
					isCONTROLLHeld = false;
					break;
				case KeyEvent.VK_SHIFT:
					isSHIFTHeld = false;
					break;
				case KeyEvent.VK_ALT:
					isALTTHeld = false;
					break;
			}
		}
		if (e.getKeyCode() >= 0 && e.getKeyCode() <= 2047) {
			keyTable[e.getKeyCode()] = false;
		}
	}

	public boolean isKeyHeld(int keyCode) {
		if (keyCode >= 0 && keyCode <= 2047) {
			return keyTable[keyCode];
		}
		return false;
	}
}

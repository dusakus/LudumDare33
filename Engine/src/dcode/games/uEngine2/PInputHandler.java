/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2;

import dcode.games.uEngine2.input.KeyWrapper;

/**
 * @author dusakus
 */
public abstract class PInputHandler {
	public KeyWrapper KW;

	abstract public void keyTyped(char Char);

	abstract public void keyPressed(int ID);

	abstract public void keyReleased(int ID);

	abstract public void keyPressed(String Key);

	abstract public void keyReleased(String Key);

	abstract public void keyPressed_W();

	abstract public void keyPressed_S();

	abstract public void keyPressed_A();

	abstract public void keyPressed_D();

	abstract public void keyPressed_Z();

	abstract public void keyPressed_X();

	abstract public void keyPressed_UP();

	abstract public void keyPressed_DOWN();

	abstract public void keyPressed_LEFT();

	abstract public void keyPressed_RIGHT();

	abstract public void keyPressed_ENTER();

	abstract public void keyPressed_SPACE();

	abstract public void keyPressed_BACKSPACE();

	abstract public void keyPressed_ESC();

	abstract public void keyPressed_TAB();

	abstract public void keyPressed_KonamiCode();

	abstract public void clickedRight(int x, int y);

	abstract public void clickedLeft(int x, int y);

	abstract public void clickedMiddle(int x, int y);

	abstract public void scrolledUp(int x, int y);

	abstract public void scrolledDown(int x, int y);

	public boolean isKeyPressed_SHIFT() {
		return KW.isIsSHIFTHeld();
	}

	public boolean isKeyPressed_CONTROLL() {
		return KW.isIsCONTROLLHeld();
	}

	public boolean isKeyPressed_ALT() {
		return KW.isIsALTTHeld();
	}

	public boolean isKeyPressed(int keyCode) {
		return KW.isKeyHeld(keyCode);
	}
}

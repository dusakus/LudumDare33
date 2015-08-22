/*
 *     <---======[ D-LIC open code 0.3 ]======--->
 *          This file is part of a project
 *          licensed with the DLoc license.
 *  Project's folder should contain the license file
 *                      _______
 *                     /dusakus\
 *         <=>---<>--{2013 stuDIO}--<>---<=>
 * 
 */
package dcode.games.uEngine.dcctools;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author bartek
 */
public class KeyManager implements KeyListener {

    public boolean isApressed = false;
    public boolean isBpressed = false;
    public boolean isUPpressed = false;
    public boolean isDOWNpressed = false;
    public boolean isLEFTpressed = false;
    public boolean isRIGHTpressed = false;
    public boolean isSTARTpressed = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                isUPpressed = true;
                break;
            case KeyEvent.VK_DOWN:
                isDOWNpressed = true;
                break;
            case KeyEvent.VK_LEFT:
                isLEFTpressed = true;
                break;
            case KeyEvent.VK_RIGHT:
                isRIGHTpressed = true;
                break;
            case KeyEvent.VK_A:
                isApressed = true;
                break;
            case KeyEvent.VK_S:
                isBpressed = true;
                break;
            case KeyEvent.VK_ENTER:
                isSTARTpressed = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()){
            case KeyEvent.VK_UP:
                isUPpressed = false;
                break;
            case KeyEvent.VK_DOWN:
                isDOWNpressed = false;
                break;
            case KeyEvent.VK_LEFT:
                isLEFTpressed = false;
                break;
            case KeyEvent.VK_RIGHT:
                isRIGHTpressed = false;
                break;
            case KeyEvent.VK_A:
                isApressed = false;
                break;
            case KeyEvent.VK_S:
                isBpressed = false;
                break;
            case KeyEvent.VK_ENTER:
                isSTARTpressed = false;
                break;
        }
    }

}

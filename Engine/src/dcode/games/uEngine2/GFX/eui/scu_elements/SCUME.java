package dcode.games.uEngine2.GFX.eui.scu_elements;

import dcode.games.uEngine2.GFX.eui.scu_modules.SCUM;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 09.06.15.
 */
public abstract class SCUME {
    private int ID = -1;
    private SCUM scum;
    protected BufferedImage line;
    private boolean updateNeeded = true ;

    public abstract boolean isLocked();
    public abstract void triggerEvent_Confirm();
    public abstract void triggerEvent_Left();
    public abstract void triggerEvent_Right();
    public abstract void triggerEvent_Reset();
    public abstract void triggerEvent_textInput(KeyEvent k);
    protected abstract void render();


    public void triggerEvent(KeyEvent k){
        updateNeeded = true;
        switch (k.getKeyCode()){
            case KeyEvent.VK_ENTER:
                triggerEvent_Confirm();
                break;
            case KeyEvent.VK_DELETE:
                triggerEvent_Reset();
                break;
            case KeyEvent.VK_LEFT:
                triggerEvent_Left();
                break;
            case KeyEvent.VK_RIGHT:
                triggerEvent_Right();
                break;
            default:
                triggerEvent_textInput(k);
        }
    }

    public BufferedImage getLine(){
        if(updateNeeded){
            render();
            updateNeeded = false;
        }
        return line;
    }

    public void setID(int i){
        ID = i;
    }

    public int getID() {
        return ID;
    }

    public boolean isSelected(){
        return ID == scum.currentID;
    }

    public void init(SCUM sc) {
        scum = sc;
    }
}

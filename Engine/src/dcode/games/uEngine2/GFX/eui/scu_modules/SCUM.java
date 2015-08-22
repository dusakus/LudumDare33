package dcode.games.uEngine2.GFX.eui.scu_modules;

import dcode.games.uEngine2.GFX.eui.scu_elements.SCUME;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by dusakus on 03.06.15.
 */
public abstract class SCUM {

    public BufferedImage content;

    public int currentID = 0;
    public ArrayList<SCUME> elements = new ArrayList<SCUME>();

    public void TriggerEvent(KeyEvent e){
        if (e!=null)
        switch (e.getKeyCode()){
            case KeyEvent.VK_DOWN:
                try{
                    elements.get(currentID+1);
                    currentID++;
                } catch (Exception r){

                }
                break;
            case KeyEvent.VK_UP:
                try{
                    elements.get(currentID-1);
                    currentID--;
                } catch (Exception r){

                }
                break;
            default:
                elements.get(currentID).triggerEvent(e);
        } else {
            for (SCUME sc : elements){
                sc.init(this);
            }
        }
        redraw();
    }

    private void redraw() {
        content = new BufferedImage(320, 20*(elements.size() + 3), BufferedImage.TYPE_INT_ARGB);
        Graphics g = content.createGraphics();
        g.setColor(new Color(64,64,8));
        g.fillRect(0, 40 + currentID*20, 320, 20);


        for (int i = 0; i < elements.size(); i++) {
            g.drawImage(elements.get(i).getLine(),0,40+i*20, null);
        }
        g.dispose();
    }

}

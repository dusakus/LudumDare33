package dcode.games.uEngine2.GFX.eui.scu_elements;

import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 09.06.15.
 */
public abstract class Element_Slider extends SCUME {
    public int value = 50;
    private final int max, min;
    private String title = "UNTITLED";

    protected Element_Slider(int min, int max, String title) {
        this.max = max;
        this.min = min;
        this.title = title;
    }


    @Override
    public void triggerEvent_Confirm() {

    }

    @Override
    public void triggerEvent_Left() {
        value = numbarTools.clamp(value - 1, min, max);
    }

    @Override
    public void triggerEvent_Right() {
        value = numbarTools.clamp(value + 1, min, max);
    }

    @Override
    public void triggerEvent_Reset() {

    }

    @Override
    public void triggerEvent_textInput(KeyEvent k) {

    }

    @Override
    protected void render() {
        line = new BufferedImage(320, 20, BufferedImage.TYPE_INT_ARGB);
        Graphics g = line.createGraphics();

        g.setColor(Color.WHITE);
        g.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        g.drawString(title, 2, 15);
        g.fillRect(121, 1, 198, 18);
        g.setColor(Color.BLACK);
        g.fillRect(123, 2, 194, 16);

        int bwidth =(int) ((value-min * 1f)/(max-min * 1f) * 192);
        if(isSelected() && !isLocked()){
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.GRAY);
        }

        g.fillRect(124, 3, bwidth, 14);
        g.dispose();
    }
}

package dcode.games.uEngine2.GFX.eui.scu_elements;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 09.06.15.
 */
public abstract class Element_Checkbox extends SCUME {
    public boolean value = false;
    private String title = "UNTITLED";

    protected Element_Checkbox(String title) {
        this.title = title;
    }


    @Override
    public void triggerEvent_Confirm() {
        value = !value;
    }

    @Override
    public void triggerEvent_Left() {
    }

    @Override
    public void triggerEvent_Right() {
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
        g.setFont(new Font(Font.MONOSPACED, 18, Font.BOLD));
        g.drawString(title, 2, 20);
        g.fillRect(18, 18, 301, 1);
        g.setColor(Color.BLACK);
        g.fillRect(16, 16, 302, 2);

        if (isSelected() && !isLocked()) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.GRAY);
        }
        if (value) {
            g.fillRect(14, 14, 303, 3);
        }
        g.dispose();
    }
}
package dcode.games.uEngine2.GFX.eui;

import dcode.games.uEngine2.GFX.ILayer;

import java.awt.*;

/**
 * Created by dusakus on 31.05.15.
 *
 * TODO: create the startup configuration utility AFTER you have the configuration stored
 */
public class Runtime implements ILayer {
    @Override
    public void draw(Graphics2D G2D) {
        G2D.setColor(Color.BLUE);
        G2D.fillRect(320,240,0,0);
    }


    //UNUSED
    @Override
    public boolean removeMe() {
        return false;
    }

    //UNUSED
    @Override
    public boolean renderMe() {
        return false;
    }
}

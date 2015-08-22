package dcode.games.uEngine2.games.LD33.layers;

import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.games.LD33.warns.Warning;

import java.awt.*;

/**
 * Created by dusakus on 22.08.2015.
 */
public class WarnLayer implements ILayer {

    public Warning[] warnings = new Warning[10];



    @Override
    public void draw(Graphics2D G2D) {

    }

    @Override
    public boolean removeMe() {
        return false;
    }

    @Override
    public boolean renderMe() {
        return true;
    }
}

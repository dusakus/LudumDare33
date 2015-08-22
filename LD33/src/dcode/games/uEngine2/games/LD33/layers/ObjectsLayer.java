package dcode.games.uEngine2.games.LD33.layers;

import dcode.games.uEngine2.BGTasks.internalTasks.LoadSpriteSheet;
import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.games.LD33.LStData;
import dcode.games.uEngine2.games.LD33.terrainOB.TerrainObject;
import dcode.games.uEngine2.tools.Shortcuts;

import java.awt.*;

/**
 * Created by dusakus on 22.08.2015.
 */
public class ObjectsLayer implements ILayer {

    @Override
    public void draw(Graphics2D G2D) {
        int offset = LStData.getLocation();
        for (TerrainObject t : LStData.terrainObjects) {
            if(t.Xlocation < offset + 321 && t.Xlocation > offset - 128){
                G2D.drawImage(Shortcuts.getTexture(t.WorldTexture), t.Xlocation + t.XOffset - offset, (t.Ylocation * LStData.laneWidth) + LStData.laneFirstY + t.YOffset, null);
            }
        }
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

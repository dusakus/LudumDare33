package dcode.games.uEngine2.games.LD33.layers;

import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.tools.Shortcuts;

import java.awt.*;

/**
 * Created by dusakus on 22.08.15.
 *
 * HUD layer for mode you chase
 */
public class HUDLayer3 implements ILayer{

	@Override
	public void draw(Graphics2D G2D) {
		if(StData.currentGC.currentSC.layers_Overlay.get(0) instanceof LoadingLayer) {} else
		G2D.drawImage(Shortcuts.getTexture("top3"),0,0,null);
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

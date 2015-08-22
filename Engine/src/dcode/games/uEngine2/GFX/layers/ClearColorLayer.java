package dcode.games.uEngine2.GFX.layers;

import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.StData;

import java.awt.*;

/**
 * Created by dusakus on 25.03.15.
 */
public class ClearColorLayer implements ILayer {

	private Color color;

	public ClearColorLayer(Color setColor) {
		color = setColor;
	}

	@Override
	public void draw(Graphics2D G2D) {
		G2D.setColor(color);
		G2D.fillRect(0, 0, StData.setup.width, StData.setup.height);
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.GFX.layers;

import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.StData;

import java.awt.*;

/**
 * @author dusakus
 */
public class FillTextureLayer implements ILayer {

	String textureKey = "missing";

	public FillTextureLayer(String key) {
		textureKey = key;
	}

	@Override
	public void draw(Graphics2D G2D) {
		G2D.drawImage(StData.resources.grf.getScaledTexture(textureKey, StData.setup.width, StData.setup.height), 0, 0, null);
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

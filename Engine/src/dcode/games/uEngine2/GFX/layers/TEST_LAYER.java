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
public class TEST_LAYER implements ILayer {

	@Override
	public void draw(Graphics2D G2D) {
		G2D.drawImage(StData.resources.grf.getScaledTexture("missing", 64, 64), 64, 64, null);

		G2D.drawImage(StData.resources.grf.getScaledTexture("missing", 16, 16), 16, 10, null);
		G2D.drawImage(StData.resources.grf.getScaledTexture("missing", 16, 16), 32, 10, null);
		G2D.drawImage(StData.resources.grf.getScaledTexture("missing", 16, 16), 48, 10, null);
		G2D.setColor(Color.red);
		G2D.drawString("TESTIFICATE", 16, 200);
		G2D.setColor(Color.BLACK);
		G2D.drawString("uEngine2 render test 2", 16, 216);
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

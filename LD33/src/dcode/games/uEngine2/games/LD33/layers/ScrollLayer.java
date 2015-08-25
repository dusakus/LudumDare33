package dcode.games.uEngine2.games.LD33.layers;

import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.LD33.LStData;
import dcode.games.uEngine2.tools.Shortcuts;

import java.awt.*;

/**
 * Created by dusakus on 22.08.15.
 */
public class ScrollLayer implements ILayer {

	private String hiding = "pre1", center = "pre2", imcomming = "pre3";


	@Override
	public void draw(Graphics2D G2D) {

		int currentOffset = calculateCurrentOffset();

		G2D.drawImage(Shortcuts.getTexture(hiding), currentOffset-100, 40, null);
		G2D.drawImage(Shortcuts.getTexture(center), currentOffset + 100, 40, null);
		G2D.drawImage(Shortcuts.getTexture(imcomming), currentOffset + 300, 40, null);

		if (currentOffset <= -100) {
			hiding = center;
			center = imcomming;
			if (LStData.blocks.isEmpty()) {
				LStData.currentMode = LStData.MODE_ERROR;
				LStData.ERRORCODE = 421;
				StData.lastMSG = "no more tiles";
			} else
				imcomming = LStData.blocks.poll();
			LStData.tileCounter++;
		}

	}

	private int calculateCurrentOffset() {
		return -1 * ((LStData.getLocation()) - (LStData.tileCounter * 200));
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

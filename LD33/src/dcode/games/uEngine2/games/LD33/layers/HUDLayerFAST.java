package dcode.games.uEngine2.games.LD33.layers;

import dcode.games.uEngine.dcctools.Delay;
import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.games.LD33.LStData;
import dcode.games.uEngine2.tools.Shortcuts;

import java.awt.*;

/**
 * Created by dusakus on 22.08.15.
 * <p/>
 * HUD layer for mode you chase
 */
public class HUDLayerFAST implements ILayer {

	int sanicPhase = 0;

	int sloc = 400;

	boolean drawSanicSpeed = false;

	Delay d = new Delay(100);


	@Override
	public void draw(Graphics2D G2D) {
		G2D.drawImage(Shortcuts.getTexture("top4"), 0, 0, null);

		if (LStData.playerSpeed * 60 > 999) {
			G2D.drawImage(Shortcuts.getTexture("sanic").getScaledInstance(22, 31, Image.SCALE_REPLICATE), 7, 7, null);
			G2D.drawImage(Shortcuts.getTexture("sanic").getScaledInstance(22, 31, Image.SCALE_REPLICATE), 29, 7, null);
			G2D.drawImage(Shortcuts.getTexture("sanic").getScaledInstance(22, 31, Image.SCALE_REPLICATE), 51, 7, null);
		} else {
			G2D.drawImage(Shortcuts.getTexture("speed"), 7, 7, null);
		}

		switch (sanicPhase) {
			case 0:
				if (LStData.theorderofsanic) sanicPhase++;
				break;
			case 1:
				LStData.msx_sanic.play(false, 1.5f);
				sanicPhase++;
				break;
			case 2:
				sloc -= 2;
				G2D.drawImage(Shortcuts.getTexture("sanic"), sloc - 60, 148, null);
				if (sloc < -60) {
					sanicPhase++;
					sloc = 320;
				}
				break;
			case 3:
				sloc -= 4;
				G2D.drawImage(Shortcuts.getTexture("sanicO"), sloc, 2, null);
				if (sloc <= 50) sanicPhase++;
				break;
			case 4:
				G2D.drawImage(Shortcuts.getTexture("sanicO"), sloc, 2, null);
				if (d.doNow()) sanicPhase++;
				break;
			case 5:
				sloc += 4;
				G2D.drawImage(Shortcuts.getTexture("sanicO"), sloc, 2, null);
				if (sloc == 200) sanicPhase++;
				break;
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

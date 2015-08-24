package dcode.games.uEngine2.games.LD33.layers;

import dcode.games.uEngine.dcctools.Delay;
import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.games.LD33.LStData;
import dcode.games.uEngine2.games.LD33.items.Item;
import dcode.games.uEngine2.tools.Shortcuts;
import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;

/**
 * Created by dusakus on 22.08.15.
 *
 * HUD layer for mode you chase
 */
public class HUDLayer implements ILayer{

	private boolean flickerIN = true;
	private Delay flickerTimer = new Delay(15);

	@Override
	public void draw(Graphics2D G2D) {
		G2D.drawImage(Shortcuts.getTexture("top1"),0,0,null);
		G2D.drawImage(Shortcuts.getTexture("perc"),7,7,null);

		Item[] items = LStData.chase_itemQ.getList(6);
		if(items[0]!=null)G2D.drawImage(Shortcuts.getTexture(items[0].texture + items[0].animationframe).getScaledInstance(14,14,Image.SCALE_REPLICATE),47,12,null);
		if(items[1]!=null)G2D.drawImage(Shortcuts.getTexture(items[1].texture + items[1].animationframe).getScaledInstance(14,14,Image.SCALE_REPLICATE),67,12,null);
		if(items[2]!=null)G2D.drawImage(Shortcuts.getTexture(items[2].texture + items[2].animationframe).getScaledInstance(14,14,Image.SCALE_REPLICATE),87,12,null);
		if(items[3]!=null)G2D.drawImage(Shortcuts.getTexture(items[3].texture + items[3].animationframe).getScaledInstance(14, 14, Image.SCALE_REPLICATE),107,12,null);
		if(items[4]!=null)G2D.drawImage(Shortcuts.getTexture(items[4].texture + items[4].animationframe).getScaledInstance(14,14,Image.SCALE_REPLICATE),127,12,null);
		if(items[5]!=null)G2D.drawImage(Shortcuts.getTexture(items[5].texture + items[5].animationframe).getScaledInstance(14, 14, Image.SCALE_REPLICATE),147,12,null);

		G2D.drawImage(Shortcuts.getTextureAsBufferedImage("barCol").getSubimage(numbarTools.clamp(LStData.battleIndex + 203, 1, 405), 2, 194, 15), 63,43,null);


		if(LStData.battleIndex < -100) G2D.drawImage(Shortcuts.getTexture("barCovF"), 64, 44, null);
		else if(LStData.battleIndex > 65 && LStData.battleIndex < 80) G2D.drawImage(Shortcuts.getTexture("barCovW"), 64, 44, null);
		else if(LStData.battleIndex >= 80 && flickerIN) G2D.drawImage(Shortcuts.getTexture("barCovW"), 64, 44, null);
		else G2D.drawImage(Shortcuts.getTexture("barCov"), 64, 44, null);

		if(flickerTimer.doNow()) flickerIN = ! flickerIN;
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

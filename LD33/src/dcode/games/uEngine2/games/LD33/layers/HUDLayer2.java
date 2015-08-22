package dcode.games.uEngine2.games.LD33.layers;

/**
 * Created by dusakus on 22.08.15.
 */

import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.games.LD33.LStData;
import dcode.games.uEngine2.games.LD33.items.Item;
import dcode.games.uEngine2.tools.Shortcuts;

import java.awt.*;

/**
 * Created by dusakus on 22.08.15.
 *
 * HUD layer for mode chased
 */
public class HUDLayer2 implements ILayer {
	@Override
	public void draw(Graphics2D G2D) {
		G2D.drawImage(Shortcuts.getTexture("top2"),0,0,null);
		G2D.drawImage(Shortcuts.getTexture("perc"),4,4,null);

		Item[] items = LStData.chase_itemQ.getList(4);
		G2D.drawImage(Shortcuts.getTexture(items[0].texture + items[0].animationframe).getScaledInstance(36,36,Image.SCALE_REPLICATE),154,4,null);
		G2D.drawImage(Shortcuts.getTexture(items[1].texture + items[1].animationframe).getScaledInstance(36,36,Image.SCALE_REPLICATE),196,4,null);
		G2D.drawImage(Shortcuts.getTexture(items[2].texture + items[2].animationframe).getScaledInstance(36,36,Image.SCALE_REPLICATE),238,4,null);
		G2D.drawImage(Shortcuts.getTexture(items[3].texture + items[3].animationframe).getScaledInstance(36,36,Image.SCALE_REPLICATE),280,4,null);
	}

	@Override
	public boolean removeMe() {
		return false;
	}

	@Override
	public boolean renderMe() {
		return false;
	}
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.GFX.sprites;

import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;

import java.awt.*;

/**
 * This class, if enabled in setup, allows you to use single (basic) sprite more
 * than once by wrapping it and shifting it's coordinates or altering them
 * compleetly
 *
 * @author dusakus
 */
public class SpriteWrapper extends Sprite implements ILogicTask {
	public boolean shiftOrReplace = true; // true for shift, false for replace;
	public int spriteId;
	public Sprite localSprite;

	public boolean enabled = false;
	private SpriteLogicTask slt;
	private int tick;

	public void setSprite(Sprite s) {
		localSprite = s;
		spriteId = -1;
		enabled = true;
	}

	public void clear() {
		spriteId = -1;
		localSprite = new nullSprite();
		enabled = false;
	}

	@Override
	public int getX() {
		if (shiftOrReplace) {
			return getSprite().x + x;
		}
		return x;
	}

	@Override
	public int getY() {
		if (shiftOrReplace) {
			return getSprite().y + y;
		}
		return y;
	}

	@Override
	public int getZ() {
		if (shiftOrReplace) {
			return getSprite().z + z;
		}
		return z;
	}

	private Sprite getSprite() {
		Sprite trg = null;
		if (spriteId >= 0) {
			trg = StData.currentGC.currentSC.sprites[spriteId];
		}
		if(trg == null){
			trg = localSprite;
		}
		if(trg == null){
			trg = new SimpleSprite("MISS",-1000,-1000,-100);
		}
		return trg;
	}

	public void setSprite(int s) {
		spriteId = s;
		enabled = true;
	}

	public void setUpdateOperation(SpriteLogicTask slt) {
		this.slt = slt;
	}

	//return texture
	@Override
	public String getTextureKey() {
		return getSprite().textureKey;
	}

	// if textureKey == null, return custom texture
	@Override
	public Image getCustomTexture() {
		return getSprite().getCustomTexture();
	}

	// if doCustomRender, perform drawing manually
	@Override
	public boolean doCustomRender() {
		return getSprite().doCustomRender();
	}

	@Override
	public void customRender(Graphics2D G) {
		getSprite().customRender(G);
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void perform() {
		if (slt != null) {
			slt.update(this, tick);
		}
	}

	@Override
	public boolean doRepeat() {
		return true;
	}
}

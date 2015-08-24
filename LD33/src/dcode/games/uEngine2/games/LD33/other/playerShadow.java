package dcode.games.uEngine2.games.LD33.other;

import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.games.LD33.LStData;

import java.awt.*;

/**
 * Created by dusakus on 23.08.15.
 */
public class playerShadow extends Sprite {

	@Override
	public String getTextureKey() {
		return "sha1";
	}

	@Override
	public int getX() {
		return LStData.playerONSCREENshift - 8;
	}

	@Override
	public int getY() {
		return LStData.playerLane*LStData.laneWidth + LStData.laneFirstY - 8;
	}

	@Override
	public Image getCustomTexture() {
		return null;
	}

	@Override
	public boolean doCustomRender() {
		return false;
	}

	@Override
	public void customRender(Graphics2D G) {

	}
}

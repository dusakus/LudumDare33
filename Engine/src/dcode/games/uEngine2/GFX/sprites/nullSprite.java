/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.GFX.sprites;

import java.awt.*;

/**
 * @author dusakus
 */
class nullSprite extends Sprite {

	public nullSprite() {
		this.textureKey = "-1";
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

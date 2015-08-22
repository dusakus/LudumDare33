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
public abstract class Sprite {
	protected int x = -100, y = -100, z = 0;
	protected String textureKey;

	//return coordinates
	public int getX() {
		return x;
	}

	public void setX(int nx) {
		x = nx;
	}

	public int getY() {
		return y;
	}

	public void setY(int ny) {
		y = ny;
	}

	public int getZ() {
		return z;
	}//maybe will be used for sorting, maybe

	public void setZ(int nz) {
		z = nz;
	}//maybe will be used for sorting, maybe

	//return texture
	public String getTextureKey() {
		return textureKey;
	}

	// if textureKey == null, return custom texture
	public abstract Image getCustomTexture();

	// if doCustomRender, perform drawing manually
	public abstract boolean doCustomRender();

	public abstract void customRender(Graphics2D G);
}

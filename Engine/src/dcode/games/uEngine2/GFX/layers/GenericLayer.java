/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.GFX.layers;

import dcode.games.uEngine2.GFX.ILayer;

import java.awt.*;

/**
 * @author dusakus
 */
public class GenericLayer implements ILayer {

	@Override
	public void draw(Graphics2D G2D) {

	}

	@Override
	public boolean removeMe() {
		return true;
	}

	@Override
	public boolean renderMe() {
		return false;
	}

}

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
public class SimpleSprite extends Sprite {

    public SimpleSprite(String texID, int startCoordX, int startCoordY, int coordZ) {
        this.textureKey = texID;
        x = startCoordX;
        y = startCoordY;
        z = coordZ;
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

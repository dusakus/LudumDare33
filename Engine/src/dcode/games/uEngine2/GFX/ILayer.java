/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.GFX;


import java.awt.*;

/**
 * @author dusakus
 */
public interface ILayer {
	public void draw(Graphics2D G2D);

	public boolean removeMe();

	public boolean renderMe();
}

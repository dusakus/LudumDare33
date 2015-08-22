/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2;

/**
 * @author dusakus
 */
public abstract class PuGameLoader {
	public abstract void loadInitialGameContent();
	public void engineStopped(){
		System.exit(0);
	}
}

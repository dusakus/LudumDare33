/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2;

import dcode.games.uEngine2.BGTasks.BackgroundTasks;
import dcode.games.uEngine2.GFX.ScreenContent;
import dcode.games.uEngine2.LOGIC.LogicTasks;
import dcode.games.uEngine2.SFX.AudioContent;

/**
 * @author dusakus
 */
public class GameContainer {
	public ScreenContent currentSC = null;
	public AudioContent currentAC = null;
	public LogicTasks currentLT = null;
	public BackgroundTasks currentBGT = null;

	public GameContainer() {
		if (StData.setup.soundEnabled) currentAC = new AudioContent();
		currentBGT = new BackgroundTasks();
		currentLT = new LogicTasks();
		currentSC = new ScreenContent();
		currentAC = new AudioContent();
	}

}

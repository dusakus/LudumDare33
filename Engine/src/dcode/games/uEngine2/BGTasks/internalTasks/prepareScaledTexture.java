/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.BGTasks.internalTasks;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.ResourceManager.TexMan;
import dcode.games.uEngine2.StData;

import java.awt.*;

/**
 * @author dusakus
 */
public class prepareScaledTexture extends PBGTask {

	TexMan.ScaledTexture stb;

	public prepareScaledTexture(TexMan.ScaledTexture scaledTextureBuffer) {
		stb = scaledTextureBuffer;
		this.TaskPriority = PRIORITY_LOW;
	}


	@Override
	public boolean isReady() {
		return stb.key.equalsIgnoreCase("missing") || !(StData.resources.grf.getTexture(stb.key) == StData.resources.grf.getTexture("missing"));
	}

	@Override
	public void perform() {
		stb.texture = StData.resources.grf.getTexture(stb.key).getScaledInstance(stb.width, stb.height, Image.SCALE_REPLICATE);
		done = true;
	}

}

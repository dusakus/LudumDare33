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
public class prepareScaledSubTexture extends PBGTask {

	TexMan.ScaledSubTexture sstb;

	public prepareScaledSubTexture(TexMan.ScaledSubTexture scaledSubTextureBuffer) {
		sstb = scaledSubTextureBuffer;
		this.TaskPriority = PRIORITY_LOW;
	}


	@Override
	public boolean isReady() {
		return sstb.key.equalsIgnoreCase("missing") || !(StData.resources.grf.getTexture(sstb.key) == StData.resources.grf.getTexture("missing"));
	}

	@Override
	public void perform() {
		sstb.texture = StData.resources.grf.getPartTexture(sstb.key, sstb.posX, sstb.posY, sstb.cropWidth, sstb.cropHeight).getScaledInstance(sstb.height, sstb.width, Image.SCALE_REPLICATE);
		done = true;
	}

}

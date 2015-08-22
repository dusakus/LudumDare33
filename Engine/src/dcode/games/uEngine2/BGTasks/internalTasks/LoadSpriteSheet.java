/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.BGTasks.internalTasks;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.StData;

import java.awt.image.BufferedImage;

/**
 * @author dusakus
 */
public class LoadSpriteSheet extends PBGTask {

	private String texKey = "UNKNOWN";
	private String addr = "missing";
	private int spriteWidth = 1;
	private int spriteHeight = 1;


	public LoadSpriteSheet(String location, String basekey, int spriteW, int spriteH) {
		this.TaskPriority = PRIORITY_LOW;
		texKey = basekey;
		addr = location;
		spriteHeight = spriteH;
		spriteWidth = spriteW;
	}

	@Override
	public boolean isReady() {
		if (StData.resources.grf.loader_internal == null) {
			return false;
		}
		return true;
	}

	@Override
	public void perform() {
		StData.LOG.println("BGTask LBT: trying to load texture from: " + addr, "D");
		StData.resources.grf.loader_internal.loadSingleTexture(addr, texKey);
		BufferedImage bf = StData.resources.grf.getTexture(texKey);
		int xSprites = bf.getWidth() / spriteWidth;
		int ySprites = bf.getHeight() / spriteHeight;

		for (int i = 0; i < xSprites; i++) {
			for (int j = 0; j < ySprites; j++) {
				BufferedImage o = bf.getSubimage(spriteWidth * i, spriteHeight * j, spriteWidth, spriteHeight);
				StData.resources.grf.registerTexture(o, texKey + "X" + i + "Y" + j);
			}
		}

		done = true;
	}
}

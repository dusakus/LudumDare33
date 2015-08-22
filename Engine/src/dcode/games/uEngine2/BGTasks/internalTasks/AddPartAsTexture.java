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
public class AddPartAsTexture extends PBGTask {

	private String texKey = "noKey";
	private String texSrc = "UNKNOWN";
	private int x, y, width, height;

	public AddPartAsTexture(String source, String key, int x, int y, int width, int height) {
		this.TaskPriority = PRIORITY_LOW;
		texKey = key;
		texSrc = source;
		this.width = width;
		this.height = height;
		this.x = x;
		this.y = y;
	}

	@Override
	public boolean isReady() {
		return texSrc.equalsIgnoreCase("missing") || (StData.resources.grf.getTexture(texSrc) != StData.resources.grf.getTexture("missing"));
	}

	@Override
	public void perform() {
		StData.LOG.println("BGTask LBT: trying to copy part of texture (" + texSrc + ")", "D");
		BufferedImage out = StData.resources.grf.getTexture(texSrc).getSubimage(x, y, width, height);
		StData.resources.grf.registerTexture(out, texKey);
		done = true;
	}

}
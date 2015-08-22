/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.BGTasks.internalTasks;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.StData;

/**
 * @author dusakus
 */
public class LoadBasicTexture extends PBGTask {

	private String texKey = "UNKNOWN";
	private String addr = "missing";

	public LoadBasicTexture(String location, String key) {
		this.TaskPriority = PRIORITY_LOW;
		texKey = key;
		addr = location;
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
		done = true;
	}

}

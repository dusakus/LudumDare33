/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.BGTasks.internalTasks;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.ResourceManager.DSU.DSU_NODE;

/**
 * @author dusakus
 */
public class DSU_OptimizeNode extends PBGTask {

	DSU_NODE dn;

	public DSU_OptimizeNode(DSU_NODE dn) {
		this.dn = dn;
		this.TaskPriority = PRIORITY_LOW;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void perform() {
		dn.optimize();
		done = true;
	}

}

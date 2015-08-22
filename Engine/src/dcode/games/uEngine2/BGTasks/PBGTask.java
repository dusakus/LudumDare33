/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.BGTasks;

import java.io.IOException;

/**
 * @author dusakus
 */
public abstract class PBGTask {
	public static final int PRIORITY_HIGH = 2;
	public static final int PRIORITY_LOW = 1;
	public static final int PRIORITY_WAIT = 0;
	public int TaskPriority = 0;
	public int TaskRetries = 0;
	protected boolean done = false;

	public abstract boolean isReady();

	public abstract void perform() throws Exception;

	public boolean isDone() {
		return done;
	}
}

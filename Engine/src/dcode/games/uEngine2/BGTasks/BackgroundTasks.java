/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.BGTasks;

import dcode.games.uEngine2.StData;

import java.util.LinkedList;

/**
 * @author dusakus
 */
public class BackgroundTasks {

    public LinkedList<PBGTask> HPTasks;
    public LinkedList<PBGTask> LPTasks;
    public LinkedList<PBGTask> WaitingTasks;
    private PBGTask lastTask = null;
    private boolean didFinishLoop = true;

    public BackgroundTasks() {
        HPTasks = new LinkedList<PBGTask>();
        LPTasks = new LinkedList<PBGTask>();
        WaitingTasks = new LinkedList<PBGTask>();

    }

    public void processContent() {
        if (!didFinishLoop) {
            StData.LOG.println("last BGTask processing tick failed at " + lastTask.getClass().getCanonicalName(), "D");
            if (lastTask.TaskRetries > 5) {
                HPTasks.remove(lastTask);
                LPTasks.remove(lastTask);
                WaitingTasks.remove(lastTask);
                StData.LOG.println("it will be skipped", "D");
            } else {
                lastTask.TaskRetries++;
                HPTasks.remove(lastTask);
                LPTasks.remove(lastTask);
                WaitingTasks.remove(lastTask);
                WaitingTasks.add(lastTask);
                StData.LOG.println("it will be retried in a few ticks", "D");
            }
        }
        didFinishLoop = false;
        if (!HPTasks.isEmpty()) {
            for (PBGTask task : HPTasks) {
                lastTask = task;
                if (task.isReady()) {
                    try {
                        task.perform();
                    } catch (Exception e) {
                        StData.EXC.BOOM(e, "HPTask", task);
                    }
                }
            }
        }
        if (!LPTasks.isEmpty()) {
            PBGTask task = LPTasks.poll();
            lastTask = task;
            if (task.isReady()) {
                try {
                    task.perform();
                } catch (Exception e) {
                    StData.EXC.BOOM(e, "LPTask", task);
                }
            } else {
                LPTasks.offer(task);
            }
        }
        for (int i = 0; i < 4; i++) {
            if (!WaitingTasks.isEmpty()) {
                PBGTask task = WaitingTasks.poll();
                lastTask = task;
                if (task.TaskPriority == PBGTask.PRIORITY_HIGH) {
                    HPTasks.offer(task);
                } else if (task.TaskPriority == PBGTask.PRIORITY_LOW) {
                    LPTasks.offer(task);
                }

            }
        }
        didFinishLoop = true;
    }

    public void clear() {
        HPTasks.clear();
        LPTasks.clear();
        WaitingTasks.clear();
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.LOGIC;

import dcode.games.uEngine2.StData;

import java.util.LinkedList;

/**
 * @author dusakus
 */
public class LogicTasks {

    LinkedList<ILogicTask> tasks;

    public LogicTasks() {
        tasks = new LinkedList<ILogicTask>();
    }

    public void performAll() {
        Object[] arr = tasks.toArray();
        for (Object o : arr) {
            ILogicTask t = (ILogicTask) o;
            if (t != null) {
                if (t.isReady()) {
                    try {
                        t.perform();
                    } catch (Exception e) {
                        StData.LOG.printerr(e, " occured while ticking " + t.getClass().getCanonicalName(), "D");
                    }
                }
                if (!t.doRepeat()) {
                    tasks.remove(t);
                }
            }
        }
    }

    public void registerBasic(ILogicTask task) {
        tasks.add(task);
    }

    public void unregisterBasic(ILogicTask task) {
        tasks.add(task);
    }

    public void clear() {
        tasks.clear();
    }
}

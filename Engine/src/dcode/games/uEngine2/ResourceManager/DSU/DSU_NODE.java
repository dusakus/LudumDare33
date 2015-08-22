/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.ResourceManager.DSU;

import dcode.games.uEngine2.BGTasks.internalTasks.DSU_OptimizeNode;
import dcode.games.uEngine2.StData;

/**
 * @author dusakus
 */
public class DSU_NODE {

    int level = -1;
    public DSU_NODE_SUBSTOR SS;
    public DSU_NODE[] NODE_ARRAY;
    public DSU_OBJECT finalKey;
    private boolean optimizationRequested = false;

    public DSU_NODE(int level, boolean extend) {
        this.level = level;
        NODE_ARRAY = new DSU_NODE[128];
        if (extend) {
            for (int i = 0; i < NODE_ARRAY.length; i++) {
                NODE_ARRAY[i] = new DSU_NODE(level+1, level < 3);
            }
        } else {
            for (int i = 0; i < NODE_ARRAY.length; i++) {
                NODE_ARRAY[i] = null;
            }
            SS = new DSU_NODE_SUBSTOR();
        }
    }

    public Object getObject(String key) {

        if (SS != null) {
            return SS.getObject(key);
        }
        if (finalKey != null && key.length() == level) {
            return finalKey.OBJ;
        }
        try {
            return NODE_ARRAY[key.charAt(level) - 32].getObject(key);
        } catch (Exception e) {
            return null;
        }
    }

    public void removeObject(String key) {
        if (SS != null) {
            SS.removeObject(key);
        }
        if (key.length() == level) {
            finalKey = null;
        }
        try {
            NODE_ARRAY[key.charAt(level) - 32].removeObject(key);
        } catch (Exception e) {
        }
    }

    public void storeObject(DSU_OBJECT ob) {
        if (SS != null) {
            SS.add(ob);
            if (SS.getSize() > 11 && !optimizationRequested) {
                StData.threadManager.BGT.addTask(new DSU_OptimizeNode(this));
                optimizationRequested = true;
            }
        } else if (ob.KEY.length() == level) {
            finalKey = ob;
        } else {
            if (NODE_ARRAY[ob.KEY.charAt(level) - 32] == null) {
                NODE_ARRAY[ob.KEY.charAt(level) - 32] = new DSU_NODE(level+1, false);
            }
            NODE_ARRAY[ob.KEY.charAt(level) - 32].storeObject(ob);
        }
    }

    public DSU_NODE getNode(String key) {
        if (key.length() == this.level) {
            return this;
        } else if (NODE_ARRAY[key.charAt(level) - 32] != null) {
            return NODE_ARRAY[key.charAt(level) - 32].getNode(key);
        }
        return null;
    }

    public void optimize() {
        StData.LOG.println("[DSU] performing node optimization on level " + level, "D");
        if (SS == null) {
            StData.LOG.println("[DSU] node already optimized!", "D");
            return;
        }
        DSU_NODE_SUBSTOR ss = SS;
        SS = null;
        for (int i = 0; i < NODE_ARRAY.length; i++) {
            if (NODE_ARRAY[i] == null) {
                NODE_ARRAY[i] = new DSU_NODE(level + 1, false);
            }
        }
        for (DSU_OBJECT ob : ss.objects) {
            StData.LOG.println("[DSU] relinking key " + ob.KEY, "D");
            if (ob.KEY.length() == level) {
                finalKey = ob;
            } else {
                this.storeObject(ob);
            }
        }
        optimizationRequested = false;
        StData.LOG.println("[DSU] node optimized!", "D");
    }
}

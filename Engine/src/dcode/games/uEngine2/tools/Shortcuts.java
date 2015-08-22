package dcode.games.uEngine2.tools;

import dcode.games.uEngine2.BGTasks.BackgroundThread;
import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.BGTasks.internalTasks.LoadBasicTexture;
import dcode.games.uEngine2.GFX.RenderThread;
import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.LOGIC.LogicThread;
import dcode.games.uEngine2.SFX.AudioThread;
import dcode.games.uEngine2.StData;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 17.04.15.
 */
public class Shortcuts {
    public static final int LAYER_BACK = 101;
    public static final int LAYER_CENTER = 102;
    public static final int LAYER_FRONT = 103;
    public static final int LAYER_OVERLAY = 104;

    /*
        This file should provide static shortcuts for most used or long engine functions
     */

    // Graphics ========================================================================================================
    //  Textures     >------------------------------------------------------------------------------------
    public static void requestTexture(String source, String key) {
        StData.generalBGT.LPTasks.add(new LoadBasicTexture(source, key));
    }           //request texture loading

    public static void registerTexture(BufferedImage image, String key) {
        StData.resources.grf.registerTexture(image, key);
    }    //register bufferedimage

    public static boolean isTexAviable(String key) {
        return StData.resources.grf.isTextureAviable(key);
    }

    //  Sprites      >------------------------------------------------------------------------------------
    public static void sortSprites() {
        StData.threadManager.RT.forceSpriteSort = true;
    }

    public synchronized static int registerSprite(Sprite s, int beginAt) {
        for (int i = beginAt; i < StData.setup.spriteTableSize; i++) {
            if (StData.currentGC.currentSC.sprites[i] == null) {
                StData.currentGC.currentSC.sprites[i] = s;
                return i;
            }
        }
        return -1;
    }

    public synchronized static void clearUnusedSprites() {
        boolean[] toRemove = new boolean[StData.setup.spriteTableSize];
        java.util.Arrays.fill(toRemove, true);
        for (int i = 0; i < StData.setup.spriteLayerSize; i++) {
            try {
                toRemove[StData.currentGC.currentSC.sprites_front[i]] = false;
            } catch (Exception ignored) {
            }
            try {
                toRemove[StData.currentGC.currentSC.sprites_middle[i]] = false;
            } catch (Exception ignored) {
            }
            try {
                toRemove[StData.currentGC.currentSC.sprites_back[i]] = false;
            } catch (Exception ignored) {
            }
            if (StData.setup.enableSpriteWrappers) {
                try {
                    toRemove[StData.currentGC.currentSC.spriteW_back[i].spriteId] = false;
                } catch (Exception ignored) {
                }
                try {
                    toRemove[StData.currentGC.currentSC.spriteW_middle[i].spriteId] = false;
                } catch (Exception ignored) {
                }
                try {
                    toRemove[StData.currentGC.currentSC.spriteW_front[i].spriteId] = false;
                } catch (Exception ignored) {
                }
            }
        }
        for (int i = 0; i < toRemove.length; i++) {
            if (toRemove[i]) StData.currentGC.currentSC.sprites[i] = null;
        }
    }
    // <--------------------------------------------------------------------------------------------------

    // Speed ===========================================================================================================
    public static void setLogicSpeed(int targetTPS) {
        StData.threadManager.LT.LOOP_TPS = targetTPS;
        StData.threadManager.LT.LOOP_Recalculate = true;
    }

    public static void setRenderSpeed(int targetFPS) {
        StData.threadManager.RT.LOOP_TPS = targetFPS;
        StData.threadManager.RT.LOOP_Recalculate = true;
    }

    public static void setBgSpeed(int targetFPS) {
        StData.threadManager.BGT.LOOP_TPS = targetFPS;
        StData.threadManager.BGT.LOOP_Recalculate = true;
    }

    public static void setAudioSpeed(int targetFPS) {
        StData.threadManager.AT.LOOP_TPS = targetFPS;
        StData.threadManager.AT.LOOP_Recalculate = true;
    }

    // BGTasks
    //  Commands     >-----------------------------------------------------------------------------------
    public static void registerRepeatedBGTask(PBGTask task, boolean Global) {
        task.TaskPriority = PBGTask.PRIORITY_HIGH;
        if (Global) StData.generalBGT.HPTasks.add(task);
        else StData.currentGC.currentBGT.HPTasks.add(task);
    }

    public static void registerOneTimeBGTask(PBGTask task, boolean Global) {
        task.TaskPriority = PBGTask.PRIORITY_LOW;
        if (Global) StData.generalBGT.LPTasks.add(task);
        else StData.currentGC.currentBGT.LPTasks.add(task);
    }

    public static void BGTasks_clear(boolean Global) {
        if (Global) StData.generalBGT.clear();
        else StData.currentGC.currentBGT.clear();
    }


    // Logging =========================================================================================================
    public static void log(String l) {
        StData.LOG.println(l);
    }

    public static void debug(String l) {
        StData.LOG.println(l, "D");
    }

    public static Image getTexture(String tid) {
        return StData.resources.grf.getTexture(tid);
    }

    // System ==========================================================================================================
    public static void resetThread(String threadsToReset) {
        if(threadsToReset.toUpperCase().contains("R")) StData.threadManager.RT = new RenderThread();
        if(threadsToReset.toUpperCase().contains("L")) StData.threadManager.LT = new LogicThread();
        if(threadsToReset.toUpperCase().contains("A")) StData.threadManager.AT = new AudioThread();
        if(threadsToReset.toUpperCase().contains("B")) StData.threadManager.BGT = new BackgroundThread();
    }

	public static BufferedImage getTextureAsBufferedImage(String Tid) {
		try{
			return (BufferedImage) getTexture(Tid);
		} catch (ClassCastException e){
			Image in = getTexture(Tid);
			BufferedImage bf = new BufferedImage(in.getWidth(null), in.getHeight(null), BufferedImage.TYPE_INT_ARGB);
			Graphics g = bf.getGraphics();
			g.drawImage(in,0,0,null);
			g.dispose();
			return bf;
		}
	}
}

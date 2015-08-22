package dcode.games.uEngine2.tools;

import dcode.games.uEngine2.BGTasks.internalTasks.DelayedInputresize;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.uGameSetup;
import dcode.games.uEngine2.window.Canvas;
import dcode.games.uEngine2.window.Window;
import dcode.games.uEngine2.window.canvases.*;

/**
 * Created by dusakus on 13.05.15.
 */
public class Resize {
    public static void updateRenderingSetup() {
        updateRenderingSetup(StData.setup.fullscreen);
    }

    public static void updateRenderingSetup(uGameSetup.FullMODE setting) {
        StData.LOG.println("Setting fullscren mode to " + setting, "D");
        StData.threadManager.canvas = null;
        Canvas c = null;
        switch (setting) {
            case nope:
                c = new W_Std();
                if (StData.threadManager.window != null) StData.threadManager.window.rimuw();
                StData.threadManager.window = new Window(c, false);
                break;
            case box:
                c = new FS_Box();
                if (StData.threadManager.window != null) StData.threadManager.window.rimuw();
                StData.threadManager.window = new Window(c, true);
                break;
            case colored_box:
                c = new FS_CBox();
                if (StData.threadManager.window != null) StData.threadManager.window.rimuw();
                StData.threadManager.window = new Window(c, true);
                break;
            case stretch:
                c = new FS_Stretch();
                if (StData.threadManager.window != null) StData.threadManager.window.rimuw();
                StData.threadManager.window = new Window(c, true);
                break;
            case scaled_box:
                if (StData.threadManager.window != null) StData.threadManager.window.rimuw();
                c = new FS_scBox();
                StData.threadManager.window = new Window(c, true);
                break;
            case colored_scaled_box:
                c = new FS_scCBox();
                if (StData.threadManager.window != null) StData.threadManager.window.rimuw();
                StData.threadManager.window = new Window(c, true);
                break;
            case setup_util:
                c = new W_Eng();
                if (StData.threadManager.window != null) StData.threadManager.window.rimuw();
                StData.threadManager.window = new Window(c, false);
                break;
            default:
                StData.LOG.println("Unknown fullscreen mode, setting to window", "E2");
                updateRenderingSetup(uGameSetup.FullMODE.nope);
        }
        StData.threadManager.canvas = c;
        if (StData.threadManager.canvas == null){
            StData.LOG.println("CANVAS NOT CREATED WHILE SWITCHING RENDER MODE", "E5");
        }
        try {
            StData.threadManager.PW.updateFSOffsets();
            StData.threadManager.window.registerListeners(StData.threadManager.KW, StData.threadManager.PW);
        } catch (Exception e){
            StData.LOG.println("Unable to update mouse offsets settings, queued for later");
            Shortcuts.registerOneTimeBGTask(new DelayedInputresize(), true);
        }
    }
}

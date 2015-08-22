package dcode.games.uEngine2.BGTasks.internalTasks;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.tools.Shortcuts;

/**
 * Created by dusakus on 30.05.15.
 */
public class DelayedInputresize extends PBGTask {
    private int delay = 8;

    public DelayedInputresize() {
        this.TaskPriority = PRIORITY_LOW;
    }

    @Override
    public boolean isReady() {
        delay--;
        return delay < 0;
    }

    @Override
    public void perform() {

        try {
            StData.threadManager.PW.updateFSOffsets();
            StData.threadManager.window.registerListeners(StData.threadManager.KW, StData.threadManager.PW);
        } catch (Exception e) {
            StData.LOG.println("Unable to update mouse offsets settings, queued for later");
            Shortcuts.registerOneTimeBGTask(new DelayedInputresize(), true);
        }
    }
}

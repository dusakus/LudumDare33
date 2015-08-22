package dcode.games.uEngine2.BGTasks.internalTasks;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.StData;

import java.io.File;
import java.io.IOException;

/**
 * Created by dusakus on 29.03.15.
 */
public class CreateStorageContainer extends PBGTask {

    private File c;
    private boolean ov;

    public CreateStorageContainer(File container, boolean overwrite) {
        c = container;
        ov = overwrite;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void perform() {
        if (c.exists() && !ov) {
            StData.LOG.println("[LSTOR]: file named " + c.getName() + " exists, creation cancelled");
            this.done = true;
            return;
        } else if (c.exists() && ov) {
            StData.LOG.println("[LSTOR]: file named " + c.getName() + " exists, recreating");
            c.renameTo(new File(c.getParentFile(), c.getName() + "_oldOrBorked"));
        }

        //Step 1: create file
        try {
            c.createNewFile();
        } catch (IOException e) {
            StData.LOG.printerr(e, "Failed to create file " + c.getName(), "E2");
        }

        //Step 2: create Writer
        //TODO: FINISH dys
    }
}

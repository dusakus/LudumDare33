package dcode.games.uEngine2.BGTasks.internalTasks;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.StData;

import java.io.File;

/**
 * Created by dusakus on 29.03.15.
 */
public class LoadConfigContainer extends PBGTask {

    File source;
    String storeAsID = "NONE";

    public LoadConfigContainer(String dataID) {

        storeAsID = dataID;

        source = new File(System.getProperty("user.dir") + "/DCODE/uEngine2/" + StData.setup.safeName + "/config/" + storeAsID + ".uec");
        if (!source.exists()) {
            StData.generalBGT.LPTasks.add(new CreateStorageContainer(source, true));
            source = null;
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void perform() {
        if (source == null) return;
    }
}

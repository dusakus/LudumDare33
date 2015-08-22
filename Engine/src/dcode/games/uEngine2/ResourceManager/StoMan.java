package dcode.games.uEngine2.ResourceManager;

import dcode.games.uEngine2.ResourceManager.DSU.DSU_NODE;

/**
 * Created by dusakus on 29.03.15.
 */
public class StoMan {

    DSU_NODE STORAGE; //NOPE NOPE NOPE, cant use DSU for configuration storage, try hash maps ]:I

    public StoMan() {
        STORAGE = new DSU_NODE(0, false);

    }


    private class dataContainer {


    }

    private class lineContainer {


    }
}

package dcode.games.uEngine2.games.LD33;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.LD33.other.ItemQueueA;
import dcode.games.uEngine2.games.LD33.other.playSprite;
import dcode.games.uEngine2.games.LD33.terrainOB.TerrainObject;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by dusakus on 09.02.15.
 */
public class LStData {

    public static final int MODE_ERROR = -1;
    public static final int MODE_INIT = 0;
    public static final int MODE_LOADBASE = 1;
    public static final int MODE_RUN = 5;
    public static final int MODE_INGAME_1 = 21;
    public static final int MODE_INGAME_2 = 22;

    public static int currentMode = 0;
    public static int currentStatus = 0;
    public static int ERRORCODE = 0;
    public static IIp currentInputProcessor = new IIp() {
        @Override
        public void upPressed() {

            if (LStData.currentMode == LStData.MODE_INGAME_1) {
                ((playSprite) StData.currentGC.currentSC.sprites[4]).goUP();
            }
        }

        @Override
        public void downPressed() {

            if (LStData.currentMode == LStData.MODE_INGAME_1) {
                ((playSprite) StData.currentGC.currentSC.sprites[4]).goDOWN();
            }
        }

        @Override
        public void leftPressed() {

        }

        @Override
        public void rightPressed() {

        }

        @Override
        public void confirmPressed() {

        }
    };

    public static int playerONSCREENshift = 160;
    public static int enemyONSCREENshift = 160;

    public static double playerPosition = 160;
    public static double playerSpeed = 5.1f;

    public static int playerLane = 3;
    public static double enemyPosition = 40;
    public static double enemySpeed = 5.1f;

    public static int enemyLane = 3;

    public static LinkedList<String> blocks = new LinkedList<String>();
    public static int tileCounter = 0;


    public static String enviroKey = "standard";

    //You chase mode:
    public static ItemQueueA chase_itemQ;
    public static ArrayList<TerrainObject> terrainObjects = new ArrayList<TerrainObject>();
    public static int laneFirstY = 90;
    public static int laneWidth = 20;

    public static int getLocation() {
        return (int) playerPosition - playerONSCREENshift;
    }
}


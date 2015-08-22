/*
 *     <---======[ D-LIC open code 0.3 ]======--->
 *          This file is part of a project
 *          licensed with the DLoc license.
 *  Project's folder should contain the license file
 *                      _______
 *                     /dusakus\
 *         <=>---<>--{2013 stuDIO}--<>---<=>
 *
 */
package dcode.games.uEngine.dcctools;


/**
 *
 * @author dusakus
 */
public class Delay{

    public static final int DELAY_TIMED = 1;
    public static final int DELAY_TICKED = 2;
    public static final int DELAY_RETRY = 3;

    private int TYPE = 0;
    private int DEFAULT = 100;
    private boolean AUTORESET = true;

    private int ticksLeft = DEFAULT;
    private long lastNano = 0L;

    public Delay(int type, int value, boolean autoReset) {
        if (type > 0 && type < 4) {
            TYPE = type;
        } else {
            TYPE = 3;
        }
        DEFAULT = value;
        ticksLeft = DEFAULT;
        AUTORESET = autoReset;
        lastNano = System.nanoTime();

    }

    public Delay(int value) {
        TYPE = 3;
        DEFAULT = value;
        ticksLeft = DEFAULT;
        lastNano = System.nanoTime();
    }

    public void reset() {
        ticksLeft = DEFAULT;
        lastNano = System.nanoTime();
    }
    
    public void newDelay(int nvVal){
        DEFAULT = nvVal;
        reset();
    }

    public boolean doNow() {
        switch (TYPE) {
            case DELAY_TIMED:
                return timed();
            case DELAY_TICKED:
                if (ticksLeft <= 0) {
                    if (AUTORESET) {
                        reset();
                    }
                    return true;
                } else {
                    return false;
                }

            case DELAY_RETRY:
                if (ticksLeft <= 0) {
                    if (AUTORESET) {
                        reset();
                    }
                    return true;
                } else {
                    ticksLeft--;
                    return false;
                }
        }

        return false;
    }

    public boolean Tick() {
        if (ticksLeft > 0) {
            ticksLeft--;
        }

        return false;
    }

    private boolean timed() {
        if(System.nanoTime() - lastNano >= DEFAULT){
            if(AUTORESET){
                lastNano = System.nanoTime();
            }
            return true;
        }
        return false;

    }

}

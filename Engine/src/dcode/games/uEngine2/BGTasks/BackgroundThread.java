/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.BGTasks;

import dcode.games.uEngine2.StData;

/**
 * @author dusakus
 */
public class BackgroundThread extends Thread {

    private static int threadIteration = -1;

    //Loop variables
    public int LOOP_TPS = StData.setup.TPS_BG;
    public int LOOP_ticks = 0;
    public boolean LOOP_RUN = true;
    public boolean LOOP_Recalculate = false;

    public BackgroundThread() {

        LOOP_TPS = StData.setup.TPS_BG;
    }

    @Override
    public void run() {
        //Timming variables
        threadIteration++;
        this.setName("Background Thread ITER" + threadIteration);
        int timeStep = 1000000000 / LOOP_TPS;
        long currentTime = System.nanoTime();
        long nextTime = System.nanoTime() + timeStep;

        //FPS counter variables
        int FPS = 0;
        long lastFPSdraw = System.nanoTime();
        //LOOP
        while (StData.gameIsRunning && LOOP_RUN) {

            //waiting for next planned time
            while (currentTime < nextTime) {
                try {
                    Thread.sleep(10);
                    currentTime = System.nanoTime();
                } catch (Exception e) {
                    currentTime = System.nanoTime();
                }
            }
            nextTime += timeStep;

            //The real part of this loop
            try {
                StData.LOG.dumpBuffer();
                StData.generalBGT.processContent();  // The generalBGTasks are the only thing that gets processed when game is frozen
                if (!StData.gameFreeze) StData.currentGC.currentBGT.processContent();
            } catch (Exception e) {
                StData.LOG.printerr(e, "[BGThread] task crashed", "E2");
            }

            //End of the real part of this loop
            if (StData.setup.debug) {
                FPS++;
                if (System.nanoTime() - lastFPSdraw >= 1000000000L) {
                    StData.LOG.println("[BGThread] Tick No." + LOOP_ticks + ", measured TPS: " + FPS, "D");
                    FPS = 0;
                    lastFPSdraw = System.nanoTime();
                }
            }
            LOOP_ticks++;
            if (LOOP_Recalculate) {
                StData.LOG.println("[BGThread] Tick No. " + LOOP_ticks + ", setting new loop speed: " + LOOP_TPS, "D");
                timeStep = 1000000000 / LOOP_TPS;
                currentTime = System.nanoTime();
                nextTime = System.nanoTime() + timeStep;
                LOOP_Recalculate = false;
            }
        }
    }

    public void addTask(PBGTask task) {
        StData.currentGC.currentBGT.WaitingTasks.offer(task);
    }
}

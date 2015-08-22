/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2;

import dcode.games.uEngine2.BGTasks.BackgroundTasks;
import dcode.games.uEngine2.BGTasks.BackgroundThread;
import dcode.games.uEngine2.GFX.RenderThread;
import dcode.games.uEngine2.LOGIC.LogicTasks;
import dcode.games.uEngine2.LOGIC.LogicThread;
import dcode.games.uEngine2.SFX.AudioThread;
import dcode.games.uEngine2.SFX.tslib.TinySound;
import dcode.games.uEngine2.input.KeyWrapper;
import dcode.games.uEngine2.input.PointerWrapper;
import dcode.games.uEngine2.tools.Resize;
import dcode.games.uEngine2.window.Canvas;
import dcode.games.uEngine2.window.Window;

/**
 * @author dusakus
 */
public class ThreadManager {

    public RenderThread RT;
    public AudioThread AT;
    public LogicThread LT;
    public BackgroundThread BGT;

    public KeyWrapper KW;
    public PointerWrapper PW;

    public Canvas canvas;
    public Window window;
    public boolean wasFreezed = false;

    public ThreadManager() {
        RT = new RenderThread();
        AT = new AudioThread();
        LT = new LogicThread();
        BGT = new BackgroundThread();
    }

    public void startEngine() {
        Resize.updateRenderingSetup();

        KW = new KeyWrapper();
        PW = new PointerWrapper();

	    RT.start();
	    if (StData.setup.soundEnabled) AT.start();
	    LT.start();
	    BGT.start();

	    window.registerListeners(KW, PW);

    }

    void setInputHandler(PInputHandler IH) {
        KW.setInputHandler(IH);
        PW.setInputHandler(IH);
    }

    public void updateCanvas() {
        if (canvas != null)
            canvas.render();
    }

    void monitorThreads() {
        StData.LOG.println("[Thread Manager] enering monitor mode");

        Thread.currentThread().setName("Thread Monitor");

        //last tick count
        int ltc_RT = -1000;
        int ltc_AT = -1000;
        int ltc_LT = -1000;

        int LTCheckCount = 0;

        int timeStep = 100000000;
        long currentTime = System.nanoTime();
        long nextTime = System.nanoTime() + timeStep;

        while (StData.gameIsRunning) {

            //tick count
            int tc_RT = RT.LOOP_ticks;
            int tc_AT = AT.LOOP_ticks;
            int tc_LT = LT.LOOP_ticks;
            if (!StData.gameFreeze) {
                if (wasFreezed) {
                    StData.LOG.println("[Thread Manager] focus regained, engine resumed");
                    wasFreezed = false;
                    BGT.LOOP_TPS = StData.setup.TPS_BG;
                    BGT.LOOP_Recalculate = true;
                }
                if (!RT.isAlive()) {
                    StData.LOG.println("[Thread Manager] Render thread died, recreating", "E3");
                    StData.LOG.dumpBuffer();
                    RT = new RenderThread();
                    RT.start();
                    RT.LOOP_ticks = ltc_RT + 1;
                }
                if (StData.setup.soundEnabled) if (!AT.isAlive()) {
                    StData.LOG.println("[Thread Manager] Audio thread died, recreating (C2)", "E3");
                    StData.LOG.dumpBuffer();
                    AT = new AudioThread();
                    AT.start();
                    AT.LOOP_ticks = ltc_AT + 1;
                }
                if (!LT.isAlive()) {
                    StData.LOG.println("[Thread Manager] Logic thread died, recreating, game might be unplayable (C2)", "E3");
                    StData.LOG.dumpBuffer();
                    LT = new LogicThread();
                    LT.start();
                    LT.LOOP_ticks = ltc_LT + 1;
                }
                if (!BGT.isAlive()) {
                    StData.LOG.println("[Thread Manager] Background thread died, recreating, game might be unplayable (C2)", "E3");
                    StData.LOG.dumpBuffer();
                    BGT = new BackgroundThread();
                    BGT.start();
                }

                if (tc_RT - ltc_RT < StData.setup.FPS / 20) {
                    StData.LOG.println("[Thread Manager] Render thread might be stuck", "E1");
                    StData.LOG.dumpBuffer();
                }

                if (tc_RT - ltc_RT < StData.setup.FPS / 200) {
                    StData.LOG.println("[Thread Manager] Render thread is stuck, resetting", "E3");
                    StData.LOG.dumpBuffer();
                    //RT.stop();
                    RT = new RenderThread();
                    RT.start();
                }

                if (StData.setup.soundEnabled) if (tc_AT - ltc_AT < StData.setup.TPS_MSX / 20) {
                    StData.LOG.println("[Thread Manager] Audio thread might be stuck", "E1");
                    StData.LOG.dumpBuffer();
                }

                if (StData.setup.soundEnabled) if (tc_AT - ltc_AT < StData.setup.TPS_MSX / 200) {
                    StData.LOG.println("[Thread Manager] Audio thread is stuck, resetting", "E3");
                    StData.LOG.dumpBuffer();
                    //AT.stop();
                    AT = new AudioThread();
                    AT.start();
                }

                if (tc_LT - ltc_LT < StData.setup.TPS_logic / 50) {
                    StData.LOG.println("[Thread Manager] Logic thread might be stuck", "E2");
                    StData.LOG.dumpBuffer();
                }

                if (tc_LT == ltc_LT) {
                    if (LTCheckCount > 100) {
                        StData.LOG.println("[Thread Manager] Logic thread is stuck, game will be restarted", "E6S");
                        StData.LOG.dumpBuffer();
                        //LT.stop();
                        reinitialize();
                        break;
                    } else {
                        LTCheckCount++;
                    }
                } else {
                    LTCheckCount = 0;
                }
            } else {
                if (!wasFreezed) {
                    StData.LOG.println("[Thread Manager] focus lost, engine paused");
                    BGT.LOOP_TPS = 2;
                    BGT.LOOP_Recalculate = true;
                    wasFreezed = true;
                }
            }
            ltc_RT = tc_RT;
            ltc_AT = tc_AT;
            ltc_LT = tc_LT;

            while (currentTime < nextTime) {
                try {
                    Thread.sleep(5);
                    currentTime = System.nanoTime();
                } catch (InterruptedException ignored) {
                }
            }
            nextTime += timeStep;
        }
        StData.LOG.dumpBuffer();
    }

    private void reinitialize() {
        TinySound.shutdown();
        RT.LOOP_RUN = false;
        AT.LOOP_RUN = false;
        LT.LOOP_RUN = false;
        BGT.LOOP_RUN = false;
        StData.currentGC = new GameContainer();
        StData.lastMSG = "REINIT";
        StData.logicTasks = new LogicTasks();
        StData.generalBGT = new BackgroundTasks();
        StData.threadManager = null;
        new Thread(new Runnable() {

            @Override
            public void run() {
                Startup.StartGame(StData.GameInitializer);
            }
        }).start();
        window.rimuw();
    }


}

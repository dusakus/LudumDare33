/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2;

import dcode.games.uEngine.sound.SoundEngine;
import dcode.games.uEngine2.BGTasks.BackgroundTasks;
import dcode.games.uEngine2.GFX.eui.SCU;
import dcode.games.uEngine2.LOGIC.LogicTasks;
import dcode.games.uEngine2.ResourceManager.ResMan;
import dcode.games.uEngine2.SFX.tslib.TinySound;
import dcode.games.uEngine2.tools.Resize;
import dcode.games.uEngine2.tools.introPlayer;
import dcode.games.uEngine2.translator.Translator;

import java.awt.event.KeyEvent;
import java.io.File;

/**
 * @author dusakus
 */
public class Startup {

    public static void StartGame(PuGameBase GB) {
        //System.setProperty("sun.java2d.opengl","True"); // does that change anything? (Except for crashing the game)
        Thread.currentThread().setName("ITNI");
        StData.GameInitializer = GB;
        StData.setup = GB.setup;
        StData.gameStorageDirectory = new File(System.getProperty("user.dir") + "/DCODE/uEngine2/" + StData.setup.safeName);
        if (!StData.gameStorageDirectory.exists()) StData.gameStorageDirectory.mkdirs();
        StData.LOG = new DCoutputH(GB.setup.debug);
        StData.setup.loadSettings();
        StData.threadManager = new ThreadManager();
        StData.resources = new ResMan();
        StData.currentGC = new GameContainer();
        StData.logicTasks = new LogicTasks();
        StData.generalBGT = new BackgroundTasks();
        if (StData.setup.enableTranslator) StData.translator = new Translator(StData.setup.defaultLangId);
	    StData.SE = new SoundEngine();
	    StData.threadManager.startEngine();
        StData.threadManager.setInputHandler(GB.initialInputHandler);
        if (!GB.setup.debug) new introPlayer().playIntro();
        //checkSCU();               //Will be part of uE2b
        try {
            GB.contentInitializer.loadInitialGameContent();
        } catch (Exception e) {
            StData.LOG.printerr(e, "GAME MIGHT BE BORKED!!!", "E3");
        }
	    Resize.updateRenderingSetup(StData.setup.fullscreen);
        StData.threadManager.monitorThreads();
        TinySound.shutdown();
        StData.LOG.println("MONITOR THREAD CEASED BEING ALIVE, EXITTING");
        StData.LOG.dumpBuffer();
        StData.LOG.END("uEngine2: BaYo!");

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        GB.contentInitializer.engineStopped();
    }

    private static void checkSCU() {
        if (StData.doEnterSetup){
            StData.doEnterSetup = false;
            new SCU();
        }
        if (!StData.INSETUP) {
            try {
                for (int i = 0; i < 600; i++) {
                    if (!StData.INSETUP && StData.threadManager.KW.isKeyHeld(KeyEvent.VK_F8)) new SCU();
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (StData.INSETUP && StData.gameIsRunning) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
        }
    }
}

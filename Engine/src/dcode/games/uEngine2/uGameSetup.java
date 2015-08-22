/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2;

import dcode.games.uEngine2.localStorage.configStorage.CContainer;

import java.io.File;

/**
 * @author dusakus
 */
public class uGameSetup {

    //Debug mode
    public boolean debug = false;                            //enable debug output and features

    //Game name
    public String safeName = "uGame_unnamed";                //Safe name, will be used in file storage
    public String screenName = "UNNAMED";                    //Will be used in messages and generic renderers
    public String windowTitle = "Unnamed uGame";            //You guessed it, will be used as window title, a suffix will be added " | in DCode uEngine 2.[ver] r[rel]"

    // Basic Render information
    public int width = 320;                                    //Width of internal canvas
    public int height = 240;                                //Height of internal canvas
    public int scale = 3;                                   //Scalig applied to internal canvas
    public FullMODE fullscreen = FullMODE.nope;                //WARNING: this will only work if supported by the runtime, will override scalling


    // Extended Render information
    public int postProcCount = 4;                            //number of aviable postProcessing Sockets

    // Speed settings
    public int FPS = 60;                                    //Render thread target speed
    public int TPS_logic = 60;                            //MAIN Logic thread target speed, if less than 1 logic tasks will be tied into render thread
    public int TPS_MSX = 0;                                //Game Audio steps per second
    public int TPS_BG = 60;                                //Background logic thread target speed, if 0 background tasks will be disabled, if -1 they will run without limit, if less than -1 they will be tied to render thread

    // Sprite system settings
    public int spriteTableSize = 64;                        //Size of the main sprite table
    public int spriteLayerSize = 16;                        //Sprite Count per layer
    public boolean enableSpriteWrappers = false;             //enable SpriteWrappers
    public boolean enableSpriteZSorting = false;

    // Translation module settings
    public boolean enableTranslator = false;                //If the translation module should be enabled, will BORK if default lang doesn't exist
    public String defaultLangId = "baseLang";                //the default language, file MUST EXIST

    // Sound system settings               					//All disabled by default
    public boolean soundEnabled = false;
    public int wavChannelCount = 0;                            //how many WAV playback channels are needed
    public int scriptChannelCount = 0;                        //how many SoundScript playback channels are needed
    public int effectChannelCount = 0;                      //how many effect (short WAV) playback channels are needed
    public int effectTableSize = 0;                            //size of the effect table
    public int generatorChannelCount = 0;                    //how many sound generators should be created

    // Fonts
    public int stringBufferSize = 16;


    public boolean frezeOnFocusLost = true;


    public void loadSettings() {
        StData.engineConfig = new CContainer(new File(StData.gameStorageDirectory, "engine.ugc"));

        StData.engineConfig.configHeader = "This file contains engine configuration, generated¬from game's initial settings.¬¬Please don't touch if you don't know what are you doing.¬¬These settings are aviable in StartupSetupUtility";

        //base settings
        debug = StData.engineConfig.getBool("uEngine2.base.Enable Debug Mode", debug);
        StData.engineConfig.getLeaf("uEngine2.base.Enable Debug Mode", false).setBComment("Enables debug mode, prepare for output spam");

        //display settings
        width = StData.engineConfig.getInt("uEngine2.display.Base width", width);
        StData.engineConfig.getLeaf("uEngine2.display.Base width", false).setIComment("Sets the internal resolution for game rendering");
        height = StData.engineConfig.getInt("uEngine2.display.Base height", height);
        scale = StData.engineConfig.getInt("uEngine2.display.Pixel size", scale);
        StData.engineConfig.getLeaf("uEngine2.display.Pixel size", false).setIComment("Sets the default scaling applied to canvas after rendering");
        fullscreen = FullMODE.valueOf(StData.engineConfig.getString("uEngine2.display.Screen mode", fullscreen.name()));
        StData.engineConfig.getLeaf("uEngine2.display.Screen mode", false).setSComment("Display modes: nope (window), box, stretch, scaled_box, colored_box, colored_scaled_box");

        //performance settings
        FPS = StData.engineConfig.getInt("uEngine2.speed.FPS", FPS);
        TPS_MSX = StData.engineConfig.getInt("uEngine2.speed.Sound thread TPS", TPS_MSX);
        TPS_logic = StData.engineConfig.getInt("uEngine2.speed.Logic thread TPS", TPS_logic);
        TPS_BG = StData.engineConfig.getInt("uEngine2.speed.Background thread TPS", TPS_BG);

        //rendering setup


        postProcCount = StData.engineConfig.getInt("uEngine2.render.PostProcCount", postProcCount);
        spriteTableSize = StData.engineConfig.getInt("uEngine2.render.sprite table size", spriteTableSize);
        spriteLayerSize = StData.engineConfig.getInt("uEngine2.render.sprite layer size", spriteLayerSize);
        enableSpriteWrappers = StData.engineConfig.getBool("uEngine2.render.sprite wrappers", enableSpriteWrappers);
        enableSpriteZSorting = StData.engineConfig.getBool("uEngine2.render.sprite Z-sort", enableSpriteZSorting);

        //audio setup
        soundEnabled = StData.engineConfig.getBool("uEngine2.audio.Audio Enabled", soundEnabled);
        StData.engineConfig.getLeaf("uEngine2.audio.Audio Enabled", false).setBComment("rly?");


        StData.engineConfig.save();
        StData.LOG.debug = debug;

    }
    public enum FullMODE {
        nope, box, stretch, scaled_box, colored_box, setup_util, colored_scaled_box;
    }
}

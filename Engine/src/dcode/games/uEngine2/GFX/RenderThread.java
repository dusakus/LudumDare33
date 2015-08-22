/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.GFX;

import dcode.games.uEngine2.GFX.postproc.IGrfPostProcessor;
import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.GFX.sprites.SpriteWrapper;
import dcode.games.uEngine2.StData;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * @author dusakus
 */
public class RenderThread extends Thread {
    private static int threadIteration = -1;

    //Loop variables
    public int LOOP_TPS = 60;
    public int LOOP_ticks = 0;
    public boolean LOOP_RUN = true;
    public boolean LOOP_Recalculate = true;

    public boolean forceSpriteSort = false;

    public RenderThread() {

        LOOP_TPS = StData.setup.FPS;
    }

    @Override
    public void run() {


        threadIteration++;
        this.setName("Render Thread ITER" + threadIteration);
        //Timming variables
        int timeStep = 1000000000 / LOOP_TPS;
        long currentTime = System.nanoTime();
        long nextTime = System.nanoTime() + timeStep;

        //FPS counter variables
        int FPS = 0;
        long lastFPSdraw = System.nanoTime();
        //LOOP
        while (StData.gameIsRunning && LOOP_RUN) {


            //waiting for next planned time
            while (currentTime < nextTime || StData.gameFreeze) {
                try {
                    Thread.sleep(0, 500000);
                    if (StData.gameFreeze) {
                        Thread.sleep(25);
                        nextTime = System.nanoTime() + timeStep;
                    }
                    currentTime = System.nanoTime();
                } catch (InterruptedException ex) {
                }
            }

            nextTime += timeStep;

            //END OF TIMING STUFF
            //==================================================================
            if (!StData.gameFreeze) {

                StData.isRendering = true;
                systemDraw(StData.NextFrame.createGraphics());

                StData.threadManager.updateCanvas();


                if (StData.NextFrame == null)
                    StData.NextFrame = new BufferedImage(StData.setup.width, StData.setup.height, BufferedImage.TYPE_INT_ARGB);

                Graphics2D G2D = StData.NextFrame.createGraphics();

                ScreenContent sc = StData.currentGC.currentSC;

                if (StData.setup.enableSpriteZSorting || forceSpriteSort) sortSprites(sc);

                if (StData.setup.enableSpriteWrappers) {
                    drawLayers(sc.layers_Background, G2D);
                    drawSprites(sc.sprites_back, G2D);
                    drawSpriteWrappers(sc.spriteW_back, G2D);
                    drawLayers(sc.layers_Center, G2D);
                    drawSprites(sc.sprites_middle, G2D);
                    drawSpriteWrappers(sc.spriteW_middle, G2D);
                    drawLayers(sc.layers_Foreground, G2D);
                    drawSprites(sc.sprites_front, G2D);
                    drawSpriteWrappers(sc.spriteW_front, G2D);
                    drawLayers(sc.layers_Overlay, G2D);
                } else {
                    drawLayers(sc.layers_Background, G2D);
                    drawSprites(sc.sprites_back, G2D);
                    drawLayers(sc.layers_Center, G2D);
                    drawSprites(sc.sprites_middle, G2D);
                    drawLayers(sc.layers_Foreground, G2D);
                    drawSprites(sc.sprites_front, G2D);
                    drawLayers(sc.layers_Overlay, G2D);
                }

                if (StData.setup.postProcCount > 0)
                    postProcess(StData.NextFrame); //TODO try to find a way to move post processing AFTER scaling!!!
                StData.isRendering = false;

            }
            //END OF TASKS
            //==================================================================
            if (StData.setup.debug) {
                FPS++;
                if (System.nanoTime() - lastFPSdraw >= 1000000000L) {
                    StData.LOG.println("[Render Thread] Tick No." + LOOP_ticks + ", measured TPS: " + FPS, "D");
                    FPS = 0;
                    lastFPSdraw = System.nanoTime();
                }
            }

            if (StData.threadManager.RT != this) {
                LOOP_RUN = false;
                StData.LOG.println("MULTIPLE INSTANCES OF RENDER THREAD DETECTED", "E3");
                this.interrupt();
            }
            LOOP_ticks++;
            if (LOOP_Recalculate) {
                StData.LOG.println("[Render Thread] Tick No. " + LOOP_ticks + ", setting new loop speed: " + LOOP_TPS, "D");
                timeStep = 1000000000 / LOOP_TPS;
                currentTime = System.nanoTime();
                nextTime = System.nanoTime() + timeStep;
                LOOP_Recalculate = false;
            }
        }
    }

    public void sortSprites(ScreenContent sc) {
        forceSpriteSort = false;
        for (int pos = 0; pos < sc.sprites_back.length - 1; pos++) {
            int loc = pos;
            while (loc >= 0) {
                if (sc.sprites_back[loc] >= 0 && sc.sprites_back[loc + 1] >= 0 && sc.sprites[sc.sprites_back[loc]] != null && sc.sprites[sc.sprites_back[loc + 1]] != null && sc.sprites[sc.sprites_back[loc]].getZ() > sc.sprites[sc.sprites_back[loc + 1]].getZ()) {
                    int tmp = sc.sprites_back[loc + 1];
                    sc.sprites_back[loc + 1] = sc.sprites_back[loc];
                    sc.sprites_back[loc] = tmp;
                }
                loc--;
            }
        }
        for (int pos = 0; pos < sc.sprites_middle.length - 1; pos++) {
            int loc = pos;
            while (loc >= 0) {
                if (sc.sprites_middle[loc] >= 0 && sc.sprites_middle[loc + 1] >= 0 && sc.sprites[sc.sprites_middle[loc]] != null && sc.sprites[sc.sprites_middle[loc + 1]] != null && sc.sprites[sc.sprites_middle[loc]].getZ() > sc.sprites[sc.sprites_middle[loc + 1]].getZ()) {
                    int tmp = sc.sprites_middle[loc + 1];
                    sc.sprites_middle[loc + 1] = sc.sprites_middle[loc];
                    sc.sprites_middle[loc] = tmp;
                }
                loc--;
            }
        }
        for (int pos = 0; pos < sc.sprites_front.length - 1; pos++) {
            int loc = pos;
            while (loc >= 0) {
                if (sc.sprites_front[loc] >= 0 && sc.sprites_front[loc + 1] >= 0 && sc.sprites[sc.sprites_front[loc]] != null && sc.sprites[sc.sprites_front[loc + 1]] != null && sc.sprites[sc.sprites_front[loc]].getZ() > sc.sprites[sc.sprites_front[loc + 1]].getZ()) {
                    int tmp = sc.sprites_front[loc + 1];
                    sc.sprites_front[loc + 1] = sc.sprites_front[loc];
                    sc.sprites_front[loc] = tmp;
                }
                loc--;
            }
        }
        if (StData.setup.enableSpriteWrappers) {
            for (int pos = 0; pos < sc.spriteW_back.length - 1; pos++) {
                int loc = pos;
                while (loc >= 0) {
                    if (sc.spriteW_back[loc] != null && sc.spriteW_back[loc + 1] != null && sc.spriteW_back[loc].getZ() > sc.spriteW_back[loc + 1].getZ()) {
                        SpriteWrapper tmp = sc.spriteW_back[loc + 1];
                        sc.spriteW_back[loc + 1] = sc.spriteW_back[loc];
                        sc.spriteW_back[loc] = tmp;
                    }
                    loc--;
                }
            }
            for (int pos = 0; pos < sc.spriteW_middle.length - 1; pos++) {
                int loc = pos;
                while (loc >= 0) {
                    if (sc.spriteW_middle[loc] != null && sc.spriteW_middle[loc + 1] != null && sc.spriteW_middle[loc].getZ() > sc.spriteW_middle[loc + 1].getZ()) {
                        SpriteWrapper tmp = sc.spriteW_middle[loc + 1];
                        sc.spriteW_middle[loc + 1] = sc.spriteW_middle[loc];
                        sc.spriteW_middle[loc] = tmp;
                    }
                    loc--;
                }
            }
            for (int pos = 0; pos < sc.spriteW_front.length - 1; pos++) {
                int loc = pos;
                while (loc >= 0) {
                    if (sc.spriteW_front[loc] != null && sc.spriteW_front[loc + 1] != null && sc.spriteW_front[loc].getZ() > sc.spriteW_front[loc + 1].getZ()) {
                        SpriteWrapper tmp = sc.spriteW_front[loc + 1];
                        sc.spriteW_front[loc + 1] = sc.spriteW_front[loc];
                        sc.spriteW_front[loc] = tmp;
                    }
                    loc--;
                }
            }
        }
    }

    private void drawLayers(ArrayList<ILayer> array, Graphics2D G2D) {
        ILayer rmTask = null;
        for (int i = 0; i < array.size(); i++) {
            ILayer layer = array.get(i);
            if (layer.renderMe()) {
                layer.draw(G2D);
            }
            if (layer.removeMe()) {
                rmTask = layer;
            }
        }
        if (rmTask != null) {
            array.remove(rmTask);
        }
    }

    private void drawSprites(int[] array, Graphics2D G2D) {
        for (int i = 0; i < array.length; i++) {
            int b = array[i];
            if (b > 0) {
                Sprite s = StData.currentGC.currentSC.sprites[b];

                if (s != null) {
                    if (s.doCustomRender()) {
                        s.customRender(G2D);
                    } else {
                        String texKey = s.getTextureKey();
                        if (texKey == null) {
                            Image im = s.getCustomTexture();
                            if (im == null) {
                                StData.LOG.println("[RENDER]: sprite [" + i + ":" + texKey + "]:MODE_CustomTexture: no texture!", "D");
                            } else {
                                G2D.drawImage(im, s.getX(), s.getY(), null);
                            }
                        } else {
                            G2D.drawImage(StData.resources.grf.getTexture(texKey), s.getX(), s.getY(), null);
                        }
                    }
                }
            }
        }
    }

    private void postProcess(BufferedImage NextFrame) {
        for (int i = 0; i < StData.currentGC.currentSC.postProcessors.length; i++) {
            IGrfPostProcessor postProcessor = StData.currentGC.currentSC.postProcessors[i];
            if (postProcessor != null) {
                BufferedImage bf = StData.NextFrame;
                try {
                    if (postProcessor.enabled()) {
                        NextFrame = postProcessor.processFrame(NextFrame);
                        StData.NextFrame = NextFrame;
                    }
                } catch (Exception e) {
                    StData.LOG.printerr(e, "[RENDER] post processing on channel " + i + " failed", "E1");
                }
                if (StData.NextFrame == null) {
                    StData.NextFrame = bf;
                    StData.LOG.println("[RENDER] post processor on channel " + i + " is borked, it's " + postProcessor.getClass().getCanonicalName(), "E3");
                }
            }
        }
    }

    private void systemDraw(Graphics2D createGraphics) {
    }

    private void drawSpriteWrappers(SpriteWrapper[] array, Graphics2D G2D) {
        for (int i = 0; i < array.length; i++) {
            SpriteWrapper s = array[i];
            if (s.enabled) {
                if (s.doCustomRender()) {
                    s.customRender(G2D);
                } else {
                    String texKey = s.getTextureKey();
                    if (texKey == null) {
                        if (s.getCustomTexture() == null) {
                            StData.LOG.println("RT: sprite with key " + s.getTextureKey() + " haz no texturez", "D"); //TODO: better message ...
                        } else {
                            G2D.drawImage(s.getCustomTexture(), s.getX(), s.getY(), null);
                        }
                    } else {
                        G2D.drawImage(StData.resources.grf.getTexture(texKey), s.getX(), s.getY(), null);
                    }
                }
            }
        }
    }
}

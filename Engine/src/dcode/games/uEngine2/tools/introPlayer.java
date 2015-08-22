package dcode.games.uEngine2.tools;

import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.GFX.layers.ClearColorLayer;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.tools.ext.j2s.gifReader;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 01.05.15.
 * <p/>
 * <p/>
 * If you want your own intro here
 * read the comments below
 * it's really simple
 */
public class introPlayer {

    private gifReader frames;
    private int pY;
    private int pX;
    private double scX;
    private double scY;
    layerDraw ld;
    private int tmp;

    public void playIntro() {
        StData.LOG.println("loading intro");
        loadTextures();
        StData.LOG.println("Creating intro context");
        createContext();
        StData.LOG.println("Playing engine intro ]:V");
        playback();
        StData.LOG.println("intro done");
        clear();
    }

    private void clear() {
        frames = null;
        ld = null;
	    StData.currentGC.currentSC.layers_Foreground.clear();
    }

    private void playback() {
        tmp = StData.setup.FPS;
        Shortcuts.setRenderSpeed(48); //here you can set starting playback speed
        while (ld.running && StData.gameIsRunning) {
            try {
                Thread.sleep(64);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Shortcuts.setRenderSpeed(tmp);
    }

    private void createContext() {
        int w = StData.setup.width;
        int h = StData.setup.height;
        scX = 1;
        scY = 1;
        w -= frames.getFrame(0).getWidth();
        while (w > frames.getFrame(0).getWidth()) {
            scX += 1;
            scY += 1;
            w -= frames.getFrame(0).getWidth();
        }
        pX = w / 2;
        pY = (h - (int) (frames.getFrame(0).getHeight() * scY)) / 2;

        ld = new layerDraw(this);
        StData.currentGC.currentSC.layers_Foreground.add(new ClearColorLayer(Color.BLACK));
        StData.currentGC.currentSC.layers_Foreground.add(ld);
    }

    private void loadTextures() {
        frames = new gifReader();
        //Here you change the filename to a proper gif. You can also replace the original gif for a custom one
        frames.read(this.getClass().getResourceAsStream("/dcode/games/uEngine2/res/intranim.gif"));
    }

    private class layerDraw implements ILayer {

        introPlayer trgt;
        public boolean running = true;

        private int frame = 0;
        int enddelay = 6;

        public layerDraw(introPlayer i) {
            trgt = i;
        }

        @Override
        public void draw(Graphics2D G2D) {
            G2D.drawImage(trgt.frames.getFrame(frame).getScaledInstance((int) (trgt.frames.getFrameSize().getWidth() * trgt.scX), (int) (trgt.frames.getFrameSize().getHeight() * trgt.scY), BufferedImage.SCALE_REPLICATE), trgt.pX, trgt.pY, null);
            if (StData.threadManager.KW.isKeyHeld(KeyEvent.VK_F8) && running) {
                StData.doEnterSetup = true;
                running = false;
            } else if (frame == trgt.frames.getFrameCount() - 1) {
                if (enddelay < 0) {
                    running = false;
                } else {
                    enddelay--;
                }
            } else {
                //Here you can set specific frames where you want the intro to change playback speed
                if (frame == 2) Shortcuts.setRenderSpeed(40);
                if (frame == 72) Shortcuts.setRenderSpeed(5);
                if (frame == 80) Shortcuts.setRenderSpeed(8);
                if (frame == 90) Shortcuts.setRenderSpeed(15);
                frame++;
            }
        }

        @Override
        public boolean removeMe() {
            return !running;
        }

        @Override
        public boolean renderMe() {
            return running;
        }
    }
}

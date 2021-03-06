package dcode.games.uEngine2.games.LD33.other;

import dcode.games.uEngine.dcctools.Delay;
import dcode.games.uEngine2.GFX.sprites.Sprite;
import dcode.games.uEngine2.games.LD33.LStData;
import dcode.games.uEngine2.tools.Shortcuts;
import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;

/**
 * Created by dusakus on 22.08.2015.
 */
public class playSprite extends Sprite {

    private String animationKey;

    private String animationMode = "run";
    private int animationFrame = 0;

    private Delay framecounter = new Delay(10);
    private int FRAME_MAX = 6;
    private boolean lanewsap = false;
    private int lanewsap_mod = -2;

    public playSprite(String animID) {
        animationKey = animID;
    }

    @Override
    public int getX() {
        return LStData.playerONSCREENshift - 15;
    }

    @Override
    public int getY() {
        if (!lanewsap) {
            y = 60 + 20 * LStData.playerLane;
        } else {
            y += lanewsap_mod;
            if(y == 60 + 20 * LStData.playerLane) lanewsap = false;
        }
        return y;
    }

    @Override
    public String getTextureKey() {
        if (framecounter.doNow()) {
            animationFrame++;
            if (animationFrame >= FRAME_MAX) animationFrame = 0;
	        framecounter.newDelay(numbarTools.clamp((int) (15 - LStData.playerSpeed), 3, 15));
        }
        return animationKey + animationMode + animationFrame;
    }

    @Override
    public Image getCustomTexture() {
        return null;
    }

    @Override
    public boolean doCustomRender() {
        return false;
    }

    @Override
    public void customRender(Graphics2D G) {

    }

    public void goUP() {
        Shortcuts.log("Going up");
        if (LStData.playerLane > 1 && !lanewsap) {
            lanewsap = true;
            lanewsap_mod = -2;
            LStData.playerLane--;
        }
    }
    public void goDOWN() {
        Shortcuts.log("Going down");
        if (LStData.playerLane < 5 && !lanewsap) {
            lanewsap = true;
            lanewsap_mod = 2;
            LStData.playerLane++;
        }
    }
}

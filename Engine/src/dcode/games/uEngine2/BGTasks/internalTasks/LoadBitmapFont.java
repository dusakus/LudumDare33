/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.BGTasks.internalTasks;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.StData;

import java.awt.image.BufferedImage;

/**
 * @author dusakus
 */
public class LoadBitmapFont extends PBGTask {

    public static final String charList = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
            + "abcdefghijklmnopqrstuvwxyz"
            + "1234567890"
            + "!?#*)(][><,.:;-+_=&~^$@\"/\\";
    private String texKey = "UNKNOWN";
    private String addr = "missing";
    private int charWidth = 1;
    private int charHeight = 1;
    private String pattern;

    public LoadBitmapFont(String location, String basekey, int charW, int charH, String charPattern) {
        TaskPriority = PRIORITY_LOW;
        texKey = basekey;
        addr = location;
        charHeight = charH;
        charWidth = charW;
        pattern = charPattern;
        StData.LOG.println("Requested loading font from " + location + " as " + basekey);
    }

    @Override
    public boolean isReady() {
        if (StData.resources.grf.loader_internal == null) {
            return false;
        }
        return true;
    }

    @Override
    public void perform() {
        StData.LOG.println("BGTask LBT: trying to load texture from: " + addr, "D");
        StData.resources.grf.loader_internal.loadSingleTexture(addr, texKey);
        BufferedImage bf = StData.resources.grf.getTexture(texKey);
        int xSprites = bf.getWidth() / charWidth;
        int ySprites = bf.getHeight() / charHeight;
        int charId = 0;

        for (int i = 0; i < xSprites; i++) {
            for (int j = 0; j < ySprites; j++) {
                BufferedImage o = bf.getSubimage(charWidth * i, charHeight * j, charWidth, charHeight);
                if (pattern == null) {
                    StData.resources.grf.registerTexture(o, texKey + "C" + charId);
                } else {
                    StData.resources.grf.registerTexture(o, texKey + "C" + charList.indexOf(pattern.charAt(charId)));
                }
                charId++;
            }
        }

        done = true;
    }
}

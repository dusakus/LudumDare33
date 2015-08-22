/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.BGTasks.internalTasks;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.ResourceManager.TexMan;
import dcode.games.uEngine2.StData;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author dusakus
 */
public class prepareString extends PBGTask {

    private TexMan.StringContainer container = null;

    public prepareString(TexMan.StringContainer stringBuffer) {
        StData.LOG.println("Requested creation of string buffer for " + stringBuffer.text + " using font " + stringBuffer.texKey);
        container = stringBuffer;
        this.TaskPriority = PRIORITY_LOW;
    }

    @Override
    public boolean isReady() {
        //StData.LOG.println("STILL WAITING");
        return StData.resources.grf.isTextureAviable(container.texKey);
    }

    @Override
    public void perform() {
        BufferedImage sizecheck = StData.resources.grf.getChar(container.texKey, 'A');
        int charWidth = sizecheck.getWidth();
        BufferedImage out = new BufferedImage(container.text.length() * charWidth, sizecheck.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = out.createGraphics();
        for (int i = 0; i < container.text.length(); i++) {
            if (container.text.charAt(i) != ' ')
                g2d.drawImage(StData.resources.grf.getChar(container.texKey, container.text.charAt(i)), charWidth * i, 0, null);
        }
        g2d.dispose();
        container.texture = out;
    }

}

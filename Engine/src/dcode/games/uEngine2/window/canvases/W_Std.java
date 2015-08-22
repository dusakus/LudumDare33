package dcode.games.uEngine2.window.canvases;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.window.Canvas;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 13.05.15.
 */
public class W_Std extends Canvas {

    private boolean doScale = false;

    public W_Std() {
        StData.LOG.println("uEngine2: Creating Standard window canvas", "N");
        setMinimumSize(new Dimension(StData.setup.width * StData.setup.scale, StData.setup.height * StData.setup.scale));
        setMaximumSize(new Dimension(StData.setup.width * StData.setup.scale, StData.setup.height * StData.setup.scale));
        setPreferredSize(new Dimension(StData.setup.width * StData.setup.scale, StData.setup.height * StData.setup.scale));

        StData.NextFrame = new BufferedImage(StData.setup.width, StData.setup.height, BufferedImage.TYPE_INT_ARGB);

        if (StData.setup.scale != 1) doScale = true;
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            bs = getBufferStrategy();
        }

        Graphics grf = bs.getDrawGraphics();

        if (doScale) {
            grf.drawImage(StData.NextFrame.getScaledInstance(StData.setup.width * StData.setup.scale, StData.setup.height * StData.setup.scale, Image.SCALE_REPLICATE), 0, 0, null);
        } else {
            grf.drawImage(StData.NextFrame, 0, 0, null);
        }

        bs.show();

        //this.repaint();
        this.requestFocusInWindow();
    }
}

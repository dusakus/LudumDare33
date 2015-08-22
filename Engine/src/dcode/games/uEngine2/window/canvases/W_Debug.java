package dcode.games.uEngine2.window.canvases;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.window.Canvas;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 13.05.15.
 */
public class W_Debug extends Canvas {

    public W_Debug() {
        StData.LOG.println("uEngine2: Creating Standard window canvas", "N");
        setMinimumSize(new Dimension(StData.setup.width + 240, StData.setup.height + 132));
        setMaximumSize(new Dimension(StData.setup.width + 240, StData.setup.height + 132));
        setPreferredSize(new Dimension(StData.setup.width + 240, StData.setup.height + 132));

        StData.NextFrame = new BufferedImage(StData.setup.width, StData.setup.height, BufferedImage.TYPE_INT_ARGB);
    }

    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            bs = getBufferStrategy();
        }

        Graphics grf = bs.getDrawGraphics();

        grf.drawImage(StData.NextFrame, 80, 32, null);//debug offset: x = 80, y = 32
        StData.dlayer.draw(grf);

        bs.show();

        //this.repaint();
        this.requestFocusInWindow();
    }
}

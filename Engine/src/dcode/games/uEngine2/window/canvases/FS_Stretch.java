package dcode.games.uEngine2.window.canvases;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.window.Canvas;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 13.05.15.
 */
public class FS_Stretch extends Canvas {

    private int coordX = 0, coordY = 0;

    public FS_Stretch() {
        StData.LOG.println("uEngine2: Creating fullscreen stretch canvas", "N");

        StData.NextFrame = new BufferedImage(StData.setup.width, StData.setup.height, BufferedImage.TYPE_INT_ARGB);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        coordX = screen.width;
        coordY = screen.height;
    }

    @Override
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            bs = getBufferStrategy();
        }

        Graphics grf = bs.getDrawGraphics();

        grf.drawImage(StData.NextFrame.getScaledInstance(coordX, coordY, Image.SCALE_REPLICATE), 0, 0, null);


        bs.show();

        //this.repaint();
        this.requestFocusInWindow();
    }
}

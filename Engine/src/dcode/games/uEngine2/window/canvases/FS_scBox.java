package dcode.games.uEngine2.window.canvases;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.window.Canvas;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 13.05.15.
 */
public class FS_scBox extends Canvas {

    private int coordX = 0, coordY = 0;
    private int sizeX = 0, sizeY = 0;
    private int scw = 0, sch = 0;
    private int delayBG = 2;

    public FS_scBox() {
        StData.LOG.println("uEngine2: Creating fullscreen ratio fill box canvas", "N");

        StData.NextFrame = new BufferedImage(StData.setup.width, StData.setup.height, BufferedImage.TYPE_INT_ARGB);

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        scw = screen.width;
        sch = screen.height;

        sizeX = screen.width;
        sizeY =(int) ((1.0f*screen.width/StData.setup.width)*StData.setup.height);
        if(sizeY > screen.height){
            sizeY = screen.height;
            sizeX =(int) ((1.0f*screen.height/StData.setup.height)*StData.setup.width);
        }

        coordX = screen.width / 2 - sizeX / 2;
        coordY = screen.height / 2 - sizeY / 2;
    }

    @Override
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(2);
            bs = getBufferStrategy();
        }

        Graphics grf = bs.getDrawGraphics();

        if(delayBG == 0){
            grf.setColor(Color.black);
            grf.fillRect(0,0,scw,sch);
            delayBG = StData.setup.FPS*10;
        }

        grf.drawImage(StData.NextFrame.getScaledInstance(sizeX, sizeY, Image.SCALE_REPLICATE), coordX, coordY, null);


        bs.show();

        //this.repaint();
        this.requestFocusInWindow();

        delayBG--;
    }
}

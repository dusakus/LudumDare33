package dcode.games.uEngine2.window.canvases;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.window.Canvas;
import sun.awt.image.ToolkitImage;

import javax.tools.Tool;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 13.05.15.
 */
public class FS_CBox extends Canvas {

    private int coordX = 0, coordY = 0;
    private boolean doScale = false;
    private int scw = 0, sch = 0;
    private int delayBG = 0;

    public FS_CBox(){
        StData.LOG.println("uEngine2: Creating fullscreen box canvas with dynamic background color", "N");

        StData.NextFrame = new BufferedImage(StData.setup.width, StData.setup.height, BufferedImage.TYPE_INT_ARGB);

        if (StData.setup.scale != 1) doScale = true;

        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();

        scw = screen.width;
        sch = screen.height;

        coordX = screen.width/2 - StData.setup.width * StData.setup.scale/2;
        coordY = screen.height/2 - StData.setup.height * StData.setup.scale/2;
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
            ToolkitImage ti = (ToolkitImage)(StData.NextFrame.getScaledInstance(1,1,Image.SCALE_AREA_AVERAGING));
            ti.preload(null);
            grf.setColor(new Color(ti.getBufferedImage().getRGB(0, 0)));
            grf.fillRect(0,0,scw,sch);
            delayBG = 1;
        }

        if (doScale) {
            grf.drawImage(StData.NextFrame.getScaledInstance(StData.setup.width * StData.setup.scale, StData.setup.height * StData.setup.scale, Image.SCALE_REPLICATE), coordX, coordY, null);
        } else {
            grf.drawImage(StData.NextFrame, coordX, coordY, null);
        }

        bs.show();

        //this.repaint();
        this.requestFocusInWindow();

        delayBG--;
    }
}

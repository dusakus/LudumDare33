package dcode.games.uEngine2.GFX.postproc;

import dcode.games.uEngine2.StData;
import sun.awt.image.ToolkitImage;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by dusakus on 29.03.15.
 */
public class PP_scaleblur implements IGrfPostProcessor {

    public float scale = 1F;

    public PP_scaleblur(float modScale) {
        scale = modScale;
    }

    @Override
    public BufferedImage processFrame(BufferedImage NextFrame) {
        ToolkitImage proc = (ToolkitImage) NextFrame.getScaledInstance((int) (NextFrame.getWidth() / scale), (int) (NextFrame.getHeight() / scale), Image.SCALE_SMOOTH);
        proc = (ToolkitImage) proc.getScaledInstance(NextFrame.getWidth(), NextFrame.getHeight(), Image.SCALE_SMOOTH);

        proc.flush();
        NextFrame.getGraphics().drawImage(proc, 0, 0, null);
        return NextFrame;
    }

    @Override
    public boolean enabled() {
        return true;
    }
}

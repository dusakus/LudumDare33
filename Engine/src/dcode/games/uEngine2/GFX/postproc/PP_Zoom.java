package dcode.games.uEngine2.GFX.postproc;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author dusakus
 */


public class PP_Zoom implements IGrfPostProcessor {

	public float scaleFactor = 1.3f;

	@Override
	public BufferedImage processFrame(BufferedImage NextFrame) {
		int targetW = (int) (NextFrame.getWidth() * scaleFactor);
		int targetH = (int) (NextFrame.getHeight() * scaleFactor);

		int cropX = targetW / 2 - NextFrame.getWidth() / 2;
		int cropY = targetH / 2 - NextFrame.getHeight() / 2;

		Image F1 = NextFrame.getScaledInstance(targetW, targetH, BufferedImage.SCALE_REPLICATE);
		BufferedImage F2 = new BufferedImage(targetW, targetH, BufferedImage.TYPE_INT_ARGB);
		F2.getGraphics().drawImage(F1, 0, 0, null);
		return F2.getSubimage(cropX, cropY, NextFrame.getWidth(), NextFrame.getHeight());
	}

	@Override
	public boolean enabled() {
		return true;
	}

}

package dcode.games.uEngine2.tools.ext.j2s;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;


/**
 * Created by dusakus on 19.04.15.
 */
public class mirrorImage {
	public static BufferedImage mirror(BufferedImage in) {
		BufferedImage bufferedImage = in.getSubimage(0, 0, in.getWidth(), in.getHeight());
		AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
		tx.translate(-bufferedImage.getWidth(null), 0);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
		return op.filter(bufferedImage, null);

	}
}

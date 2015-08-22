/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.ResourceManager.loaders;

import dcode.games.uEngine2.StData;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

/**
 * @author dusakus
 */
public class InternalTextureLoader {

	//Load texture from /dcode/games/uEngine2/games/[safename]/res/gfx/[shortUrl]
	public void loadSingleTexture(String shortUrl, String identifier) {
		BufferedImage BI = StData.resources.grf.getTexture("MISSING");
		try {
			BufferedImage BII = ImageIO.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/" + StData.setup.safeName + "/res/gfx/" + shortUrl));
			if (BII != null) {
				BI = BII;
			} else {
				StData.LOG.println("MissingTexture for " + identifier + ", file " + shortUrl + " not found, using missing texture", "E2");
			}
		} catch (Exception ex) {
			StData.LOG.println("MissingTexture for " + identifier + ", file " + shortUrl + " not found, using missing texture", "E2");
		}
		if (BI != null) {
			StData.resources.grf.registerTexture(BI, identifier);
		}

	}
}

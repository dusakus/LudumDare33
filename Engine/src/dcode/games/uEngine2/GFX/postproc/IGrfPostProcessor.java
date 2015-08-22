/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.GFX.postproc;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @author dusakus
 */
public interface IGrfPostProcessor {
	public BufferedImage processFrame(BufferedImage NextFrame);

	public boolean enabled();
}

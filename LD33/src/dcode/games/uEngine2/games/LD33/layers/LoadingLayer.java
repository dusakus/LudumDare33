package dcode.games.uEngine2.games.LD33.layers;

import dcode.games.uEngine.dcctools.Delay;
import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.tools.ext.j2s.gifReader;

import java.awt.*;

/**
 * Created by dusakus on 24.08.15.
 */
public class LoadingLayer implements ILayer {
	private Delay d = new Delay(8);

	private gifReader anim;

	private int anim_frame = 0;

	public LoadingLayer() {
		anim = new gifReader();
		anim.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/LD33/res/gfx/jif/loading.gif"));
	}

	@Override
	public void draw(Graphics2D G2D) {
		G2D.drawImage(anim.getFrame(anim_frame), 0, 0, null);

		if(d.doNow()){
			anim_frame++;

		}
	}

	@Override
	public boolean removeMe() {
		return anim_frame >= anim.getFrameCount()-1;
	}

	@Override
	public boolean renderMe() {
		return anim_frame <= anim.getFrameCount()-1;
	}
}

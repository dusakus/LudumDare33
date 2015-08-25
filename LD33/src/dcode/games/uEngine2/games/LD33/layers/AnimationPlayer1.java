package dcode.games.uEngine2.games.LD33.layers;

import dcode.games.uEngine.dcctools.Delay;
import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.games.LD33.LStData;
import dcode.games.uEngine2.tools.Shortcuts;
import dcode.games.uEngine2.tools.ext.j2s.gifReader;

import java.awt.*;

/**
 * Created by dusakus on 24.08.15.
 */
public class AnimationPlayer1 implements ILayer {

	private Delay d;

	private gifReader darken;
	private gifReader anim;

	private int anim_frame = 0;
	private int darken_frame = 0;

	private int phase = 0;
	public boolean DONE = false;

	private boolean slurp = false;

	public AnimationPlayer1(String animid) {
		darken = new gifReader();
		darken.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/LD33/res/gfx/jif/transp_darken.gif"));
		anim = new gifReader();
		anim.read(getClass().getResourceAsStream("/dcode/games/uEngine2/games/LD33/res/gfx/jif/" + animid + ".gif"));
		if (animid.equals("endin1")) slurp = true;
	}

	@Override
	public void draw(Graphics2D G2D) {
		if (phase >= 2) G2D.drawImage(Shortcuts.getTexture("animb1"), 0, 0, null);
		if (phase >= 2)
			G2D.drawImage(anim.getFrame(anim_frame).getScaledInstance(160, 120, Image.SCALE_REPLICATE), 80, 40, null);
		if (phase < 3) {
			G2D.setColor(new Color(0, 0, 0, darken_frame / 10f));
			G2D.fillRect(0, 0, 320, 200);
		}
		if (phase > 5) {
			G2D.setColor(new Color(0, 0, 0, darken_frame / 10f));
			G2D.fillRect(0, 0, 320, 200);
		}

		switch (phase) {
			case 0:
				d = new Delay(7);
				phase++;
				break;
			case 1:
				if (d.doNow()) darken_frame++;
				if (darken_frame == 9) phase++;
				break;
			case 2:
				if (d.doNow()) darken_frame--;
				if (darken_frame == 0) phase++;
				break;
			case 3:
				d.newDelay(10);
				phase++;
				break;
			case 4:
				if (d.doNow()) {
					anim_frame++;
					if (slurp && anim_frame == 6) LStData.sfx_slurp.play();
				}
				if (anim_frame == anim.getFrameCount() - 1) phase++;
				if (anim_frame == anim.getFrameCount() - 1) d.newDelay(100);
				break;
			case 5:
				if (d.doNow()) {
					phase++;
					d.newDelay(7);
				}
				break;
			case 6:
				if (d.doNow()) darken_frame++;
				if (darken_frame == 9) phase++;
				break;
			case 7:
				this.DONE = true;
		}
	}

	@Override
	public boolean removeMe() {
		return DONE;
	}

	@Override
	public boolean renderMe() {
		return true;
	}
}

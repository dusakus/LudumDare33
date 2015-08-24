package dcode.games.uEngine2.games.LD33.layers;

import dcode.games.uEngine.dcctools.Delay;
import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.tools.Shortcuts;

import java.awt.*;

/**
 * Created by dusakus on 22.08.15.
 */
public class notificationLayer_speedDOWN implements ILayer {

	int step = 320;
	Delay d = new Delay(10);

	@Override
	public void draw(Graphics2D G2D) {
		if(step > 140){
			step -= 8;
		} else if(step > 120){
			step -= 3;
		} else if(step > 100){
			step --;
		} else if(step > 80){
			if(d.doNow()) step--;
		} else if(step > 81){
			step--;
		} else if(step > 60){
			step -=5;
		} else {
			step -= 15;
		}
		G2D.drawImage(Shortcuts.getTexture("bonin2"), step, 160, null);
	}

	@Override
	public boolean removeMe() {
		return step < -600;
	}

	@Override
	public boolean renderMe() {
		return step > -500;
	}
}

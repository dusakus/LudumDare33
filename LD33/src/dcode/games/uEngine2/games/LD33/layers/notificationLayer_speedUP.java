package dcode.games.uEngine2.games.LD33.layers;

import dcode.games.uEngine.dcctools.Delay;
import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.tools.Shortcuts;

import java.awt.*;

/**
 * Created by dusakus on 22.08.15.
 */
public class notificationLayer_speedUP implements ILayer {

	int step = 0;
	Delay d = new Delay(10);

	@Override
	public void draw(Graphics2D G2D) {
		if(step < 90){
			step += 8;
		} else if(step < 110){
			step += 3;
		} else if(step < 130){
			step ++;
		} else if(step < 150){
			if(d.doNow()) step++;
		} else if(step < 151){
			step++;
		} else if(step < 170){
			step +=5;
		} else {
			step += 15;
		}
		G2D.drawImage(Shortcuts.getTexture("bonin1"), step, 80, null);
	}

	@Override
	public boolean removeMe() {
		return step > 600;
	}

	@Override
	public boolean renderMe() {
		return step < 500;
	}
}

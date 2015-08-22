package dcode.games.uEngine2.games.LD33.terrainOB.events;

import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.LD33.LStData;
import dcode.games.uEngine2.games.LD33.layers.notificationLayer_speedDOWN;
import dcode.games.uEngine2.games.LD33.terrainOB.TerrainObject;

/**
 * Created by dusakus on 22.08.15.
 */
public class speedDownEvent extends CollisionEvent{
	@Override
	public void onCollision(boolean player, TerrainObject to) {
		if(player){
			StData.currentGC.currentSC.layers_Overlay.add(new notificationLayer_speedDOWN());
			LStData.playerSpeed -= 0.5f;
		} else {
			LStData.enemySpeed -= 0.5f;
		}
		to.YOffset = 1000;
		to.collisionoffset = -1000;
	}
}

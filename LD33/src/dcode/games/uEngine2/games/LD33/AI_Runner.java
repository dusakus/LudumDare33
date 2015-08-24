package dcode.games.uEngine2.games.LD33;

import dcode.games.uEngine.dcctools.Delay;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.LD33.other.eneSprite;
import dcode.games.uEngine2.games.LD33.terrainOB.TerrainObject;
import dcode.games.uEngine2.games.LD33.terrainOB.events.speedDownEvent;
import dcode.games.uEngine2.games.LD33.terrainOB.events.speedUpEvent;
import dcode.games.uEngine2.tools.numbarTools;

/**
 * Created by dusakus on 23.08.15.
 */
public class AI_Runner implements ILogicTask {
	private long gameIdentifier;
	private eneSprite spr;

	private Delay currentWait = new Delay(100);

	private int nextOperation = 0;
	private int[] worldData = new int[20];
	private double lastSpeed = 0;

	public AI_Runner(int spriteID) {
		gameIdentifier = LStData.gIDENT;
		spr = (eneSprite) StData.currentGC.currentSC.sprites[spriteID];
		worldData[0] = -1;
		worldData[1] = 0;
		worldData[9] = (int) LStData.enemySpeed;
		worldData[2] = 0;
		worldData[3] = 0;
		worldData[4] = 0;
		worldData[5] = 0;
		worldData[6] = 0;
		worldData[7] = 0;
		worldData[8] = 0;
		worldData[10] = 0;
	}

	@Override
	public boolean isReady() {
		return true;
	}

	@Override
	public void perform() {

		if(LStData.enemySpeed > lastSpeed){
			worldData[9]++;
			lastSpeed = LStData.enemySpeed;
		}
		if(LStData.enemySpeed < lastSpeed){
			worldData[9]--;
			worldData[8]++;
			lastSpeed = LStData.enemySpeed;
		}

		if (currentWait.doNow()) {
			switch (nextOperation) {
				case 0:
					if (worldData[0] != worldData[1]) {
						nextOperation = 21;
						currentWait.newDelay(3);
					} else {
						selectBestLane();
					}
					break;
				case 21:
					update_pros();
					nextOperation = 0;
					worldData[0] = worldData[1];
					break;
				case 31:
					spr.goUP();
					worldData[1] = worldData[1] + 1;
					currentWait.newDelay(numbarTools.clamp(30 - (worldData[8]/5), 5, 30));
					nextOperation = 0;
					break;
				case 32:
					spr.goDOWN();
					worldData[1] = worldData[1] + 1;
					currentWait.newDelay(numbarTools.clamp(30 - (worldData[8]/5), 5, 30));
					nextOperation = 0;
					break;
			}
		}
	}

	private void selectBestLane() {
		int laneup = worldData[2] - worldData[3] + 2;
		int lanestay = worldData[4] - worldData[5] + 2;
		int lanedown = worldData[6] - worldData[7] + 2;

		if(lanedown > lanestay){
			if(laneup > lanedown){
				nextOperation = 31;
			} else {
				nextOperation = 32;
			}
		} else if(laneup > lanestay){
			nextOperation = 31;
		} else {
			worldData[1]++;
			currentWait.newDelay(numbarTools.clamp(15 - (worldData[8]/5), 2, 15));
		}
		worldData[7] = 0;
		worldData[2] = 0;
		worldData[4] = 0;
		worldData[3] = 0;
		worldData[5] = 0;
		worldData[6] = 0;
	}

	private void update_pros() {
		int viewDist = numbarTools.clamp(150 - worldData[8], 20, 150);

		for (TerrainObject to : LStData.terrainObjects) {
			if (to.Ylocation == LStData.enemyLane - 1 && to.Xlocation + to.collisionoffset > LStData.enemyPosition && to.Xlocation + to.collisionoffset + to.collisionLenght <= LStData.enemyPosition + viewDist) {
				if (to.onCollision instanceof speedUpEvent) {
					if(LStData.playerPosition < LStData.enemyPosition) worldData[4] += 5;
					if(LStData.playerPosition >= LStData.enemyPosition) worldData[4] -= 5;
				} else if (to.onCollision instanceof speedDownEvent) {
					if(LStData.playerPosition < LStData.enemyPosition) worldData[4] -= 5;
					if(LStData.playerPosition >= LStData.enemyPosition) worldData[4] += 5;
				} else {
					worldData[5] += 2;
				}
			}
			if (to.Ylocation == LStData.enemyLane && to.Xlocation + to.collisionoffset > LStData.enemyPosition && to.Xlocation + to.collisionoffset + to.collisionLenght <= LStData.enemyPosition + viewDist) {
				if (to.onCollision instanceof speedUpEvent) {
					if(LStData.playerPosition < LStData.enemyPosition) worldData[4] += 5;
					if(LStData.playerPosition >= LStData.enemyPosition) worldData[4] -= 5;
				} else if (to.onCollision instanceof speedDownEvent) {
					if(LStData.playerPosition < LStData.enemyPosition) worldData[4] -= 5;
					if(LStData.playerPosition >= LStData.enemyPosition) worldData[4] += 5;
				} else {
					worldData[7] += 2;
				}
			}
			if (to.Ylocation == LStData.enemyLane - 2 && to.Xlocation + to.collisionoffset > LStData.enemyPosition && to.Xlocation + to.collisionoffset + to.collisionLenght <= LStData.enemyPosition + viewDist) {
				if (to.onCollision instanceof speedUpEvent) {
					if(LStData.playerPosition < LStData.enemyPosition) worldData[4] += 5;
					if(LStData.playerPosition >= LStData.enemyPosition) worldData[4] -= 5;
				} else if (to.onCollision instanceof speedDownEvent) {
					if(LStData.playerPosition < LStData.enemyPosition) worldData[4] -= 5;
					if(LStData.playerPosition >= LStData.enemyPosition) worldData[4] += 5;
				} else {
					worldData[3] += 2;
				}
			}
		}
		if (LStData.enemyLane >= 5){
			worldData[7] = 9999;
		}
		if (LStData.enemyLane <= 1){
			worldData[3] = 9999;
		}
	}

	@Override
	public boolean doRepeat() {
		return LStData.gIDENT == gameIdentifier;
	}
}

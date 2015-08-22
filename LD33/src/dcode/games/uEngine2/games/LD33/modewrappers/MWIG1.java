package dcode.games.uEngine2.games.LD33.modewrappers;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.GFX.layers.FillTextureLayer;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.LD33.LStData;
import dcode.games.uEngine2.games.LD33.layers.HUDLayer;
import dcode.games.uEngine2.games.LD33.layers.ObjectsLayer;
import dcode.games.uEngine2.games.LD33.layers.ScrollLayer;
import dcode.games.uEngine2.games.LD33.layers.WarnLayer;
import dcode.games.uEngine2.games.LD33.other.ItemQueueA;
import dcode.games.uEngine2.games.LD33.other.eneSprite;
import dcode.games.uEngine2.games.LD33.other.playSprite;
import dcode.games.uEngine2.games.LD33.terrainOB.TerrainObject;
import dcode.games.uEngine2.games.LD33.terrainOB.events.CollisionEvent;
import dcode.games.uEngine2.games.LD33.terrainOB.events.speedDownEvent;
import dcode.games.uEngine2.games.LD33.terrainOB.events.speedUpEvent;
import dcode.games.uEngine2.tools.Shortcuts;

import java.awt.*;
import java.awt.image.BufferedImage;

import static dcode.games.uEngine2.tools.Shortcuts.*;

/**
 * Created by dusakus on 22.08.15.
 */
public class MWIG1 implements IMW {
	private int tilecount = 1;

	public static final int DEATHLIMIT = 300;
	private int lastperc = -1;

	@Override
	public void init() {

	}

	@Override
	public int tick(int status) {
		switch (status) {
			case 10:
				requestTexture("scroll/count3.png", "pre1");
				requestTexture("scroll/count2.png", "pre2");
				requestTexture("scroll/count1.png", "pre3");
				requestTexture("header1.png", "top1");
				status = 20;
				break;
			case 20:
				log("Setting up the game");
				LStData.playerPosition = 30;
				LStData.enemyPosition = 200;
				LStData.playerONSCREENshift = 30;
				status++;
				break;
			case 21:
				log("creating item queue");
				LStData.chase_itemQ = new ItemQueueA();
				status = 30;
			case 30:
				log("Loading tiles for environment " + LStData.enviroKey);
				status++;
				break;
			case 31:
				requestTexture("scroll/" + LStData.enviroKey + "/tile" + tilecount + ".png", "til" + tilecount);
				try {
					getClass().getResourceAsStream("/dcode/games/uEngine2/games/LD33/res/gfx/scroll/" + LStData.enviroKey + "/tile" + tilecount + ".png").read();
					tilecount++;
				} catch (Exception E) {
					status++;
					log("loaded " + tilecount + " tiles");
				}
				break;
			case 32:
				StData.currentGC.currentSC.layers_Overlay.add(new HUDLayer());
				status = 99;
				break;
			case 99:
				StData.currentGC.currentSC.layers_Background.add(new FillTextureLayer("scbg0"));
				StData.currentGC.currentSC.layers_Background.add(new ScrollLayer());
				StData.currentGC.currentSC.layers_Foreground.add(new ObjectsLayer());
				StData.currentGC.currentSC.layers_Overlay.add(new WarnLayer());
				StData.currentGC.currentSC.sprites[4] = new playSprite("char1");
				StData.currentGC.currentSC.sprites[5] = new eneSprite("charf");
				StData.currentGC.currentSC.sprites_front[0] = 4;
				StData.currentGC.currentSC.sprites_front[1] = 5;
				log("Started game mode 1");
				status = 101;
				break;
			case 101:
				if (LStData.blocks.size() < 3) {
					LStData.blocks.add("til" + (StData.gRand.nextInt(tilecount - 1) + 1));
				}
				if (StData.gRand.nextInt(20) == 2) {
					queueObstacle();
				}
				if (StData.gRand.nextInt(200) == 16) {
					queueBonus();
				}
				if (StData.gRand.nextInt(200) == 16) {
					queueBonus2();
				}
				clearObstacles();
				if(LStData.playerSpeed <= 1){
					LStData.playerPosition +=1;
				} else
				LStData.playerPosition += LStData.playerSpeed;
				if(LStData.enemySpeed <= 1){
					LStData.enemyPosition +=1;
				} else
				LStData.enemyPosition += LStData.enemySpeed;
				percUpdate();
				checkCollision();

		}
		if (status < 10) status = 10;
		return status;
	}

	private void checkCollision() {
		for (TerrainObject to : LStData.terrainObjects) {
			if (to.Ylocation == LStData.playerLane + -1 && to.Xlocation + to.collisionoffset <= LStData.playerPosition && to.Xlocation + to.collisionoffset + to.collisionLenght >= LStData.playerPosition) {
				to.onCollision.onCollision(true, to);
				log("playerCollision");
			}
			if (to.Ylocation == LStData.enemyLane - 1 && to.Xlocation + to.collisionoffset <= LStData.enemyPosition && to.Xlocation + to.collisionoffset + to.collisionLenght >= LStData.enemyPosition) {
				to.onCollision.onCollision(false, to);
				log("Enemy coliision");
			}
		}
	}

	private void clearObstacles() {
		try {
			TerrainObject te = LStData.terrainObjects.get(0);
			while (te != null && te.Xlocation < LStData.getLocation() - 129) {
				TerrainObject t2 = LStData.terrainObjects.get(LStData.terrainObjects.indexOf(te) + 1);
				LStData.terrainObjects.remove(te);
				te = t2;
			}
		} catch (Exception e) {

		}
	}

	private void queueObstacle() {
		log("creating an object");

		TerrainObject a = new TerrainObject();
		a.Xlocation = LStData.getLocation() + 400;
		a.Ylocation = StData.gRand.nextInt(5);
		a.WorldTexture = "ob01";
		a.WarnTexture = "arrL";
		a.XOffset = -6;
		a.YOffset = -8;
		a.collisionLenght = 8;
		a.collisionoffset = -4;
		a.onCollision = new CollisionEvent() {
			@Override
			public void onCollision(boolean player, TerrainObject to) {
				if (player) {
					LStData.playerSpeed = LStData.playerSpeed / 2;
					double d = LStData.playerSpeed;
					registerOneTimeBGTask(new ModifyPlayerSpeed(d / 2, 10), false);
					registerOneTimeBGTask(new ModifyPlayerSpeed(d / 4, 20), false);
					registerOneTimeBGTask(new ModifyPlayerSpeed(d / 4, 40), false);
				} else {
					LStData.enemySpeed = LStData.enemySpeed / 2;
					double d = LStData.enemySpeed;
					registerOneTimeBGTask(new ModifyEnemySpeed(d / 2, 10), false);
					registerOneTimeBGTask(new ModifyEnemySpeed(d / 4, 20), false);
					registerOneTimeBGTask(new ModifyEnemySpeed(d / 4, 40), false);
				}
				to.collisionLenght = 0;
				to.collisionoffset = -1111;
			}
		};

		log("created at: x" + a.Xlocation + " on lane " + (a.Ylocation + 1));

		LStData.terrainObjects.add(a);
	}

	private void queueBonus2() {
		log("creating an bonus");

		TerrainObject a = new TerrainObject();
		a.Xlocation = LStData.getLocation() + 400;
		a.Ylocation = StData.gRand.nextInt(5);
		a.WorldTexture = "bonic2";
		a.WarnTexture = "arrL";
		a.XOffset = -6;
		a.YOffset = -8;
		a.collisionLenght = 8;
		a.collisionoffset = -4;
		a.onCollision = new speedDownEvent();

		log("created at: x" + a.Xlocation + " on lane " + (a.Ylocation + 1));

		LStData.terrainObjects.add(a);
	}
	private void queueBonus() {
		log("creating an bonus");

		TerrainObject a = new TerrainObject();
		a.Xlocation = LStData.getLocation() + 400;
		a.Ylocation = StData.gRand.nextInt(5);
		a.WorldTexture = "bonic1";
		a.WarnTexture = "arrL";
		a.XOffset = -6;
		a.YOffset = -8;
		a.collisionLenght = 8;
		a.collisionoffset = -4;
		a.onCollision = new speedUpEvent();

		log("created at: x" + a.Xlocation + " on lane " + (a.Ylocation + 1));

		LStData.terrainObjects.add(a);
	}

	private void percUpdate() {
		int diff = (int) (LStData.enemyPosition - LStData.playerPosition);
		int percp = (int) (((DEATHLIMIT - diff) * 1.0f / DEATHLIMIT) * 100);
		if (percp >= 0 && percp <= 100 && percp != lastperc) {
			if (true) {
				BufferedImage fnt = Shortcuts.getTextureAsBufferedImage("fontP");
				BufferedImage bf = new BufferedImage(66, 31, BufferedImage.TYPE_INT_ARGB);
				Graphics g = bf.getGraphics();
				if (percp > 99) {
					g.drawImage(fnt.getSubimage(22, 0, 22, 31), 0, 0, null);
					g.drawImage(fnt.getSubimage(0, 0, 22, 31), 22, 0, null);
					g.drawImage(fnt.getSubimage(0, 0, 22, 31), 44, 0, null);
				} else {
					if (percp > 9) g.drawImage(fnt.getSubimage((percp / 10) * 22, 0, 22, 31), 22, 0, null);
					g.drawImage(fnt.getSubimage((percp - ((percp / 10) * 10)) * 22, 0, 22, 31), 44, 0, null);
				}
				g.dispose();
				Shortcuts.registerTexture(bf, "perc");
				lastperc = percp;
			}
		} else {
		}

	}

	class ModifyPlayerSpeed extends PBGTask {
		private double v;
		private int timer;

		public ModifyPlayerSpeed(double v, int i) {
			super();
			this.v = v;
			timer = i;
		}

		@Override
		public boolean isReady() {
			timer--;
			return timer == 0;
		}

		@Override
		public void perform() throws Exception {
			LStData.playerSpeed += v;
			this.done = true;
		}
	}

	class ModifyEnemySpeed extends PBGTask {
		private double v;
		private int timer;

		public ModifyEnemySpeed(double v, int i) {
			super();
			this.v = v;
			timer = i;
		}

		@Override
		public boolean isReady() {
			timer--;
			return timer == 0;
		}

		@Override
		public void perform() throws Exception {
			LStData.enemySpeed += v;
			this.done = true;
		}
	}
}

package dcode.games.uEngine2.games.LD33.modewrappers;

import dcode.games.uEngine.dcctools.Delay;
import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.GFX.ScreenContent;
import dcode.games.uEngine2.GFX.layers.FillTextureLayer;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.LD33.LStData;
import dcode.games.uEngine2.games.LD33.layers.AnimationPlayer1;
import dcode.games.uEngine2.games.LD33.layers.HUDLayer3;
import dcode.games.uEngine2.games.LD33.layers.ScrollLayer_MENU;
import dcode.games.uEngine2.games.LD33.other.ItemQueueA;
import dcode.games.uEngine2.games.LD33.other.playSprite;
import dcode.games.uEngine2.games.LD33.other.playerShadow;
import dcode.games.uEngine2.games.LD33.terrainOB.TerrainObject;
import dcode.games.uEngine2.games.LD33.terrainOB.events.CollisionEvent;
import dcode.games.uEngine2.games.LD33.terrainOB.events.speedDownEvent;
import dcode.games.uEngine2.games.LD33.terrainOB.events.speedUpEvent;
import dcode.games.uEngine2.tools.Shortcuts;
import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import static dcode.games.uEngine2.tools.Shortcuts.*;

/**
 * Created by dusakus on 22.08.15.
 */
public class MWMENU implements IMW {
	private int tilecount = 1;

	public static final int DEATHLIMIT = 300;
	private int lastperc = -1;
	private AnimationPlayer1 a;

	@Override
	public void init() {

	}

	@Override
	public int tick(int status) {
		switch (status) {
			case 10:
				clearGame();
				requestTexture("header3.png", "top1");
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
				StData.currentGC.currentSC.layers_Overlay.add(new HUDLayer3());
				status = 99;
				break;
			case 99:
				StData.currentGC.currentSC.layers_Background.add(new FillTextureLayer("scbg0"));
				StData.currentGC.currentSC.layers_Background.add(new ScrollLayer_MENU());
				StData.currentGC.currentSC.sprites[4] = new playSprite("char1");
				StData.currentGC.currentSC.sprites[9] = new playerShadow();
				StData.currentGC.currentSC.sprites_back[0] = 9;
				StData.currentGC.currentSC.sprites_front[0] = 4;
				log("Started game mode 1");
				LStData.playerONSCREENshift = -20;
				status = 100;
				break;
			case 100:
				LStData.playerPosition += 5;
				LStData.playerONSCREENshift += 2;
				if (LStData.playerONSCREENshift >= 30) status = 101;
				break;
			case 101:
				if (LStData.blocks.size() < 3) {
					LStData.blocks.add("til" + (StData.gRand.nextInt(tilecount - 1) + 1));
				}

				LStData.playerPosition += 5;

				if (StData.threadManager.KW.isKeyHeld(KeyEvent.VK_SPACE)) {
					status++;
				}
				break;
			case 102:
				LStData.playerONSCREENshift += 5;
				if (LStData.playerONSCREENshift > 340)
					switch (LStData.playerLane) {
						case 1:
							status = 201;
							break;
						case 5:
							LStData.currentMode = LStData.MODE_EXIT;
							break;
						default:
							LStData.playerONSCREENshift = -20;
							status = 100;
					}
				break;
			case 201:
				a = new AnimationPlayer1("start1");
				StData.currentGC.currentSC.layers_Overlay.add(a);
				status++;
				break;
			case 202:
				if (a.DONE) status++;
				break;
			case 203:
				clearGame();
				status = 0;
				LStData.currentMode = LStData.MODE_INGAME_1;
		}
		return status;
	}

	private void clearGame() {
		LStData.battleIndex = 60;
		LStData.gIDENT = System.nanoTime();
		LStData.terrainObjects = new LinkedList<TerrainObject>();
		LStData.tileCounter = 0;
		LStData.enemyPosition = 160;
		LStData.playerPosition = 40;
		LStData.playerSpeed = 3.1;
		LStData.enemySpeed = 3.1;
		LStData.blocks = new LinkedList<String>();
		ScreenContent sc = StData.currentGC.currentSC;
		StData.currentGC.currentSC = new ScreenContent();
		StData.currentGC.currentSC.layers_Overlay = sc.layers_Overlay;
	}

	private void checkCollision() {
		for (TerrainObject to : LStData.terrainObjects) {
			if (to.Ylocation == LStData.playerLane + -1 && to.Xlocation + to.collisionoffset <= LStData.playerPosition && to.Xlocation + to.collisionoffset + to.collisionLenght + numbarTools.clamp((int) (LStData.playerSpeed - 8), 1, 150) >= LStData.playerPosition) {
				to.onCollision.onCollision(true, to);
			}
			if (to.Ylocation == LStData.enemyLane - 1 && to.Xlocation + to.collisionoffset <= LStData.enemyPosition && to.Xlocation + to.collisionoffset + to.collisionLenght + numbarTools.clamp((int) (LStData.playerSpeed - 8), 1, 150) >= LStData.enemyPosition) {
				to.onCollision.onCollision(false, to);
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

	Delay batdel = new Delay(10);

	private void percUpdate() {
		int diff = numbarTools.mod((int) (LStData.enemyPosition - LStData.playerPosition));
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
		}
		if (percp >= 50) {
			if (batdel.doNow()) {
				LStData.battleIndex -= (percp - 50) / 10 + 1;
				batdel.newDelay(30);
			}
		}
		if (percp <= 0) {
			if (batdel.doNow()) {
				LStData.battleIndex++;
				batdel.newDelay(30);
			}
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

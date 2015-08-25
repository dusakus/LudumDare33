package dcode.games.uEngine2.games.LD33.modewrappers;

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

import java.awt.event.KeyEvent;
import java.util.LinkedList;

import static dcode.games.uEngine2.tools.Shortcuts.log;
import static dcode.games.uEngine2.tools.Shortcuts.requestTexture;

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
				LStData.msx_sanic.stop();
				LStData.msx_menu.play(true, 0.6);
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
				if (LStData.playerONSCREENshift > 340) {
					switch (LStData.playerLane) {
						case 1:
							status = 201;
							LStData.msx_menu.stop();
							break;
						case 2:
							status = 401;
							break;
						case 3:
							clearGame();
							status = 0;
							LStData.currentMode = LStData.MODE_INGAME_2;
							LStData.msx_menu.stop();
							break;
						case 4:
							clearGame();
							status = 0;
							LStData.currentMode = LStData.MODE_INGAME_3;
							LStData.msx_menu.stop();
							break;
						case 5:
							LStData.msx_menu.stop();
							LStData.currentMode = LStData.MODE_EXIT;
							break;
						default:
							LStData.playerONSCREENshift = -20;
							status = 100;
					}
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
				break;
			case 401:
				nextEnvironement();
				LStData.playerONSCREENshift = -20;
				status = 10;
		}
		return status;
	}

	private void nextEnvironement() {
		if(LStData.enviroKey.equalsIgnoreCase("standard")){
			LStData.enviroKey = "forest";
		} else {
			LStData.enviroKey = "standard";
		}
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

}

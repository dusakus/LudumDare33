package dcode.games.uEngine2.games.LD33;

import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.SFX.tslib.TinySound;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.LD33.layers.LoadingLayer;
import dcode.games.uEngine2.games.LD33.modewrappers.*;
import dcode.games.uEngine2.tools.Shortcuts;

import static dcode.games.uEngine2.StData.gameIsRunning;
import static dcode.games.uEngine2.StData.lastMSG;
import static dcode.games.uEngine2.games.LD33.LStData.*;
import static dcode.games.uEngine2.tools.Shortcuts.log;
import static dcode.games.uEngine2.tools.Shortcuts.requestTexture;

public class BaseLogic implements ILogicTask {

	boolean isReady = false;
	private IMW modeWrapper;

	public BaseLogic() {


		if (currentMode != MODE_INIT) {
			currentMode = MODE_ERROR;
			ERRORCODE = 101;
			lastMSG = "already initialized";
		} else {
			isReady = true;
		}
	}

	@Override
	public boolean isReady() {
		return isReady;
	}

	@Override
	public void perform() {
		switch (currentMode) {
			case MODE_INIT:
				currentStatus = 2;
				currentMode = MODE_LOADBASE;
				break;
			case MODE_ERROR:
				StData.LOG.println("ERROR INTERNAL EXIT STATUS: status:" + currentStatus + ", errorcode:" + ERRORCODE + " msg " + lastMSG);
				if(ERRORCODE == 421) log("[scrolling layer]: Hey, tile queue generator, YOU ARE TOO SLOW!!");
				gameIsRunning = false;
				break;
			case MODE_LOADBASE:

				StData.currentGC.currentSC.layers_Overlay.add(new LoadingLayer());

				Shortcuts.requestTexture("plan_alpha.png", "plan");

				Shortcuts.requestTexture("percfont.png", "fontP");
				Shortcuts.requestTexture("nac.png", "scbg0");
				Shortcuts.requestTexture("arrow.png", "arrL");
				Shortcuts.requestTexture("arrow.png", "arrL");
				Shortcuts.requestTexture("obstakel.png", "ob01");
				Shortcuts.requestTexture("shadow.png", "sha1");
				Shortcuts.requestTexture("barF1.png", "barCol");
				Shortcuts.requestTexture("barO1.png", "barCov");
				Shortcuts.requestTexture("barO2.png", "barCovF");
				Shortcuts.requestTexture("barO3.png", "barCovW");
				Shortcuts.requestTexture("animbox1.png", "animb1");
				requestTexture("header1_info.png", "top1i");
				Shortcuts.requestTexture("header3.png", "top3");
				Shortcuts.requestTexture("header4.png", "top4");
				Shortcuts.requestTexture("sanic.png", "sanic");
				Shortcuts.requestTexture("ordersanic.png", "sanicO");

				LStData.sfx_fail = TinySound.loadSound("/dcode/games/uEngine2/games/LD33/res/sfx/fail.wav");
				LStData.sfx_faster = TinySound.loadSound("/dcode/games/uEngine2/games/LD33/res/sfx/faster.wav");
				LStData.sfx_hit = TinySound.loadSound("/dcode/games/uEngine2/games/LD33/res/sfx/hit.wav");
				LStData.sfx_slower = TinySound.loadSound("/dcode/games/uEngine2/games/LD33/res/sfx/slower.wav");
				LStData.sfx_warning = TinySound.loadSound("/dcode/games/uEngine2/games/LD33/res/sfx/warning.wav");
				LStData.sfx_slurp = TinySound.loadSound("/dcode/games/uEngine2/games/LD33/res/sfx/slurpT1.wav");

				LStData.msx_menu = TinySound.loadMusic("/dcode/games/uEngine2/games/LD33/res/msx/msx1.wav");
				LStData.msx_game = TinySound.loadMusic("/dcode/games/uEngine2/games/LD33/res/msx/msx2.wav");
				LStData.msx_sanic = TinySound.loadMusic("/dcode/games/uEngine2/games/LD33/res/msx/sanic.wav");

				for (int i = 1; i < 7; i++) {
					Shortcuts.requestTexture("char1/Bohatyr" + i + ".png", "char1run" + (i - 1));
					Shortcuts.requestTexture("charf/fsmrun" + i + ".png", "charfrun" + (i - 1));
				}

				for (int i = 1; i < 3; i++) {
					Shortcuts.requestTexture("bon" + i + ".png", "bonic" + i);
					Shortcuts.requestTexture("notif" + i + ".png", "bonin" + i);
				}
				//StData.currentGC.currentSC.layers_Overlay.add(new FillTextureLayer("plan"));

				currentMode = MODE_RUN;
				currentStatus = 0;
				break;
			case MODE_RUN:
				currentMode = MODE_MENU;
				currentStatus = 0;
				break;
			case MODE_MENU:
				if (currentStatus == 0) {
					modeWrapper = new MWMENU();
					currentStatus++;
				} else if (currentStatus == 1) {
					modeWrapper.init();
					currentStatus = 10;
				} else
					currentStatus = modeWrapper.tick(currentStatus);
				break;
			case MODE_INGAME_1:
				if (currentStatus == 0) {
					modeWrapper = new MWIG1();
					currentStatus++;
				} else if (currentStatus == 1) {
					modeWrapper.init();
					currentStatus = 10;
				} else
					currentStatus = modeWrapper.tick(currentStatus);
				break;
			case MODE_INGAME_2:
				if (currentStatus == 0) {
					modeWrapper = new MWIGO();
					currentStatus++;
				} else if (currentStatus == 1) {
					modeWrapper.init();
					currentStatus = 10;
				} else
					currentStatus = modeWrapper.tick(currentStatus);
				break;
			case MODE_INGAME_3:
				if (currentStatus == 0) {
					modeWrapper = new MWIGS();
					currentStatus++;
				} else if (currentStatus == 1) {
					modeWrapper.init();
					currentStatus = 10;
				} else
					currentStatus = modeWrapper.tick(currentStatus);
				break;
			case MODE_EXIT:
				StData.gameIsRunning = false;
				break;
			default:
				lastMSG = "Unknown mode: " + currentMode;
				currentMode = MODE_ERROR;
				ERRORCODE = 102;
		}
	}

	@Override
	public boolean doRepeat() {
		return true;
	}
}



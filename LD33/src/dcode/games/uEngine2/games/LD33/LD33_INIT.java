package dcode.games.uEngine2.games.LD33;

import dcode.games.uEngine2.PuGameBase;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.Startup;
import dcode.games.uEngine2.uGameSetup;

public class LD33_INIT {
	public static void main(String[] args) {
		uGameSetup gs = new uGameSetup();
		gs.FPS = 120;
		gs.TPS_logic = 60;
		gs.TPS_MSX = 60;
		gs.TPS_BG = 30;

		gs.debug = false;
		gs.width = 320;
		gs.height = 200;
		gs.scale = 3;
		gs.frezeOnFocusLost = false;
		gs.soundEnabled = true;
		gs.fullscreen = uGameSetup.FullMODE.nope;

		gs.safeName = "LD33";
		gs.screenName = "TITLED";
		gs.windowTitle = "[LD33] Speedy GOO-zales and the meal of infinity";

		PuGameBase gb = new PuGameBase();

		gb.setup = gs;
		gb.initialInputHandler = new InputHandler();
		gb.contentInitializer = new INIT();

		Startup.StartGame(gb);
	}

	private static class INIT extends dcode.games.uEngine2.PuGameLoader {
		@Override
		public void loadInitialGameContent() {
			StData.logicTasks.registerBasic(new BaseLogic());
		}
	}
}

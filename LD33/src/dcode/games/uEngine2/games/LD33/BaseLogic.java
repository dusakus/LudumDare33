package dcode.games.uEngine2.games.LD33;

import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.games.LD33.layers.LoadingLayer;
import dcode.games.uEngine2.games.LD33.modewrappers.IMW;
import dcode.games.uEngine2.games.LD33.modewrappers.MWIG1;
import dcode.games.uEngine2.games.LD33.modewrappers.MWMENU;
import dcode.games.uEngine2.tools.Shortcuts;

import static dcode.games.uEngine2.StData.gameIsRunning;
import static dcode.games.uEngine2.StData.lastMSG;
import static dcode.games.uEngine2.games.LD33.LStData.*;

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
                Shortcuts.requestTexture("header3.png", "top3");

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



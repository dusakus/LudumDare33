package dcode.games.uEngine2.GFX.eui;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.GFX.ILayer;
import dcode.games.uEngine2.GFX.ScreenContent;
import dcode.games.uEngine2.GFX.eui.scu_modules.SCUM;
import dcode.games.uEngine2.GFX.eui.scu_modules.SCUM_Master;
import dcode.games.uEngine2.GameContainer;
import dcode.games.uEngine2.LOGIC.ILogicTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.tools.Resize;
import dcode.games.uEngine2.tools.Shortcuts;
import dcode.games.uEngine2.uGameSetup;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Created by dusakus on 31.05.15.
 */
public class SCU implements ILayer, ILogicTask {

    private SCUM masterModule = new SCUM_Master();
    private SCUM currentModule = masterModule;

    public SCU() {
        enterSetup();
    }

    @Override
    public void draw(Graphics2D G2D) {
        renderModule(G2D, currentModule);
    }

    @Override
    public boolean removeMe() {
        return !StData.INSETUP;
    }

    @Override
    public boolean renderMe() {
        return StData.INSETUP;
    }

    private void renderModule(Graphics2D g, SCUM module) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 320, 240);
        g.drawImage(module.content, 0, 0, null);
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void perform() {
        if (StData.threadManager.KW.isKeyHeld(KeyEvent.VK_ESCAPE)) leaveSetup();
    }

    private void enterSetup() {
        StData.LOG.println("Entering Startup configuration utility...");
        StData.INSETUP = true;
        StData.currentGC.currentSC.layers_Foreground.add(this);
        StData.threadManager.LT.LOOP_TPS = 30;
        StData.threadManager.LT.LOOP_Recalculate = true;
        StData.currentGC.currentLT.registerBasic(this);
        Resize.updateRenderingSetup(uGameSetup.FullMODE.setup_util);
        Graphics g = StData.NextFrame.getGraphics();
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 320, 240);
        g.setColor(Color.YELLOW);
        g.setFont(new Font(Font.MONOSPACED,Font.BOLD,32));
        g.drawString("ENTERING SETUP...", 2, 64);
        StData.scu = this;
        currentModule.TriggerEvent(null);
    }

    private void leaveSetup() {
        StData.LOG.println("Leaving Startup configuration utility...");
        StData.INSETUP = false;
        StData.currentGC = new GameContainer();
        StData.threadManager.LT.LOOP_TPS = StData.setup.TPS_logic;
        StData.threadManager.LT.LOOP_Recalculate = true;
        Resize.updateRenderingSetup();
    }

    @Override
    public boolean doRepeat() {
        return StData.INSETUP;
    }
}

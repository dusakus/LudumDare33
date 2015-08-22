/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dcode.games.uEngine2.window;

import dcode.games.uEngine2.BGTasks.PBGTask;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.input.KeyWrapper;
import dcode.games.uEngine2.input.PointerWrapper;
import dcode.games.uEngine2.tools.Resize;
import dcode.games.uEngine2.tools.Shortcuts;
import dcode.games.uEngine2.uGameSetup;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;

/**
 * @author dusakus
 */
public class Window {

    private JFrame window;
    private Canvas gc;

    public Window(Canvas GC, boolean full) {
        gc = GC;
        StData.LOG.println("uEngine2: Creating window", "N");
        window = new JFrame(StData.setup.windowTitle + " | in DCode uEngine " + StData.VersionString);
        window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        window.setLayout(new BorderLayout());
        window.add(GC, BorderLayout.CENTER);
        window.setResizable(false);
        if (full) {
            if (!isFullscreenSupported()) {
                Shortcuts.registerOneTimeBGTask(new Windowify(), true);
            } else {
                window.setUndecorated(true);
                GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
                env.getDefaultScreenDevice().setFullScreenWindow(window);
                window.addFocusListener(new FocusListener() {

                    @Override
                    public void focusGained(FocusEvent arg0) {
                        window.setAlwaysOnTop(true);
                    }

                    @Override
                    public void focusLost(FocusEvent arg0) {
                        window.setAlwaysOnTop(false);
                    }
                });
            }
        } else {
            window.pack();
            window.setLocationRelativeTo(null);
        }
        window.setVisible(true);
        StData.LOG.println("uEngine2: Done", "N");
    }

    private boolean isFullscreenSupported() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = env.getDefaultScreenDevice();
        StData.LOG.println("isFullScreenSupported: " + defaultScreen.isFullScreenSupported(), "D");
        return defaultScreen.isFullScreenSupported();
    }

    public void registerListeners(KeyWrapper keyM, PointerWrapper pointM) {
        StData.LOG.println("Attaching input listeners to window...");
        gc.addKeyListener(keyM);
        gc.addMouseListener(pointM);
        gc.requestFocus();
        gc.requestFocusInWindow();

        window.addWindowListener(new exitListener());
    }

    public void registerExtMouseListener(MouseListener ml) {
        gc.addMouseListener(ml);
    }

    public void updateWLabel(String s) {
        if (s != null) {
            window.setTitle(s);
        } else {
            window.setTitle(StData.setup.windowTitle + " | in DCode uEngine " + StData.VersionString);
        }
    }

    public void rimuw() {
        window.dispose();
    }

    private class Windowify extends PBGTask {
        public Windowify(){
            this.TaskPriority = PRIORITY_LOW;
        }
        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void perform() {
            StData.setup.fullscreen = uGameSetup.FullMODE.nope;
            Resize.updateRenderingSetup();
            done = true;
        }
    }
}

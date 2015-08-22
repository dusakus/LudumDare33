/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine2.input;

import dcode.games.uEngine2.PInputHandler;
import dcode.games.uEngine2.StData;
import dcode.games.uEngine2.tools.numbarTools;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * @author dusakus
 */
public class PointerWrapper implements MouseListener {

    private PInputHandler PIH;
    private int offsetX = 0, offsetY = 0;
    private float scalemodX = 0, scalemodY = 0;

    public void setInputHandler(PInputHandler pih) {
        PIH = pih;
        updateFSOffsets();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int clicxX = (int) ((e.getX() - offsetX) / scalemodX);
        int clicxY = (int) ((e.getY() - offsetY) / scalemodY);
        if (PIH != null && numbarTools.checkBetween(clicxX, 0, StData.setup.width) && numbarTools.checkBetween(clicxY, 0, StData.setup.height)) {
            switch (e.getButton()) {
                case 1:
                    PIH.clickedLeft(clicxX, clicxY);
                    break;
                case 3:
                    PIH.clickedRight(clicxX, clicxY);
                    break;
                case 2:
                    PIH.clickedMiddle(clicxX, clicxY);
                    break;
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    public void updateFSOffsets() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeX = screen.width;
        int sizeY = (int) ((1.0f * screen.width / StData.setup.width) * StData.setup.height);
        if (sizeY > screen.height) {
            sizeY = screen.height;
            sizeX = (int) ((1.0f * screen.height / StData.setup.height) * StData.setup.width);
        }
        switch (StData.setup.fullscreen) {
            case nope:
                scalemodX = StData.setup.scale;
                scalemodY = StData.setup.scale;
                offsetX = 0;
                offsetY = 0;
                break;
            case box:
                scalemodX = StData.setup.scale;
                scalemodY = StData.setup.scale;
                offsetX = screen.width / 2 - StData.setup.width * StData.setup.scale / 2;
                offsetY = screen.height / 2 - StData.setup.height * StData.setup.scale / 2;
                break;
            case stretch:
                scalemodX = 1.0f * screen.width / StData.setup.width;
                scalemodY = 1.0f * screen.height / StData.setup.height;
                offsetX = 0;
                offsetY = 0;
                break;
            case scaled_box:
                scalemodX = 1.0f * sizeX / StData.setup.width;
                scalemodY = 1.0f * sizeY / StData.setup.height;
                offsetX = screen.width / 2 - sizeX / 2;
                offsetY = screen.height / 2 - sizeY / 2;
                break;
            case colored_box:
                scalemodX = StData.setup.scale;
                scalemodY = StData.setup.scale;
                offsetX = screen.width / 2 - StData.setup.width * StData.setup.scale / 2;
                offsetY = screen.height / 2 - StData.setup.height * StData.setup.scale / 2;
                break;
            case colored_scaled_box:
                scalemodX = 1.0f * sizeX / StData.setup.width;
                scalemodY = 1.0f * sizeY / StData.setup.height;
                offsetX = screen.width / 2 - sizeX / 2;
                offsetY = screen.height / 2 - sizeY / 2;
                break;
        }
        if (StData.INSETUP) {
            scalemodX = 2;
            scalemodY = 2;
            offsetX = 0;
            offsetY = 0;
        }
        StData.LOG.println("[PWrap] ====Setting new input offsets====");
        StData.LOG.println("[PWrap] -> offsetX: " + offsetX);
        StData.LOG.println("[PWrap] -> offsetY: " + offsetY);
        StData.LOG.println("[PWrap] -> scaleX: " + scalemodX);
        StData.LOG.println("[PWrap] -> scaleY: " + scalemodY);
        StData.LOG.println("[PWrap] targetting mode " + StData.setup.fullscreen);
        StData.LOG.println("[PWrap] detected info");
        StData.LOG.println("[PWrap] <- screen width: " + screen.width);
        StData.LOG.println("[PWrap] <- screen height: " + screen.height);
        StData.LOG.println("[PWrap] <- game frame sizeX: " + sizeX);
        StData.LOG.println("[PWrap] <- game frame sizeY: " + sizeY);
    }
}

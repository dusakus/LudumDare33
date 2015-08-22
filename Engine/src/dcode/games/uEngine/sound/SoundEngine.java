/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine.sound;

import dcode.games.uEngine.sound.parts.INotePlayer;
import dcode.games.uEngine.sound.parts.NP_GBBEEP;
import dcode.games.uEngine2.StData;

/**
 *
 * @author dusakus
 */
public class SoundEngine {

    public SoundEngine() {
        INotePlayer inp = new NP_GBBEEP();
        StData.currentGC.currentAC.msxCH1 = new MSX(this);
        StData.currentGC.currentAC.msxCH1.defaultNP = inp;
        StData.currentGC.currentAC.msxCH2 = new MSX(this);
        StData.currentGC.currentAC.msxCH2.defaultNP = inp;
        StData.currentGC.currentAC.msxCH3 = new MSX(this);
        StData.currentGC.currentAC.msxCH3.defaultNP = inp;
        StData.currentGC.currentAC.msxCH4 = new MSX(this);
        StData.currentGC.currentAC.msxCH4.defaultNP = inp;
    }

    public void tick() {
        StData.currentGC.currentAC.msxCH1.tick();
        StData.currentGC.currentAC.msxCH2.tick();
        StData.currentGC.currentAC.msxCH3.tick();
        StData.currentGC.currentAC.msxCH4.tick();
    }

}

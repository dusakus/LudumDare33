/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine.sound.parts;

import dcode.games.uEngine2.SFX.tslib.Music;
import dcode.games.uEngine2.SFX.tslib.TinySound;
import dcode.games.uEngine2.StData;

/**
 *
 * @author dusakus
 */
public class NP_GBBEEP implements INotePlayer {

    private Music[][] clips;

    public NP_GBBEEP() {
	    if(!TinySound.isInitialized()) TinySound.init();
        StData.LOG.println("uEngine-GB note player: loading sound files", "N");
        clips = new Music[7][13];
        for (int i = 1; i < 7; i++) {
            for (int j = 1; j < 13; j++) {
                try {
                    clips[i][j] = TinySound.loadMusic(getClass().getClassLoader().getResource("dcode/games/uEngine/res/SFX/GBbeepLow/" + i + "-" + j + ".wav"));
                } catch (Exception ex) {
                    StData.LOG.printerr(ex, "Error occured when loading sounds", "E2");
                }
            }
        }
        StData.LOG.println("uEngine-GB note player: done", "N");
    }

    @Override
    public int getOctaveCount() {
        return 6;
    }

    @Override
    public void startNote(Note note) {
        if (note.octave > 0 && note.octave < 7 && note.note > 0 && note.note < 13) {
            try {
                clips[note.octave][note.note].play(true);
            } catch (Exception ex) {
                StData.LOG.printerr(ex, "Error occured when starting Note", "E2");
            }
        }
    }

    @Override
    public void stopNote(Note note) {
        if (note.octave > 0 && note.octave < 7 && note.note > 0 && note.note < 13) {
            try {
                clips[note.octave][note.note].stop();
            } catch (Exception ex) {
                StData.LOG.printerr(ex, "Error occured when stopping Note", "E2");
            }
        }
    }

    @Override
    public void resetNote(Note note) {
        if (note.octave > 0 && note.octave < 7 && note.note > 0 && note.note < 13) {
            try {
            } catch (Exception ex) {
                StData.LOG.printerr(ex, "Error occured when resetting note", "E2");
            }
        }
    }

}

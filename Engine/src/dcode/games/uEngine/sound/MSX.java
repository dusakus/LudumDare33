/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine.sound;

import dcode.games.uEngine.sound.parts.INotePlayer;
import dcode.games.uEngine.sound.parts.Note;
import dcode.games.uEngine2.StData;

import javax.sound.sampled.Clip;
import java.util.LinkedList;

/**
 *
 * @author dusakus
 */
public class MSX {

    SoundEngine SE;
    Clip CH;

    public INotePlayer defaultNP;
    private boolean inplay = false;
    private Note lastNote;
    public boolean muted;

    public MSX(SoundEngine SE) {
        this.SE = SE;
        lastNote = null;
    }

    public void playNote(Note n) {
        if (lastNote != null && lastNote.octave >= 0) {
            defaultNP.stopNote(lastNote);
        }
        lastNote = n;
        if (n == null || n.octave == -1) {
            defaultNP.stopNote(lastNote);
            return;
        }
        defaultNP.stopNote(n);
        defaultNP.resetNote(n);
        if (!muted) {

            defaultNP.startNote(n);
        }
    }

    private LinkedList<Note> notes;
    private boolean repeat = false;
    private int ticksLeft = 0;
    private int reloadDelay = StData.setup.TPS_MSX;

    public void newLoop() {
        notes = new LinkedList<Note>();
        inplay = true;
    }

    public void addNote(Note n) {
        notes.offer(n);
    }

    public void setRepeat(boolean val) {
        repeat = val;
    }

    public void tick() {
        if (inplay) {
            if (ticksLeft == 0) {
	            if(notes.isEmpty()){
		            defaultNP.stopNote(lastNote);
		            inplay = false;
		            return;
	            }
                ticksLeft = notes.peek().lenghtInTicks;
                playNote(notes.peek());
                if (repeat) {
                    notes.offer(notes.peek());
                }
                notes.poll();
                reloadDelay = StData.setup.TPS_MSX / 2;
            }
            if (reloadDelay == 0) {
                playNote(lastNote);
                reloadDelay = StData.setup.TPS_MSX;
            }

            ticksLeft--;
            reloadDelay--;
        }
    }

    public void endLoop() {
        repeat = false;
        inplay = false;
        notes = new LinkedList<Note>();
        ticksLeft = 0;
    }
    
    public int getCurrentNoteO(){
        return this.lastNote.octave;
    }
    public int getCurrentNoteN(){
        return this.lastNote.note;
    }
}

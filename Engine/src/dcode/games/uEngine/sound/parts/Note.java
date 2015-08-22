/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dcode.games.uEngine.sound.parts;

/**
 *
 * @author dusakus
 */
public class Note {

    public int lenghtInTicks = 1;
    public int octave = 1;
    public int note = 8;

    public Note(int octave, int note, int lenghtInTicks) {
        this.octave = octave;
        this.note = note;
        this.lenghtInTicks = lenghtInTicks;
    }
}

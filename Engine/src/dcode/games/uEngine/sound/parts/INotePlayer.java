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
public interface INotePlayer {

    abstract int getOctaveCount();

    abstract public void startNote(Note n);

    abstract public void stopNote(Note n);

    abstract public void resetNote(Note n);
}

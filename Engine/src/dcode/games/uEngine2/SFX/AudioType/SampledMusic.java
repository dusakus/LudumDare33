package dcode.games.uEngine2.SFX.AudioType;

import dcode.games.uEngine2.StData;

/**
 * Created by dusakus on 13.04.15.
 */
public class SampledMusic {
    String msPlayerID;

    public boolean isPlaying = false;
    private boolean wasPlaying = false;
    public boolean isStopped = false;
    private boolean wasStopped = false;
    public boolean isLooped = false;
    private boolean wasLooped = false;

    public float volume = 1;
    private float density = -1;      //Because reasons
    public float pan = 0;


    //Check if changed, and update backend if needed
    public void updateStatus() {
        if (msPlayerID == null) return;                  //return if id not specified
        if (isPlaying != wasPlaying) {
            StData.resources.smsx.setPlaying(msPlayerID, isPlaying, isLooped);
            wasPlaying = isPlaying;
        }
        if (isStopped != wasStopped) {
            StData.resources.smsx.setStopped(msPlayerID, isStopped);
            wasStopped = isStopped;
        }
        if (isLooped != wasLooped) {
            StData.resources.smsx.setLooped(msPlayerID, isLooped);
            wasLooped = isLooped;
        }
    }
}

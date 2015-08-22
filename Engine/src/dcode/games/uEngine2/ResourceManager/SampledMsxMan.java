package dcode.games.uEngine2.ResourceManager;

import dcode.games.uEngine2.ResourceManager.DSU.DSU_NODE;
import dcode.games.uEngine2.SFX.tslib.Music;

/**
 * Created by dusakus on 29.03.15.
 */
public class SampledMsxMan {

    private DSU_NODE musicRegistry;

    public SampledMsxMan(){
        musicRegistry= new DSU_NODE(0, false);
    }

    public void setPlaying(String streamID, boolean isPlaying, boolean isLoop) {
        Music m = (Music) musicRegistry.getObject(streamID);
        if(m!= null){
            if(isPlaying){
                m.play(isLoop);
            } else {
                m.pause();
            }
        }
    }

    public void setStopped(String streamID, boolean isStopped) {
        Music m = (Music) musicRegistry.getObject(streamID);
        if(m!= null){
            if(isStopped){
                m.stop();
            }
        }
    }

    public void setLooped(String streamID, boolean isLooped) {
        Music m = (Music) musicRegistry.getObject(streamID);
        if(m!= null){
            m.setLoop(isLooped);
        }
    }
}

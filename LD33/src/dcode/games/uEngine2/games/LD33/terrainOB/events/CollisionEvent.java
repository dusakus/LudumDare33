package dcode.games.uEngine2.games.LD33.terrainOB.events;

import dcode.games.uEngine2.games.LD33.terrainOB.TerrainObject;

/**
 * Created by dusakus on 22.08.2015.
 */
public abstract class CollisionEvent {
    public abstract void onCollision(boolean player,TerrainObject to);
}

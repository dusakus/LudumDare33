package dcode.games.uEngine2.games.LD33.terrainOB;

import dcode.games.uEngine2.games.LD33.terrainOB.events.CollisionEvent;

/**
 * Created by dusakus on 22.08.2015.
 */
public class TerrainObject {
    public int Xlocation = -1, Ylocation = 2;
    public int XOffset = 0, YOffset = 0;
    public int collisionoffset = 15, collisionLenght = 30;
    public CollisionEvent onCollision = null;
    public String WorldTexture = "MISSING";
    public String WarnTexture = "MISSING";
    public String name = "unnamed";
}

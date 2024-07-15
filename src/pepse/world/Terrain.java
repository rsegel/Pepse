package pepse.world;

import danogl.util.Vector2;




public class Terrain {
    private static final float DEFAULT_GROUND_FACTOR = (float) (2.0/3.0);
    static private float groundHeightAtX0;
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = windowDimensions.y() * DEFAULT_GROUND_FACTOR;
    }

    public static float getGroundHeightAtX0(){
        return groundHeightAtX0;
    }

    // TODO: implement with perlin noise
    public float groundHeightAt(float x) { return groundHeightAtX0; }

}

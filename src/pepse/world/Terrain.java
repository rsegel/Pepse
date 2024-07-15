package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.util.ArrayList;
import java.util.List;
import java.awt.Color;




public class Terrain {
    private static final float DEFAULT_GROUND_FACTOR = (float) (2.0/3.0);
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    static private float groundHeightAtX0;
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = windowDimensions.y() * DEFAULT_GROUND_FACTOR;
    }

    public static float getGroundHeightAtX0(){
        return groundHeightAtX0;
    }

    // TODO: implement with perlin noise
    public float groundHeightAt(float x) { return groundHeightAtX0; }

    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();
        // find the closest X that is a multiple of 30
        // it should be smaller than minX
        int closestStartX = minX - (minX % 30);
        int closestEndX = maxX + (30 - (maxX % 30));
        for (int x = closestStartX; x < closestEndX; x += 30) {
            // create a block at x, groundHeightAt(x)
            Block block = new Block(
                    new Vector2(x, groundHeightAt(x)),
                    new RectangleRenderable(BASE_GROUND_COLOR)
            );
            blocks.add(block);
        }
        return blocks;



    }

}
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
    private static final double DEPTH_OF_BLOCKS = 20;
    private static final double DEPTH_OF_BLOCKS1 = DEPTH_OF_BLOCKS;
    private static final int DIST_BWTWEENBLOCKS = 30;
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
        int closestStartX = minX - (minX % DIST_BWTWEENBLOCKS);
        int closestEndX = maxX + (DIST_BWTWEENBLOCKS - (maxX % DIST_BWTWEENBLOCKS));
        for (int x = closestStartX; x < closestEndX; x += DIST_BWTWEENBLOCKS) {
            for (int i = 0; i < DEPTH_OF_BLOCKS1; i++) {
                // create a block at x, groundHeightAt(x)
                Block block = new Block(
                        new Vector2(
                                (float) x,
                                (float) (Math.floor(groundHeightAt(x)/Block.SIZE) * Block.SIZE)
                                        + i * Block.SIZE
                        ),
                        new RectangleRenderable(BASE_GROUND_COLOR)
                );
                blocks.add(block);
            }
        }
        return blocks;



    }

}
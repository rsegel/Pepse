package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.awt.Color;
import java.util.Map;

import static pepse.world.Block.SIZE;


public class Terrain {
    private static final float DEFAULT_GROUND_FACTOR = (float) (2.0/3.0);
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final double DEPTH_OF_BLOCKS = 20;
    private static final double DEPTH_OF_BLOCKS1 = DEPTH_OF_BLOCKS;
    private static final int DIST_BWTWEENBLOCKS = 30;
    private static final double NOISE_FACTOR = 15;
    private static NoiseGenerator p = null;
    static private float groundHeightAtX0;
    private static Map<Float, Float> groundHeights = new HashMap<>();
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = windowDimensions.y() * DEFAULT_GROUND_FACTOR;
        p = new NoiseGenerator((double) seed, (int) groundHeightAtX0);
    }

    public static float getGroundHeightAtX0(){
        return groundHeightAtX0;
    }

    private void generateGroundHeight(int x){
        float y = (float) (groundHeightAtX0 + DEPTH_OF_BLOCKS * p.noise(x, NOISE_FACTOR));
        y = (float) (Math.floor(y / SIZE) * SIZE);
        groundHeights.put((float) x, y);
    }

    public static float groundHeightAt(float x) {
        return groundHeights.get(x);
    }


    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();
        // find the closest X that is a multiple of 30
        // it should be smaller than minX
        int closestStartX = minX - (minX % DIST_BWTWEENBLOCKS);
        int closestEndX = maxX + (DIST_BWTWEENBLOCKS - (maxX % DIST_BWTWEENBLOCKS));
        for (int x = closestStartX; x < closestEndX; x += DIST_BWTWEENBLOCKS) {
            generateGroundHeight(x);
            for (int i = 0; i < DEPTH_OF_BLOCKS1; i++) {
                boolean isTopLayer = i == 0;
                // create a block at x, groundHeightAt(x)
                Block block = new Block(
                        new Vector2(
                                (float) x,
                                groundHeightAt(x) + i * SIZE),
                        new RectangleRenderable(ColorSupplier.approximateColor(BASE_GROUND_COLOR)), isTopLayer
                );
                blocks.add(block);
            }
        }
        return blocks;



    }

}
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

/**
 * A class that represents the terrain in the game.
 * The terrain is generated using Perlin noise.,
 * and responsible for creating the blocks that make up the ground.
 */
public class Terrain {
    private static final float DEFAULT_GROUND_FACTOR = (float) (2.0/3.0);
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private static final double DEPTH_OF_BLOCKS = 30;
    private static final int DIST_BETWEEN_BLOCKS = 30;
    private static final double NOISE_FACTOR = 10;
    private static final int NUM_OF_ADD = 2;
    private static NoiseGenerator p = null;
    static private float groundHeightAtX0;
    private static final Map<Float, Float> groundHeights = new HashMap<>();

    /**
     * Creates a new terrain object with the specified window dimensions and seed.
     * @param windowDimensions The dimensions of the window.
     * @param seed The seed for the Perlin noise generator.
     */
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = windowDimensions.y() * DEFAULT_GROUND_FACTOR;
        p = new NoiseGenerator(seed, (int) groundHeightAtX0);
    }

    /**
     * getter for groundHeightAtX0
     * @return the ground height at x = 0
     */
    public static float getGroundHeightAtX0(){
        return groundHeightAtX0;
    }

    private void generateGroundHeight(int x){
        float y = (float) (groundHeightAtX0 + DEPTH_OF_BLOCKS * p.noise(x, NOISE_FACTOR));
        y = (float) (Math.floor(y / SIZE) * SIZE);
        groundHeights.put((float) x, y);
    }
    /**
     * Returns the height of the ground at the specified x coordinate.
     * @param x The x coordinate.
     * @return The height of the ground at the specified x coordinate.
     */
    public static float groundHeightAt(float x) {
        x = (float) (Math.floor(x / SIZE) * SIZE);
        return groundHeights.get(x);
    }

    /**
     * Creates a list of blocks in the specified range.
     * @param minX The minimum x coordinate.
     * @param maxX The maximum x coordinate.
     * @return A list of blocks in the specified range.
     */
    public List<Block> createInRange(int minX, int maxX) {
        List<Block> blocks = new ArrayList<>();
        // find the closest X that is a multiple of 30
        // it should be smaller than minX
        int closestStartX = minX - (minX % DIST_BETWEEN_BLOCKS) - NUM_OF_ADD * SIZE;
        int closestEndX = maxX + (DIST_BETWEEN_BLOCKS - (maxX % DIST_BETWEEN_BLOCKS)) + NUM_OF_ADD * SIZE;
        for (int x = closestStartX; x < closestEndX; x += DIST_BETWEEN_BLOCKS) {
            generateGroundHeight(x);
            createBlocksAtX(x, blocks);
        }
        return blocks;

    }

    private void createBlocksAtX(int x, List<Block> blocks) {
        for (int i = 0; i < DEPTH_OF_BLOCKS; i++) {
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

}
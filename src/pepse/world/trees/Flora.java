package pepse.world.trees;

import danogl.GameObject;
import danogl.util.Vector2;
import pepse.world.GeneratorInRange;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Class for creating trees in the world at random locations
 */
public class Flora implements GeneratorInRange {
    private static final int TREE_SPACING = 90;
    private static final float PROBABILITY_FOR_TREE = 0.2f;
    private final Function<Float, Float> getGroundHeight;
    private final int seed;
    private final BiConsumer<GameObject, GameObject> fruitEatingHandler;

    /**
     * Create a flora object
     * @param getGroundHeight a function that returns the ground height at a given x value
     * @param seed the seed for the random number generator
     */
    public Flora(Function<Float, Float> getGroundHeight, int seed,
                 BiConsumer<GameObject, GameObject> fruitEatingHandler) {
        this.getGroundHeight = getGroundHeight;
        this.seed = seed;
        this.fruitEatingHandler = fruitEatingHandler;
    }


    /**
     * Create trees in the world at random locations
     * @param minX the minimum x value for the trees
     * @param maxX the maximum x value for the trees
     * @return a list of trees
     */
    public List<GameObject> createInRange(int minX, int maxX) {
        List<GameObject> trees = new ArrayList<>();
        for (int x = minX; x <= maxX; x += TREE_SPACING){
            float blockX = (float) ((double) (x / TREE_SPACING) * TREE_SPACING);
            Random random = new Random(Objects.hash(blockX, seed));
            if (random.nextFloat() > PROBABILITY_FOR_TREE) continue;
            Vector2 location = new Vector2(blockX, this.getGroundHeight.apply(blockX));
            trees.add(new Tree(location, seed, fruitEatingHandler));
        }
        return trees;
    }

}

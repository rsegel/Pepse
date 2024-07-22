package pepse.world.trees;

import danogl.util.Vector2;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import static pepse.world.Block.SIZE;

/**
 * Class for creating trees in the world at random locations
 */
public class Flora {
    private static final int TREE_SPACEING = 90;
    private static final float PROBABILITY_FOR_TREE = 0.2f;
    private static final int RANDOM_SEED = 42;

    /**
     * Create trees in the world at random locations
     * @param minX the minimum x value for the trees
     * @param maxX the maximum x value for the trees
     * @return a list of trees
     */
    public static List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();
        for (int x = minX; x <= maxX; x += TREE_SPACEING){
            float blockX = (float) (Math.floor(x / SIZE) * SIZE);
            Random random = new Random(Objects.hash(blockX, RANDOM_SEED));
            if (random.nextFloat() > PROBABILITY_FOR_TREE) continue;
            Vector2 location = new Vector2(blockX, Terrain.groundHeightAt(blockX));
            trees.add(new Tree(location, RANDOM_SEED));
        }
        return trees;
    }

}

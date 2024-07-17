package pepse.world.trees;

import danogl.util.Vector2;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static pepse.world.Block.SIZE;


public class Flora {

    private static final int TREE_SPACEING = 80;
    private static final float PROBABILITY_FOR_TREE = 0.2f;

    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();
        for (int x = minX; x <= maxX; x += TREE_SPACEING){
            if (Math.random() > PROBABILITY_FOR_TREE) continue;
            float blockX = (float) (Math.floor(x / SIZE) * SIZE);
            Vector2 location = new Vector2(blockX, Terrain.groundHeightAt(blockX));
            // TODO - correct for changing terrain
            trees.add(new Tree(location));
        }
        return trees;
    }

}

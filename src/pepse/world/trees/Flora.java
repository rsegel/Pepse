package pepse.world.trees;

import danogl.util.Vector2;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Flora {

    private static final int TREE_SPACEING = 80;
    private static final float PROBABILITY_FOR_TREE = 0.1f;

    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();
        for (int x = minX; x <= maxX; x += TREE_SPACEING){
            if (Math.random() > PROBABILITY_FOR_TREE) continue;
            Vector2 location = new Vector2(x, Terrain.getGroundHeightAtX0());
            // TODO - correct for changing terrain
            trees.add(new Tree(location));
        }
        return trees;
    }

}

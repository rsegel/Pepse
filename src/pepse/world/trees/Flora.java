package pepse.world.trees;

import danogl.util.Vector2;
import pepse.world.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Flora {

    private static final int RANDOM_BOUND = 10;

    public List<Tree> createInRange(int minX, int maxX) {
        List<Tree> trees = new ArrayList<>();
        Random random = new Random();
        for (int x = minX; x <= maxX; x += 10){
            if (random.nextInt(RANDOM_BOUND) != 0) continue;
            Vector2 location = new Vector2(x, Terrain.getGroundHeightAtX0());
            trees.add(new Tree(location));
        }
        return trees;
    }

}

package pepse.world;

import danogl.GameObject;

import java.util.List;

/**
 * Interface for generating objects in a given range of the game.
 */
public interface GeneratorInRange {
    /**
     * Generate objects in a range.
     * @param minX The minimum x value for the objects.
     * @param maxX The maximum x value for the objects.
     * @return A list of objects.
     */
    List<GameObject> createInRange(int minX, int maxX);
}

package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.LayerGetter;
import pepse.Tags;

/**
 * A block is a simple immovable object that can be used to create walls or floors.
 */
public class Block extends GameObject {
    /**
     * The size of a block.
     */
    public static final int SIZE = 30;
    /**
     * Creates a new block at the specified position.
     * @param topLeftCorner The top left corner of the block.
     * @param renderable The renderable to use for the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable, boolean isTopLayer) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        if (isTopLayer) {
            setTag(TagsToNames.getTagName(Tags.TOP_LAYER_BLOCK));
        } else {
            setTag(TagsToNames.getTagName(Tags.BLOCK));
        }
    }

}


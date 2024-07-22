package pepse;

import danogl.collisions.Layer;
import pepse.world.TagsToNames;

/**
 * A class that gets the layer of a tag.
 */
public class LayerGetter {
    private static final int skyLayer = Layer.BACKGROUND - 1;
    private static final int NIGHT_LAYER = Layer.UI + 1;
    private static final int UNDERGROUND_LAYER = Layer.BACKGROUND + 1;

    /**
     * Gets the layer of a tag.
     * @param tag The tag to get the layer of.
     * @return The layer of the tag.
     */
    public static int getLayer(Tags tag) {

        switch (tag) {
            case AVATAR:
                return Layer.DEFAULT;
            case SUN, SUN_HALO:
                return Layer.BACKGROUND;
            case BLOCK:
                return UNDERGROUND_LAYER;
            case TREE, FRUIT, LEAF, TOP_LAYER_BLOCK:
                return Layer.STATIC_OBJECTS;
            case ENERGY, COLLECTED_FRUIT:
                return Layer.UI;
            case SKY:
                return skyLayer;
            case NIGHT:
                return NIGHT_LAYER;
            default:
                return Layer.DEFAULT;
        }
    }
}


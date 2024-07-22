package pepse;

import danogl.collisions.Layer;
import pepse.world.TagsToNames;

/**
 * A class that gets the layer of a tag.
 */
public class LayerGetter {
    /**
     * Gets the layer of a tag.
     * @param tag The tag to get the layer of.
     * @return The layer of the tag.
     */
    public static int getLayer(Tags tag) {
        final int skyLayer = Layer.BACKGROUND - 1;
        final int NIGHT_LAYER = Layer.UI + 1; //TODO - consider

        switch (tag) {
            case Tags.AVATAR:
                return Layer.DEFAULT;
            case Tags.SUN, Tags.SUN_HALO:
                return Layer.BACKGROUND;
            case Tags.BLOCK:
                return Layer.BACKGROUND + 1;
            case  Tags.TREE, Tags.FRUIT, Tags.LEAF:
                return Layer.STATIC_OBJECTS;
            case Tags.TOP_LAYER_BLOCK:
                return Layer.STATIC_OBJECTS;
            case Tags.ENERGY:
                return Layer.UI;
            case Tags.COLLECTED_FRUIT:
                return Layer.UI;
            case Tags.SKY:
                return skyLayer;
            case Tags.NIGHT:
                return NIGHT_LAYER;
            default:
                return Layer.DEFAULT;
        }
    }
}


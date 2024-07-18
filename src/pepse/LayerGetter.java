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
        switch (tag) {
            case Tags.AVATAR:
                return Layer.DEFAULT;
            case Tags.BLOCK:
                return Layer.BACKGROUND;
            case Tags.TOP_LAYER_BLOCK:
                return Layer.STATIC_OBJECTS;
            case Tags.TREE:
                return Layer.STATIC_OBJECTS;
            case Tags.FRUIT:
                return Layer.STATIC_OBJECTS;
            case Tags.LEAF:
                return Layer.STATIC_OBJECTS;
            case Tags.SKY:
                return Layer.BACKGROUND;
            case Tags.NIGHT:
                return Layer.FOREGROUND;
            case Tags.SUN:
                return Layer.BACKGROUND;
            case Tags.ENERGY:
                return Layer.UI;
            case Tags.COLLECTED_FRUIT:
                return Layer.UI;
            default:
                return Layer.DEFAULT;
        }
    }
}

//            case "avatar":
//                return Layer.DEFAULT;
//            case "block":
//                return Layer.BACKGROUND;
//            case "topLayerBlock":
//                return Layer.STATIC_OBJECTS;
//            case "tree":
//                return Layer.STATIC_OBJECTS;
//            case "fruit":
//                return Layer.STATIC_OBJECTS;
//            case "leaf":
//                return Layer.STATIC_OBJECTS;
//            case "sky", "sunHalo":
//                return Layer.BACKGROUND;
//            case "night":
//                return Layer.FOREGROUND;
//            case "sun":
//                return Layer.BACKGROUND;
//            case "energy":
//                return Layer.UI;
//            case "collectedFruit":
//                return Layer.UI;
//            default:
//                return Layer.DEFAULT;


package pepse.world;

import pepse.Tags;
/**
 * A class that converts tags to names.
 */
public class TagsToNames {
    /**
     * Converts a tag to a name.
     * @param tag The tag to convert.
     * @return The name of the tag.
     */
    public static String getTagName(Tags tag) {
        switch (tag) {
            case AVATAR:
                return "avatar";
            case TREE:
                return "tree";
            case FRUIT:
                return "fruit";
            case SKY:
                return "sky";
            case NIGHT:
                return "night";
            case SUN:
                return "sun";
            case SUN_HALO:
                return "sun halo";
            case ENERGY:
                return "energy";
            case LEAF:
                return "leaf";
            case COLLECTED_FRUIT:
                return "collected fruit";
            case BLOCK:
                return "block";
            case TOP_LAYER_BLOCK:
                return "top layer block";
            default:
                return "unknown";
        }
    }
    /**
     * Converts a name to a tag.
     * @param name The name to convert.
     * @return The tag of the name.
     */
    public static Tags getTag(String name) {
        switch (name) {
            case "avatar":
                return Tags.AVATAR;
            case "tree":
                return Tags.TREE;
            case "fruit":
                return Tags.FRUIT;
            case "sky":
                return Tags.SKY;
            case "night":
                return Tags.NIGHT;
            case "sun":
                return Tags.SUN;
            case "sun halo":
                return Tags.SUN_HALO;
            case "energy":
                return Tags.ENERGY;
            case "leaf":
                return Tags.LEAF;
            case "collected fruit":
                return Tags.COLLECTED_FRUIT;
            case "block":
                return Tags.BLOCK;
            case "top layer block":
                return Tags.TOP_LAYER_BLOCK;
            default:
                return Tags.UNKNOWN;
        }
    }
}

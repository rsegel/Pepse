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
                return "Avatar";
            case GROUND:
                return "Ground";
            case UNDER_GROUND:
                return "Under Ground";
            case TREE:
                return "Tree";
            case FRUIT:
                return "Fruit";
            case FLOWER:
                return "Flower";
            case SKY:
                return "Sky";
            case NIGHT:
                return "Night";
            case SUN:
                return "Sun";
            case SUN_HALO:
                return "Sun Halo";
            case ENERGY:
                return "Energy";
            case LEAF:
                return "Leaf";
            case COLLECTED_FRUIT:
                return "Collected Fruit";
            default:
                return "Unknown";
        }
    }
    public static Tags getTag(String name) {
        switch (name) {
            case "Avatar":
                return Tags.AVATAR;
            case "Ground":
                return Tags.GROUND;
            case "Under Ground":
                return Tags.UNDER_GROUND;
            case "Tree":
                return Tags.TREE;
            case "Fruit":
                return Tags.FRUIT;
            case "Flower":
                return Tags.FLOWER;
            case "Sky":
                return Tags.SKY;
            case "Night":
                return Tags.NIGHT;
            case "Sun":
                return Tags.SUN;
            case "Sun Halo":
                return Tags.SUN_HALO;
            case "Energy":
                return Tags.ENERGY;
            case "Leaf":
                return Tags.LEAF;
            case "Collected Fruit":
                return Tags.COLLECTED_FRUIT;
            case "Block":
                return Tags.BLOCK;
            case "Top Layer Block":
                return Tags.TOP_LAYER_BLOCK;
            default:
                return Tags.UNKNOWN;
        }
    }
}

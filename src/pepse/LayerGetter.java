package pepse;

import danogl.collisions.Layer;

public class LayerGetter {
    public static int getLayer(Tags tag) {
        switch (tag) {
            case AVATAR:
                return Layer.DEFAULT;
            case GROUND:
                return Layer.STATIC_OBJECTS;
            case UNDER_GROUND:
                return Layer.BACKGROUND;
            case TREE:
                return Layer.STATIC_OBJECTS;
            case FRUIT:
                return Layer.STATIC_OBJECTS + 2;
            case FLOWER:
                return Layer.STATIC_OBJECTS + 1;
            case SKY:
                return Layer.BACKGROUND;
            case NIGHT:
                return Layer.FOREGROUND;
            case SUN:
                return Layer.BACKGROUND;
            case SUN_HALO:
                return Layer.BACKGROUND;
            case ENERGY:
                return Layer.UI;
            default:
                throw new IllegalArgumentException("Invalid tag");
        }
    }
}



package pepse;

import danogl.collisions.Layer;

public class LayerGetter {
    public static int getLayer(String tag) {
        switch (tag) {
            case "avatar":
                return Layer.DEFAULT;
            case "ground":
                return Layer.STATIC_OBJECTS;
            case "tree":
                return Layer.STATIC_OBJECTS;
            case "fruit":
                return Layer.STATIC_OBJECTS + 2;
            case "flower":
                return Layer.STATIC_OBJECTS + 1;
            case "sky", "sunHalo":
                return Layer.BACKGROUND;
            case "night":
                return Layer.FOREGROUND;
            case "sun":
                return Layer.BACKGROUND;
            case "energy":
                return Layer.UI;
            case "underGround":
                return Layer.BACKGROUND;
            default:
                return Layer.DEFAULT;
        }
    }
}



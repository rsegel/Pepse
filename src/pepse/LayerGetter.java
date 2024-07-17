package pepse;

import danogl.collisions.Layer;

public class LayerGetter {
    public static int getLayer(String tag) {
        switch (tag) {
            case "avatar":
                return Layer.DEFAULT;
            case "block":
                return Layer.BACKGROUND;
            case "topLayerBlock":
                return Layer.STATIC_OBJECTS;
            case "tree":
                return Layer.STATIC_OBJECTS;
            case "fruit":
                return Layer.STATIC_OBJECTS;
            case "leaf":
                return Layer.STATIC_OBJECTS;
            case "sky", "sunHalo":
                return Layer.BACKGROUND;
            case "night":
                return Layer.FOREGROUND;
            case "sun":
                return Layer.BACKGROUND;
            case "energy":
                return Layer.UI;
            default:
                return Layer.DEFAULT;
        }
    }
}



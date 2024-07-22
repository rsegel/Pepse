package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.Tags;

import java.awt.*;
/**
 * A class that represents the sky in the game.
 */
public class Sky {
    private static final Color DECODE = Color.decode("#80C6E5");
    private static final Color BASIC_SKY_COLOR = DECODE;
    /**
     * Creates a new sky object with the specified window dimensions.
     * @param windowDimensions The dimensions of the window.
     * @return The sky object.
     */
    public static GameObject create(Vector2 windowDimensions){
        GameObject sky = new GameObject(
                Vector2.ZERO,
                windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(TagsToNames.getTagName(Tags.SKY));
        return sky;
    }
}

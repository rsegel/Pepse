package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.world.TagsToNames;

import java.awt.*;
/**
 * Night class creates a black rectangle that covers the
 * screen and transitions its opacity from 0 to 0.5 and back to 0
 * over a given cycle length.
 */
public class Night {
    private static final Float MIDNIGHT_OPACITY = 0.5f;
    private static final Float MIDDAY_OPACITY = 0.0f;
    private static final int TWO = 2;
    /**
     * Creates a night object that transitions its opacity from 0 to 0.5 and back to 0
     * over a given cycle length.
     * @param windowDimensions the dimensions of the window
     * @param cycleLength the length of the cycle in seconds
     * @return the night object
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag(TagsToNames.getTagName(pepse.Tags.NIGHT));
        new Transition<Float>(night,
                night.renderer()::setOpaqueness,
                MIDDAY_OPACITY,
                MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength / TWO,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
        return night;
    }

}

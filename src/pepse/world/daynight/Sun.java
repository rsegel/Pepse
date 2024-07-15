package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.util.Vector2;
import danogl.gui.rendering.OvalRenderable;
import java.awt.*;
import danogl.components.CoordinateSpace;
import pepse.world.Terrain;


public class Sun {

    private static final float SUN_DIM = 75;
    private static final Float FULL_CIRCLE = 360f;

    /**
     * Create a sun object, and return it.
     * @param windowDimensions The dimensions of the window.
     * @param cycleLength The length of the day-night cycle.
     * @return The sun object.
     */
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength) {
        // we create the sun in the center of X axis and the middle of the sky in Y axis,
        // using the OvalRendrable class to draw the sun.
        // TODO: sure?
        GameObject sun = new GameObject(
                new Vector2(windowDimensions.x() / 2,
                        (windowDimensions.y() - Terrain.getGroundHeightAtX0()) / 2),
                new Vector2(SUN_DIM, SUN_DIM), // TODO: sun's real dims
                new OvalRenderable(Color.YELLOW)
        );
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");
        Vector2 initialSunCenter = sun.getCenter();
        float horizon = Terrain.getGroundHeightAtX0();
        Vector2 cycleCenter = new Vector2(windowDimensions.x() / 2, horizon);
        new Transition<Float>(sun,
                (Float angle) -> sun.setCenter(initialSunCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
                0f, FULL_CIRCLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength, Transition.TransitionType.TRANSITION_LOOP, null);
        return sun;
    }
}
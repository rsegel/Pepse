package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.Transition;
import danogl.util.Vector2;
import danogl.gui.rendering.OvalRenderable;
import java.awt.*;
import danogl.components.CoordinateSpace;
import pepse.LayerGetter;
import pepse.Tags;
import pepse.world.TagsToNames;
import pepse.world.Terrain;

/**
 * The Sun class is responsible for creating the sun object in the game.
 * The sun is a yellow circle that moves in a circular path in the sky.
 * The sun's path is determined by the day-night cycle length.
 */
public class Sun {

    private static final float SUN_DIM = 75;
    private static final Float FULL_CIRCLE = 360f;
    private static final int TWO = 2;
    private static final float ZERO_FLOAT = 0f;

    /**
     * Create a sun object, and return it.
     * @param windowDimensions The dimensions of the window.
     * @param cycleLength The length of the day-night cycle.
     * @return The sun object.
     */
    public static GameObject create(Vector2 windowDimensions,
                                    float cycleLength) {
        GameObject sun = createSun(windowDimensions);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(TagsToNames.getTagName(Tags.SUN));
        Vector2 initialSunCenter = sun.getCenter();
        float horizon = Terrain.getGroundHeightAtX0();
        Vector2 cycleCenter = new Vector2(windowDimensions.x() / TWO, horizon);
        applyTransition(sun, cycleLength, initialSunCenter, cycleCenter);
        return sun;

    }

    private static void applyTransition(GameObject sun, float cycleLength,
                                        Vector2 initialSunCenter, Vector2 cycleCenter) {
        new Transition<Float>(sun,
                (Float angle) -> sun.setCenter(initialSunCenter.
                        subtract(cycleCenter).rotated(angle).add(cycleCenter)),
                ZERO_FLOAT, FULL_CIRCLE,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                cycleLength, Transition.TransitionType.TRANSITION_LOOP, null);

    }

    private static GameObject createSun(Vector2 windowDimensions) {
        return new GameObject(
                new Vector2(windowDimensions.x() / TWO,
                        (windowDimensions.y() - Terrain.getGroundHeightAtX0()) / TWO),
                new Vector2(SUN_DIM, SUN_DIM),
                new OvalRenderable(Color.YELLOW)
        );
    }

    /**
     * Get the sun's diameter.
     * @return The sun's diameter.
     */
    public static float getSunDim() {
        return SUN_DIM;
    }
}
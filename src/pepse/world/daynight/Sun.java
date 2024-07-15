package pepse.world.daynight;

import danogl.GameObject;
import danogl.util.Vector2;
import danogl.gui.rendering.OvalRenderable;
import java.awt.*;
import danogl.components.CoordinateSpace;



public class Sun {

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
                new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2),
                new Vector2(50, 50), // TODO: sun's real dims
                new OvalRenderable(Color.YELLOW)
        );
        // set the coordinates space of the sun to combine with camera space.
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag("sun");

        return sun;
    }
}
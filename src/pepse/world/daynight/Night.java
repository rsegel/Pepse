package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Night {
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        GameObject night = new GameObject(Vector2.ZERO, windowDimensions, new RectangleRenderable(Color.BLACK));
        night.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        night.setTag("night");
        return night;
    }

}

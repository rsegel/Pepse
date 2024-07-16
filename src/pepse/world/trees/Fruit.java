package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.*;

public class Fruit extends GameObject {

    public Fruit(Vector2 location) {
        super(location,
                new Vector2(10, 10),
                new OvalRenderable(new Color(255, 0, 0)));
    }
}

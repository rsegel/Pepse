package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Fruit extends GameObject {

    public Fruit(Vector2 location) {
        super(location,
                new Vector2(10, 10),
                new OvalRenderable(new Color(255, 0, 0)));
        this.setTag("fruit");
    }

    public boolean shouldCollideWith(GameObject other) {
        if (other.getTag().equals("leaf") || other.getTag().equals("fruit"))
            return false;
        return(super.shouldCollideWith(other));
    }
}

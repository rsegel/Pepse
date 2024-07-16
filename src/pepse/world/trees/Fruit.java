package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Fruit extends GameObject {

    private static final float FRUIT_SIZE = 15;
    private static final Color FRUIT_COLOR = new Color(255, 0, 0);

    public Fruit(Vector2 location) {
        super(location,
                new Vector2(FRUIT_SIZE, FRUIT_SIZE),
                new OvalRenderable(FRUIT_COLOR));
        this.setTag("fruit");
    }

    public boolean shouldCollideWith(GameObject other) {
        if (other.getTag().equals("leaf") || other.getTag().equals("fruit"))
            return false;
        return(super.shouldCollideWith(other));
    }
}

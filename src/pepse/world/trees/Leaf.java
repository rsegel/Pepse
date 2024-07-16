package pepse.world.trees;

import danogl.GameObject;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Leaf extends GameObject {
    private static final int LEAF_SIZE = 10;
    private static final Color LEAF_COLOR = new Color(50, 200, 30);

    public Leaf(Vector2 location) {
        super(location,
                new Vector2(LEAF_SIZE, LEAF_SIZE),
                new RectangleRenderable(LEAF_COLOR));
        this.setTag("leaf");
    }

    public boolean shouldCollideWith(GameObject other) {
        if (other.getTag().equals("leaf") || other.getTag().equals("fruit"))
            return false;
        return(super.shouldCollideWith(other));
    }

    public static int getLeafSize(){
        return LEAF_SIZE;
    }
}

package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

public class Leaf extends GameObject {
    private static final int LEAF_SIZE = 20;
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final Float ROTATION_ANGLE = 15f;
    private static final float CYCLE_LENGTH = 1f;

    public Leaf(Vector2 location) {
        super(location,
                new Vector2(LEAF_SIZE, LEAF_SIZE),
                new RectangleRenderable(LEAF_COLOR));
        this.setTag("leaf");
        float waitTime = (float) (Math.random());
        new ScheduledTask(this,
                waitTime,
                false, this::rotateLeaf);
    }

    private void rotateLeaf() {
        new Transition<Float>(this,
                this.renderer()::setRenderableAngle,
                -ROTATION_ANGLE, ROTATION_ANGLE, Transition.LINEAR_INTERPOLATOR_FLOAT,
                CYCLE_LENGTH, Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
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

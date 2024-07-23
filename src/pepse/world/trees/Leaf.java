package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.Tags;
import pepse.util.ColorSupplier;
import pepse.world.TagsToNames;

import java.awt.Color;
/**
 * Class representing a leaf in the game
 */
public class Leaf extends GameObject {
    private static final int LEAF_SIZE = 20;
    private static final Color BASE_LEAF_COLOR = new Color(50, 200, 30);
    private static final Float ROTATION_ANGLE = 15f;
    private static final float CYCLE_LENGTH = 1f;
    private static final float DEG_CHANGE_ON_JUMP = 90;
    private static final float JUMP_TIME = 2;
    /**
     * Constructor for the leaf
     * @param location the location of the leaf
     */
    public Leaf(Vector2 location) {
        super(location,
                new Vector2(LEAF_SIZE, LEAF_SIZE),
                new RectangleRenderable(ColorSupplier.approximateColor(BASE_LEAF_COLOR)));
        this.setTag(TagsToNames.getTagName(Tags.LEAF));
        float waitTime = (float) (Math.random());
        new ScheduledTask(this,
                waitTime,
                false, this::wobbleLeaf);
    }

    private void wobbleLeaf() {
        new Transition<>(this,
                this.renderer()::setRenderableAngle,
                -ROTATION_ANGLE, ROTATION_ANGLE, Transition.LINEAR_INTERPOLATOR_FLOAT,
                CYCLE_LENGTH, Transition.TransitionType.TRANSITION_BACK_AND_FORTH, null);
    }
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other.getTag().equals(TagsToNames.getTagName(Tags.LEAF)) ||
                other.getTag().equals(TagsToNames.getTagName(Tags.FRUIT)))
            return false;
        return(super.shouldCollideWith(other));
    }
    /**
     * Getter for the size of the leaf
     * @return the size of the leaf
     */
    public static int getLeafSize(){
        return LEAF_SIZE;
    }
    /**
     * Method to be called when the avatar jumps using the observer pattern
     */
    public void avatarJumped() {
        float current_angle = this.renderer().getRenderableAngle();
        new Transition<>(this,
                (Float angle) -> this.renderer().setRenderableAngle(angle), current_angle,
                current_angle + DEG_CHANGE_ON_JUMP,
                Transition.LINEAR_INTERPOLATOR_FLOAT,
                JUMP_TIME, Transition.TransitionType.TRANSITION_ONCE, null);
    }
}

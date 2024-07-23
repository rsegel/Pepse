package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.Tags;
import pepse.util.ColorSupplier;
import pepse.world.TagsToNames;
import static pepse.PepseGameManager.CYCLE_DEFAULT;

import java.awt.Color;
/**
 * Class for creating fruit in the world at random locations
 */
public class Fruit extends GameObject{

    private static final float FRUIT_SIZE = 15;
    private static final Color FRUIT_COLOR = new Color(255, 0, 0);
    private static final int CYCLE_LENGTH = CYCLE_DEFAULT;
    private static final int FRUIT_COLOR_DELTA = 150;
    /**
     * Create a fruit at a location
     * @param location the location of the fruit
     */
    public Fruit(Vector2 location) {
        super(location,
                new Vector2(FRUIT_SIZE, FRUIT_SIZE),
                new OvalRenderable(FRUIT_COLOR));
        this.setTag(TagsToNames.getTagName(Tags.FRUIT));
    }
    /**
     * makes sure that the fruit does not collide with other fruit or leaves
     * @param other the object to check for collision with
     * @return true if the object is not a leaf or fruit
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        if (other.getTag().equals(TagsToNames.getTagName(Tags.LEAF)) ||
                other.getTag().equals(TagsToNames.getTagName(Tags.FRUIT)))
            return false;
        return(super.shouldCollideWith(other));
    }
    /**
     * When the avatar collides with the fruit, the fruit disappears and reappears after a delay
     * @param other the object that the fruit collided with
     * @param collision the collision that occurred
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(TagsToNames.getTagName(Tags.AVATAR)) &&
                this.getTag().equals(TagsToNames.getTagName(Tags.FRUIT))) {
            this.renderer().setOpaqueness(0);
            new ScheduledTask(this,
                    CYCLE_LENGTH,
                    false, this::reAppear);
        }
    }

    private void reAppear() {
        this.renderer().setOpaqueness(1);
        this.setTag(TagsToNames.getTagName(Tags.FRUIT));
    }
    /**
     * Method to be called when the avatar jumps using the observer pattern
     */
    public void avatarJumped() {
        this.renderer().setRenderable(new OvalRenderable(randomFruitColor()));
    }

    private Color randomFruitColor() {
        return ColorSupplier.approximateColor(FRUIT_COLOR, FRUIT_COLOR_DELTA);
    }
}

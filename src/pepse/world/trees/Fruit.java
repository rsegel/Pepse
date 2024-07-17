package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.awt.*;

public class Fruit extends GameObject{

    private static final float FRUIT_SIZE = 15;
    private static final Color FRUIT_COLOR = new Color(255, 0, 0);
    private static final int CYCLE_LENGTH = 30; //TODO - unify with gameManager somehow
    private static final int FRUIT_COLOR_DELTA = 150;

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

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals("avatar") && this.getTag().equals("fruit")) {
            this.renderer().setOpaqueness(0);
            new ScheduledTask(this,
                    CYCLE_LENGTH,
                    false, this::reAppear);
        }
    }

    private void reAppear() {
        this.renderer().setOpaqueness(1);
        this.setTag("fruit");
    }

    public void avatarJumped() {
        this.renderer().setRenderable(new OvalRenderable(randomFruitColor()));
    }

    private Color randomFruitColor() {
        return ColorSupplier.approximateColor(FRUIT_COLOR, FRUIT_COLOR_DELTA);
    }
}

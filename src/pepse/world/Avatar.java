package pepse.world;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;
import danogl.gui.rendering.AnimationRenderable;
import pepse.Tags;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * The Avatar class represents the player's character in the game.
 * The avatar can move left and right, jump and collect fruits.
 * The avatar has a certain amount of energy that is consumed when moving or jumping.
 * The avatar can collect fruits to gain energy.
 */
public class Avatar extends GameObject {

    static final String AVATAR_PATH = "assets/idle_0.png";
    private static final float FRUIT_ENERGY = 10;
    private final ImageReader imagereader;
    private static final int ENERGY_LOSS_JUMP = 10;
    private static final double ENERGY_LOSS_RUN = 0.5;
    final UserInputListener inputListener;
    private static final float VELOCITY_X = 400;
    private static final float ENERGY_INIT = 1000;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final Vector2 AVATAR_SIZE = new Vector2(30, 50);
    private boolean isRight = true;
    private float TIME_BETWEEN_FRAMES = 0.2f;
    private AvatarState state;
    private ArrayList<Runnable> updateWhenJumping = new ArrayList<Runnable>();

    private static final Color AVATAR_COLOR = Color.DARK_GRAY;
    private static final String[] AVATAR_RUN_PATHS = new String[]{
            "assets/run_0.png",
            "assets/run_1.png",
            "assets/run_2.png",
            "assets/run_3.png",
            "assets/run_4.png",
            "assets/run_5.png"
    };
    private static final String[] AVATAR_JUMP_PATHS = new String[]{
            "assets/jump_0.png",
            "assets/jump_1.png",
            "assets/jump_2.png",
            "assets/jump_3.png"
    };
    private static final String[] AVATAR_IDLE_PATHS = new String[]{
            "assets/idle_0.png",
            "assets/idle_1.png",
            "assets/idle_2.png",
            "assets/idle_3.png"
    };

    private float energy;


    /**
     * Constructor for the Avatar class.
     * @param topLeftCorner The top left corner of the avatar.
     * @param inputListener The input listener that listens for key presses.
     * @param imageReader The image reader that reads the images for the avatar.
     */
    public Avatar(Vector2 topLeftCorner,
                  UserInputListener inputListener,
                  ImageReader imageReader) {
        super(topLeftCorner,
                AVATAR_SIZE,
                imageReader.readImage(AVATAR_PATH, true));
        this.imagereader = imageReader;
        this.inputListener = inputListener;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        energy = ENERGY_INIT;
        this.setTag(TagsToNames.getTagName(Tags.AVATAR));
        this.state = AvatarState.IDLE;
        renderer().setRenderable(new AnimationRenderable(AVATAR_IDLE_PATHS, imagereader,
                true, TIME_BETWEEN_FRAMES));
    }


    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float xVel = 0;
        handleVelocity(xVel);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) &&
                getVelocity().y() == 0 &&
                energy > ENERGY_LOSS_JUMP) {
            handleJump();
        }
        if (state == AvatarState.IDLE && energy < ENERGY_INIT) {
            energy += 1;
        }
    }

    private void handleVelocity(float xVel) {
        // check if the avatar is running and has enough energy
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energy > ENERGY_LOSS_RUN) {
            xVel = handleLeftMovement(xVel);
        } else if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energy > ENERGY_LOSS_RUN) {
            xVel = handleRightMovement(xVel);
        } else if (getVelocity().y() != 0) {
            // set the renderable to AnimationRenderable if the state is not JUMP
            if (state != AvatarState.JUMP) {
                renderer().setRenderable(new AnimationRenderable(AVATAR_JUMP_PATHS,
                        imagereader, false, TIME_BETWEEN_FRAMES));
                state = AvatarState.JUMP;}
        } else {
            if (state != AvatarState.IDLE) {
                renderer().setRenderable(new AnimationRenderable(AVATAR_IDLE_PATHS, imagereader,
                        true, TIME_BETWEEN_FRAMES));
                state = AvatarState.IDLE;
            }
        }
        transform().setVelocityX(xVel);
    }
    /**
     * Returns the energy of the avatar.
     * @return The energy of the avatar.
     */
    public float getEnergy() {
        return energy;
    }

    /**
     * Adds an observer to the avatar.
     * @param observer The observer to be added to the avatar.
     */
    public void addJumpObserver(Runnable observer) {
        updateWhenJumping.add(observer);
    }

    private void handleJump() {
        transform().setVelocityY(VELOCITY_Y);
        energy -= ENERGY_LOSS_JUMP;
        for (Runnable observer : updateWhenJumping) {
            observer.run();
        }
    }

    private float handleLeftMovement(float xVel) {
        xVel -= VELOCITY_X;
        energy -= (float) ENERGY_LOSS_RUN;
        // set the renderable to AnimationRenderable if the sta
        if (state == AvatarState.IDLE) {
            renderer().setRenderable(new AnimationRenderable(AVATAR_RUN_PATHS,
                    imagereader, true, TIME_BETWEEN_FRAMES));
            state = AvatarState.RUN;
        }
        if (isRight) {
            renderer().setIsFlippedHorizontally(true);
            isRight = false;
        }
        return xVel;
    }

    private float handleRightMovement(float xVel) {
        xVel += VELOCITY_X;
        energy -= (float) ENERGY_LOSS_RUN;
        if (state == AvatarState.IDLE) {
            renderer().setRenderable(new AnimationRenderable(AVATAR_RUN_PATHS, imagereader,
                    true, TIME_BETWEEN_FRAMES));
            state = AvatarState.RUN;
        }
        if (!isRight) {
            renderer().setIsFlippedHorizontally(false);
            isRight = true;
        }
        return xVel;
    }

    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if (other.getTag().equals(TagsToNames.getTagName(Tags.FRUIT))) {
            energy += FRUIT_ENERGY;
            if (energy > ENERGY_INIT) {
                energy = ENERGY_INIT;
            }
            other.setTag(TagsToNames.getTagName(Tags.COLLECTED_FRUIT));
        }
        if (other.getTag().equals(TagsToNames.getTagName(Tags.TOP_LAYER_BLOCK))) {
            transform().setVelocityY(0);
        }
        super.onCollisionEnter(other, collision);
    }
}
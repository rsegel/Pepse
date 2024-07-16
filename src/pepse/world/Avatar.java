package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.util.Vector2;

import java.awt.*;
import java.awt.event.KeyEvent;


public class Avatar extends GameObject implements EnergyCallback {

    static final String AVATAR_PATH = "assets/idle_0.png";
    private static final int ENERGY_LOSS_JUMP = 10;
    private static final double ENERGY_LOSS_RUN = 0.5;
    final UserInputListener inputListener;
    private static final float VELOCITY_X = 400;
    private static final float ENERGY_INIT = 100;
    private static final float VELOCITY_Y = -650;
    private static final float GRAVITY = 600;
    private static final Vector2 AVATAR_SIZE = new Vector2(30, 50);

    private static final Color AVATAR_COLOR = Color.DARK_GRAY;
    private float energy;

    public Avatar(Vector2 topLeftCorner,
                  UserInputListener inputListener,
                  ImageReader imageReader) {
        super(topLeftCorner,
                AVATAR_SIZE,
                imageReader.readImage(AVATAR_PATH, true));
        this.inputListener = inputListener;
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        energy = ENERGY_INIT;
    }

    @Override
    public void update(float deltaTime) {
        boolean toAddEnergy = true;
        super.update(deltaTime);
        float xVel = 0;
        // check if the avatar is running and has enough energy
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT) && energy > ENERGY_LOSS_RUN){
            xVel -= VELOCITY_X;
            energy -= (float) ENERGY_LOSS_RUN;
            toAddEnergy = false;
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT) && energy > ENERGY_LOSS_RUN){
            xVel += VELOCITY_X;
            energy -= (float) ENERGY_LOSS_RUN;
            toAddEnergy = false;
        }
        transform().setVelocityX(xVel);
        // TODO: how to handle jumping?
        if(
                inputListener.isKeyPressed(KeyEvent.VK_SPACE) &&
                        getVelocity().y() == 0 &&
                        energy > ENERGY_LOSS_JUMP) {
            transform().setVelocityY(VELOCITY_Y);
            energy -= ENERGY_LOSS_JUMP;
            toAddEnergy = false;
        }
        if(toAddEnergy && energy < ENERGY_INIT){
            energy += 1;
        }
        // TODO: prevent the avatar from going off the screen
    }

    @Override
    public int getEnergy() {
        return (int) energy;
    }
}

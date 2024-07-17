package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import java.awt.Color;
import java.util.function.Supplier;

/**
 * A class that represents the energy renderer,
 * using a colored bar to represent the energy of the avatar.
 * The class uses functional programming to get the energy of the avatar
 * through a callback function, instead of having a private avatar field.
 */
public class EnergyRenderer extends GameObject  {
    private final Supplier<Float> energyCallback;
    private static final Vector2 ENERGY_BAR_POSITION = new Vector2(10, 10);
    private static final Vector2 ENERGY_BAR_MAX_SIZE = new Vector2(100, 20);
    private static final Color ENERGY_BAR_COLOR = new Color(0, 0, 255);  // Blue color

    private static final Vector2 DEFAULT_POS_FOR_BAR = new Vector2(10, 10);

    /**
     * Constructor for the energy renderer.
     *
     *
     * @param energyCallback The callback function to get the energy of the avatar.
     */
    public EnergyRenderer(Supplier<Float> energyCallback) {
        super(DEFAULT_POS_FOR_BAR, ENERGY_BAR_MAX_SIZE, new RectangleRenderable(ENERGY_BAR_COLOR));
        this.energyCallback = energyCallback;
        setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
    }

    /**
     * The update method of the energy renderer.
     * It updates the energy bar based on the energy of the avatar.
     * @param deltaTime The time passed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        float energy = energyCallback.get();
        // set the dimensions of the energy bar based on the energy of the avatar,
        // but keep the left top corner of the energy bar the same
        transform().setDimensions(new Vector2(energy, ENERGY_BAR_MAX_SIZE.y()));
        transform().setTopLeftCorner(ENERGY_BAR_POSITION);
    }


}

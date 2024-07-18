package pepse.world.daynight;

import danogl.GameObject;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;
import danogl.components.CoordinateSpace;
import pepse.Tags;
import pepse.world.TagsToNames;


import java.awt.*;


import static pepse.world.daynight.Sun.getSunDim;
/**
 * Creates a halo around the sun
 */
public class SunHalo {

    private static final float HALO_FACTOR = 2.0F;
    private static final Color sunHaloColor = new Color(255, 255, 0, 20);
    private static final int TWO = 2;
    /**
     * Creates a halo around the sun
     * @param sun the sun to put the halo around
     * @return the halo
     */
    public static GameObject create(GameObject sun) {
        GameObject halo = new GameObject(
                // put the halo around the sun
                getHaloPosition(sun),
                // TODO: using the sun's diameter as the halo's diameter from the sun's class, is this correct?
                new Vector2(getSunDim() * HALO_FACTOR, getSunDim() * HALO_FACTOR),
                new OvalRenderable(sunHaloColor)
        );
        halo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        halo.setTag(TagsToNames.getTagName(Tags.SUN_HALO));
        halo.addComponent((deltaTime) -> {
            halo.setTopLeftCorner(getHaloPosition(sun));
        });
        return halo;
    }

    private static Vector2 getHaloPosition(GameObject sun) {
        return sun.getCenter().subtract(new Vector2(getSunDim() * HALO_FACTOR / TWO,
                getSunDim() * HALO_FACTOR / TWO));
    }

}

package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;

import java.util.List;
import java.awt.Color;




public class Terrain {
    private static final float DEFAULT_GROUND_FACTOR = (float) (2.0/3.0);
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    static private float groundHeightAtX0;
    public Terrain(Vector2 windowDimensions, int seed){
        groundHeightAtX0 = windowDimensions.y() * DEFAULT_GROUND_FACTOR;
    }

    public static float getGroundHeightAtX0(){
        return groundHeightAtX0;
    }

    // TODO: implement with perlin noise
    public float groundHeightAt(float x) { return groundHeightAtX0; }

    public List<Block> createInRange(int minX, int maxX) {
        // create list of a single block
        Block singleBlock = new Block(
                // put the block in the top left corner of the screen
                new Vector2(0, 0),
                new RectangleRenderable(ColorSupplier.approximateColor(
                        BASE_GROUND_COLOR))
        );
        singleBlock.setTag("ground");
        return List.of(singleBlock);

    }

}
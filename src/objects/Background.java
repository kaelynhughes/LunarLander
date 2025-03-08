package objects;

import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Rectangle;
import edu.usu.graphics.Texture;
import edu.usu.graphics.Color;
import enums.RenderLayerEnum;

public class Background implements Rendered{
    private Graphics2D graphics;
    private float windowSize;
    private RenderLayerEnum layer;
    private Texture texture;

    public Background(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        initialize(graphics, windowSize, layer);
    }

    @Override
    public void initialize(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        this.graphics = graphics;
        this.windowSize = windowSize;
        this.layer = layer;

        try {
            texture = new Texture("resources/images/background.jpeg");
        } catch (RuntimeException e) {
            System.out.println("Error occurred while loading background. Stack trace below:");
            System.out.println(e.getStackTrace());
        }
    }

    @Override
    public void renderObject() {
        Rectangle rectangle = new Rectangle(0f - (windowSize / 2), 0f - (windowSize / 2), windowSize, windowSize, layer.getValue());
        graphics.draw(texture, rectangle, Color.WHITE);

    }
}

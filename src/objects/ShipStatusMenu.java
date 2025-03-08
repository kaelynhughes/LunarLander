package objects;

import edu.usu.graphics.Color;
import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Rectangle;
import enums.RenderLayerEnum;

public class ShipStatusMenu implements Rendered{
    private float windowSize;
    private Graphics2D graphics;
    private RenderLayerEnum layer;

    public ShipStatusMenu(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        initialize(graphics, windowSize, layer);
    }

    @Override
    public void initialize(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        this.windowSize = windowSize;
        this.graphics = graphics;
        this.layer = layer;
    }

    @Override
    public void renderObject() {
        float menuHeight = windowSize / 8;
        float menuWidth = windowSize / 4;
        float menuTop = 0f - windowSize / 2;
        float menuLeft = (0f + windowSize / 2) - menuWidth;

        Rectangle menu = new Rectangle(menuLeft, menuTop, menuWidth, menuHeight, layer.getValue());
        graphics.draw(menu, Color.BLACK);

    }
}

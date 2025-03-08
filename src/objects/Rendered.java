package objects;

import edu.usu.graphics.Graphics2D;
import enums.RenderLayerEnum;

public interface Rendered {
    public void initialize(Graphics2D graphics, float windowSize, RenderLayerEnum layer);
    public void renderObject();
}

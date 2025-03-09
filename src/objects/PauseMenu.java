package objects;

import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Rectangle;
import enums.ColorsEnum;
import enums.RenderLayerEnum;

public class PauseMenu implements Rendered {
    private enum PauseStatus {
        OFF,
        PAUSED,
        CRASHED,
        LANDED
    }

    private Graphics2D graphics;
    private RenderLayerEnum layer;
    private PauseStatus status;

    private float left;
    private float top;
    private float modalHeight;
    private float modalWidth;

    public PauseMenu(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        initialize(graphics, windowSize, layer);
    }

    @Override
    public void initialize(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        this.graphics = graphics;
        this.layer = layer;

        this.modalHeight = windowSize * 0.3f;
        this.modalWidth = windowSize * 0.6f;

        this.left = 0f - modalWidth / 2;
        this.top = 0f - modalHeight / 2;
    }

    @Override
    public void renderObject() {
        Rectangle background = new Rectangle(left, top, modalWidth, modalHeight, layer.getValue());
        graphics.draw(background, ColorsEnum.BLACK.getColor());
        switch (status) {
            case OFF -> renderOff();
            case PAUSED -> renderPaused();
            case CRASHED -> renderCrashed();
            case LANDED -> renderLanded();
        }
    }

    public void renderOff() {}

    public void renderPaused() {
    }

    public void renderCrashed() {
    }

    public void renderLanded() {
    }

    public void pause() {
        status = PauseStatus.PAUSED;
    }
    public void resume() {
        status = PauseStatus.OFF;
    }
    public void crash() {
        status = PauseStatus.CRASHED;
    }
    public void land() {
        status = PauseStatus.LANDED;
    }
}

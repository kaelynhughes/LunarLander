package objects;

import edu.usu.graphics.Color;
import edu.usu.graphics.Font;
import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Rectangle;
import enums.ColorsEnum;
import enums.RenderLayerEnum;
import org.joml.Vector3f;

import java.text.DecimalFormat;

public class ShipStatusMenu implements Rendered{
    private float windowSize;
    private Graphics2D graphics;
    private RenderLayerEnum layer;

    private Ship ship;

    private Font font;

    public ShipStatusMenu(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        initialize(graphics, windowSize, layer);
        font = new Font("resources/fonts/emilys-candy.regular.ttf", 36, false);
    }

    @Override
    public void initialize(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        this.windowSize = windowSize;
        this.graphics = graphics;
        this.layer = layer;
    }

    public void registerShip(Ship ship) {
        this.ship = ship;
    }

    @Override
    public void renderObject() {
        float menuHeight = windowSize / 8;
        float menuWidth = windowSize / 4;
        float menuTop = 0f - windowSize / 2;
        float menuLeft = (0f + windowSize / 2) - menuWidth;
        float textLeft = menuLeft + (0.05f * menuWidth);

        Rectangle menu = new Rectangle(menuLeft, menuTop, menuWidth, menuHeight, layer.getValue());
        graphics.draw(menu, ColorsEnum.BLACK.getColor());

        if (ship == null) {
            graphics.drawTextByWidth(font, "Searching for ship", textLeft, menuTop + (0.05f * menuWidth), menuWidth * 0.9f, layer.higher(layer).getValue(), ColorsEnum.WHITE.getColor());
        } else {
            float lineHeight = ((menuHeight - 0.01f) / 3) + (0.005f * menuWidth);
            DecimalFormat formatter = new DecimalFormat("####0.00");

            float degrees = (float) Math.toDegrees(ship.getRotation());

             String string1 = "Fuel: " + formatter.format(ship.getFuel());
//            Vector3f center = ship.getCenter();
//            String string1 = "(" + formatter.format(center.x) + ", " + formatter.format(center.y) + ")";
            String string2 = "Speed: " + formatter.format(ship.getSpeed());
            String string3 = "Angle: " + formatter.format(degrees);

            ColorsEnum color1 = ship.isFuelLeft() ? ColorsEnum.GREEN : ColorsEnum.WHITE;
            ColorsEnum color2 = ship.isSafeSpeed() ? ColorsEnum.GREEN : ColorsEnum.WHITE;
            ColorsEnum color3 = ship.isSafeAngle() ? ColorsEnum.GREEN : ColorsEnum.WHITE;

            float top1 = menuTop + 0.005f;
            float top2 = top1 + (0.005f * menuWidth) + lineHeight;
            float top3 = top2 + (0.005f * menuWidth) + lineHeight;

            graphics.drawTextByHeight(font, string1, textLeft, top1, lineHeight, layer.getValue(), color1.getColor());
            graphics.drawTextByHeight(font, string2, textLeft, top2, lineHeight, layer.getValue(), color2.getColor());
            graphics.drawTextByHeight(font, string3, textLeft, top3, lineHeight, layer.getValue(), color3.getColor());
        }

    }
}

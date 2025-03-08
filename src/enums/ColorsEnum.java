package enums;

import edu.usu.graphics.Color;

public enum ColorsEnum {
    GRAY(new Color(0.549f, 0.573f, 0.675f)),
    WHITE(new Color(1f, 1f, 1f));

    private final Color color;
    ColorsEnum(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}

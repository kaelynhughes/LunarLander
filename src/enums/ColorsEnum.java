package enums;

import edu.usu.graphics.Color;

public enum ColorsEnum {
//    GRAY(new Color(0.549f, 0.573f, 0.675f)),
    GRAY(new Color(0.631f, 0.333f, 0.725f)),

    WHITE(new Color(0.976f, 0.82f, 0.82f)),
    BLACK(new Color(0.043f, 0.075f, 0.329f)),
    PINK(new Color(0.969f, 0.396f, 0.639f)),
    GREEN(new Color(0f, 0.784f, 0.612f));

    private final Color color;
    ColorsEnum(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}

package enums;

public enum RenderLayerEnum {
    BOTTOM(0),
    SUBPAR(0.25f),
    MIDDLE(0.5f),
    ALRIGHT(0.75f),
    TOP(1);

    private final float value;
    RenderLayerEnum(float value) {
        this.value = value;
    }
    public float getValue() {
        return value;
    }
    public float getDifference() {
        return 0.25f;
    }
}

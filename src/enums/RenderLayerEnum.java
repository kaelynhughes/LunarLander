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
    public RenderLayerEnum higher(RenderLayerEnum e)
    {
        int index = e.ordinal();
        int nextIndex = index + 1;
        RenderLayerEnum[] layers = RenderLayerEnum.values();
        if (nextIndex >= layers.length) {
            nextIndex = layers.length - 1;
        }
        return layers[nextIndex];
    }
    public RenderLayerEnum lower(RenderLayerEnum e)
    {
        int index = e.ordinal();
        int nextIndex = index - 1;
        RenderLayerEnum[] layers = RenderLayerEnum.values();
        if (nextIndex < 0) {
            nextIndex = 0;
        }
        return layers[nextIndex];
    }
}

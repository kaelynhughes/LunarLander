package enums;

public enum DifficultyEnum {
    LEVEL1(2, 0.1f),
    LEVEL2(1, 0.05f);

    private final int safeZones;
    private final float safeWidth;

    DifficultyEnum(int safeZones, float safeWidth) {
        this.safeZones = safeZones;
        this.safeWidth = safeWidth;
    }

    public int getSafeZones() {
        return safeZones;
    }

    public float getSafeWidth() {
        return safeWidth;
    }
}

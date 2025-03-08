package objects;

import edu.usu.graphics.Graphics2D;
import edu.usu.graphics.Rectangle;
import edu.usu.graphics.Triangle;
import edu.usu.graphics.Color;

import enums.DifficultyEnum;
import enums.ColorsEnum;
import enums.RenderLayerEnum;

import org.joml.Vector2f;
import org.joml.Vector3f;

import tools.MyRandom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Terrain implements Rendered {
    private final List<TerrainPoint> points;
    private Graphics2D graphics;
    private RenderLayerEnum layer;

    private final MyRandom randomizer = new MyRandom();

    private float windowSize;
    private final DifficultyEnum difficulty;

    private float bottom;

    public Terrain(Graphics2D graphics, float windowSize, RenderLayerEnum layer, DifficultyEnum difficulty) {
        points = new ArrayList<>();
        this.difficulty = difficulty;
        initialize(graphics, windowSize, layer);
    }

    public void initialize(Graphics2D graphics, float windowSize, RenderLayerEnum layer) {
        this.graphics = graphics;
        this.windowSize = windowSize;
        this.layer = layer;

        this.bottom = 0f + (windowSize / 2);

        List<SafeZone> safeZones = createSafeZones(this.difficulty);
        Collections.sort(safeZones);

        TerrainPoint left = new TerrainPoint(0f - (windowSize / 2), 0.25f, layer);
        points.add(left);
        for (SafeZone safeZone: safeZones) {
            TerrainPoint right = new TerrainPoint(safeZone.getLeft());
            points.add(right);

            randomMidpointDisplacement(left, right);

            left = new TerrainPoint(safeZone.getRight());
            points.add(left);
        }
        TerrainPoint right = new TerrainPoint(0f + (windowSize / 2), 0.25f, layer);
        points.add(right);
        randomMidpointDisplacement(left, right);


        Collections.sort(points);
    }

    @Override
    public void renderObject() {
        float lineHeight = 0.002f;
        for (int i = 1; i < points.size(); i++) {
            TerrainPoint prev = points.get(i - 1);
            TerrainPoint curr = points.get(i);

            Vector3f bottom1 = new Vector3f(prev.getX(), bottom, layer.getValue());
            Vector3f bottom2 = new Vector3f(curr.getX(), bottom, layer.getValue());

            float distance = (float) Math.hypot(curr.getX() - prev.getX(), curr.getY() - prev.getY());
            Vector2f center = new Vector2f(
                    (prev.getX() + curr.getX()) / 2,
                    (prev.getY() + curr.getY()) / 2
            );
            float angle = (float) Math.atan2(curr.getY() - prev.getY(), curr.getX() - prev.getX());

            Rectangle topLine = new Rectangle(center.x - distance / 2, center.y - lineHeight / 2, distance, lineHeight, RenderLayerEnum.TOP.getValue());

            Triangle t1 = new Triangle(prev.getPoint(), curr.getPoint(), bottom1);
            Triangle t2 = new Triangle(curr.getPoint(), bottom2, bottom1);

            graphics.draw(t1, ColorsEnum.GRAY.getColor());
            graphics.draw(t2, ColorsEnum.GRAY.getColor());
            graphics.draw(topLine, angle, center, ColorsEnum.WHITE.getColor());

        }
    }

    private void randomMidpointDisplacement(TerrainPoint start, TerrainPoint end) {
        randomMidpointDisplacement(start, end, 0);
    }

    private void randomMidpointDisplacement(TerrainPoint start, TerrainPoint end, int divisions) {
        int max = 6;
        if (divisions > max) {
            return;
        }

        float top = 0f - (windowSize / 2);

        // larger value of S = more abrupt features, less smooth
        float s = calculateDisplacement(divisions, max);
        float r = s * (float) randomizer.nextGaussian(0, 1) * Math.abs(end.getX() - start.getX());

        float y = ((start.getY() + end.getY()) / 2) + r;
        float x = ((end.getX() - start.getX()) / 2) + start.getX();

        if (y > bottom) {
            y = bottom;
        }

        if (y < top) {
            y = top;
        }

        TerrainPoint midpoint = new TerrainPoint(x, y, this.layer);
        points.add(midpoint);

        randomMidpointDisplacement(start, midpoint, divisions + 1);
        randomMidpointDisplacement(midpoint, end, divisions + 1);
    }

    private float calculateDisplacement(int complete, float totalDivisions) {
        // more completed divisions means a smaller line length & we want a smaller line length to have a smaller displacement
        float x = (totalDivisions - complete) / totalDivisions;
        float adjusted = 4 * x * (1 - x);
        return (float) (0.1 + 0.8 * adjusted);
//        return (totalDivisions - complete) / totalDivisions;
    }

    private List<SafeZone> createSafeZones(DifficultyEnum difficulty) {
        List<SafeZone> safeZones = new ArrayList<>();
        float leftBound = (0f - (windowSize / 2)) * 0.85f;
        float rightBound = (0f + (windowSize / 2)) * 0.85f;
        float lowerBound = (0f + windowSize / 2) * 0.85f;
        // more than 85% away from the border so that the safe zones aren't so high they're either impossible or too easy to reach
        float upperBound = (0f - windowSize / 2) * 0.15f;

        for (int i = 0; i < difficulty.getSafeZones(); i++) {
            while (safeZones.size() == i) {
                SafeZone safeZone = new SafeZone(
                        difficulty.getSafeWidth(),
                        randomizer.nextRange(leftBound + (difficulty.getSafeWidth() / 2), rightBound - (difficulty.getSafeWidth() / 2)),
                        randomizer.nextRange(upperBound, lowerBound),
                        layer
                );
                boolean overlaps = false;
                for (SafeZone existingZone: safeZones) {
                    if (safeZone.overlaps(existingZone)) {
                        overlaps = true;
                    }
                }
                if (!overlaps) {
                    safeZones.add(safeZone);
                }
            }
        }
        return safeZones;
    }
}

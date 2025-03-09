package tools;

import objects.Ship;
import objects.Terrain;
import org.joml.Vector2f;

import java.util.List;

public class CollisionDetector {
    private Terrain terrain;
    private Ship ship;
    public CollisionDetector(Terrain terrain, Ship ship) {
        this.terrain = terrain;
        this.ship = ship;
    }

    public boolean getCollision() {
        Vector2f circleCenter = new Vector2f(ship.getCenter().x, ship.getCenter().y);
        float circleRadius = ship.getShipWidth() / 2;

        List<Vector2f> points = terrain.getTerrainPoints();
        if (points.size() < 2) {
            return false;
        }
        for (int i = 1; i < points.size(); i++) {
            Vector2f prev = points.get(i - 1);
            Vector2f curr = points.get(i);
            if (lineCircleIntersection(prev, curr, circleCenter, circleRadius)) {
//                System.out.println("Ship at (" + circleCenter.x + ", " + circleCenter.y + ") crashed into line between (" + prev.x + ", " + prev.y + ") and (" + curr.x + ", " + curr.y + ").");
                return true;
            }
        }
        return false;
    }

    public boolean getSafeCollision() {
        Vector2f circleCenter = new Vector2f(ship.getCenter().x, ship.getCenter().y);
        float circleRadius = ship.getShipWidth() / 2;

        Vector2f[] points = terrain.getSafeSegment(circleCenter);
        if (points.length < 2) {
            return false;
        }
        boolean inLeftBound = ship.getCenter().x - (circleRadius / 2) > points[0].x && ship.getCenter().x + (circleRadius / 2) > points[0].x;
        boolean inRightBound = ship.getCenter().x - (circleRadius / 2) < points[1].x && ship.getCenter().x + (circleRadius / 2) < points[1].x;
        return inLeftBound && inRightBound;
    }

    private boolean lineCircleIntersection(Vector2f pt1, Vector2f pt2, Vector2f circleCenter, float circleRadius) {
        // Translate points to circle's coordinate system
        Vector2f d = new Vector2f(pt2).sub(pt1);
        Vector2f f = new Vector2f(pt1).sub(new Vector2f(circleCenter.x + (circleRadius / 2), circleCenter.y + (circleRadius / 2)));

        float a = d.dot(d);
        float b = 2 * f.dot(d);
        float c = f.dot(f) - circleRadius * circleRadius;

        float discriminant = b * b - 4 * a * c;

        // If the discriminant is negative, no real roots and thus no intersection
        if (discriminant < 0) {
            return false;
        }

        // Check if the intersection points are within the segment
        discriminant = (float) Math.sqrt(discriminant);
        float t1 = (-b - discriminant) / (2 * a);
        float t2 = (-b + discriminant) / (2 * a);

        if (t1 >= 0 && t1 <= 1) {
            return true;
        }
        if (t2 >= 0 && t2 <= 1) {
            return true;
        }
        return false;
    }

}

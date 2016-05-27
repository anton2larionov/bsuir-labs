package basic.shapes;

import basic.shapes.interfaces.Polylineable;
import basic.shapes.interfaces.ShapeBuilder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Многоугольник (замкнутая ломаная без самопересечений)
 */
public class Polygon extends FillableShape implements Polylineable {

    private final Polyline polyline;

    protected Polygon(final Collection<Point> pts) {
        polyline = new Polyline(pts, true);
    }

    @Override
    public String getName() {
        return "Polygon";
    }

    @Override
    public Polyline getPolyline() {
        return polyline;
    }

    /**
     * Строитель полигона на основе описаной окружности
     */
    public static class PolygonBuilder implements ShapeBuilder<Polygon> {

        private int n = 3;
        private Point center;
        private double r = 1;

        /**
         * @param n число вершин
         * @throws IllegalArgumentException если n < 3
         */
        public PolygonBuilder(final int n) throws IllegalArgumentException {
            if (n < 3) {
                throw new IllegalArgumentException("n must be >= 3");
            }
            this.n = n;
        }

        /**
         * @param center центр описаной окружности
         */
        public PolygonBuilder setCenter(final Point center) {
            this.center = center;
            return this;
        }

        /**
         * @param r радиус описаной окружности
         */
        public PolygonBuilder setR(final double r) {
            if (r <= 0) {
                throw new IllegalArgumentException();
            }
            this.r = r;
            return this;
        }

        @Override
        public Polygon buildShape() {
            double x = 0.0, y = 0.0;
            if (center != null) {
                x = center.getX();
                y = center.getY();
            }
            final double delta = 2 * Math.PI / n;

            final Collection<Point> pts = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                pts.add(new Point(
                        x + r * Math.cos(delta * i),
                        y + r * Math.sin(delta * i)));
            }

            return new Polygon(pts);
        }

    }

    @Override
    public double area() {
        final ArrayList<Point> pts = new ArrayList<>();
        pts.addAll(polyline.vertices());
        final int size = pts.size();

        double sq = 0.0;
        int j = size - 1; // номер предыдущей вершины
        for (int i = 0; i < size; i++) {
            sq += (pts.get(j).getX() + pts.get(i).getX()) *
                    (pts.get(j).getY() - pts.get(i).getY());
            j = i;
        }
        return Math.abs(sq / 2);
    }

}

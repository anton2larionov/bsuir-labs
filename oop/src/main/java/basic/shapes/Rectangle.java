package basic.shapes;

import basic.shapes.interfaces.ShapeBuilder;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Прямоугольник
 */
public final class Rectangle extends Polygon {

    private Rectangle(final Collection<Point> pts) {
        super(pts);
    }

    @Override
    public String getName() {
        return "Rectangle";
    }

    /**
     * Строитель прямоугольника по двум противоположным вершинам
     */
    public static class RectangleBuilder implements ShapeBuilder<Rectangle> {

        private Point leftBottom, rightTop;

        /**
         * @param leftBottom левая нижняя вершина
         */
        public RectangleBuilder setLeftBottomPoint(final Point leftBottom) {
            this.leftBottom = leftBottom;
            return this;
        }

        /**
         * @param rightTop правая верхняя вершина
         */
        public RectangleBuilder setRightTopPoint(final Point rightTop) {
            this.rightTop = rightTop;
            return this;
        }

        @Override
        public Rectangle buildShape() {

            double x = rightTop.getX() - leftBottom.getX();
            double y = rightTop.getY() - leftBottom.getY();

            if (x == 0 || y == 0) {
                throw new IllegalArgumentException();
            }

            final Collection<Point> pts = new ArrayList<>(4);

            pts.add(leftBottom);
            pts.add(new Point(leftBottom.getX(), leftBottom.getY() + y));
            pts.add(rightTop);
            pts.add(new Point(leftBottom.getX() + x, leftBottom.getY()));

            return new Rectangle(pts);
        }

    }

}

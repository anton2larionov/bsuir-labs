package basic.shapes;

import basic.shapes.interfaces.Shape;

/**
 * Точка (immutable)
 */
public class Point implements Shape {

    private final double x, y;

    public Point(final double x, final double y) {
        super();
        this.x = x;
        this.y = y;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a " + getName() +
                " with coordinates: (" + getX() + ", " + getY() + ")");
    }

    @Override
    public String getName() {
        return "Point";
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Point other = (Point) obj;
        return Double.doubleToLongBits(x) == Double.doubleToLongBits(other.x) &&
                Double.doubleToLongBits(y) == Double.doubleToLongBits(other.y);
    }
}

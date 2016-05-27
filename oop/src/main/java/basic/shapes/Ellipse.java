package basic.shapes;

/**
 * Эллипс
 */
public class Ellipse extends FillableShape {

    private final double a, b;
    private final Point center;

    /**
     * Конструктор эллипса
     *
     * @param center центр
     * @param a      большая полуось
     * @param b      малая полуось
     * @throws IllegalArgumentException если a <= 0 или b <= 0
     */
    public Ellipse(final Point center, final double a, final double b)
            throws IllegalArgumentException {
        super();

        if (a <= 0 || b <= 0) {
            throw new IllegalArgumentException("a and b must be > 0");
        }

        this.center = center;
        this.a = Math.max(a, b);
        this.b = Math.min(a, b);
    }

    @Override
    public String getName() {
        return "Ellipse";
    }

    @Override
    public double area() {
        return a * b * Math.PI;
    }

    public Point getCenter() {
        return center;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

}

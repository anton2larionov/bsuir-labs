package basic.shapes;

/**
 * Отрезок (immutable)
 */
public class Segment extends AbstractShape {

    private final Point first, second;

    /**
     * Конструктор отрезка
     *
     * @param first  начальная точка
     * @param second конечная точка
     * @throws IllegalArgumentException если начальная и конечная точки совпадают
     */
    public Segment(final Point first, final Point second)
            throws IllegalArgumentException {
        super();
        if (first.equals(second)) {
            throw new IllegalArgumentException(
                    "the first point equals to the second point");
        }
        this.first = first;
        this.second = second;
    }

    public Point getFirstPoint() {
        return first;
    }

    public Point getSecondPoint() {
        return second;
    }

    public double getLenght() {
        return Math.hypot(first.getX() - second.getX(),
                first.getY() - second.getY());
    }

    public Segment getInvertedSegment() {
        return new Segment(getSecondPoint(), getFirstPoint());
    }

    @Override
    public String getName() {
        return "Segment";
    }

}

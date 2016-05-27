package basic.shapes;

/**
 * Линия (immutable)
 */
public class Line extends AbstractShape {

    private final Point first, second;

    /**
     * Конструктор линии
     *
     * @param baseSegment базовый отрезок
     */
    public Line(final Segment baseSegment) {
        this(baseSegment.getFirstPoint(),
                baseSegment.getSecondPoint());
    }

    /**
     * Конструктор линии
     *
     * @param first  первая базовая точка
     * @param second вторая базовая точка
     * @throws IllegalArgumentException если базовые точки совпадают
     */
    public Line(final Point first, final Point second)
            throws IllegalArgumentException {
        if (first.equals(second)) {
            throw new IllegalArgumentException(
                    "the first point equals to the second point");
        }
        this.first = first;
        this.second = second;
    }

    @Override
    public String getName() {
        return "Line";
    }

    /**
     * @return одномерный массив из базовых точек
     */
    public Point[] getBasePoints() {
        return new Point[]{first, second};
    }

}

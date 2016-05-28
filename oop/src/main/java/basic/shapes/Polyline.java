package basic.shapes;

import basic.shapes.interfaces.Polylineable;

/**
 * Полилиния (ломаная)
 */
public class Polyline extends AbstractShape implements Polylineable {

    private final boolean closed;
    private final ArrayList<Segment> segments;
    private final LinkedList<Point> vertices;

    /**
     * Конструктор полилинии
     *
     * @param pts    набор точек (вершин)
     * @param closed замкнутость
     * @throws IllegalArgumentException если в набор точек входит менее 3 точек
     */
    public Polyline(final Collection<Point> pts, final boolean closed)
            throws IllegalArgumentException {
        super();
        if (pts.size() < 3) {
            throw new IllegalArgumentException("too few points");
        }

        segments = new ArrayList<>();
        vertices = new LinkedList<>();

        Iterator<Point> iterator = pts.iterator();

        Point first = iterator.next();
        vertices.add(first);

        while (iterator.hasNext()) {
            Point second = iterator.next();
            vertices.add(second);
            segments.add(new Segment(first, second));
            first = second;
        }

        if (closed) {
            segments.add(new Segment(vertices.getLast(), vertices.getFirst()));
            this.closed = true;
        } else {
            this.closed = false;
        }
    }

    /**
     * @return замкнутая полилиния на основе текущей
     */
    public Polyline getClosed() {
        if (isClosed()) {
            return this;
        }
        return new Polyline(vertices, true);
    }

    @Override
    public String getName() {
        return "Polyline";
    }

    @Override
    public Polyline getPolyline() {
        return this;
    }

    public boolean isClosed() {
        return closed;
    }

    @Override
    public String toString() {
        return String.format("%s with %d segments and %.3f total lenght",
                super.toString(), totalSegments(), totalLenght());
    }

    public double totalLenght() {
        return segments.stream()
                .mapToDouble(Segment::getLenght)
                .sum();
    }

    public int totalSegments() {
        return segments.size();
    }

    public Collection<Segment> segments() {
        return Collections.unmodifiableCollection(segments);
    }

    public Collection<Point> vertices() {
        return Collections.unmodifiableCollection(vertices);
    }

}

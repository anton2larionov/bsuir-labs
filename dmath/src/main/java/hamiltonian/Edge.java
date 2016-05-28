package hamiltonian;

/**
 * Ребро графа
 */
public class Edge {
    /**
     * начальная вершина
     */
    private String mFrom;

    /**
     * конечная вершина
     */
    private String mTo;

    /**
     * Конструктор
     *
     * @param from начальная вершина
     * @param to   конечная вершина
     */
    public Edge(String from, String to) {
        mFrom = from;
        mTo = to;
    }

    /**
     * @return конечная вершина
     */
    public String getDestination() {
        return mTo;
    }

    /**
     * @return начальная вершина
     */
    public String getSource() {
        return mFrom;
    }

}

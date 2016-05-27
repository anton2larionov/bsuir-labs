package hamiltonian;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Граф
 */
public class DirGraph {
    private static final String ZERO = "0";
    private static final String ONE = "1";

    /**
     * имя начальной вершины
     */
    private static final char FirstVertex = 'A';

    /**
     * индекс начальной вершины
     */
    private static final int start = 0;

    /**
     * матрица смежности
     */
    private int[][] adjM;

    /**
     * модифицированная матрица смежности
     */
    private String[][] modAdjM;

    /**
     * массив имен вершин
     */
    private String[] vert;

    /**
     * список ребер
     */
    private List<Edge> edges = new LinkedList<>();

    /**
     * число вершин
     */
    private int size;

    /**
     * Конструктор
     *
     * @param matrix квадратная матрица, заполненная 0 или 1
     * @throws IllegalArgumentException
     */
    public DirGraph(int[][] matrix) throws IllegalArgumentException {
        int rows = matrix.length;
        int columns = matrix[0].length;

        if (rows != columns || rows < 2)
            throw new IllegalArgumentException();

        setVertexCount(rows);

        // заполнение матрицы смежности
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++) {
                int num = matrix[i][j];
                if (num == 0 || num == 1)
                    adjM[i][j] = num;
                else
                    throw new IllegalArgumentException();
            }

        fillDirGraph();
    }

    public DirGraph(File fromFile) throws FileNotFoundException,
            NoSuchElementException, IllegalArgumentException {

        Scanner scanner = new Scanner(fromFile);

        int count = scanner.nextInt();

        setVertexCount(count);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int num = scanner.nextInt();
                if (num == 0 || num == 1)
                    adjM[i][j] = num;
                else
                    throw new IllegalArgumentException();
            }
        }

        scanner.close();
        fillDirGraph();
    }

    /**
     * Задание размера матрицы (числа вершин)
     *
     * @param count число вершин
     */
    private void setVertexCount(int count) {
        size = count;
        adjM = new int[size][size];
        vert = new String[size];
    }

    /**
     * @return матрица смежности графа
     */
    public int[][] getAdjacencyMatrix() {
        int[][] copy = new int[size][size];

        for (int i = 0; i < size; i++)
            System.arraycopy(adjM[i], 0, copy[i], 0, size);

        return copy;
    }

    /**
     * Заполнение массива имен вершин и списка граней
     */
    private void fillDirGraph() {
        // заполнение массива имен вершин
        for (int i = 0; i < size; i++) {
            vert[i] = String.valueOf((char) (FirstVertex + i));
        }

        // заполнение списка граней
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (adjM[i][j] != 0) {
                    edges.add(new Edge(vert[i], vert[j]));
                }
            }
        }
    }

    /**
     * Поиск гамильтоновых путей в графе на основе алгебраического алгоритма
     */
    public List<String> hamiltonianPath() {
        modAdjM = new String[size][size];
        // заполнение модифицированной матрицы смежности
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                modAdjM[i][j] = (adjM[i][j] == 0) ? ZERO : vert[j];
            }
        }

        // обнуление главной диагонали модифицированной матрицы
        for (int i = 0; i < size; i++) {
            modAdjM[i][i] = ZERO;
        }

        // вспомогательная матрица P1
        Cell[][] matrP1 = new Cell[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (adjM[i][j] != 0 && i != j) {
                    matrP1[i][j] = new Cell();
                    matrP1[i][j].addElement(ONE);
                }
            }
        }

        Cell[][] mP = matrP1;
        Cell[][] mPnext = matrP1;

        // вспомогательные матрицы P2, P3, ... , P(n-1)
        for (int count = 2; count < size; count++) {

            mPnext = new Cell[size][size];

            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (i != j) {
                        mPnext[i][j] = new Cell();
                        for (int k = 0; k < size; k++) {
                            mPnext[i][j].addElements(multString(modAdjM[i][k],
                                    mP[k][j], i, j));
                        }
                    }
                }
            }

            mP = mPnext;
        }

        // определение всех гамильтоновых путей
        // hamiltonianPath - ячейка матрицы P5, в которой каждый элемент главной
        // диагонали содержит все гамильтоновы пути
        Cell hamiltonianPath = new Cell();
        for (int k = 0; k < size; k++) {
            hamiltonianPath.addElements(
                    vert[start],
                    multString(modAdjM[start][k], mPnext[k][start], start,
                            start));
        }

        return hamiltonianPath.getList();

    }

    /**
     * Умножение слов
     */
    private List<String> multString(String str, Cell cell, int i, int j) {

        if (cell == null || str.equals(ZERO) || str.contains(vert[i])
                || str.contains(vert[j]))
            return null;

        List<String> data = new ArrayList<>();
        Iterator<String> it = cell.getList().iterator();

        String tmp;

        while (it.hasNext()) {
            tmp = it.next();

            if (tmp.equals(ONE))
                tmp = "";
            if (!(tmp.contains(vert[i]) || tmp.contains(vert[j])))
                data.add(str + tmp);
        }

        return data;
    }

}
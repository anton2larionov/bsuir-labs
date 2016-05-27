package linear;

/**
 * Вспомогательный класс
 */
public class Helper {

    public static final double EPS = 1e-16;

    /**
     * Вычисление ранга матрицы
     *
     * @param matrix матрица
     * @return ранг матрицы
     */
    public static int rank(double[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;

        int rank = Math.max(n, m);    // начальное значение ранга

        boolean[] line_used = new boolean[n];
        for (int i = 0; i < n; i++) line_used[i] = false;

        for (int i = 0; i < m; ++i) {
            int j;
            for (j = 0; j < n; ++j)
                if (!line_used[j] && Math.abs(matrix[j][i]) > EPS)
                    break;
            if (j == n)
                --rank;
            else {
                line_used[j] = true;
                for (int p = i + 1; p < m; ++p)
                    matrix[j][p] /= matrix[j][i];
                for (int k = 0; k < n; ++k)
                    if (k != j && Math.abs(matrix[k][i]) > EPS)
                        for (int p = i + 1; p < m; ++p)
                            matrix[k][p] -= matrix[j][p] * matrix[k][i];
            }
        }
        return rank;
    }
}

package linear;

/**
 * Решение СЛАУ методом Гаусса
 */
public class GaussMethod implements Method {

    @Override
    public double[] calculate(double[][] A, double[] b) {
        int n = A.length;

        double[][] M = new double[n][n + 1];

        for (int i = 0; i < n; i++)
            System.arraycopy(A[i], 0, M[i], 0, n);

        for (int i = 0; i < n; i++) M[i][n] = b[i];

        for (int i = 0; i < n; i++) {
            double max = Math.abs(M[i][i]);
            int imax = i;        // номер строки с максимальным элементом

            for (int p = i + 1; p < n; p++)
                if (max < Math.abs(M[p][i])) {
                    max = Math.abs(M[p][i]);
                    imax = p;
                }

            double tmp;
            if (imax != i) {
                // обмен местами элементов строк с максимальным элементом
                for (int j = i; j <= n; j++) {
                    tmp = M[i][j];
                    M[i][j] = M[imax][j];
                    M[imax][j] = tmp;
                }
            }

            double v = M[i][i];        // элемент на главной диагонали, являющийся максимальным

            if (Math.abs(v) < Helper.EPS)
                throw new IllegalArgumentException("Cистема уравнений плохо обусловлена.");

            for (int j = i; j <= n; j++)
                M[i][j] = M[i][j] / v;

            for (int p = i + 1; p < n; p++) {        // все последующие строки после строки с максимальным элементом
                v = M[p][i];
                for (int j = i + 1; j <= n; j++)
                    M[p][j] = M[p][j] - M[i][j] * v;
            }

        }

        double[] x = new double[n];

        x[n - 1] = M[n - 1][n];        // n-ый элемент решения

        // вычисление всех элементов решения в обратном порядке
        for (int i = n - 1; i >= 0; i--) {
            x[i] = M[i][n];
            for (int j = i + 1; j < n; j++)
                x[i] = x[i] - M[i][j] * x[j];
        }
        return x;
    }

    @Override
    public String getMethodName() {
        return "Метод Гаусса";
    }

}
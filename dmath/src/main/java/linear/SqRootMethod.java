package linear;

/**
 * Решение СЛАУ методом квадратного корня (метод Холецкого)
 */
public class SqRootMethod implements Method {

    @Override
    public double[] calculate(double[][] A, double[] b) {
        int n = A.length;

        int i, j, p;

        double[][] B, C;
        B = new double[n][n];
        C = new double[n][n];

        for (i = 0; i < n; i++) {
            B[i][0] = A[i][0];
            C[0][i] = A[0][i] / B[0][0];
            if (i != 0)
                C[i][i] = 1;
        }

        double Sum;

        for (i = 1; i < n; i++)
            for (j = 1; j < n; j++) {
                if (j > i) {
                    Sum = 0;
                    for (p = 0; p <= i - 1; p++)
                        Sum += (B[i][p] * C[p][j]);
                    C[i][j] = ((A[i][j] - Sum) / B[i][i]);
                }
                if (i >= j) {
                    Sum = 0;
                    for (p = 0; p <= j - 1; p++)
                        Sum += (B[i][p] * C[p][j]);
                    B[i][j] = A[i][j] - Sum;
                }
            }

        double[] y = new double[n];
        double[] x = new double[n];
        y[0] = b[0] / B[0][0];

        for (i = 1; i < n; i++) {
            Sum = 0;
            for (p = 0; p <= i - 1; p++)
                Sum += (B[i][p] * y[p]);
            y[i] = (b[i] - Sum) / B[i][i];
        }
        x[n - 1] = y[n - 1];

        for (i = n - 2; i >= 0; i--) {
            Sum = 0;
            for (j = i + 1; j < n; j++)
                Sum += C[i][j] * x[j];
            x[i] = y[i] - Sum;
        }

        return x;
    }

    @Override
    public String getMethodName() {
        return "Метод Холецкого";
    }
}
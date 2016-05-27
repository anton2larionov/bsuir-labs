package calculus;

public class NumDifferentiation {

    /**
     * число точек разбиения интервала
     */
    private static final int N = 500;

    /**
     * Дифференцирование функции с помощью сплайнов
     *
     * @param fun  имя функции
     * @param data исходные данные
     */
    public static DiffResult splines(Function1 fun, Data data) {
        double a = data.getA();
        double b = data.getB();
        double x = data.getX();

        double h = (b - a) / N;
        double[] y = new double[N];

        for (int i = 0; i < y.length; i++) {
            double tmp = h * i;
            y[i] = fun.execute(a + tmp);
        }

        int m = (int) Math.round((x - a) / h + h / 2);
        double h1 = 2 * h;
        double h2 = h * h;

        double der1 = 0, der2 = 0;

        if (m == 0) {
            der1 = (-3 * y[0] + 4 * y[1] - y[2]) / h1;
            der2 = (2 * y[0] - 5 * y[1] + 4 * y[2] - y[3]) / h2;

        } else if ((m > 0) && (m < N)) {
            der1 = (-y[m - 1] + y[m + 1]) / h1;
            der2 = (y[m - 1] - 2 * y[m] + y[m + 1]) / h2;

        } else if (m == N) {
            der1 = (y[N - 3] - 4 * y[N - 2] + 3 * y[N - 1]) / h1;
            der2 = (-y[N - 4] + 4 * y[N - 3] - 5 * y[N - 2] + 2 * y[N - 1]) / h2;
        }

        DiffResult res = new DiffResult();
        res.setFirstDer(der1);
        res.setSecondDer(der2);

        return res;
    }
}
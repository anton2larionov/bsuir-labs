package interpolation;

/**
 * Класс, предназначенный для Аппроксимации функций
 * с помощью кубического сплайна
 */
public class CubicSpline {

    /**
     * Структура, описывающая сплайн на каждом сегменте сетки
     */
    private class SplineTuple {
        double a, b, c, d, x;
    }

    /**
     * массив сплайнов
     */
    private SplineTuple[] splines;

    /**
     * Построение сплайна
     *
     * @param x узлы сетки, должны быть упорядочены по возрастанию
     * @param y значения функции в узлах сетки
     */
    public CubicSpline(double[] x, double[] y) {

        int n = x.length;    // n - количество узлов сетки

        // Инициализация массива сплайнов
        splines = new SplineTuple[n];
        for (int i = 0; i < n; ++i) {
            splines[i] = new SplineTuple();
            splines[i].x = x[i];
            splines[i].a = y[i];
        }
        splines[0].c = splines[n - 1].c = 0.0;

        // Решение СЛАУ относительно коэффициентов сплайнов c[i] методом прогонки для трехдиагональных матриц
        // Вычисление прогоночных коэффициентов - прямой ход метода прогонки
        double[] alpha = new double[n - 1];
        double[] beta = new double[n - 1];
        alpha[0] = beta[0] = 0.0;
        for (int i = 1; i < n - 1; ++i) {
            double hi = x[i] - x[i - 1];
            double hi1 = x[i + 1] - x[i];
            double C = 2.0 * (hi + hi1);
            double F = 6.0 * ((y[i + 1] - y[i]) / hi1 - (y[i] - y[i - 1]) / hi);
            double z = (hi * alpha[i - 1] + C);
            alpha[i] = -hi1 / z;
            beta[i] = (F - hi * beta[i - 1]) / z;
        }

        // Нахождение решения - обратный ход метода прогонки
        for (int i = n - 2; i > 0; --i) {
            splines[i].c = alpha[i] * splines[i + 1].c + beta[i];
        }

        // По известным коэффициентам c[i] вычисление значений b[i] и d[i]
        for (int i = n - 1; i > 0; --i) {
            double hi = x[i] - x[i - 1];
            splines[i].d = (splines[i].c - splines[i - 1].c) / hi;
            splines[i].b = hi * (2.0 * splines[i].c + splines[i - 1].c) / 6.0 + (y[i] - y[i - 1]) / hi;
        }
    }

    /**
     * Вычисление значения интерполированной функции в произвольной точке
     *
     * @param x произвольная точка
     * @return значение интерполированной функции
     */
    public double interpolate(double x) {
        // Если сплайны ещё не построены
        if (splines == null) {
            return Double.NaN;
        }

        int n = splines.length;
        SplineTuple s;

        if (x <= splines[0].x) { // Если x меньше точки сетки x[0], то используется первый эл-нт массива
            s = splines[0];
        } else if (x >= splines[n - 1].x) { // Если x больше точки сетки x[n-1], то используется последний эл-т массива
            s = splines[n - 1];
        } else { // Иначе x лежит между граничными точками сетки
            // бинарный поиск нужного элемента массива
            int i = 0;
            int j = n - 1;
            while (i + 1 < j) {
                int k = i + (j - i) / 2;
                if (x <= splines[k].x) {
                    j = k;
                } else {
                    i = k;
                }
            }
            s = splines[j];
        }

        double dx = x - s.x;

        // Вычисление значения сплайна в заданной точке
        return s.a + (s.b + (s.c / 2.0 + s.d * dx / 6.0) * dx) * dx;
    }

}

package calculus;

public class NumIntegration {

    /**
     * Вычисление определенного интеграла с помощью Квадратурной формулы Гаусса
     *
     * @param fun  имя функции
     * @param data исходные данные
     */
    public static IntegralResult quadrature(Function1 fun, Data data) {
        double a = data.getA();
        double b = data.getB();
        double c = Math.sqrt(3. / 5);

        double h1 = (b - a) / 2;
        double c1 = c * h1;
        double x2 = (b + a) / 2;

        double f1 = fun.execute(x2 - c1);
        double f3 = fun.execute(x2 + c1);

        double s1 = h1 * (5 * f1 + 8 * fun.execute(x2) + 5 * f3) / 9;

        int k = 2;

        double h, d, x1, x3, s2;

        do {
            h = (b - a) / k;
            h1 = h / 2;
            c1 = c * h1;
            x2 = a + h1;
            x1 = x2 - c1;
            x3 = x2 + c1;
            s2 = 0;
            for (int i = 0; i < k; i++) {
                s2 = s2 + 5 * fun.execute(x1) + 8 * fun.execute(x2) + 5 * fun.execute(x3);
                x1 += h;
                x2 += h;
                x3 += h;
            }
            s2 = s2 * h1 / 9;
            d = Math.abs(s1 - s2) / 63;
            s1 = s2;
            k *= 2;
        } while (!(d < h));

        IntegralResult res = new IntegralResult();
        res.setValue(s1);
        res.setError(d);

        return res;
    }

    /**
     * Вычисление определенного интеграла с помощью Метода Симпсона
     *
     * @param fun  имя функции
     * @param data исходные данные
     */
    public static IntegralResult simpson(Function1 fun, Data data) {
        double a = data.getA();
        double b = data.getB();
        double f0 = fun.execute(a);
        double f1 = fun.execute(b);

        double s = f0 - f1;
        double s1 = (b - a) * (f0 + f1 + 4 * fun.execute((a + b) / 2)) / 6;
        int k = 2;

        double h, x1, x2, s2, d;

        do {
            h = (b - a) / k;
            x1 = a + h / 2;
            x2 = a + h;
            s2 = s;
            for (int i = 0; i < k; i++) {
                s2 = s2 + 4 * fun.execute(x1) + 2 * fun.execute(x2);
                x1 += h;
                x2 += h;
            }
            s2 *= h / 6;
            d = Math.abs(s1 - s2) / 15;
            s1 = s2;
            k *= 2;
        } while (!(d < h));

        IntegralResult res = new IntegralResult();
        res.setValue(s1);
        res.setError(d);

        return res;
    }

}
package calculus;

public class Example {

    /** функция f1 */
    private static final Function1 f1 = new Function1() {
        @Override
        public double formula(double x) {
            return 1 + 1 / x;
        }
    };

    /** функция f2 */
    private static final Function1 f2 = new Function1() {
        @Override
        public double formula(double d) {
            return Math.cos(d);
        }
    };

    public static void main(String[] args) {
        // исходные данные
        Data data = new Data(1, 2);
        // дифференцирование f1
        DiffResult res = NumDifferentiation.splines(f1, data);

        // интегрирование f2
        IntegralResult reQ = NumIntegration.quadrature(f2, data);
        IntegralResult reS = NumIntegration.simpson(f2, data);
    }
}

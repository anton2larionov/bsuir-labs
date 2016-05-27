package calculus;

/**
 * Используется как шаблон для функций
 */
public abstract class Function1 {

    /**
     * Формула, по которой производится вычисление значения функции
     *
     * @param x точка, в которой необходимо вычислить значение функции
     * @return значение функции
     */
    abstract double formula(double x);

    /**
     * Запуск вычислений по алгоритму, определенному в методе {@link Function1#formula}
     */
    public double execute(double x) {
        double res = formula(x);
        if (!Double.isFinite(res)) {
            throw new ArithmeticException("Произошла недопустимая арифметическая операция!");
        }
        return res;
    }

}
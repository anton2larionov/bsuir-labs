package calculus;

/**
 * Используется для хранения результатов численного интегрирования
 */
public class IntegralResult {

    /**
     * результат
     */
    private double value;

    /**
     * погрешность вычислений
     */
    private double error;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

}
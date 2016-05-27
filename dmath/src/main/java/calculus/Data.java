package calculus;

/**
 * Используется для хранения интервала исходных данных
 */
public class Data {

    /**
     * нижняя граница интервала
     */
    private double a;

    /**
     * верхняя граница интервала
     */
    private double b;

    /**
     * Создание интервала исходных данных [a, b]
     *
     * @param a нижняя граница интервала
     * @param b верхняя граница интервала
     * @throws IllegalArgumentException если значение нижней границы интервала больше или
     *                                  равно значению верхней границы
     */
    public Data(double a, double b) throws IllegalArgumentException {
        if (!(a < b))
            throw new IllegalArgumentException("Верхняя граница интервала [a, b] должна быть больше нижней.");
        this.a = a;
        this.b = b;
    }

    /**
     * @return значение нижней границы интервала [a, b]
     */
    public double getA() {
        return a;
    }

    /**
     * @return значение верхней границы интервала [a, b]
     */
    public double getB() {
        return b;
    }

    /**
     * @return значение середины интервала [a, b]
     */
    public double getX() {
        return (this.a + this.b) / 2;
    }

}

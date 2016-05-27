package linear;

public interface Method {

    /**
     * Метод решения СЛАУ
     *
     * @param A матрица системы
     * @param b столбец свободных значений
     * @return решение СЛАУ
     */
    double[] calculate(double[][] A, double[] b);

    /**
     * @return Название метода решения СЛАУ
     */
    String getMethodName();

}

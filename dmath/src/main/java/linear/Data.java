package linear;

/**
 * Данные для решения СЛАУ
 */
public class Data {
    /**
     * число неизвестных
     */
    public static final int N = 3;

    /**
     * матрица А
     */
    private double[][] m_A;

    /**
     * столбец свободных значений
     */
    private double[] m_b;


    public double[][] getM_A() {
        return m_A;
    }

    public void setM_A(double[][] m_A) {
        this.m_A = m_A;
    }

    public double[] getM_b() {
        return m_b;
    }

    public void setM_b(double[] m_b) {
        this.m_b = m_b;
    }

}
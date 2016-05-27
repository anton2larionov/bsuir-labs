package linear;

/**
 * Класс, предназначеный для решения СЛАУ по выбранному методу
 */
public class Calculator {

    /**
     * Тип решения СЛАУ
     */
    public enum SolutionType {
        ONESOLUTION,    // одно решение
        INFSOLUTIONS,   // бесконечное множество решений
        NOSOLUTION      // нет решений
    }

    /**
     * метод решения СЛАУ
     */
    private Method method;

    public Calculator(Method method) {
        this.method = method;
    }

    public double[] apply(Data data) {
        // определение имеет ли СЛАУ решение или нет
        SolutionType solution = checkData(data);
        if (solution.equals(SolutionType.NOSOLUTION)) {
            throw new SolutionException("Система уравнений не имеет решений!");
        } else if (solution.equals(SolutionType.INFSOLUTIONS)) {
            throw new SolutionException("Система уравнений имеет бесконечное множество решений!");
        }

        double[] m_x; // массив неизвестных

        // нахождение корней СЛАУ
        try {
            m_x = method.calculate(data.getM_A(), data.getM_b());
        } catch (ArithmeticException ex) {
            throw new SolutionException("Произошла недопустимая арифметическая операция!", ex);
        }

        return m_x;
    }

    /**
     * Определение типа решения СЛАУ по Теореме Кронекера-Капелли
     *
     * @param data исходные данные
     * @return тип решения СЛАУ
     */
    private SolutionType checkData(Data data) {
        int N = Data.N;

        double[][] m_A = data.getM_A();
        double[] m_b = data.getM_b();

        double[][] Ab = new double[N][N + 1];    // расширенная матрица СЛАУ
        double[][] A = new double[N][N];

        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++) {
                Ab[i][j] = m_A[i][j];
                A[i][j] = m_A[i][j];
            }

        for (int i = 0; i < N; i++) Ab[i][N] = m_b[i];

        // вычисление рангов матриц
        int rankAb = Helper.rank(Ab);
        int rankA = Helper.rank(A);

        // определение типа решения СЛАУ
        if ((rankA == N) && (rankAb == N)) {
            return SolutionType.ONESOLUTION;
        } else if (rankA != rankAb) {
            return SolutionType.NOSOLUTION;
        } else return SolutionType.INFSOLUTIONS;
    }
}
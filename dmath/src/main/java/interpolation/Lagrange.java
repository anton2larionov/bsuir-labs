package interpolation;

/**
 * Класс,  предназначенный  для интерполяции  функций
 * с  помощью  Интерполяционного  полинома  Лагранжа
 */
public class Lagrange {
    /**
     * множество  точек
     */
    private double[] x;
    /**
     * множество  значений  функции  в данных  точках  x
     */
    private double[] y;
    /**
     * количество точек,  используемых  для построения  Интерполяционного  полинома
     */
    private static final int T = 7;

    /**
     * @param x множество  точек
     * @param y множество  значений  функции в  данных точках
     */
    public Lagrange(double[] x, double[] y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Вычисление значения интерполированной функции  в  произвольной  точке
     *
     * @param arg произвольная  точка
     * @return значение интерполированной функции
     */
    public double interpolate(double arg) {
        double result = 0;
        int n = x.length;   // n -  количество узловых  точек
        // определение  номера элемента,  ближайшего по значению к  arg,
        // с  использованием  бинарного  поиска
        int left = 0;
        int right = n - 1;
        while (left + 1 < right) {
            int aver = left + (right - left) / 2;
            if (arg <= x[aver]) {
                right = aver;
            } else {
                left = aver;
            }
        }
        // предварительное вычисление  начальной и  конечной  границы  интерполяции
        // на  основании заданного  диапазона  T
        int start = right - T / 2;
        int fin = start + T;
        if (start < 0) {    // если  начальный индекс  меньше  нуля
            fin -= start;
            start = 0;
        } else if (fin > n) { // если  конечный  индекс выходит  за  пр еделы  массива
            start -= (fin - n);
            fin = n;
        }
        // вычисление  значения  функции в  заданной  точке
        for (int i = start; i < fin; i++) {
            double k = 1;
            for (int j = start; j < fin; j++) {
                if (j != i) {
                    k *= ((arg - x[j]) / (x[i] - x[j]));
                }
            }
            result += (k * y[i]);
        }
        return result;
    }
}
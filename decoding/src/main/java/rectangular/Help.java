package rectangular;

/**
 * Вспомогальный класс.
 */
public class Help {
    /**
     * Вес.
     *
     * @param x число
     * @return сумма цифр в бинарном представлении числа
     */
    public static int weigth(int x) {
        int r = x % 2;
        int c = x;
        while (c > 1) {
            c >>= 1;
            r += c % 2;
        }
        return r;
    }

    /**
     * Дополнить строку нулями с начала.
     *
     * @param s строка
     * @param c требуемое количество символов
     * @return строка, дополненная нулями
     */
    public static String addNull(String s, int c) {
        if (s.length() > c) {
            throw new IllegalArgumentException(s.length() + " > " + c);
        }
        while (s.length() < c) {
            s = "0" + s;
        }
        return s;
    }
}
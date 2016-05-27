package rectangular;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс для создания Кодера-декодера прямоугольного бинарного кода.
 */
public class RectangularCD {
    private final int rowC, colC;
    private final int k, n, m;

    /**
     * Минимальный вес лидера смежного класса.
     */
    private int w = 1;

    /**
     * Смежные классы.
     */
    private int[][] groups;
    private Set<Integer> val;

    /**
     * Кодер-декодер прямоугольного бинарного кода.
     *
     * @param rowC число строк
     * @param colC число столбцов
     */
    public RectangularCD(int rowC, int colC) {
        if (rowC < 2 || colC < 2 || rowC > 9 || colC > 9) {
            throw new IllegalArgumentException();
        }

        k = rowC * colC;
        n = k + rowC + colC;
        m = n - k;
        this.rowC = rowC;
        this.colC = colC;
    }

    /**
     * Кодировать сообщение.
     *
     * @param str сообщение
     * @return кодовое слово
     */
    public String code(String str) {
        String s = Help.addNull(str, k);

        int[] arr = new int[k];

        for (int i = 0; i < k; i++) {
            arr[i] = Integer.parseInt(s.substring(i, i + 1), 2);
        }
        int[][] code = new int[rowC + 1][];

        for (int i = 0; i < rowC; i++) {
            code[i] = new int[colC + 1];
        }

        // сумма по столбцам
        int[] colS = new int[colC];

        for (int i = 0; i < rowC; i++) {
            // сумма по строке
            int rowS = 0;
            for (int j = 0; j < colC; j++) {
                code[i][j] = arr[j + i * colC];
                rowS ^= code[i][j];
                colS[j] ^= code[i][j];
            }
            code[i][colC] = rowS;
        }

        code[rowC] = colS;

        StringBuilder b = new StringBuilder();

        for (int i = 0; i < rowC; i++) {
            for (int j = 0; j < colC + 1; j++) {
                b.append(String.valueOf(code[i][j]));
            }
        }

        for (int j = 0; j < colC; j++) {
            b.append(String.valueOf(code[rowC][j]));
        }
        return b.toString();
    }

    /**
     * Декодировать кодовое слово.
     *
     * @param str кодовое слово
     * @return декодированное сообщение
     */
    public String decode(String str) {
        boolean error;
        String s = Help.addNull(str, n);
        int[] arr = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(s.substring(i, i + 1), 2);
        }

        int[][] dcode = new int[rowC][colC];
        int[] colS = new int[colC];

        // считывание информационной части
        for (int i = 0; i < rowC; i++) {
            System.arraycopy(arr, 0 + i * (colC + 1), dcode[i], 0, colC);
        }

        // поиск и исправление ошибок
        do {
            error = false;
            int a = -1, b = -1;
            for (int i = 0; i < rowC; i++) {
                int rowS = 0;
                for (int j = 0; j < colC; j++) {
                    rowS ^= dcode[i][j];
                    colS[j] ^= dcode[i][j];
                }
                if (rowS != arr[colC + i * (colC + 1)]) {
                    // найдена строка с ошибкой
                    error = true;
                    a = i;
                }
            }

            for (int j = 0; j < colC; j++) {
                if (colS[j] != arr[j + rowC * (colC + 1)]) {
                    // найден столбец с ошибкой
                    error = true;
                    b = j;
                }
            }

            if (a == -1 || b == -1) {
                break;
            }

            dcode[a][b] ^= 1;

        } while (error);

        StringBuilder b = new StringBuilder();
        for (int i = 0; i < rowC; i++) {
            for (int j = 0; j < colC; j++) {
                b.append(String.valueOf(dcode[i][j]));
            }
        }

        return b.toString();
    }

    /**
     * Декодировать кодовое слово по лидеру смежного класса.
     *
     * @param str кодовое слово
     * @return декодированное сообщение
     */
    public String decodeByGroups(String str) {
        String s = Help.addNull(str, n);
        if (groups == null) {
            // заполнение таблицы смежных классов
            createGroups();
        }
        int x = Integer.parseInt(s, 2);

        for (int j = 0; j < groups[0].length; j++) {
            // если кодовое слово находится в первой строке таблицы
            if (x == groups[0][j]) {
                return decode(Integer.toBinaryString(x));
            }
        }

        // поиск слова в таблице
        for (int i = 1; i < groups.length; i++) {
            for (int j = 0; j < groups[0].length; j++) {
                if (x == groups[i][j]) {
                    // сложение кодового слова с лидером смежного класса
                    x ^= groups[i][0];
                    break;
                }
            }
        }
        return decode(Integer.toBinaryString(x));
    }

    /**
     * Создать смежные классы.
     */
    private void createGroups() {
        val = new HashSet<>();
        groups = new int[1 << m][1 << k];
        for (int j = 0; j < groups[0].length; j++) {
            // заполнение первой строки таблицы
            groups[0][j] = Integer.parseInt(code(Integer.toBinaryString(j)), 2);
            val.add(groups[0][j]);
        }
        // максимальное значение вектора n
        int max = (1 << n) - 1;
        // стартовое значение лидера первого смежного класса
        int tmp = 1;
        for (int i = 1; i < groups.length; i++) {
            // проверка условий неповторяемости и минимальности веса
            while (inSet(tmp) || Help.weigth(tmp) > w) {
                tmp++;
                if (tmp > max) {
                    // если превышено максимальное значение,
                    // то ослабить требование по весу
                    tmp = 1;
                    w++;
                }
            }

            groups[i][0] = tmp;
            val.add(groups[i][0]);

            for (int j = 1; j < groups[0].length; j++) {
                // заполнение остальных строк таблицы
                groups[i][j] = groups[i][0] ^ groups[0][j];
                val.add(groups[i][j]);
            }
        }
    }

    /**
     * Проверка возможных повторений в таблице смежных классов.
     *
     * @param tmp проверяемый лидер смежного класса
     * @return {@code false} - повторов не возникнет,
     * {@code true} - иначе
     */
    private boolean inSet(int tmp) {
        if (val.contains(tmp)) {
            return true;
        }
        for (int j = 1; j < groups[0].length; j++) {
            if (val.contains(tmp ^ groups[0][j])) {
                return true;
            }
        }
        return false;
    }

    /**
     * Получить длину информационной части.
     *
     * @return длина информационной части.
     */
    public int getK() {
        return k;
    }

    /**
     * Получить длину блока.
     *
     * @return длина блока.
     */
    public int getN() {
        return n;
    }

}

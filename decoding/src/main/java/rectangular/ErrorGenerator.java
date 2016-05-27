package rectangular;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Класс для создания генератора ошибок кодового слова.
 */
public class ErrorGenerator {

    private final int base, n;

    /**
     * Число ошибок.
     */
    private final int count;

    /**
     * Список ошибок.
     */
    private List<Error> errors;

    /**
     * Конструктор генератора ошибок кодового слова.
     *
     * @param code кодовое слово
     * @param n    длина кодового блока
     */
    public ErrorGenerator(String code, int n) {
        if (code.length() > n) {
            throw new IllegalArgumentException(code.length() + " > " + n);
        }
        this.n = n;
        base = Integer.parseInt(code, 2);
        count = 1 << n;
        errors = new ArrayList<>(count - 1);
        fillList();
    }

    /**
     * Заполнить список ошибками.
     */
    private void fillList() {
        for (int i = 0; i < count; i++) {
            if (i != base) {
                int c = Help.weigth(i ^ base);
                String e = Help.addNull(Integer.toBinaryString(i), n);
                errors.add(new Error(e, c));
            }
        }
    }

    /**
     * Получить список ошибок.
     *
     * @return список ошибок
     */
    public List<Error> getErrors() {
        return Collections.unmodifiableList(errors);
    }
}
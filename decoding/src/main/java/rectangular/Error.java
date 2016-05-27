package rectangular;

/**
 * Класс для описания ошибки кодового слова.
 */
public class Error {
    private boolean fixed;
    private final int factor;
    private final String errStr;

    /**
     * Конструктор ошибки
     *
     * @param errStr ошибочное кодовое слово
     * @param factor кратность ошибки
     */
    Error(String errStr, int factor) {
        this.errStr = errStr;
        this.factor = factor;
    }

    /**
     * @return {@code true} - ошибка исправлена,
     * {@code false} - иначе
     */
    boolean isFixed() {
        return fixed;
    }

    /**
     * @param fixed передать {@code true}, если ошибка исправлена
     */
    void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    /**
     * @return кратность ошибки
     */
    int getFactor() {
        return factor;
    }

    /**
     * @return ошибочное кодовое слово
     */
    String getErrStr() {
        return errStr;
    }
}
package stream;

/**
 * Интерфейс алгоритма потокового шифрования
 */
@FunctionalInterface
public interface KeyIterator {

    /**
     * @return следующий элемент ключевой последовательности
     */
    int next();

}

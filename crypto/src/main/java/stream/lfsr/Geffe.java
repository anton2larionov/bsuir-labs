package stream.lfsr;

import stream.KeyIterator;

/**
 * Генератор Геффе.
 */
public class Geffe implements KeyIterator {

    private final LFSR lfsr1, lfsr2, lfsr3;

    /**
     * @param lfsr1 вход мультиплексора
     * @param lfsr2 вход мультиплексора
     * @param lfsr3 управляет выходом мультиплексора
     */
    public Geffe(LFSR lfsr1, LFSR lfsr2, LFSR lfsr3) {
        this.lfsr1 = lfsr1;
        this.lfsr2 = lfsr2;
        this.lfsr3 = lfsr3;
    }

    @Override
    public int next() {
        int tmp0 = lfsr1.next() & lfsr2.next();
        int tmp1 = (~lfsr1.next()) & lfsr3.next();
        return tmp0 | tmp1;
    }
}

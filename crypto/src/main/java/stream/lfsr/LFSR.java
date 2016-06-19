package stream.lfsr;

import stream.KeyIterator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Регистр сдвига с линейной обратной связью.
 */
public class LFSR implements KeyIterator {

    private final int[] register;
    private final int[] init;
    private final int[] taps;
    private final int N;

    /**
     * Конструктор регистра сдвига с линейной обратной связью.
     * @param stringRegister начальное состояние регистров
     */
    public LFSR(StringRegister stringRegister) {
        this.register = stringRegister.register();
        this.taps = Taps.get(register.length);
        N = register.length - 1;

        // сохранение начального состояния
        init = new int[N + 1];
        System.arraycopy(register, 0, init, 0, N + 1);
    }

    /**
     * Симулирование ситуации сдвига регистра.
     * @return элемент ключевого потока
     */
    @Override
    public int next() {
        int out = register[0];
        int bit = computeBit();
        System.arraycopy(register, 1, register, 0, N);
        register[N] = bit;
        return out;
    }

    /**
     * @return полная ключевая последовательность
     */
    public List<Integer> key() {
        List<Integer> integers = new ArrayList<>();
        System.arraycopy(init, 0, register, 0, N + 1); // reset
        do {
            integers.add(next());
        } while (!Arrays.equals(register, init));

        return integers;
    }

    // вычисление следующего бита
    private int computeBit() {
        int bit = register[0];

        for (int i = 1; i < taps.length; i++) {
            bit ^= register[register.length - taps[i]];
        }
        return bit;
    }
}

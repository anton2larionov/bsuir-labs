package idea;

/**
 * Математические функции.
 * Аргументы и результат находятся в диапазоне 0..0xFFFF.
 */
public final class Math {

    // Сложение в аддитивной группе
    public static int add(int a, int b) {
        return (a + b) & 0xFFFF;
    }

    // Аддитивная инверсия
    public static int addInv(int x) {
        return (0x10000 - x) & 0xFFFF;
    }

    // Умножение в мультипликативной группе
    public static int mul(int a, int b) {
        long r = (long) a * b;
        if (r != 0) {
            return (int) (r % 0x10001) & 0xFFFF;
        } else {
            return (1 - a - b) & 0xFFFF;
        }
    }

    // Мультипликативная инверсия
    public static int mulInv(int x) {
        if (x <= 1) {
            return x;
        }
        int y = 0x10001;
        int t0 = 1;
        int t1 = 0;
        while (true) {
            t1 += y / x * t0;
            y = y % x;
            if (y == 1) {
                return 0x10001 - t1;
            }
            t0 += x / y * t1;
            x = x % y;
            if (x == 1) {
                return t0;
            }
        }
    }
}

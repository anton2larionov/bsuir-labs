package rsa;

/**
 * Поиск значения параметров ключа RSA.
 */
public class RsaKey {

    private final int r;
    private final int e, d;

    /**
     * @param p число
     * @param q число
     * @param e открытый ключ
     */
    public RsaKey(int p, int q, int e) {
        if (p == q) {
            throw new IllegalArgumentException("Числа p и q не могут быть равны.");
        }
        if (!(MathUtil.prime(p) && MathUtil.prime(q))) {
            throw new IllegalArgumentException("Числа p и q должны быть простыми.");
        }
        // модуль
        r = p * q;
        // функция Эйлера
        int phi = (p - 1) * (q - 1);

        if (phi < 3) {
            // нельзя найти e, такое что 1 < e < phi
            throw new IllegalArgumentException("Числа p и q слишком малы.");
        }
        // проверка открытого ключа
        if (MathUtil.gcd(phi, e) != 1) {
            // НОД(phi, e) должен быть равен 1
            throw new IllegalArgumentException("Открытый ключ не взаимно простой со значением функции Эйлера.");
        }
        this.e = e;
        // секретный ключ
        d = MathUtil.mulInv(phi, e);

        if (1 != ((e * d) % phi)) {
            // не выполняется условие: (e * d) = 1 MOD phi
            throw new RuntimeException("Алгоритм поиска параметров работает не корректно.");
        }
    }

    // модуль
    public int getR() {
        return r;
    }

    // открытый ключ
    public int getE() {
        return e;
    }

    // секретный ключ
    public int getD() {
        return d;
    }

    // поиск значения открытого ключа
    private static int findE(int phi) {
        int n = 2;
        int e = fermatNumber(n);
        // НОД(phi, e) должен быть равен 1
        while (MathUtil.gcd(phi, e) != 1) {
            e = fermatNumber(++n);
        }
        return e;
    }

    // числа Ферма
    private static int fermatNumber(int n) {
        return (int) (Math.pow(2, Math.pow(2, n)) + 1);
    }

    @Override
    public String toString() {
        return "Ключ RSA:\n" +
                "  открытый ключ " + String.format("{%d, %d}%n", getR(), getE()) +
                "  закрытый ключ " + String.format("{%d, %d}%n", getR(), getD());
    }
}

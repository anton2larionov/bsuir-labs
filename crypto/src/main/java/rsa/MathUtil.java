package rsa;

public class MathUtil {

    // быстрое возведение в степень по модулю n
    public static int fastExp(int x, int y, int n) {
        int a = x, b = y, c = 1;

        while (b != 0) {
            while (b % 2 == 0) {
                b /= 2;
                a = (a * a) % n;
            }
            b -= 1;
            c = (c * a) % n;
        }
        return c;
    }

    // НОД(x, y)
    public static int gcd(int x, int y) {
        int a = Math.max(x, y);
        int b = Math.min(x, y);
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }

    // Мультипликативная инверсия
    // (d * e) % phi == 1
    public static int mulInv(int x, int y) {
        int d0 = x, d1 = y;
        int a0 = 0, a1 = 1;

        while (d1 > 1) {
            int q = d0 / d1;
            int d2 = d0 % d1;
            int a2 = a0 - q * a1;
            d0 = d1;
            d1 = d2;
            a0 = a1;
            a1 = a2;
        }
        return (a1 < 0) ? a1 + x : a1;
    }

    // определить, является ли данное число N простым
    public static boolean prime(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++)
            if (n % i == 0)
                return false;
        return true;
    }
}

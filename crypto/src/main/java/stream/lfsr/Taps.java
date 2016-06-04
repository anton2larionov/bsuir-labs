package stream.lfsr;

import java.util.HashMap;
import java.util.Map;

/**
 * Taps (ненулевые коэффициентов многочлена) для LFSR-регистров разной длины.
 * Степень многочлена {@code taps[0]} равна {@code registers.length}.
 * Например, для {@code x^9 + x^5 + 1} значение {@code taps == {9, 5}}.
 * <p>See
 * <a href="http://www.xilinx.com/support/documentation/application_notes/xapp052.pdf">
 *     XAPP 052 July 7,1996 (Version 1.1), p. 5
 *     </a>.
 * </p>
 */
public class Taps {

    private static Map<Integer, int[]> map = new HashMap<>();

    static {
        map.put(3, new int[]{3, 2});
        map.put(4, new int[]{4, 1});
        map.put(5, new int[]{5, 3});
        map.put(6, new int[]{6, 5});
        map.put(7, new int[]{7, 6});
        map.put(8, new int[]{8, 6, 5, 4});
        map.put(9, new int[]{9, 5});
        map.put(10, new int[]{10, 7});
        map.put(11, new int[]{11, 9});
        map.put(12, new int[]{12, 6, 4, 1});
        map.put(13, new int[]{13, 4, 3, 1});
        map.put(14, new int[]{14, 5, 3, 1});
        map.put(15, new int[]{15, 14});
        map.put(16, new int[]{16, 15, 13, 4});
        map.put(17, new int[]{17, 14});
        map.put(18, new int[]{18, 11});
        map.put(19, new int[]{19, 6, 2, 1});
        map.put(20, new int[]{20, 17});
        map.put(21, new int[]{21, 19});
        map.put(22, new int[]{22, 21});
        map.put(23, new int[]{23, 18});
        map.put(24, new int[]{24, 23, 22, 17});
        map.put(25, new int[]{25, 22});
        map.put(26, new int[]{26, 6, 2, 1});
        map.put(27, new int[]{27, 5, 2, 1});
        map.put(28, new int[]{28, 25});
        map.put(29, new int[]{29, 27});
        map.put(30, new int[]{30, 6, 4, 1});
        map.put(31, new int[]{31, 28});
        map.put(32, new int[]{32, 22, 2, 1});
        map.put(33, new int[]{33, 2});
        map.put(34, new int[]{34, 27, 2, 1});
        map.put(35, new int[]{35, 33});
        map.put(36, new int[]{36, 25});
        map.put(37, new int[]{37, 5, 4, 3, 2, 1});
        map.put(38, new int[]{38, 6, 5, 1});
        map.put(39, new int[]{39, 35});
        map.put(40, new int[]{40, 38, 21, 19});
        map.put(41, new int[]{41, 38});
        map.put(42, new int[]{42, 41, 20, 19});
        map.put(43, new int[]{43, 42, 38, 37});
        map.put(44, new int[]{44, 43, 18, 17});
        map.put(45, new int[]{45, 44, 42, 41});
        map.put(46, new int[]{46, 45, 26, 25});
        map.put(47, new int[]{47, 42});
        map.put(48, new int[]{48, 47, 21, 20});
        map.put(49, new int[]{49, 4});
        map.put(50, new int[]{50, 49, 24, 23});
        map.put(51, new int[]{51, 50, 36, 35});
        map.put(52, new int[]{52, 49});
        map.put(53, new int[]{53, 52, 38, 37});
        map.put(54, new int[]{54, 53, 18, 17});
        map.put(55, new int[]{55, 31});
        map.put(56, new int[]{56, 55, 35, 34});
        map.put(57, new int[]{57, 5});
        map.put(58, new int[]{58, 39});
        map.put(59, new int[]{59, 58, 38, 37});
        map.put(60, new int[]{60, 59});
        map.put(61, new int[]{61, 60, 46, 45});
        map.put(62, new int[]{62, 61, 6, 5});
        map.put(63, new int[]{63, 62});
        map.put(64, new int[]{64, 63, 61, 60});
    }

    public static int[] get(int n) {
        return map.get(n);
    }
}

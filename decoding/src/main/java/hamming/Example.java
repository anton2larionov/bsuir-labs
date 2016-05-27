package hamming;

public class Example {

    public static void main(String[] args) {

        // порождающа¤ матрица треугольного (10,6)-кода
        int[][] g = new int[][]{
                {1, 0, 0, 1, 0, 0, 0, 0, 0, 1},
                {0, 1, 0, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 1, 1, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 1, 0, 0, 1},
                {0, 0, 0, 0, 0, 1, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1}};

        // проверочна¤ матрица треугольного (10,6)-кода
        int[][] h = new int[][]{
                {1, 1, 1, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 0, 0, 0, 1, 0, 1, 1, 0},
                {1, 0, 0, 0, 1, 0, 0, 1, 0, 1}};

        CoderDecoder cd = new CoderDecoder(g, h);

        // проверка работоспособности
        System.out.println(cd.code("011111"));        // исходное сообщение
        System.out.println(cd.decode("0110111110"));// кодовое слово без ошибок
        System.out.println(cd.decode("1110111110"));// кодовое слово с ошибкой

        // код Хэмминга (7,4)
        HammingCode hc = new HammingOrd(3);
        hc.printMatrixH();

        // проверка работоспособности
        cd = new CoderDecoder(hc.getMatrixG(), hc.getMatrixH());
        System.out.println(cd.code("0110"));        // исходное сообщение
        System.out.println(cd.decode("0110110"));// кодовое слово без ошибок
        System.out.println(cd.decode("0111110"));// кодовое слово с ошибкой

        // расширенный код Хэмминга (16,11)
        hc = new HammingExt(4);
        hc.printMatrixH();

        // проверка работоспособности
        cd = new CoderDecoder(hc.getMatrixG(), hc.getMatrixH());
        System.out.println(cd.code("01100111101"));            // исходное сообщение
        System.out.println(cd.decode("0110011110111001"));    // кодовое слово без ошибок
        System.out.println(cd.decode("0110011110111011"));    // кодовое слово с ошибкой

        // скорость кода
        System.out.println(hc.rateOfCode());

        // генераци¤ всех кодовых слов
        for (int i = 0; i < (1 << hc.getK()); i++) {
            System.out.println(cd.code(Integer.toBinaryString(i)));
        }
    }
}
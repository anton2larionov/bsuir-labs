package rectangular;

import java.util.List;

public class Example {

    public static void main(String[] args) {
        // создание кодера-декодера
        RectangularCD cd = new RectangularCD(2, 2);

        // пример работы кодера-декодера
        System.out.println(cd.code("1010"));
        System.out.println(cd.decode("10110100"));
        System.out.println(cd.decodeByGroups("10110100"));

        // кодовые слова с одинарной ошибкой
        System.out.println(cd.decode("00110100"));
        System.out.println(cd.decodeByGroups("00110100"));
    }

    /**
     * Тестирование декодирования по лидеру смежного класса.
     *
     * @param cd кодер-декодер
     */
    static void testDecByGL(RectangularCD cd) {
        // тестирование метода для всех сообщений 2^k
        for (int i = 0; i < (1 << cd.getK()); i++) {
            final String word = Help.addNull(Integer.toBinaryString(i), cd.getK());
            final String code = cd.code(word);

            // генерация ошибок
            ErrorGenerator eg = new ErrorGenerator(code, cd.getN());
            List<Error> errors = eg.getErrors();

            // поиск и исправление ошибок
            errors.forEach(e -> {
                String decode = cd.decodeByGroups(e.getErrStr());
                if (word.equals(decode)) {
                    e.setFixed(true);
                }
            });

            // общее число исправленных ошибок
            long c = errors.stream().filter(Error::isFixed).count();

            // исправленные ошибки первой кратности
            long c1 = errors.stream()
                    .filter(Error::isFixed).filter(e -> e.getFactor() == 1)
                    .count();
            // исправленные ошибки второй кратности
            long c2 = errors.stream()
                    .filter(Error::isFixed).filter(e -> e.getFactor() == 2)
                    .count();

            System.out.print("c =\t" + c + "\t");
            System.out.print("c1 =\t" + c1 + "\t");
            System.out.print("c2 =\t" + c2 + "\t");
        }
    }

    /**
     * Тестирование декодирования по лидеру смежного класса.
     *
     * @param cd кодер-декодер
     */
    static void testDecByAlg(RectangularCD cd) {
        for (int i = 0; i < (1 << cd.getK()); i++) {
            final String word = Help.addNull(Integer.toBinaryString(i), cd.getK());
            final String code = cd.code(word);

            ErrorGenerator eg = new ErrorGenerator(code, cd.getN());
            List<Error> errors = eg.getErrors();

            errors.forEach(e -> {
                String decode = cd.decode(e.getErrStr());
                if (word.equals(decode)) {
                    e.setFixed(true);
                }
            });

            long c = errors.stream().filter(Error::isFixed).count();
            long c1 = errors.stream()
                    .filter(Error::isFixed).filter(e -> e.getFactor() == 1)
                    .count();
            long c2 = errors.stream()
                    .filter(Error::isFixed).filter(e -> e.getFactor() == 2)
                    .count();

            System.out.print("c =\t" + c + "\t");
            System.out.print("c1 =\t" + c1 + "\t");
            System.out.print("c2 =\t" + c2 + "\t");
        }
    }
}

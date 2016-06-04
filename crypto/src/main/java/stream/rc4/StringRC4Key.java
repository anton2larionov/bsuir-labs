package stream.rc4;

import java.util.Arrays;

/**
 * Строка, содержащая пользовательский секретный ключ.
 */
public class StringRC4Key {

    private final String charReg;

    public StringRC4Key(String charReg) {
        String tmp = charReg.trim();
        if (!tmp.matches("[\\d ]+")) {
            throw new IllegalArgumentException("Недопустимый символ в ключе RC4.");
        }
        this.charReg = tmp;
    }

    public int[] userKey() {
        return Arrays.stream(charReg.split("\\s++")).mapToInt(Integer::parseInt).toArray();
    }
}

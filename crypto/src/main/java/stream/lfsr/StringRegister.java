package stream.lfsr;

/**
 * Строка-регистр.
 * Допустимая длина {@code register} от 3 до 64 бит.
 */
public class StringRegister {

    private final String charReg;

    public StringRegister(String charReg) {
        String tmp = charReg.trim();
        if (!tmp.matches("[0-1]{3,64}")) {
            throw new IllegalArgumentException("Недопустимая строка-регистр.");
        }
        if (tmp.chars().allMatch(ch -> ch == '0')) {
            throw new IllegalArgumentException("Регистр не может состоять только из 0.");
        }
        this.charReg = tmp;
    }

    public int[] register() {
        return charReg.chars().map(ch -> ch - 48).toArray();
    }
}

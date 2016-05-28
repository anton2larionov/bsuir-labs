package idea;

public final class StringKey {

    private String charKey;

    public StringKey(String charKey) {
        if (charKey.length() > 32) {
            throw new IllegalArgumentException("Недопустимая длина строки-ключа.");
        }

        if (!charKey.matches("[A-Fa-f0-9]*")) {
            throw new IllegalArgumentException("Недопустимый символ в строке-ключе.");
        }
        this.charKey = charKey;
    }

    /**
     * @return 16-байтный бинарный ключ
     */
    public byte[] binaryKey() {
        // дополнить строку нулями с начала
        while (charKey.length() % 4 != 0) {
            charKey = "0" + charKey;
        }

        int[] a = new int[8];
        int groups = charKey.length() / 4;

        for (int i = 0; i < groups; i++) {
            String subString = charKey.substring(4 * i, 4 * (i + 1));
            a[8 - groups + i] = Integer.parseInt(subString, 16);
        }

        byte[] key = new byte[16];
        for (int i = 0; i < a.length; i++) {
            key[i * 2] = (byte) (a[i] >> 8);
            key[i * 2 + 1] = (byte) a[i];
        }
        return key;
    }
}

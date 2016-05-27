package jdbcxml.data;

public enum TagName {
    CATALOGUE,
    MEDICAMENT,
    TITLE,
    ON_PRESCRIPTION,
    PURCHASE_DATE,
    DESCRIPTION,
    ID,
    DISCOUNT;

    public String toPrefixAttribute() {
        return "c:" + toAttribute();
    }

    public String toAttribute() {
        return toString().toLowerCase().replace("_", "-");
    }

    public static TagName tagNameOf(String string) {
        return TagName.valueOf(string.toUpperCase().replace("-", "_"));
    }
}

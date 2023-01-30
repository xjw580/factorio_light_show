package club.xiaojiawei.enums;

/**
 * @author 肖嘉威
 * @date 2023/1/28 下午12:23
 */
public enum ComparatorEnum {

    EQ("="),
    NE("!="),
    GT(">"),
    LT("<"),
    LE("<="),
    GE(">=")
    ;
    private final String value;
    ComparatorEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

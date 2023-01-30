package club.xiaojiawei.enums;

/**
 * @author 肖嘉威
 * @date 2023/1/28 下午12:01
 */
public enum SignalTypeEnum {

    /**
     * icon里用的
     */
    ITEM("item"),
    /**
     * filter里用的
     */
    VIRTUAL("virtual")
    ;

    private final String value;
    SignalTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

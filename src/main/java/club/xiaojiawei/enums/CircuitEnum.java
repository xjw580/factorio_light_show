package club.xiaojiawei.enums;

/**
 * @author 肖嘉威
 * @date 2023/1/28 下午12:31
 * 电路出入口
 */
public enum CircuitEnum {
    /**
     * 入口
     */
    IN(1),
    /**
     * 出口
     */
    OUT(2),
    /**
     * 单口，不分出入口
     */
    SINGLE(0)
    ;
    private final int value;
    CircuitEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

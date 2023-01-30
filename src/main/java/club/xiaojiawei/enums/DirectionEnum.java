package club.xiaojiawei.enums;

/**
 * @author 肖嘉威
 * @date 2023/1/28 下午12:59
 * 物品朝向
 */
public enum DirectionEnum {
    UP(0),
    UNDER(4),
    LEFT(6),
    RIGHT(2)
    ;

    private final int value;
    DirectionEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}

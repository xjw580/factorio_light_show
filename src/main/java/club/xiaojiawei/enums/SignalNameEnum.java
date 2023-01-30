package club.xiaojiawei.enums;

/**
 * @author 肖嘉威
 * @date 2023/1/27 下午9:38
 */
public enum SignalNameEnum {

    /**
     * 红信号
     */
    RED("signal-red"),
    /**
     * 绿信号
     */
    GREEN("signal-green"),
    /**
     * 蓝信号
     */
    BLUE("signal-blue"),
    /**
     * 黄信号
     */
    YELLOW("signal-yellow"),
    /**
     * 粉信号
     */
    PINK("signal-pink"),
    /**
     * 青信号
     */
    CYAN("signal-cyan"),
    /**
     * 白信号
     */
    WHITE("signal-white"),
    /**
     * 黑信号
     */
    BLACK("signal-black"),
    /**
     * 常量运算器里的信号的前缀
     */
    CONSTANT_PREFIX("signal-"),
    /**
     * 判断运算器
     */
    DECIDER_COMBINATOR("decider-combinator"),
    /**
     * 常量运算器
     */
    CONSTANT_COMBINATOR("constant-combinator")
    ;

    private final String value;
    SignalNameEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

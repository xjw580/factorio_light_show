package club.xiaojiawei.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author 肖嘉威
 * @date 2023/1/25 下午11:22
 */
@Setter
@Getter
public class Icon {

    private Signal signal;

    private int index;

    public Icon(Signal signal, int index) {
        this.signal = signal;
        this.index = index;
    }

    @Override
    public String toString() {
        return """
                {
                    "signal":"""
                + signal  +
                    """
                    ,"index":"""
                + index +
                """
                }
                """;
    }
}

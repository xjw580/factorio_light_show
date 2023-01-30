package club.xiaojiawei.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 肖嘉威
 * @date 2023/1/26 上午10:46
 */
@Setter
@Getter
@Builder
public class Connections {

    private Connection one;

    private Connection two;

    @Override
    public String toString() {
        return "{" +
                (one == null? "" :
                "\"1\":" + one) +
                (two == null? "" :
                ", \"2\":" + two) +
                '}';
    }
}

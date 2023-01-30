package club.xiaojiawei.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author 肖嘉威
 * @date 2023/1/26 上午10:18
 */
@Setter
@Getter
@Builder
public class Connection {

    private List<Color> red;

    private List<Color> green;

    @Override
    public String toString() {
        return "{" +
                (red == null? "" :
                "\"red\":" + red) +
                (green == null? "" :
                ", \"green\":" + green) +
                '}';
    }
}

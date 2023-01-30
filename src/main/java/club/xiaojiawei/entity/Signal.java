package club.xiaojiawei.entity;

import club.xiaojiawei.enums.SignalNameEnum;
import club.xiaojiawei.enums.SignalTypeEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 肖嘉威
 * @date 2023/1/25 下午11:21
 */
@Setter
@Getter
public class Signal {

    private String type;
    private String name;

    public Signal(String type, String name) {
        this.type = type;
        this.name = name;
    }

    public Signal(SignalTypeEnum typeEnum, String name) {
        this(typeEnum.getValue(), name);
    }

    public Signal(SignalTypeEnum typeEnum, SignalNameEnum nameEnum) {
        this(typeEnum.getValue(), nameEnum.getValue());
    }

    @Override
    public String toString() {
        return "{" +
                "\"type\":\"" + type + '\"' +
                ", \"name\":\"" + name + '\"' +
                '}';
    }
}

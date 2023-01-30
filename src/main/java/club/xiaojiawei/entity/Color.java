package club.xiaojiawei.entity;

import club.xiaojiawei.enums.CircuitEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 肖嘉威
 * @date 2023/1/26 上午10:45
 */
@Setter
@Getter
public class Color {

    private int entityId;

    private int circuitId;

    public Color(int entityId, int circuitId) {
        this.entityId = entityId;
        this.circuitId = circuitId;
    }

    public Color(int entityId, CircuitEnum circuitEnum) {
        this(entityId, circuitEnum.getValue());
    }

    @Override
    public String toString() {
        return "{" +
                "\"entity_id\":" + entityId +
                (circuitId == 0? "" :
                ", \"circuit_id\":" + circuitId)
                +
                '}';
    }
}

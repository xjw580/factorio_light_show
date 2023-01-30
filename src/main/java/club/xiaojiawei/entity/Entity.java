package club.xiaojiawei.entity;

import club.xiaojiawei.enums.DirectionEnum;
import club.xiaojiawei.enums.SignalNameEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 肖嘉威
 * @date 2023/1/25 下午11:23
 */
@Setter
@Getter
public class Entity{

    private int entityNumber;
    private String name;
    private Position position;
    private int direction;
    private ControlBehavior controlBehaviors;
    private Connections connections;

    public Entity(int entityNumber, SignalNameEnum nameEnum, Position position, DirectionEnum directionEnum, ControlBehavior controlBehaviors, Connections connections) {
        this(entityNumber, nameEnum.getValue(), position, directionEnum.getValue(), controlBehaviors, connections);
    }
    public Entity(int entityNumber, String name, Position position, int direction, ControlBehavior controlBehaviors, Connections connections) {
        this.entityNumber = entityNumber;
        this.name = name;
        this.position = position;
        this.direction = direction;
        this.controlBehaviors = controlBehaviors;
        this.connections = connections;
    }

    @Override
    public String toString() {
        return "{" +
                "\"entity_number\":" + entityNumber +
                ", \"name\":\"" + name + '\"' +
                ", \"position\":" + position +
                (direction == 0? "" :
                ", \"direction\":" + direction) +
                ", \"control_behavior\":" + controlBehaviors +
                (connections.getOne() == null && connections.getTwo() == null? "" :
                ", \"connections\":" + connections) +
                '}';
    }
}

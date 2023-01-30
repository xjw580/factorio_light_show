package club.xiaojiawei.builder;

import club.xiaojiawei.entity.*;
import club.xiaojiawei.enums.CircuitEnum;
import club.xiaojiawei.enums.DirectionEnum;
import club.xiaojiawei.enums.SignalNameEnum;

import java.util.ArrayList;

/**
 * @author 肖嘉威
 * @date 2023/1/30 下午5:37
 */
public class DeciderCombinatorEntityBuilder implements EntityBuilder{

    @Override
    public Entity build(int entityNumber, DirectionEnum directionEnum, double x, double y, ControlBehavior controlBehavior) {
        return new Entity(
                entityNumber,
                SignalNameEnum.DECIDER_COMBINATOR,
                new Position(x, y),
                directionEnum,
                controlBehavior,
                Connections.builder()
                        .one(Connection.builder()
                                .red(new ArrayList<>(){{add(new Color(entityNumber + (directionEnum == DirectionEnum.UP? -1 : 1), CircuitEnum.SINGLE));}})
                                .green(new ArrayList<>(){{add(new Color(entityNumber + 4, CircuitEnum.IN));}})
                                .build())
                        .two(Connection.builder()
                                .red(new ArrayList<>(){{add(new Color(entityNumber + 4, CircuitEnum.OUT));}})
                                .build())
                        .build()
        );
    }
}

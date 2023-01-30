package club.xiaojiawei.builder;

import club.xiaojiawei.entity.ControlBehavior;
import club.xiaojiawei.entity.Entity;
import club.xiaojiawei.enums.DirectionEnum;

/**
 * @author 肖嘉威
 * @date 2023/1/28 上午10:56
 */
public interface EntityBuilder {

    /**
     * 建造方法
     * @param entityNumber
     * @param directionEnum
     * @param x
     * @param y
     * @param controlBehavior
     * @return
     */
    Entity build(int entityNumber, DirectionEnum directionEnum, double x, double y, ControlBehavior controlBehavior);

}

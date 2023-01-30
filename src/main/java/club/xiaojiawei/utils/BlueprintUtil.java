package club.xiaojiawei.utils;

import club.xiaojiawei.builder.ConstantCombinatorEntityBuilder;
import club.xiaojiawei.builder.DeciderCombinatorEntityBuilder;
import club.xiaojiawei.entity.*;
import club.xiaojiawei.enums.*;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static club.xiaojiawei.constant.ConfigConst.ABOVE_ROWS;
import static club.xiaojiawei.constant.ConfigConst.BELOW_ROWS;
import static club.xiaojiawei.enums.SignalNameEnum.CONSTANT_PREFIX;

/**
 * @author 肖嘉威
 * @date 2023/1/24 22:14
 * 蓝图工具类
 */
public class BlueprintUtil {

    private final static DeciderCombinatorEntityBuilder DECIDER_COMBINATOR_ENTITY_BUILDER = new DeciderCombinatorEntityBuilder();
    private final static ConstantCombinatorEntityBuilder CONSTANT_COMBINATOR_ENTITY_BUILDER = new ConstantCombinatorEntityBuilder();
    /**
     * Json字符串转蓝图代码
     * @param json Json字符串
     * @return 蓝图代码
     */
    public static String jsonToCode(String json){
        String code = "";
        if (json != null){
//            1.获取Json字符串的byte数组
            byte[] jsonBytes = json.getBytes(StandardCharsets.UTF_8);
//            2.压缩byte数组
            byte[] compress = ZlibUtil.compress(jsonBytes, 9);
//            3.Base64编码压缩后的byte数组
            byte[] encode = Base64.getEncoder().encode(compress);
//            4.前缀加0
            code = "0" + new String(encode);
        }
        return code;
    }

    /**
     * 根据Excel的数值转成Json字符串
     * 出口circuitId是2，入口是1，单口都是0
     * 朝向往下direction是4,往上是0
     * @param excelNums Excel的数值
     * @return Json字符串
     */
    public static String excelNumsToJson(List<List<Integer>> excelNums){
        List<Icon> icons = new ArrayList<>();
        List<Entity> entities = new ArrayList<>();
        Blueprint blueprint = new Blueprint(icons, entities);
        icons.add(new Icon(new Signal(SignalTypeEnum.ITEM, SignalNameEnum.DECIDER_COMBINATOR), 1));
        icons.add(new Icon(new Signal(SignalTypeEnum.ITEM, SignalNameEnum.CONSTANT_COMBINATOR), 2));
        int entityNumber = 1, col = 0;
        double x = 261.5, y = -68, initY = y;
        Filters filters;
        List<Filters.Filter> filterList;
        ProgressBarUtil.reset(excelNums.size());
        for (List<Integer> excelNum : excelNums) {
//            第一个判断运算器
            entities.add(DECIDER_COMBINATOR_ENTITY_BUILDER.build(
                    entityNumber++,
                    DirectionEnum.UP,
                    x,
                    y,
                    new DeciderConditions(new DeciderConditions.DeciderCondition(col, ComparatorEnum.EQ))
            ));
            y += 1.5;
//            第二个常量运算器
            filters = new Filters(filterList = new ArrayList<>());
            int i = 0;
            for (; i < ABOVE_ROWS; i++) {
                filterList.add(new Filters.Filter(new Signal(SignalTypeEnum.VIRTUAL, CONSTANT_PREFIX.getValue() + i), excelNum.get(i), i + 1));
            }
            entities.add(CONSTANT_COMBINATOR_ENTITY_BUILDER.build(
                    entityNumber++,
                    DirectionEnum.UP,
                    x,
                    y,
                    filters
            ));
            y++;
//            第三个常量运算器
            filters = new Filters(filterList = new ArrayList<>());
            for (i = 0; i < BELOW_ROWS; i++) {
                filterList.add(new Filters.Filter(new Signal(SignalTypeEnum.VIRTUAL, CONSTANT_PREFIX.getValue() + i), excelNum.get(i + ABOVE_ROWS), i + 1));
            }
            entities.add(CONSTANT_COMBINATOR_ENTITY_BUILDER.build(
                    entityNumber++,
                    DirectionEnum.UNDER,
                    x,
                    y,
                    filters
            ));
            y++;
//            第四个判断运算器
            entities.add(DECIDER_COMBINATOR_ENTITY_BUILDER.build(
                    entityNumber++,
                    DirectionEnum.UNDER,
                    x,
                    y,
                    new DeciderConditions(new DeciderConditions.DeciderCondition(col, ComparatorEnum.EQ))
            ));
//            一列完成
            col++;
            x++;
            y = initY;
            ProgressBarUtil.print();
        }
        entities.get(0).getConnections().getOne().getGreen().add(new Color(4, CircuitEnum.IN));
        entities.get(3).getConnections().getOne().getGreen().add(new Color(1, CircuitEnum.IN));
        return blueprint
                .toString()
                .replaceAll("\n", "")
                .replace(" ", "");
    }

}

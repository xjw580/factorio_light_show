package club.xiaojiawei.entity;

import club.xiaojiawei.enums.ComparatorEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author 肖嘉威
 * @date 2023/1/26 上午11:05
 */
@Setter
@Getter
public class DeciderConditions implements ControlBehavior{

    private DeciderCondition deciderCondition;

    @Setter
    @Getter
    public static class DeciderCondition{
        private int constant;
        private String comparator;
        public static final boolean COPY_COUNT_FROM_INPUT = true;

        public DeciderCondition(int constant, String comparator) {
            this.constant = constant;
            this.comparator = comparator;
        }

        public DeciderCondition(int constant, ComparatorEnum comparatorEnum) {
            this(constant, comparatorEnum.getValue());
        }

        @Override
        public String toString() {
            return "{"+
                    "\"first_signal\": {\n" +
                    "    \"type\": \"virtual\",\n" +
                    "    \"name\": \"signal-check\"\n" +
                    "}," +
                    "\"constant\":" + constant +
                    ", \"comparator\":\"" + comparator + '\"' +
                    ",\"output_signal\": {\n" +
                    "    \"type\": \"virtual\",\n" +
                    "    \"name\": \"signal-everything\"\n" +
                    "},"
                    +
                    "\"copy_count_from_input\":" + COPY_COUNT_FROM_INPUT +
                    "}";
        }
    }

    public DeciderConditions(DeciderCondition deciderCondition) {
        this.deciderCondition = deciderCondition;
    }

    @Override
    public String toString() {
        return "{" +
                "\"decider_conditions\":" + deciderCondition +
                '}';
    }
}

package club.xiaojiawei.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 肖嘉威
 * @date 2023/1/26 上午10:12
 */
@Setter
@Getter
public class Filters implements ControlBehavior{

    private List<Filter> filters;

    @Setter
    @Getter
    public static class Filter{
        private Signal signal;

        private int count;

        private int index;

        public Filter(Signal signal, int count, int index) {
            this.signal = signal;
            this.count = count;
            this.index = index;
        }

        @Override
        public String toString() {
            return "{" +
                    "\"signal\":" + signal +
                    ", \"count\":" + count +
                    ", \"index\":" + index +
                    '}';
        }
    }

    public Filters(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public String toString() {
        return "{" +
                "\"filters\":" + filters +
                '}';
    }
}

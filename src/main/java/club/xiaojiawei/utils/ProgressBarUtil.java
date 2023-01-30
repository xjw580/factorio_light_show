package club.xiaojiawei.utils;

import java.util.stream.Stream;

/**
 * @author 肖嘉威
 * @date 2023/1/29 下午7:12
 * 进度条打印工具
 */
public class ProgressBarUtil {

    private static int total;
    private static final char INCOMPLETE = '░';
    private static final char COMPLETE = '█';
    private static StringBuilder bar;
    private static int index;

    public static void reset(int total){
        ProgressBarUtil.total = total;
        ProgressBarUtil.index = 0;
        ProgressBarUtil.bar = new StringBuilder();
        ProgressBarUtil.bar = new StringBuilder();
        Stream.generate(() -> INCOMPLETE).limit(total).forEach(bar::append);
    }
    public static void print(){
        if (index < total){
            bar.replace(index, index + 1, String.valueOf(COMPLETE));
            String progressBar = "\r" + bar;
            String percent = " " + ++index + "/" + total + " ";
            System.out.print(progressBar + percent);
        }else {
            reset(total);
            print();
        }
    }

}

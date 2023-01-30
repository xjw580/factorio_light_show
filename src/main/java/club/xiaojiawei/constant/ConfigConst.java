package club.xiaojiawei.constant;

/**
 * @author 肖嘉威
 * @date 2023/1/30 下午6:45
 */
public class ConfigConst {

    public final static String EXCEL_PATH = "/home/zerg/Desktop/excel/";
    public final static String IMAGE_PATH = "/home/zerg/Desktop/images/";
    public final static String PIXELATION_IMAGE_PATH = IMAGE_PATH + "pixel/";
    public final static String VIDEO_PATH = "/home/zerg/Desktop/test.mp4";
    /**
     * 每隔多少秒抽一次帧
     */
    public final static double INTERVAL = 0.5;
    /**
     * 开始处理的时间
     */
    public final static int START_TIME = 9;
    /**
     * 结束处理的时间
     */
    public final static int END_TIME = 25;
    /**
     * 像素化时每个像素的由原来几个像素得来
     */
    public final static int PIXEL_SIZE_720P = 40;
    public final static int PIXEL_SIZE_1080P = 80;

    /**
     * 屏幕的上部分
     */
    public final static int ABOVE_ROWS = 6;
    /**
     * 屏幕的下部分
     */
    public final static int BELOW_ROWS = 10;
    /**
     * 每一块屏幕的列数
     */
    public static final int SINGLE_REGION_ROWS = ABOVE_ROWS + BELOW_ROWS;
    /**
     * 每一块屏幕的行数
     */
    public static final int SINGLE_REGION_COLS = 26;
}

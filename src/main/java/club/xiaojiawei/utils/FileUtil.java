package club.xiaojiawei.utils;

/**
 * @author 肖嘉威
 * @date 2023/1/28 下午1:21
 */
public class FileUtil {

    /**
     * 获取文件类型
     * @param fileName 文件名
     * @return
     */
    public static String getFileType(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }
}

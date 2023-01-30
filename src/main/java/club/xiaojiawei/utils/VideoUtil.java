package club.xiaojiawei.utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * @author 肖嘉威
 * @date 2023/1/24 23:11
 */
public class VideoUtil {

    private final static long ONE_SECOND = 1_000_000L;

    /**
     * 获取视频帧图片
     * @param video
     * @param interval
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<BufferedImage> getVideoPic(File video, double interval, int startTime, int endTime) {
        if (!video.exists()){
            System.out.println("视频不存在");
        }
        ArrayList<BufferedImage> images = new ArrayList<>();
        try (FFmpegFrameGrabber ff = new FFmpegFrameGrabber(video)
        ){
            ff.start();
//            校验开始和结束时间是否合法
            long totalTime = ff.getLengthInTime() / ONE_SECOND;
            if (startTime < 0 || endTime < 0 || startTime > totalTime || endTime > totalTime || startTime > endTime || interval > totalTime){
                return images;
            }
            Frame frame;
            double frameRate = ff.getFrameRate();
            ff.setVideoFrameNumber((int) (frameRate * startTime));
            int count = (int) ((endTime - startTime) / interval);
            ProgressBarUtil.reset(count);
            for (int i = 0; i < count;){
                frame = ff.grab();
                if (frame.image != null) {
                    //创建一个帧-->图片的转换器
                    try (Java2DFrameConverter converter = new Java2DFrameConverter()){
                        BufferedImage image = converter.getBufferedImage(frame);
                        images.add(image);
                        ff.setVideoFrameNumber((int) (ff.getFrameNumber() + interval * frameRate));
                        i++;
                        ProgressBarUtil.print();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return images;
    }

    /**
     * 跳过指定秒
     * @param frameRate 帧率
     * @param skipTime 需要跳过多久，单位秒
     * @param ff
     * @return 跳过的帧数
     */
    private static int skipTime(double frameRate, int skipTime, FFmpegFrameGrabber ff){
        int skipFrame = (int) (skipTime * frameRate);
        int result = skipFrame;
        while (skipFrame-- > 0){
            try {
                ff.grab();
            } catch (FrameGrabber.Exception e) {
                throw new RuntimeException(e);
            }
        }
        return result;
    }

    /**
     * 保存图片
     * @param bufferedImages 图片
     * @param directory 保存路径
     */
    public static void saveImages(List<BufferedImage> bufferedImages, String directory){
        File file = new File(directory);
        if (!file.exists() && !file.mkdirs()){
            System.out.println("图片目录不存在且创建失败");
            return;
        }
        for (int i = 0; i < bufferedImages.size(); i++) {
            try {
                ImageIO.write(bufferedImages.get(i), "png", new File(directory + "/" + i + ".png"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}



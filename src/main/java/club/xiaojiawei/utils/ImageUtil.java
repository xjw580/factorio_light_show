package club.xiaojiawei.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

/**
 * @author 肖嘉威
 * @date 2023/1/24 23:17
 */
public class ImageUtil {

    /**
     * 图片像素化
     * @param images
     * @param pixelSize
     * @return
     */
    public static List<BufferedImage> imagePixelation(List<BufferedImage> images, int pixelSize){
        ArrayList<BufferedImage> pixelationImages = new ArrayList<>();
        if (images != null && images.size() > 0){
            ProgressBarUtil.reset(images.size());
            for (BufferedImage image : images) {
                pixelationImages.add(pixelate(image, pixelSize));
                ProgressBarUtil.print();
            }
        }
        return pixelationImages;
    }

    private static BufferedImage pixelate(BufferedImage imageToPixelate, int pixelSize) {
        BufferedImage pixelateImage = new BufferedImage(
                imageToPixelate.getWidth(),
                imageToPixelate.getHeight(),
                imageToPixelate.getType());
        for (int y = 0; y < imageToPixelate.getHeight(); y += pixelSize) {
            for (int x = 0; x < imageToPixelate.getWidth(); x += pixelSize) {
                Color dominantColor = getDominantColor(getCroppedImage(imageToPixelate, x, y, pixelSize, pixelSize));
                int minYd = Math.min(y + pixelSize, pixelateImage.getHeight());
                int minXd = Math.min(x + pixelSize, pixelateImage.getWidth());
                for (int yd = y; yd < minYd; yd++) {
                    for (int xd = x; xd < minXd; xd++) {
                        pixelateImage.setRGB(xd, yd, dominantColor.getRGB());
                    }
                }
            }
        }

        return pixelateImage;
    }

    private static BufferedImage getCroppedImage(BufferedImage image, int startX, int startY, int width, int height) {
        if (startX < 0) {
            startX = 0;
        }else if (startX > image.getWidth()){
            startX = image.getWidth();
        }else if (startX + width > image.getWidth()){
            width = image.getWidth() - startX;
        }
        if (startY < 0) {
            startY = 0;
        }else if (startY > image.getHeight()){
            startY = image.getHeight();
        }else if (startY + height > image.getHeight()){
            height = image.getHeight() - startY;
        }
        return image.getSubimage(startX, startY, width, height);
    }

    private static Color getDominantColor(BufferedImage image) {
        Map<Integer, Integer> colorCounter = new HashMap<>(100);
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int currentRGB = image.getRGB(x, y);
                colorCounter.put(currentRGB, colorCounter.getOrDefault(currentRGB, 0) + 1);
            }
        }
        return getDominantColor(colorCounter);
    }

    private static Color getDominantColor(Map<Integer, Integer> colorCounter) {
        Optional<Map.Entry<Integer, Integer>> max = colorCounter.entrySet().stream()
                .max(Map.Entry.comparingByValue());
        int result = 0;
        if (max.isPresent()){
            result = max.get().getKey();
        }
        return new Color(result);
    }
}



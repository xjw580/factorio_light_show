package club.xiaojiawei;

import club.xiaojiawei.utils.ExcelUtil;
import club.xiaojiawei.utils.ImageUtil;
import club.xiaojiawei.utils.VideoUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static club.xiaojiawei.constant.ConfigConst.*;
import static club.xiaojiawei.enums.ExcelTypeEnum.XLSX;

/**
 * @author 肖嘉威
 * @date 2023/1/24 22:59
 */
public class GenerateExcel {
    /**
     * 读取视频抽帧然后生成Excel
     * @param imagePath
     * @param videoPath
     * @param pixelationImagePath
     * @param excelPath
     * @param pixelSize
     */
    public static void imagesToExcel(
            String imagePath,
            String videoPath,
            String pixelationImagePath,
            String excelPath,
            int pixelSize
    ){
        List<BufferedImage> bufferedImages = VideoUtil.getVideoPic(new File(videoPath), INTERVAL, START_TIME, END_TIME);
        System.out.println("视频抽帧完成");
        VideoUtil.saveImages(bufferedImages, imagePath);
        List<BufferedImage> pixelationImages = ImageUtil.imagePixelation(bufferedImages, pixelSize);
        System.out.println("图片像素化完成");
        VideoUtil.saveImages(pixelationImages, pixelationImagePath);
        ExcelUtil.pixelImageToExcel(pixelationImages, excelPath, 1, 4, pixelSize);
        System.out.println("像素图片转Excel完成");
    }

    /**
     * 创建 x * y 的Excel文件
     * @param excelCount
     * @param outPath
     */
    public static void createEmptyExcel(int excelCount, String outPath, int totalRows, int totalCols){
        for (int i = 0; i < excelCount; i++) {
            XSSFWorkbook workbook = new XSSFWorkbook();
            ExcelUtil.createSheetOfExcel(workbook, totalRows, totalCols);
            File file = new File(outPath + "/" + i + XLSX.getValue());
            try(FileOutputStream outputStreamExcel = new FileOutputStream(file)){
                workbook.write(outputStreamExcel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public static void main(String[] args) {
//        createEmptyExcel(1, EXCEL_PATH, ExcelUtil.SINGLE_REGION_ROWS, ExcelUtil.SINGLE_REGION_COLS << 1);
        imagesToExcel(IMAGE_PATH, VIDEO_PATH, PIXELATION_IMAGE_PATH, EXCEL_PATH, PIXEL_SIZE_720P);
    }
}

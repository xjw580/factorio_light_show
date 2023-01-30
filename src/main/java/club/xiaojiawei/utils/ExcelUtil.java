package club.xiaojiawei.utils;

import club.xiaojiawei.customize.BitSetPlus;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static club.xiaojiawei.constant.ConfigConst.SINGLE_REGION_COLS;
import static club.xiaojiawei.constant.ConfigConst.SINGLE_REGION_ROWS;
import static club.xiaojiawei.enums.ExcelTypeEnum.XLS;
import static club.xiaojiawei.enums.ExcelTypeEnum.XLSX;

/**
 * @author 肖嘉威
 * @date 2023/1/25 下午11:07
 */
public class ExcelUtil {


    /**
     * 读取Excel文件
     * @param file
     * @return
     */
    private static Workbook readExcel(File file){
        try(InputStream inputStream = new FileInputStream(file)){
            Workbook workbook = null;
            String fileType = FileUtil.getFileType(file.getAbsolutePath());
            if (XLS.getValue().equals(fileType)){
                workbook = new HSSFWorkbook(inputStream);
            }else if (XLSX.getValue().equals(fileType)){
                workbook = new XSSFWorkbook(inputStream);
            }
            return workbook;
        }catch (NotOfficeXmlFileException e){
            System.out.println("请关闭WPS!!!");
            throw new RuntimeException(e);
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据Excel计算二进制并转成十进制数值
     * @param excelPath Excel目录
     * @param startRow 起始读取行
     * @param startCol 起始读取列
     * @return
     */
    public static List<List<Integer>> getExcelNums(String excelPath, int startRow, int startCol){
        File excelDir = new File(excelPath);
        if (!excelDir.exists()){
            System.out.println("Excel目录不存在");
            return new ArrayList<>();
        }
        File[] files = excelDir.listFiles();
        if (files == null){
            return new ArrayList<>(0);
        }
        shellSort(files);
        List<List<Integer>> excelNums = new ArrayList<>(files.length);
        ProgressBarUtil.reset(files.length);
        for (File file : files) {
            try (Workbook workbook = readExcel(file)){
                if (workbook == null){
                    continue;
                }
                excelNums.add(calcNum(workbook, startRow, startCol));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProgressBarUtil.print();
        }
        return excelNums;
    }

    public static List<List<Integer>> getExcelNums(List<Workbook> workbooks, int startRow, int startCol){
        if (workbooks == null){
            return new ArrayList<>(0);
        }
        List<List<Integer>> excelNums = new ArrayList<>(workbooks.size());
        ProgressBarUtil.reset(workbooks.size());
        for (Workbook workbook : workbooks) {
            excelNums.add(calcNum(workbook, startRow, startCol));
            ProgressBarUtil.print();
        }
        return excelNums;
    }

    private static List<Integer> calcNum(Workbook workbook, int startRow, int startCol){
        Sheet sheet = workbook.getSheetAt(0);
        int rows = startRow + SINGLE_REGION_ROWS;
        ArrayList<Integer> result = new ArrayList<>();
        for (int r = startRow; r < rows; r++) {
            Row row = sheet.getRow(r);
            int num = 0;
            if (row != null){
                int cols = startCol + SINGLE_REGION_COLS;
                for (int c = startCol; c < cols; c++) {
                    if (isBackground(row.getCell(c))){
                        num |= (1 << c - startCol);
                    }
                }
            }
            result.add(num);
        }
        return result;
    }

    /**
     * 像素图片转换成Excel
     * @param pixelImages 像素图片
     * @param outPath Excel输出路径
     * @param startRow 像素图片的起始读取行
     * @param startCol 像素图片的起始读取列
     */
    public static void pixelImageToExcel(List<BufferedImage> pixelImages, String outPath, int startRow, int startCol, int pixelSize){
        File dir = new File(outPath);
        if (!dir.exists() && !dir.mkdirs()){
            System.out.println("输出路径不存在且创建失败");
            return;
        }
        int endRow = startRow + SINGLE_REGION_ROWS, endCol = startCol + SINGLE_REGION_COLS;
        int rowEndPixel = endRow * pixelSize, colEndPixel = endCol * pixelSize, rowStartPixel = startRow * pixelSize, colStartPixel = startCol * pixelSize;
        ProgressBarUtil.reset(pixelImages.size());
        for (int i = 0; i < pixelImages.size(); i++) {
            File file = new File(outPath + "/" + i + XLSX.getValue());
            try(FileOutputStream outputStreamExcel = new FileOutputStream(file);
                XSSFWorkbook workbook = new XSSFWorkbook()
            ){
                Sheet sheet = createSheetOfExcel(workbook, endRow - startRow, endCol - startCol);
                BufferedImage pixelImage = pixelImages.get(i);
                for (int r = rowStartPixel; r < rowEndPixel; r += pixelSize) {
                    for (int c = colStartPixel; c < colEndPixel; c += pixelSize) {
                        Row row = sheet.getRow(r / pixelSize - startRow);
                        Cell cell = row.getCell(c / pixelSize - startCol);
                        setCellStyle(workbook, cell, pixelImage.getRGB(c, r));
                    }
                }
                workbook.write(outputStreamExcel);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ProgressBarUtil.print();
        }
    }

    /**
     * 判断单元格是否有指定颜色背景
     * @param cell
     * @return
     */
    private static boolean isBackground(Cell cell){
        return cell != null && cell.getCellStyle().getFillForegroundColorColor() != null
                &&
                ((XSSFColor)(cell.getCellStyle().getFillForegroundColorColor())).getARGBHex().lastIndexOf("00B050") != -1;
    }


    /**
     * 设置单元格背景色
     * @param workbook
     * @param cell
     * @param rgb
     */
    private static void setCellStyle(XSSFWorkbook workbook, Cell cell, int rgb){
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor(new XSSFColor(new java.awt.Color(rgb)));
        cell.setCellStyle(cellStyle);
    }

    private static void setCellStyle(HSSFWorkbook workbook, Cell cell, int rgb){
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setFillForegroundColor((short) rgb);
        cell.setCellStyle(cellStyle);
    }

    private static void setCellStyle(Workbook workbook, Cell cell, int rgb){
        if (workbook instanceof XSSFWorkbook){
            setCellStyle((XSSFWorkbook) workbook, cell, rgb);
        }else {
            setCellStyle((HSSFWorkbook) workbook, cell, rgb);
        }
    }

    /**
     * 创建sheet
     * @param workbook Excel
     * @param totalRow sheet的行数
     * @param totalCol sheet的列数
     * @return
     */
    public static Sheet createSheetOfExcel(Workbook workbook, int totalRow, int totalCol){
        Sheet sheet = workbook.createSheet();
        int width = (int) (sheet.getDefaultRowHeight() * 2.8);
//        创建单元格
        for (int r = 0; r < totalRow; r++) {
            Row row = sheet.createRow(r);
            for (int c = 0; c < totalCol; c++) {
                row.createCell(c);
            }
        }
//        设置列宽
        for (int i = 0; i < totalCol; i++) {
            sheet.setColumnWidth(i, width);
        }
        return sheet;
    }

    /**
     * 希尔排序,根据文件名排序
     * @param files
     */
    private static void shellSort(File[] files) {
        for(int gap = files.length >> 1; gap > 0; gap >>= 1){
            for (int i = gap; i < files.length; i++) {
                int j = i;
                File temp = files[j];
                if (Integer.parseInt(files[j].getName().replaceAll(XLSX.getValue(), "")) < Integer.parseInt(files[j - gap].getName().replaceAll(XLSX.getValue(), ""))) {
                    while (j - gap >= 0 && Integer.parseInt(temp.getName().replaceAll(XLSX.getValue(), "")) < Integer.parseInt(files[j - gap].getName().replaceAll(XLSX.getValue(), ""))) {
                        files[j] = files[j - gap];
                        j -= gap;
                    }
                    files[j] = temp;
                }
            }
        }
    }

    /**
     * 平移Excel里的图案
     */
    public static List<Workbook> translationPattern(File excelFile, int readRows, int readCols, int amount){
        try(Workbook workbook = readExcel(excelFile)){
            BitSetPlus[] rawNums = new BitSetPlus[readRows];
            for (int i = 0; i < rawNums.length; i++) {
                rawNums[i] = new BitSetPlus();
            }
            Sheet rawSheet = workbook.getSheetAt(0);
            for (int r = 0; r < readRows; r++) {
                Row row = rawSheet.getRow(r);
                for (int c = 0; c < readCols; c++) {
                    if (isBackground(row.getCell(c))){
                        BitSetPlus bitSetPlus = new BitSetPlus();
                        bitSetPlus.set(c);
                        rawNums[r].or(bitSetPlus);
                    }
                }
            }
            BitSetPlus base = new BitSetPlus();
            base.set(0, SINGLE_REGION_COLS, true);
            int translateNum = 0, temp = 0, count = readCols + SINGLE_REGION_COLS * amount + 1;
            ArrayList<Workbook> workbooks = new ArrayList<>();
            while (count-- >= 0){
                XSSFWorkbook newWorkbook = new XSSFWorkbook();
                Sheet newSheet = createSheetOfExcel(newWorkbook, SINGLE_REGION_ROWS, SINGLE_REGION_COLS << amount - 1);
                for (int ti = 0; ti < rawNums.length; ti++) {
                    Row row = newSheet.createRow(ti);
                    for (int cc = 0; cc < amount; cc++) {
                        BitSetPlus bitSetPlus = rawNums[ti].moveLeft(temp).itemMoveRight(cc * SINGLE_REGION_COLS).itemMoveRight(translateNum).itemAnd(base);
                        for (int i = 0; i < bitSetPlus.size(); i++) {
                            Cell cell = row.createCell(i + cc * SINGLE_REGION_COLS);
                            if (bitSetPlus.get(i)){
                                setCellStyle(newWorkbook, cell, 45136);
                            }
                        }
                    }
                }
                if (++translateNum == readCols + 1){
                    translateNum = 0;
                    temp = SINGLE_REGION_COLS * amount;
                }
                workbooks.add(newWorkbook);
            }
            return workbooks;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

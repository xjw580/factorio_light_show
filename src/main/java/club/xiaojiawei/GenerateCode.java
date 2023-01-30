package club.xiaojiawei;

import club.xiaojiawei.utils.BlueprintUtil;
import club.xiaojiawei.utils.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.List;

import static club.xiaojiawei.constant.ConfigConst.*;
import static club.xiaojiawei.enums.ExcelTypeEnum.XLSX;

/**
 * @author 肖嘉威
 * @date 2023/1/26 下午5:06
 */
public class GenerateCode {


    /**
     * 将路径下的Excel文件转换成数值
     * @param path
     * @param starRow
     * @param startCol
     * @return
     */
    public static List<List<Integer>> excelToNums(String path, int starRow, int startCol){
        return ExcelUtil.getExcelNums(path, starRow, startCol);
    }

    /**
     * 以指定Excel文件为模板，通过位移获取其他Excel文件，然后将他们转换成数值
     * @param path
     * @param starRow
     * @param startCol
     * @return
     */
    public static List<List<Integer>> translationExcelToNums(String path, int starRow, int startCol, int readCols, int amount){
        List<Workbook> workbooks = ExcelUtil.translationPattern(new File(path), 16, readCols, amount);
        return ExcelUtil.getExcelNums(workbooks, starRow, startCol);
    }


    public static void main(String[] args) {
//        List<List<Integer>> excelNums = excelToNums(EXCEL_PATH, 0, 0);
        List<List<Integer>> excelNums = translationExcelToNums(EXCEL_PATH + "/" + 0 + XLSX.getValue(), 0, 26, 59, 2);

        System.out.println("Excel处理完成");
        String json = BlueprintUtil.excelNumsToJson(excelNums);
        System.out.println("Json字符串生成成功");
        String code = BlueprintUtil.jsonToCode(json);
        System.out.println("蓝图代码生成成功\n");
        System.out.println(code);
    }
}

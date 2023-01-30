package club.xiaojiawei.enums;

/**
 * @author 肖嘉威
 * @date 2023/1/28 下午1:16
 * 表格类型
 */
public enum ExcelTypeEnum {
    XLS(".xls"),
    XLSX(".xlsx")
    ;
    private final String value;
    ExcelTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

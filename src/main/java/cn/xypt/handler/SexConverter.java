package cn.xypt.handler;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

/**
 * @author zhangchao
 * @date 2024/4/17
 */
public class SexConverter implements Converter<Integer> {

    @Override
    public Class<?> supportJavaTypeKey() {
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadCellData<?> cellData, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return "男".equals(cellData.getStringValue()) ? 0 : 1;
    }

    @Override
    public WriteCellData<?> convertToExcelData(Integer value, ExcelContentProperty contentProperty,
        GlobalConfiguration globalConfiguration) {
        return new WriteCellData<>(value.equals(0) ? "男" : "女");
    }

}

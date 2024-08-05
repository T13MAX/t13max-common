package com.t13max.template.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.t13max.template.exception.TemplateException;
import com.t13max.template.gen.ExcelData;
import com.t13max.template.gen.GenerateConfig;
import com.t13max.template.gen.SheetData;
import com.t13max.template.util.Log;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author: t13max
 * @since: 16:20 2024/8/5
 */
public class TemplateReadListener implements ReadListener<LinkedHashMap<Integer, String>> {

    private final ExcelData excelData;

    public TemplateReadListener(ExcelData excelData) {
        this.excelData = excelData;
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        final String sheetName = context.readSheetHolder().getSheetName();
        if (sheetName.startsWith(GenerateConfig.ignore)) {
            return;
        }
        // 添加Sheet表数据结构
        final SheetData sheetData = excelData.getSheetDataMap().computeIfAbsent(sheetName, k -> new SheetData(excelData.getExcelName(), sheetName));
        //处理表头数据
        sheetData.processHeadSheet(headMap, context);
    }

    @Override
    public void invoke(LinkedHashMap<Integer, String> rowMap, AnalysisContext context) {
        final String sheetName = context.readSheetHolder().getSheetName();
        final SheetData sheetData = excelData.getSheetDataMap().get(sheetName);
        if (sheetData == null) {
            Log.template.error("未找到Sheet, Sheet表内容生成失败, sheetName = {}", context.readSheetHolder().getSheetName());
            return;
        }
        try {
            //处理行数据
            sheetData.processDataSheet(rowMap, context);
        } catch (TemplateException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
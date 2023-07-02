package com.atguigu.eduservice.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.excel.ExcelEntity;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exception.GuliException;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<ExcelEntity> {

    //英文listener监听器不能被spring管理 需要自己new 不能注入其他对象
    public EduSubjectService eduSubjectService;

    public  SubjectExcelListener(){    }

    public  SubjectExcelListener(EduSubjectService eduSubjectService){
        this.eduSubjectService=eduSubjectService;
    }


    @Override
    public void invoke(ExcelEntity excelEntity, AnalysisContext analysisContext) {
        if (excelEntity==null){
            throw new GuliException(20001,"文件数据为空");
        }
    }
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

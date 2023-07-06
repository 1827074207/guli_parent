package com.atguigu.eduservice.listener;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelEntity;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exception.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

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
        EduSubject eduOneSubject=this.existOneSubject(eduSubjectService,excelEntity.getOneSubjectName());
        if (eduOneSubject==null){
            eduOneSubject=new EduSubject();
            eduOneSubject.setParentId("0");
            eduOneSubject.setTitle(excelEntity.getOneSubjectName());
            eduSubjectService.save(eduOneSubject);
        }

        //二级分类
        String pid=eduOneSubject.getId();
        EduSubject eduTwoSubject=this.existTwoSubject(eduSubjectService,excelEntity.getTwoSubjectName(),pid);
        if (eduTwoSubject==null){
            eduTwoSubject=new EduSubject();
            eduTwoSubject.setParentId(pid);
            eduTwoSubject.setTitle(excelEntity.getTwoSubjectName());
            eduSubjectService.save(eduTwoSubject);
        }
    }
    //判断一级分类不能重复添加
    private EduSubject existOneSubject(EduSubjectService eduSubjectService,String name){
        QueryWrapper<EduSubject> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id","0");
        EduSubject one = eduSubjectService.getOne(queryWrapper);
        return one;
    }
    private EduSubject existTwoSubject(EduSubjectService eduSubjectService,String name,String pid){
        QueryWrapper<EduSubject> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("title",name);
        queryWrapper.eq("parent_id",pid);
        EduSubject Two= eduSubjectService.getOne(queryWrapper);
        return Two;
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {

    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}

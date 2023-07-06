package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.ExcelEntity;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2023-07-02
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectServices) {
        try {
            InputStream inputStream=file.getInputStream();

            EasyExcel.read(inputStream, ExcelEntity.class,new SubjectExcelListener(eduSubjectServices)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //课程分类列表
    @Override
    public List<OneSubject> getAllOneSubject() {
        QueryWrapper<EduSubject> wrapperOne=new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> OneeduSubjects = baseMapper.selectList(wrapperOne);


        QueryWrapper<EduSubject> wrapperTwo=new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> TwoeduSubjects = baseMapper.selectList(wrapperTwo);


        List<OneSubject> finaSubject=new ArrayList<>();

        for (int i = 0; i < OneeduSubjects.size(); i++) {
            EduSubject eduSubject=OneeduSubjects.get(i);
            OneSubject oneSubject=new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);
            finaSubject.add(oneSubject);

            List<TwoSubject> twoFinaSubjects=new ArrayList<>();

            for (int j = 0; j < TwoeduSubjects.size(); j++) {
                EduSubject tsubject=TwoeduSubjects.get(j);
                if (tsubject.getParentId().equals(eduSubject.getId())){
                    TwoSubject twoSubject=new TwoSubject();
                    BeanUtils.copyProperties(tsubject,twoSubject);
                    twoFinaSubjects.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoFinaSubjects);
        }

        return finaSubject;
    }
}

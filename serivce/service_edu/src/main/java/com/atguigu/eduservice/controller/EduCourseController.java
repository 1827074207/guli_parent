package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.vo.CourseInfoVo;
import com.atguigu.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-07-12
 */
@RestController
@RequestMapping("/eduservice/edu-course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("addCourse")
    public R addCourse(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.addCourse(courseInfoVo);
        return R.ok();
    }
}


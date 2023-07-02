package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.entity.vo.TeacherQuery;
import com.atguigu.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2023-06-09
 */
@Api(tags = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {
    //把service做封装
    @Autowired
    private EduTeacherService eduTeacherService;

    @GetMapping("findAll")
    public R findTeacher(){
        List<EduTeacher> list = eduTeacherService.list(null);
        if(list.size()>0){
            return R.ok().data("items",list);
        }else{
            return R.error().message("无数据");
        }
    }

    @DeleteMapping("{id}")
    public R deleteTeacher(@PathVariable String id) {
        EduTeacher edu = eduTeacherService.getById(id);
        if (edu != null) {
            boolean b = eduTeacherService.removeById(id);
            if (b) {
                return R.ok();
            } else {
                return R.error();
            }
        } else {
            return R.error().message("无数据");
        }
    }

    //current 是当前页
    //limit 是每页多少条数据
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageTeacher(@PathVariable long current,
                         @PathVariable long limit){
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        // 所有数据都会进入pageTeacher 后面是条件 page方法就是puls封装的分页方法
        eduTeacherService.page(pageTeacher,null);
        //总记录数
        long total = pageTeacher.getTotal();
        //全部数据
        List<EduTeacher> records = pageTeacher.getRecords();
//        return R.ok().data("total",total).data("items",records);
        //可以传对象也可以像上面一样传值
        return R.ok().data("rows",pageTeacher);
    }

    //条件查询分页 逻辑代码写在service中
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //@RequestBody 是传输json数据 required是设置数据可以全部为空
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        QueryWrapper wrapper = eduTeacherService.findTeacher(teacherQuery);
        //排序 把创建时间的最新排在最前面
        wrapper.orderByDesc("gmt_create");
        // 所有数据都会进入pageTeacher 后面是条件 page方法就是puls封装的分页方法
        eduTeacherService.page(pageTeacher,wrapper);
        //总记录数
        long total = pageTeacher.getTotal();
        //全部数据
        List<EduTeacher> records = pageTeacher.getRecords();
       return R.ok().data("total",total).data("items",records);
        //可以传对象也可以像上面一样传值
        //return R.ok().data("rows",pageTeacher);
    }






    //逻辑代码应该写在service中
    @PostMapping("pageTeacherCondition2/{current}/{limit}")
    public R pageTeacherCondition2(@PathVariable long current,
                                  @PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        //@RequestBody 是传输json数据 required是设置数据可以全部为空
        Page<EduTeacher> pageTeacher=new Page<>(current,limit);
        //querywraaper 是条件查询 new个对象来设置条件
        QueryWrapper<EduTeacher> wrapper=new QueryWrapper();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        if (!StringUtils.isEmpty(name)){
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_create",end);
        }
        // 所有数据都会进入pageTeacher 后面是条件 page方法就是puls封装的分页方法
        eduTeacherService.page(pageTeacher,wrapper);
        //总记录数
        long total = pageTeacher.getTotal();
        //全部数据
        List<EduTeacher> records = pageTeacher.getRecords();
       return R.ok().data("total",total).data("items",records);
        //可以传对象也可以像上面一样传值
//        return R.ok().data("rows",pageTeacher);
    }

    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody EduTeacher eduTeacher){
//        boolean flge=eduTeacherService.save(eduTeacher);
//        if (flge){
//            return R.ok();
//        }else {
//            return R.error();
//        }
        return eduTeacherService.save(eduTeacher) ? R.ok() : R.error();
    }

    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable String id){
        EduTeacher eduTeacher = eduTeacherService.getById(id);
        if (eduTeacher!=null){
            return R.ok().data("Teacher",eduTeacher);
        }else{
            return R.error().message("查无此人");
        }
    }

    @PostMapping("updateTeacher")
//    @PutMapping("updateTeacher/{id}")
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }
    }
}


package com.atguigu.eduservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("eduservice/user")
@CrossOrigin
public class EduLoginController {

    private EduTeacherService eduTeacherService;
    @PostMapping("login")
    public R Login(){
        return R.ok().data("token","admin");
    }


    @GetMapping("info")
    public R Info(){

        return R.ok().data("roles","[admin]").data("name","admin").data("avatar","http://a.hiphotos.baidu.com/image/pic/item/e824b899a9014c087eb617650e7b02087af4f464.jpg");
    }
}

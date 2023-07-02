package com.guigu.oss.controller;


import com.atguigu.commonutils.R;
import com.guigu.oss.service.OssService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.awt.font.MultipleMaster;

@Api(tags = "上传管理")
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {

    @Autowired
    private OssService ossService;


    @PostMapping("uploadOssFile")
    public R uploadOssFile(MultipartFile file){
        String url=ossService.uploadFileAvatar(file);
        return R.ok().data("url",url);
    }

}
